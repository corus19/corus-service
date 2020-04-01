package com.coronatracker.service;

import com.coronatracker.db.model.ContactDetails;
import com.coronatracker.db.model.CovidStatus;
import com.coronatracker.db.model.User;
import com.coronatracker.db.repository.UserRepository;
import com.coronatracker.dto.BulkContactDetailsDTO;
import com.coronatracker.dto.FacebookUserDetailsDTO;
import com.coronatracker.dto.LoginDTO;
import com.coronatracker.dto.UserDetailsDTO;
import com.coronatracker.exceptions.BulkInsertionFailureException;
import com.coronatracker.exceptions.EntityNotFoundException;
import com.coronatracker.util.Util;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sampath Katari on 26/03/20.
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FacebookService facebookService;

    @Autowired
    private ContactDetailsService contactDetailsService;

    public User createUserWithBluetoothSignatureIfNotExists(final String bluetoothSignature) {
        final Optional<User> userByBluetoothSignature = userRepository.findByBluetoothSignature(bluetoothSignature);
        //For now else condition won't be executed because we are already sending the bluetooth signature of user who is already registered with us.
        //Just keeping the logic for future purpose if needed. Will be removed later on.
        return userByBluetoothSignature.orElseGet(() -> userRepository.save(User.builder()
                .bluetoothSignature(bluetoothSignature)
                .covidContactStatus(CovidStatus.NEGATIVE)
                .build()));
    }

    public User getById(final String userId) {
        final Optional<User> byId = userRepository.findById(userId);
        if(byId.isPresent()) {
            return byId.get();
        }
        throw new EntityNotFoundException("User not found for the given id");
    }

    public List<User> getById(final List<String> userIds) {
        Iterable<User> users = userRepository.findAllById(userIds);

        return Lists.newArrayList(users);
    }

    public User createUser(final LoginDTO loginDTO) {
        final FacebookUserDetailsDTO facebookUserDetailsDTO = facebookService.verifyWithFacebook(loginDTO.getFbToken());
        final Optional<User> userByEmail = userRepository.findByEmail(facebookUserDetailsDTO.getEmail());
        //User already exists in the db with this email
        if(userByEmail.isPresent()) {
            log.info("User already exits for the given bluetooth signature.");
            final User user = userByEmail.get();
            user.setFbAuthToken(loginDTO.getFbToken());
            user.setName(facebookUserDetailsDTO.getName());
            user.setEmail(facebookUserDetailsDTO.getEmail());
            return userRepository.save(user);
        }
        //Create a new user record
        log.info("Creating a new user record");
        return userRepository.save(User.builder()
                .name(facebookUserDetailsDTO.getName())
                .email(facebookUserDetailsDTO.getEmail())
                .fbAuthToken(loginDTO.getFbToken())
                .covidContactStatus(CovidStatus.NEGATIVE)
                .bluetoothSignature(UUID.randomUUID().toString())
                .build());
    }

    public UserDetailsDTO getUser(final String userId) {
        return UserDetailsDTO.fromUser(getById(userId));
    }

    public UserDetailsDTO updateUserInfo(final String userId, final UserDetailsDTO userDetailsDTO) {
        final User user = getById(userId);
        user.setDeviceId(Util.getUpdatedValue(user.getDeviceId(), userDetailsDTO.getDeviceId()));
        user.setCountry(Util.getUpdatedValue(user.getCountry(), userDetailsDTO.getCountry()));
        user.setOs(Util.getUpdatedValue(user.getOs(), userDetailsDTO.getOs()));
        user.setOsVersion(Util.getUpdatedValue(user.getOsVersion(), userDetailsDTO.getOsVersion()));
        user.setFcmId(Util.getUpdatedValue(user.getFcmId(), userDetailsDTO.getFcmId()));
        return UserDetailsDTO.fromUser(userRepository.save(user));
    }

    public UserDetailsDTO updateUserCovidStatusPositive(final String userId) {
        log.info("Updating covid status to positive for user" + userId);
        final User user = getById(userId);
        user.setCovidContactStatus(CovidStatus.POSITIVE);
        return UserDetailsDTO.fromUser(userRepository.save(user));
    }

    public void bulkContactDetails(final String userId, final BulkContactDetailsDTO bulkContactDetailsDTO) {
        try {
            final User fromUser = getById(userId);
            final List<ContactDetails> contactDetailsList = bulkContactDetailsDTO.getDeviceRequests().stream().map(contactDetailsDTO -> {
                final User toUser = createUserWithBluetoothSignatureIfNotExists(contactDetailsDTO.getBluetoothSignature());
                return ContactDetails.builder()
                        .fromUserId(fromUser)
                        .toUserId(toUser)
                        .latitude(contactDetailsDTO.getLat())
                        .longitude(contactDetailsDTO.getLng())
                        .dateTime(contactDetailsDTO.getTimestamp())
                        .rssi(contactDetailsDTO.getRssi())
                        .txPower(contactDetailsDTO.getTxPower())
                        .build();
            })
                    //Condition to make sure we do not insert the same record again
                    .filter(contactDetails -> !contactDetailsService.hasUsersAlreadyRelated(contactDetails.getFromUserId(), contactDetails.getToUserId(), contactDetails.getDateTime()))
                    .collect(Collectors.toList());
            contactDetailsService.insertBulkDetails(contactDetailsList);
        } catch(final Exception ex) {
            log.error(ex.getMessage());
            throw new BulkInsertionFailureException("Bulk insertion failed. Please try again.");
        }
    }
}

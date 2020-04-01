package com.coronatracker.service;

import com.coronatracker.db.model.ContactDetails;
import com.coronatracker.db.model.User;
import com.coronatracker.db.repository.ContactDetailsRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sampath Katari on 26/03/20.
 */
@Service
public class ContactDetailsService {
    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    public void insertBulkDetails(final List<ContactDetails> contactDetailsList) {
        contactDetailsRepository.saveAll(contactDetailsList);
    }

    public boolean hasUsersAlreadyRelated(final User fromUser, final User toUser, final long dateTime) {
        final Optional<ContactDetails> byFromUserIdAndToUserId = contactDetailsRepository.findByFromUserIdAndToUserIdAndDateTime(fromUser, toUser, dateTime);
        return byFromUserIdAndToUserId.isPresent();
    }
}

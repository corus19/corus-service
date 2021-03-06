package com.coronatracker.db.repository;

import com.coronatracker.db.model.ContactDetails;
import com.coronatracker.db.model.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sampath Katari on 26/03/20.
 */
@Repository
public interface ContactDetailsRepository extends CrudRepository<ContactDetails, String>{
    Optional<ContactDetails> findByFromUserIdAndToUserIdAndDateTime(final User fromUserId, final User toUserId, final long dateTime);
}

package com.coronatracker.db.repository;

import com.coronatracker.db.model.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sampath Katari on 26/03/20.
 */
@Repository
public interface UserRepository extends CrudRepository<User, String>{
    Optional<User> findByBluetoothSignature(final String bluetoothSignature);
    Optional<User> findByEmail(final String email);
}

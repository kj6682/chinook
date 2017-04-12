package org.kj6682.hop;

import org.springframework.data.repository.CrudRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Optional;

interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByUsername(String username);

}
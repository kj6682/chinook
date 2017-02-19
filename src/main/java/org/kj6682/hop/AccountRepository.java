package org.kj6682.hop;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByUsername(String username);

}

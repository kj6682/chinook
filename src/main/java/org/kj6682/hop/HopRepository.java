package org.kj6682.hop;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * This is the JPA repository for managing Hops.
 *
 * The Hopster microservice is bound to MongoDb in order to keep thing as simple as possible.
 * In case a different base should be needed, we will provide a specific version of the service
 *
 */
interface HopRepository extends CrudRepository<Hop, Long> {

    List<Hop> findAll();

    @Query("select u from Hop u where (u.title like %?1%) or (u.author like %?1%)")
    List<Hop> searchByAuthorOrTitle(String search4me);
}
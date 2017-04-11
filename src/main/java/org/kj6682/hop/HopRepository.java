package org.kj6682.hop;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

/**
 * This is the JPA repository for managing Hops.
 *
 * The Hopster microservice is bound to MongoDb in order to keep thing as simple as possible.
 * In case a different base should be needed, we will provide a specific version of the service
 *
 */
interface HopRepository extends CrudRepository<Hop, Long> {

    List<Hop> findAll();

    @Query("select u from Hop u where (lower(u.title) like %:search4me%) or (lower(u.author) like %:search4me%)")
    List<Hop> searchByAuthorOrTitle(@Param("search4me") String search4me);


}
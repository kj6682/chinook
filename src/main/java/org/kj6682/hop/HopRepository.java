package org.kj6682.hop;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

/**
 * This is the JPA repository for managing Hops.
 *
 * The Hopster microservice is bound to MongoDb in order to keep thing as simple as possible.
 * In case a different base should be needed, we will provide a specific version of the service
 *
 * In order to adopt Java 8 Optionals and Streams it is necessary to let this class extend
 *
 * <pre><code>
 *     org.springframework.data.repository.Repository
 * </code></pre>
 *
 * instead of
 *
 * <pre><code>
 *     org.springframework.data.repository.PagingAndSortingRepository
 * </code></pre>
 *
 * It seems working quite as well, but I had to add the following methods in the interface
 *
 * <pre><code>
 *
 *     Page<Hop> findAll(Pageable pageable)
 *
 *     Optional<Hop> findOne(Long id)
 *
 *     Hop save(Hop hop)
 * </code></pre>
 *
 * Spring Data generates the implementation with no problems.
 * To have a quick start on Optional read this
 *
 * http://blog.jhades.org/java-8-how-to-use-optional/
 *
 */
interface HopRepository extends Repository<Hop, Long> {

    List<Hop> findAll();

    Page<Hop> findAll(Pageable pageable);

    Optional<Hop> findById(Long id);

    Hop save(Hop hop);

    void delete(Long id);

    @Query("select u from Hop u where (lower(u.title) like %:search4me%) or (lower(u.author) like %:search4me%)")
    List<Hop> searchByAuthorOrTitle(@Param("search4me") String search4me);


}
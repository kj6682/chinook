package org.kj6682.hop;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by luigi on 11/04/2017.
 * <p>
 * This is a test class for entities Hops and their repository
 * <p>
 * We define herewith the contract that should be accomplished by the method
 * <p>
 * findAll
 * <p>
 * <p>
 * No further documentation should be necessary as reading though this test class
 * everything is explained
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class HopRepositoryfindAllTest {

    private final static Logger log = Logger.getLogger(HopRepositoryfindAllTest.class.getName());

    public static final int MAX_ITEMS = 3;
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HopRepository repository;

    @Before
    public void setup() {
        this.entityManager.persist(new Hop("das urteil", "kafka", "book", "nowhere"));
        this.entityManager.persist(new Hop("kafka", "pietro citati", "book", "nowhere"));
        this.entityManager.persist(new Hop("Amerika", "KAFKA", "book", "nowhere"));
    }

    @Test
    public void should_return_the_full_list_of_hops() throws Exception {

        List<Hop> hops = this.repository.findAll();

        assertThat(hops.size()).isEqualTo(MAX_ITEMS);

    }

}

package org.kj6682.hop;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
public class HopRepository_findOne {

    private final static Logger log = Logger.getLogger(HopRepository_findOne.class.getName());

    public static final int MAX_ITEMS = 3;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HopRepository repository;

    @Before
    public void setup() {
        for (int i = 0; i < MAX_ITEMS; i++) {
            this.entityManager.persist(new Hop("title " + i, "author " + i, "book", "nowhere"));
        }
    }


    @Test
    public void should_return_optional() throws Exception {


        Hop hop = this.repository.findOne(-1L).orElse(new Hop("unkonw", "unkonw", "unkonw", "unkonw"));

        System.out.println(hop);

    }


}
//:)
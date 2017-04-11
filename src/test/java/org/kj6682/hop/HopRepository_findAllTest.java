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

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
public class HopRepository_findAllTest {

    private final static Logger log = Logger.getLogger(HopRepository_findAllTest.class.getName());

    public static final int MAX_ITEMS = 30;
    public static final int PAGE_SIZE = 10;

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
    public void should_return_the_full_list_of_hops() throws Exception {

        List<Hop> hops = this.repository.findAll();

        assertThat(hops.size()).isEqualTo(MAX_ITEMS);

    }

    @Test
    public void should_return_one_page() throws Exception {

        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return PAGE_SIZE;
            }

            @Override
            public int getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };

        Page<Hop> page = this.repository.findAll(pageable);
        assertThat(page).isNotNull();
        assertThat(page.getSize()).isEqualTo(PAGE_SIZE);
        assertThat(page.getTotalElements()).isEqualTo(MAX_ITEMS);
        assertThat(page.getTotalPages()).isEqualTo(MAX_ITEMS / PAGE_SIZE);

        List<Integer> list = page.getContent().stream()
                .map(w -> w.getTitle().split("\\s+")) // "author X" -> ["author", "X"]
                .map(i -> Integer.valueOf(i[1]))      // "X" -> X
                .filter(z -> (z < 0) || (z > 10))     // remove all items out of range
                .collect(Collectors.toList());        // use this to verify the list if empty
        assertThat(list).isNotNull();
        assertThat(list).isEmpty();

    }
}

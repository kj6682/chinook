package org.kj6682.hop;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@DataJpaTest
public class HopRepository_largeListTest {

    private final static Logger log = Logger.getLogger(HopRepository_largeListTest.class.getName());

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
    public void shouldReturnTheFullListOfHops() throws Exception {

        List<Hop> hops = this.repository.findAll();

        assertThat(hops.size()).isEqualTo(MAX_ITEMS);

    }

    @Test
    public void shouldReturnPage1() throws Exception {

        TestPageable pageable = new TestPageable(0, PAGE_SIZE);


        Page<Hop> page = this.repository.findAll(pageable);

        assertThat(page).isNotNull();
        assertThat(page.getSize()).isEqualTo(PAGE_SIZE);
        assertThat(page.getTotalElements()).isEqualTo(MAX_ITEMS);
        assertThat(page.getTotalPages()).isEqualTo(MAX_ITEMS / PAGE_SIZE);

        verifyRange(page, 0, 10);

    }

    @Test
    public void shouldReturnPage2() throws Exception {

        TestPageable pageable = new TestPageable(1, PAGE_SIZE);

        Page<Hop> page = this.repository.findAll(pageable);
        assertThat(page).isNotNull();
        assertThat(page.getSize()).isEqualTo(PAGE_SIZE);
        assertThat(page.getTotalElements()).isEqualTo(MAX_ITEMS);
        assertThat(page.getTotalPages()).isEqualTo(MAX_ITEMS / PAGE_SIZE);

        verifyRange(page, 10, 20);
    }

    @Test
    public void shouldReturnPage3() throws Exception {

        TestPageable pageable = new TestPageable(2, PAGE_SIZE);


        Page<Hop> page = this.repository.findAll(pageable);
        assertThat(page).isNotNull();
        assertThat(page.getSize()).isEqualTo(PAGE_SIZE);
        assertThat(page.getTotalElements()).isEqualTo(MAX_ITEMS);
        assertThat(page.getTotalPages()).isEqualTo(MAX_ITEMS / PAGE_SIZE);

        verifyRange(page, 20, 30);

    }

    @Test
    public void shouldReturnPage4() throws Exception {

        TestPageable pageable = new TestPageable(3, PAGE_SIZE);

        Page<Hop> page = this.repository.findAll(pageable);
        assertThat(page).isNotNull();
        assertThat(page.getSize()).isEqualTo(PAGE_SIZE);
        assertThat(page.getTotalElements()).isEqualTo(MAX_ITEMS);
        assertThat(page.getTotalPages()).isEqualTo(MAX_ITEMS / PAGE_SIZE);

        assertThat(page).isEmpty();

    }

    @Test
    public void shouldReturnPage5of7Elements() throws Exception {

        TestPageable pageable = new TestPageable(4, 7);

        Page<Hop> page = this.repository.findAll(pageable);

        assertThat(page).isNotNull();
        assertThat(page.getSize()).isEqualTo(7);
        assertThat(page.getNumberOfElements()).isEqualTo(2);

        assertThat(page.getTotalElements()).isEqualTo(MAX_ITEMS);


    }

    @Test
    public void givenAuthorOrTitleShouldReturnPage1() throws Exception {

        TestPageable pageable = new TestPageable(0, PAGE_SIZE);


        Page<Hop> page = this.repository.searchByAuthorOrTitle("1", pageable);

        assertThat(page).isNotNull();
        assertThat(page.getNumberOfElements()).isEqualTo(10);
        assertThat(page.getSize()).isEqualTo(PAGE_SIZE);
        assertThat(page.getTotalElements()).isEqualTo(12);
        assertThat(page.getTotalPages()).isEqualTo(2);

    }

    @Test
    public void givenAuthorOrTitleShouldReturnPage2() throws Exception {

        TestPageable pageable = new TestPageable(1, PAGE_SIZE);


        Page<Hop> page = this.repository.searchByAuthorOrTitle("1", pageable);

        assertThat(page).isNotNull();
        assertThat(page.getNumberOfElements()).isEqualTo(2);
        assertThat(page.getSize()).isEqualTo(PAGE_SIZE);
        assertThat(page.getTotalElements()).isEqualTo(12);
        assertThat(page.getTotalPages()).isEqualTo(2);

    }

    private class TestPageable implements Pageable{
        private final int pageNumber;
        private final int pageSize;

        private TestPageable(int pageNumber, int pageSize){
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
        }

        @Override
        public int getPageNumber() {
            return this.pageNumber;
        }

        @Override
        public int getPageSize() {
            return this.pageSize;
        }

        @Override
        public int getOffset() {
            return getPageNumber() * getPageSize();
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
    }

    private void verifyRange(Page<Hop> page, int min, int max) {
        List<Integer> list = page.getContent().stream()
                .map(w -> w.getTitle().split("\\s+")) // "author X" -> ["author", "X"]
                .map(i -> Integer.valueOf(i[1]))      // "X" -> X
                .filter(z -> (z < min) || (z > max))     // remove all items out of range
                .collect(Collectors.toList());

        assertThat(list).isNotNull();
        assertThat(list).isEmpty();
    }
}
//:)
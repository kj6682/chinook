package org.kj6682.hop;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by luigi on 11/04/2017.
 *
 * This is a test class for entities Hops and their repository
 *
 * We define herewith the contract that should be accomplished by the method
 *
 *   searchByAuthorOrTitle
 *
 *
 * No further documentation should be necessary as reading though this test class
 * everything is explained
 *
 *
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@DataJpaTest
public class HopRepository_searchByAuthorOrTitleTest {

    public static final int MAX_ITEMS = 3;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HopRepository repository;

    @Before
    public void setup(){
        this.entityManager.persist(new Hop("das urteil", "kafka", "book", "nowhere"));
        this.entityManager.persist(new Hop("kafka", "pietro citati", "book", "nowhere"));
        this.entityManager.persist(new Hop("Amerika", "KAFKA", "book", "nowhere"));
    }

    @Test
    public void givenAuthorOrTitleShouldReturnAListOfHops() throws Exception {

        List<Hop> hops = this.repository.searchByAuthorOrTitle("kafka");

        assertThat(hops).isNotNull();

        //should return by author...
        assertThat(hops.get(0).getAuthor()).isEqualTo("kafka");

        //...or by title
        assertThat(hops.get(1).getTitle()).isEqualTo("kafka");

    }

    @Test
    public void givenLowerCaseArgumentsShouldReturnAListOfHops() throws Exception {

        List<Hop> hops = this.repository.searchByAuthorOrTitle("kafka");

        assertThat(hops).isNotNull();

        //should return lower author ...
        assertThat(hops.get(0).getAuthor()).isEqualTo("kafka");
        assertThat(hops.get(0).getTitle()).isEqualTo("das urteil");

        //..as well as upper case
        assertThat(hops.get(2).getAuthor()).isEqualTo("KAFKA");
        assertThat(hops.get(2).getTitle()).isEqualTo("Amerika");

    }

    @Test
    public void givenUpperCaseArgumentsShouldReturnAnEmptyListOfHops() throws Exception {

        List<Hop> hops = this.repository.searchByAuthorOrTitle("KAFKA");

        assertThat(hops).isNotNull();

        assertThat(hops.size()).isEqualTo(0);

    }


    @Test
    public void givenPartialArgumentsShouldReturnAListOfHops() throws Exception {

        List<Hop> hops = this.repository.searchByAuthorOrTitle("kaf");

        assertThat(hops).isNotNull();

        //should return by author...
        assertThat(hops.get(0).getAuthor()).isEqualTo("kafka");

        //...or by title
        assertThat(hops.get(1).getTitle()).isEqualTo("kafka");
    }

    @Test
    public void givenWrongMatchShouldReturnAnEmptyListOfHops() throws Exception{

        List<Hop> hops = this.repository.searchByAuthorOrTitle("Bubbles");

        assertThat(hops).isNotNull();
        assertThat(hops.size()).isEqualTo(0);
    }

    @Test
    public void givenNoArgumentsShouldReturnTheFullListOfHops() throws Exception{

        List<Hop> hops = this.repository.searchByAuthorOrTitle("");

        assertThat(hops).isNotNull();
        assertThat(hops.size()).isEqualTo(MAX_ITEMS);
    }

    /**
     * check this out to have a deeper explaination
     * http://www.thoughts-on-java.org/get-query-results-stream-hibernate-5/
     *
     * @throws Exception

     @Test
     public void should_return_a_stream_of_hops() throws Exception {

     Stream<Hop> hops = this.repository.findByAuthorOrTitle("kafka");

     assertThat(hops).isNotNull();

     //this is how I wish to use the API
     hops.map(h -> h.getTitle() + " was written by " + h.getAuthor())
     .forEach(m -> log.info(m.toString()));


     }
     */
}

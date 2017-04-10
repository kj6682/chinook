package org.kj6682.hop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by luigi on 11/04/2017.
 *
 * This is another test class for entities Hops and their repository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class HopRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HopRepository repository;

    @Test
    public void searchByAuthorOrTitle_should_return_a_list_of_hops_for_author() throws Exception {

        this.entityManager.persist(new Hop("das urteil", "kafka", "book", "nowhere"));
        this.entityManager.persist(new Hop("kafka", "pietro citati", "book", "nowhere"));

        List<Hop> hops = this.repository.searchByAuthorOrTitle("kafka");
        assertThat(hops.get(0).getAuthor()).isEqualTo("kafka");
        assertThat(hops.get(1).getTitle()).isEqualTo("kafka");

    }



}

package org.kj6682.hop;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by luigi on 10/04/2017.
 *
 * This class tests the entity HOP
 * It looks pretty useless but it is a good exercise.
 * It would have been better to define this class in a classic TDD approach to define the
 * error messages to be thrown on construction
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class HopEntityTest {

    public static final String ON_TITLE = "A reasonable title is necessary when creating a Hop";
    public static final String ON_AUTHOR = "A Hop needs an author";
    public static final String ON_TYPE = " strict type is necessary when creating a Hop";
    public static final String ON_LOCATION = "A Hop is needless without a location";


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void create_when_title_is_null_should_throw_exception() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage(ON_TITLE);
        new Hop(null, "author", "type", "location");

    }

    @Test
    public void create_when_title_is_empty_should_throw_exception() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage(ON_TITLE);
        new Hop("", "author", "type", "location");

    }

    @Test
    public void create_when_author_is_null_should_throw_exception() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage(ON_AUTHOR);
        new Hop("title", null, "type", "location");
    }

    @Test
    public void create_when_author_is_empty_should_throw_exception() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage(ON_AUTHOR);
        new Hop("title", "", "type", "location");
    }

    @Test
    public void create_when_type_is_null_should_throw_exception() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("A" + ON_TYPE);
        new Hop("title", "author", null, "location");
    }

    @Test
    public void create_when_type_is_empty_should_throw_exception() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("A" + ON_TYPE);
        new Hop("title", "author", "", "location");
    }

    @Test
    public void create_when_location_is_null_should_throw_exception() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage(ON_LOCATION);
        new Hop("title", "author", "type", null);
    }

    @Test
    public void create_when_location_is_empty_should_throw_exception() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage(ON_LOCATION);
        new Hop("title", "author", "type", "");
    }

}

package org.kj6682.hop;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by luigi on 10/04/2017.
 *
 * This class tests the entity HOP
 * It looks pretty useless but it is a good exercise.
 * It would have been better to define this class in a classic TDD approach to define the
 * error messages to be thrown on construction
 *
 * after revising this
 * https://dzone.com/articles/7-popular-unit-test-naming
 * I changed my mind on test naming convention passing
 * from method 1 (MethodName_StateUnderTest_ExpectedBehavior)
 * to 4 (features to be tested)
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)

@ActiveProfiles({"postgresql", "create"})
public class HopEntityTest {

    public static final String ON_TITLE = "A reasonable title is necessary when creating a Hop";
    public static final String ON_AUTHOR = "A Hop needs an author";
    public static final String ON_TYPE = " strict type is necessary when creating a Hop";
    public static final String ON_LOCATION = "A Hop is needless without a location";


    @Rule
    public ExpectedException thrown = ExpectedException.none();

//    @Autowired
//    private TestEntityManager entityManager;


    @Test
    public void givenTitleIsNullShouldThrowException() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage(ON_TITLE);
        new Hop(null, "author", "type", "location");

    }

    @Test
    public void givenTitleIsEmptyShouldThrowException() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage(ON_TITLE);
        new Hop("", "author", "type", "location");

    }

    @Test
    public void givenAuthorIsNullShouldThrowException() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage(ON_AUTHOR);
        new Hop("title", null, "type", "location");
    }

    @Test
    public void givenAuthorIsEmptyShouldThrowException() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage(ON_AUTHOR);
        new Hop("title", "", "type", "location");
    }

    @Test
    public void givenTypeIsNullShouldThrowException() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("A" + ON_TYPE);
        new Hop("title", "author", null, "location");
    }

    @Test
    public void givenTypeIsEmptyShouldThrowException() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("A" + ON_TYPE);
        new Hop("title", "author", "", "location");
    }

    @Test
    public void givenLocationIsNullShouldThrowException() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage(ON_LOCATION);
        new Hop("title", "author", "type", null);
    }

    @Test
    public void givenLocationIsEmptyShouldThrowException() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage(ON_LOCATION);
        new Hop("title", "author", "type", "");
    }

}

package org.kj6682.hop;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by luigi on 12/04/2017.
 */
public class HopServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private HopRepository hopRepository;

    private HopService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.service = new HopService();
        this.service.setHopRepository(this.hopRepository);
    }

    @Test
    public void findOne_when_id_is_null_should_throw_IllegalArgumentException() throws Exception{

        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("A reasonable id is necessary when searching for one specific Hop");

        this.service.findOne(null);
    }

    @Test
    public void findOne_when_id_is_empty_should_throw_IllegalArgumentException() throws Exception{

    }

    @Test
    public void findOne_when_id_is_not_matching_should_return_Optional() throws Exception{

    }

}

package org.kj6682.hop;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kj6682.hop.Hop.*;
import static org.kj6682.hop.Hop.UNKNOWN_LOCATION;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;

/**
 * Created by luigi on 12/04/2017.
 *
 * This class is important because it defines the contract of HopService.
 * That is the way we want to use the methods of the HopService.
 *
 * If we define this class before the implementation of the HopService, we are granted to have a better desing ;)
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
    public void findOne_when_id_is_not_matching_should_return_dummy_Hop() throws Exception{
        Optional<Hop> optional = Optional.ofNullable(null);
        given(this.hopRepository.findById(anyLong())).willReturn(optional);

        Hop hop = this.service.findOne(-1L);

        assertThat(hop.getTitle()).isEqualTo(UNKNOWN_TITLE);
        assertThat(hop.getAuthor()).isEqualTo(UNKNOWN_AUTHOR);
        assertThat(hop.getType()).isEqualTo(UNKNOWN_TYPE);
        assertThat(hop.getLocation()).isEqualTo(UNKNOWN_LOCATION);
    }

    @Test
    public void findOne_when_id_does_match_should_return_valid_Hop() throws Exception{
        Optional<Hop> optional = Optional.of(new  Hop("title", "author", "type", "location"));

        given(this.hopRepository.findById(anyLong())).willReturn(optional);

        Hop hop = this.service.findOne(1L);

        assertThat(hop.getTitle()).isEqualTo("title");
        assertThat(hop.getAuthor()).isEqualTo("author");
        assertThat(hop.getType()).isEqualTo("type");
        assertThat(hop.getLocation()).isEqualTo("location");
    }


}

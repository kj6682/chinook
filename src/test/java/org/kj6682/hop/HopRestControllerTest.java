package org.kj6682.hop;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

/**
 * On the controller junit test there is no much to test actually.
 *
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HopRestControllerTest {

    private static final Logger logger =
            LoggerFactory.getLogger(HopRestControllerTest.class);

    @Mock
    HopService hopService;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void teardown() {

    }

    @Test
    public void shouldFindOneElement() {

        when(hopService.findOne(anyLong())).thenReturn( new Hop("title", "author", "type", "location" ) );
        HopRestController controller = new HopRestController(hopService);

        assertNotNull(controller.get(1L));

    }



    @Test
    public void shouldFindTheListOfElements() {

        when(hopService.find(anyString(), any())).thenReturn(listHops());

        HopRestController controller = new HopRestController(hopService);

        assertNotNull(controller.find("", null));

        verify(hopService, only()).find(anyString(), any());

   }


    @Test
    public void shouldInsertOneElement(){
        HopRestController controller = new HopRestController(hopService);

        controller.create(new Hop());
        verify(hopService, only()).insertOne(anyString(), anyString(), anyString(), anyString());
        logger.info("insert_OK");

    }

    @Test
    public void shouldUpdateOneElement(){
        HopRestController controller = new HopRestController(hopService);

        controller.update(1L, "title", "author", "book", "nowhere");
        verify(hopService, atMost(1)).deleteOne(anyLong());
        verify(hopService, atMost(1)).insertOne(anyString(), anyString(), anyString(), anyString());

    }

    @Test
    public void shouldDeleteOneElement(){
        HopRestController controller = new HopRestController(hopService);

        controller.delete(1L);
        verify(hopService, only()).deleteOne(anyLong());

    }

    private List<Hop>  listHops(){
        List<Hop> hops = new LinkedList<>();
        for(int i = 0; i <10; i++){
            hops.add( new Hop("title", "author", "type", "location" ) );
        }
        return hops;
    }


}

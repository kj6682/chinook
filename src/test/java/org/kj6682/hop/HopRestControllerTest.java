package org.kj6682.hop;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
    public void findById_OK() {

        when(hopService.findById(anyLong())).thenReturn(getHop());
        HopRestController controller = new HopRestController(hopService);

        assertNotNull(controller.findById(1L));
        logger.info("findById_OK");
    }

    @Test
    public void findById_NO_RESULTS() {

        when(hopService.findById(anyLong())).thenReturn(null);
        HopRestController controller = new HopRestController(hopService);

        assertNull(controller.findById(1L));
        logger.info("findById_NO_RESULTS");

    }

    @Test
    public void find_OK() {

        when(hopService.find(anyString())).thenReturn(listHops());

        HopRestController controller = new HopRestController(hopService);

        assertNotNull(controller.find(""));

        verify(hopService, only()).find(anyString());
        verify(hopService, never()).findAll();

        logger.info("find_OK");
    }


    @Test
    public void insert(){
        HopRestController controller = new HopRestController(hopService);

        controller.create(new Hop());
        verify(hopService, only()).insertOne(anyString(), anyString(), anyString(), anyString());
        logger.info("insert_OK");

    }

    @Test
    public void update(){
        HopRestController controller = new HopRestController(hopService);

        controller.update(1L, "title", "author", "book", "nowhere");
        verify(hopService, only()).replaceOne(anyLong(), anyString(), anyString(), anyString(), anyString());
        logger.info("update_OK");

    }

    @Test
    public void delete(){
        HopRestController controller = new HopRestController(hopService);

        controller.delete(1L);
        verify(hopService, only()).delete(anyLong());
        logger.info("delete_OK");

    }

    private List<Hop>  listHops(){
        List<Hop> hops = new LinkedList<>();
        for(int i = 0; i <10; i++){
            hops.add(getHop());
        }
        return hops;
    }
    private Hop getHop() {
        Hop hop = new Hop();
        hop.setTitle("title");
        hop.setAuthor("author");
        hop.setType("type");
        hop.setLocation("location");
        return hop;
    }


}

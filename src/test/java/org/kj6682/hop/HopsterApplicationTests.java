package org.kj6682.hop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@IfProfileValue(name = "spring.active.profiles", value = "integration")
public class HopsterApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void test() {
        ResponseEntity<Hop> forEntity;
        forEntity = this.restTemplate.getForEntity(
                "/hop/{id}", Hop.class, "57c688b2085cc40bb17b7d2e");
        System.out.println(forEntity.getBody());
    }


}


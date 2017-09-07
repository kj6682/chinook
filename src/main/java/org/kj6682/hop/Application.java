package org.kj6682.hop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.stream.Stream;

/**
 * This is the main Application.
 * It embeds its configuration in order to keep things clean and simple.
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Profile({"h2", "ddl-create"})
    @Bean
    CommandLineRunner initHops(HopRepository hopRepository) {

        return (evt) -> {
            System.out.println("******  running the HopRepositoryFiller *******");
            Stream.of("1984,george orwell, book, shelf1",
                    "der prozess,franz kafka, book, shelf1",
                    "amerika,franz kafka, book, shelf1",
                    "decameron,giovanni boccaccio, book, shelf2",
                    "la divina commedia, dante alighieri, book, shelf2"
            )
                    .map(x -> x.split(","))
                    .forEach(tuple -> hopRepository.save(new Hop(tuple[0], tuple[1], tuple[2], tuple[3])));
        };
    }
}

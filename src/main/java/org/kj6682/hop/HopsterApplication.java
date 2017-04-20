package org.kj6682.hop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.stream.Stream;

/**
 * This is the main Application.
 * It embeds its configuration in order to keep things clean and simple.
 */

@SpringBootApplication
public class HopsterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HopsterApplication.class, args);
    }

    @Profile({"create", "h2"})
    @Component
    static class AccountRepositoryFiller implements CommandLineRunner {

        private final AccountRepository accountRepository;


        @Autowired
        public AccountRepositoryFiller(AccountRepository accountRepository) {
            this.accountRepository = accountRepository;
        }

        @Override
        public void run(String... args) throws Exception {
            System.out.println("******  running the SapleDataCLR *******");
            Stream.of("john,lennon", "paul,mccarntey", "george,harrison", "ringo,starr")
                    .map(x -> x.split(","))
                    .forEach(tuple -> accountRepository.save(new Account(tuple[0], tuple[1], true)));
        }
    }

    @Profile({"h2"})
    @Component
    static class HopRepositoryFiller implements CommandLineRunner {

        private final HopRepository hopRepository;

        @Autowired
        public HopRepositoryFiller(HopRepository hopRepository) {
            this.hopRepository = hopRepository;
        }

        @Override
        public void run(String... args) throws Exception {
            System.out.println("******  running the SapleDataCLR *******");
            Stream.of("1984,george orwell, book, shelf1",
                    "der prozess,franz kafka, book, shelf1",
                    "amerika,franz kafka, book, shelf1",
                    "decameron,giovanni boccaccio, book, shelf2"
                    )
                    .map(x -> x.split(","))
                    .forEach(tuple -> hopRepository.save(new Hop(tuple[0], tuple[1], tuple[2], tuple[3])));
        }
    }
}

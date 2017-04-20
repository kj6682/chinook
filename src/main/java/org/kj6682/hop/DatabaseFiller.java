package org.kj6682.hop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * Created by luigi on 20/04/2017.
 */
@Configuration
public class DatabaseFiller {
    @Profile({"create", "h2"})
    @Component
    class AccountRepositoryFiller implements CommandLineRunner {

        private final AccountRepository accountRepository;


        @Autowired
        public AccountRepositoryFiller(AccountRepository accountRepository) {
            this.accountRepository = accountRepository;
        }

        @Override
        public void run(String... args) throws Exception {
            System.out.println("******  running the AccountRepositoryFiller *******");
            Stream.of("john,lennon", "paul,mccarntey", "george,harrison", "ringo,starr")
                    .map(x -> x.split(","))
                    .forEach(tuple -> accountRepository.save(new Account(tuple[0], tuple[1], true)));
        }
    }

    @Profile({"h2"})
    @Component
    class HopRepositoryFiller implements CommandLineRunner {

        private final HopRepository hopRepository;

        @Autowired
        public HopRepositoryFiller(HopRepository hopRepository) {
            this.hopRepository = hopRepository;
        }

        @Override
        public void run(String... args) throws Exception {
            System.out.println("******  running the HopRepositoryFiller *******");
            Stream.of("1984,george orwell, book, shelf1",
                    "der prozess,franz kafka, book, shelf1",
                    "amerika,franz kafka, book, shelf1",
                    "decameron,giovanni boccaccio, book, shelf2",
                    "la divina commedia, dante alighieri, book, shelf2"
            )
                    .map(x -> x.split(","))
                    .forEach(tuple -> hopRepository.save(new Hop(tuple[0], tuple[1], tuple[2], tuple[3])));
        }
    }
}

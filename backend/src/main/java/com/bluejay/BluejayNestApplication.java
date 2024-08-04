package com.bluejay;

import com.bluejay.customer.Customer;
import com.bluejay.customer.CustomerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BluejayNestApplication {
    public static void main(String[] args) {
        SpringApplication.run(BluejayNestApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {

        return args -> {
            Faker faker = new Faker();
            Name name = faker.name();
            Customer customer = new Customer(
                    name.fullName(),
                    faker.internet().safeEmailAddress(),
                    faker.phoneNumber().cellPhone(),
                    faker.random().toString()
                   );
            customerRepository.save(customer);
        };
    }

}

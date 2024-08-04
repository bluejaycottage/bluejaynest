package com.bluejay.journey;

import com.bluejay.customer.Customer;
import com.bluejay.customer.CustomerRegistrationRequest;
import com.bluejay.customer.CustomerUpdateRequest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIT {

    @Autowired
    private WebTestClient webTestClient;
    private static final String CUSTOMER_URL = "/";

    @Test
    void canRegisterACustomer() {
        //create registration request
        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress() + UUID.randomUUID();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String password = "password";
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                name, email, phoneNumber, password
        );

        //sent a post request
        webTestClient.post()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        //make sure that customer is present
        Customer expectedCustomer = new Customer(
                name, email, phoneNumber, password, "at new customer"
        );

        assertThat(allCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedCustomer);

        assert allCustomers != null;
        Long id = allCustomers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        expectedCustomer.setId(id);

        //get customer by id
        webTestClient.get()
                .uri(CUSTOMER_URL + "id/{customerId}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {
                })
                .isEqualTo(expectedCustomer);

        //get customer by email
        webTestClient.get()
                .uri(CUSTOMER_URL + "email/{customerEmail}", email)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {
                })
                .isEqualTo(expectedCustomer);

        //get customer by phone
        webTestClient.get()
                .uri(CUSTOMER_URL + "phone/{customerPhone}", phoneNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {
                })
                .isEqualTo(expectedCustomer);
    }

    @Test
    void canDeleteCustomerById() {
        //create registration request
        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress() + UUID.randomUUID();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String password = "password";
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                name, email, phoneNumber, password
        );

        //sent a post request
        webTestClient.post()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        assert allCustomers != null;
        Long id = allCustomers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //delete customer by id
        webTestClient.get()
                .uri(CUSTOMER_URL + "delete/{customerId}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        //get customer by id
        webTestClient.get()
                .uri(CUSTOMER_URL + "id/{customerId}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateCustomerById() {
        //create registration request
        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress() + UUID.randomUUID();
        String phoneNumber = faker.phoneNumber().phoneNumber();
        String password = "password";
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                name, email, phoneNumber, password
        );

        //sent a post request
        webTestClient.post()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        assert allCustomers != null;
        Long id = allCustomers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //update customer by id
        String newName = "Alan";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                newName, null, null
        );
        webTestClient.put()
                .uri(CUSTOMER_URL + "update/{customerId}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get customer by id
        Customer updatedCustomer = webTestClient.get()
                .uri(CUSTOMER_URL + "id/{customerId}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Customer.class)
                .returnResult()
                .getResponseBody();
        Customer expectedCustomer = new Customer(
                id, newName, email, phoneNumber, password, "at new customer"
        );
        assert updatedCustomer != null;
        assertThat(updatedCustomer).isEqualTo(expectedCustomer);
    }
}

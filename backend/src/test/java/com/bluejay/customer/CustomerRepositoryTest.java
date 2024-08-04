package com.bluejay.customer;

import com.bluejay.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestcontainers {
    @Autowired
    private CustomerRepository underTest;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findByEmail() {
        //Given
        String email = FAKER.internet().safeEmailAddress();
        String name = FAKER.name().fullName();
        String phone = FAKER.phoneNumber().cellPhone();
        String password = "password";
        String at = "at now";
        Customer customer = new Customer(
                name,
                email,
                phone,
                password,
                at
        );
        underTest.save(customer);
        Long id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(RuntimeException::new);

        //When
        Optional<Customer> actual = underTest.findByEmail(email);

        //Then
        assertTrue(actual.isPresent());
        assertEquals(id, actual.get().getId());
        assertEquals(customer.getName(), actual.get().getName());
        assertEquals(customer.getEmail(), actual.get().getEmail());
        assertEquals(customer.getPhone(), actual.get().getPhone());
        assertEquals(customer.getPassword(), actual.get().getPassword());
        assertEquals(customer.getAt(), actual.get().getAt());
    }

    @Test
    void findByPhone() {
        //Given
        String email = FAKER.internet().safeEmailAddress();
        String name = FAKER.name().fullName();
        String phone = FAKER.phoneNumber().cellPhone();
        String password = "password";
        String at = "at now";
        Customer customer = new Customer(
                name,
                email,
                phone,
                password,
                at
        );
        underTest.save(customer);
        Long id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(RuntimeException::new);

        //When
        Optional<Customer> actual = underTest.findByPhone(phone);

        //Then
        assertTrue(actual.isPresent());
        assertEquals(id, actual.get().getId());
        assertEquals(customer.getName(), actual.get().getName());
        assertEquals(customer.getEmail(), actual.get().getEmail());
        assertEquals(customer.getPhone(), actual.get().getPhone());
        assertEquals(customer.getPassword(), actual.get().getPassword());
        assertEquals(customer.getAt(), actual.get().getAt());
    }

    @Test
    void existsByEmail() {
        //Given
        String email = FAKER.internet().safeEmailAddress();
        String name = FAKER.name().fullName();
        String phone = FAKER.phoneNumber().cellPhone();
        String password = "password";
        String at = "at now";
        Customer customer = new Customer(
                name,
                email,
                phone,
                password,
                at
        );
        underTest.save(customer);

        //When
        boolean actual = underTest.existsByEmail(email);

        //Then
        assertTrue(actual);
    }

    @Test
    void existsByEmailFailsWhenEmailDoesNotExist() {
        assertFalse(underTest.existsByEmail(FAKER.internet().safeEmailAddress()+ UUID.randomUUID().toString()));
    }
}
package com.bluejay.customer;

import com.bluejay.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void findAllCustomer() {
        //Given
        Customer customer = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().safeEmailAddress(),
                "123456790",
                "password",
                "at now"
        );
        underTest.insertCustomer(customer);
        //When
        List<Customer> actual = underTest.findAllCustomer();

        //Then
        assertNotNull(actual);
        assertFalse(actual.isEmpty());
    }

    @Test
    void findCustomerById() {
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
        underTest.insertCustomer(customer);
        Long id = underTest.findAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(RuntimeException::new);

        //When
        Optional<Customer> actual = underTest.findCustomerById(id);

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
    void willReturnEmptyWhenFindCustomerById() {
        //Given
        Long id = 0L;

        //When
        Optional<Customer> actual = underTest.findCustomerById(id);

        //Then
        assertFalse(actual.isPresent());
    }

    @Test
    void findCustomerByEmail() {
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
        underTest.insertCustomer(customer);
        Long id = underTest.findAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(RuntimeException::new);

        //When
        Optional<Customer> actual = underTest.findCustomerByEmail(email);

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
    void findCustomerByPhone() {
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
        underTest.insertCustomer(customer);
        Long id = underTest.findAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(RuntimeException::new);

        //When
        Optional<Customer> actual = underTest.findCustomerByPhone(phone);

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
    void existsCustomerByEmail() {
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
        underTest.insertCustomer(customer);

        //When
        boolean actual = underTest.existsCustomerByEmail(email);

        //Then
        assertTrue(actual);
    }

    @Test
    void existsCustomerByEmailWillReturnFalseWhenDoesNotExists() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        //Then
        assertFalse(underTest.existsCustomerByEmail(email));

    }

    @Test
    void existsCustomerById() {
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
        underTest.insertCustomer(customer);
        Long id = underTest.findAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(RuntimeException::new);

        //When
        boolean actual = underTest.existsCustomerById(id);

        //Then
        assertTrue(actual);
    }

    @Test
    void existsCustomerByIdWillReturnFalseWhenIdNotPresent() {
        assertFalse(underTest.existsCustomerById(-1L));
    }

    @Test
    void insertCustomer() {
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
        underTest.insertCustomer(customer);
        Long id = underTest.findAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(RuntimeException::new);

        //Then
        Optional<Customer> actual = underTest.findCustomerById(id);
        assertTrue(actual.isPresent());
    }

    @Test
    void deleteCustomerById() {
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
        underTest.insertCustomer(customer);
        Long id = underTest.findAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(RuntimeException::new);

        //When
        underTest.deleteCustomerById(id);

        //Then
        Optional<Customer> actual = underTest.findCustomerById(id);
        assertFalse(actual.isPresent());
    }

    @Test
    void updateCustomerName() {
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
        underTest.insertCustomer(customer);
        Long id = underTest.findAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(RuntimeException::new);
        String newName = "foo new name";

        //When
        customer.setId(id);
        customer.setName(newName);
        underTest.updateCustomer(customer);

        //Then
        Optional<Customer> actual = underTest.findCustomerById(id);
        assertTrue(actual.isPresent());
        assertEquals(newName, actual.get().getName());
        assertEquals(customer.getEmail(), actual.get().getEmail());
        assertEquals(customer.getPhone(), actual.get().getPhone());
        assertEquals(customer.getPassword(), actual.get().getPassword());
        assertEquals(customer.getAt(), actual.get().getAt());
    }

    @Test
    void updateCustomerEmail() {
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
        underTest.insertCustomer(customer);
        Long id = underTest.findAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(RuntimeException::new);
        String newEmail = "foo@ new email";//maybe use UUID.randomUUID().toString()
        //When
        customer.setId(id);
        customer.setEmail(newEmail);
        underTest.updateCustomer(customer);

        //Then
        Optional<Customer> actual = underTest.findCustomerById(id);
        assertTrue(actual.isPresent());
        assertEquals(customer.getName(), actual.get().getName());
        assertEquals(newEmail, actual.get().getEmail());
        assertEquals(customer.getPhone(), actual.get().getPhone());
        assertEquals(customer.getPassword(), actual.get().getPassword());
        assertEquals(customer.getAt(), actual.get().getAt());
    }

    @Test
    void updateCustomerPhone() {
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
        underTest.insertCustomer(customer);
        Long id = underTest.findAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(RuntimeException::new);
        String newPhone = "foo new phone";

        //When
        customer.setId(id);
        customer.setPhone(newPhone);
        underTest.updateCustomer(customer);

        //Then
        Optional<Customer> actual = underTest.findCustomerById(id);
        assertTrue(actual.isPresent());
        assertEquals(customer.getName(), actual.get().getName());
        assertEquals(customer.getEmail(), actual.get().getEmail());
        assertEquals(newPhone, actual.get().getPhone());
        assertEquals(customer.getPassword(), actual.get().getPassword());
        assertEquals(customer.getAt(), actual.get().getAt());
    }

    @Test
    void willUpdateAllPropertiesCustomer() {
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
        underTest.insertCustomer(customer);
        Long id = underTest.findAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(RuntimeException::new);
        String newName = "foo new name";
        String newEmail = "foofoo@ new email";
        String newPhone = "foo new phone";

        //When
        customer.setId(id);
        customer.setName(newName);
        customer.setEmail(newEmail);
        customer.setPhone(newPhone);
        underTest.updateCustomer(customer);

        //Then
        Optional<Customer> actual = underTest.findCustomerById(id);
        assertTrue(actual.isPresent());
        assertEquals(newName, actual.get().getName());
        assertEquals(newEmail, actual.get().getEmail());
        assertEquals(newPhone, actual.get().getPhone());
        assertEquals(customer.getPassword(), actual.get().getPassword());
        assertEquals(customer.getAt(), actual.get().getAt());
    }

    @Test
    void willNotUpdateWhenNothingToUpdate() {
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
        underTest.insertCustomer(customer);
        Long id = underTest.findAllCustomer()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(RuntimeException::new);

        //When
        customer.setId(id);
        underTest.updateCustomer(customer);

        //Then
        Optional<Customer> actual = underTest.findCustomerById(id);
        assertTrue(actual.isPresent());
        assertEquals(customer.getName(), actual.get().getName());
        assertEquals(customer.getEmail(), actual.get().getEmail());
        assertEquals(customer.getPhone(), actual.get().getPhone());
        assertEquals(customer.getPassword(), actual.get().getPassword());
        assertEquals(customer.getAt(), actual.get().getAt());
    }
}
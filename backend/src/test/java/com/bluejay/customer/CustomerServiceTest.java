package com.bluejay.customer;

import com.bluejay.excepton.DuplicateResourceException;
import com.bluejay.excepton.RequestValidationException;
import com.bluejay.excepton.ResourceNotFound;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDao);
    }

    @Test
    void findAllCustomers() {
        //When
        underTest.findAllCustomers();
        //Then
        verify(customerDao).findAllCustomer();
    }

    @Test
    void canFindCustomerByEmail() {
        //Given
        Faker faker = new Faker();
        String email = faker.internet().safeEmailAddress();
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().cellPhone();
        String password = "password";
        String at = "at now";
        Customer customer = new Customer(
                name,
                email,
                phone,
                password,
                at
        );
        when(customerDao.findCustomerByEmail(email)).thenReturn(Optional.of(customer));

        //When
        Customer actual = underTest.findCustomerByEmail(email);

        //Then
        assertEquals(customer, actual);
    }

    @Test
    void willThrowWhenFindCustomerByEmailReturnsNull() {
        //Given
        String email = "foo@bar.com";
        when(customerDao.findCustomerByEmail(email)).thenReturn(Optional.empty());

        //When
        //Then
        ResourceNotFound exception = assertThrows(ResourceNotFound.class, () -> underTest.findCustomerByEmail(email));
        assertEquals("customer email %s is not found".formatted(email), exception.getMessage());
    }

    @Test
    void canFindCustomerByPhone() {
        //Given
        Faker faker = new Faker();
        String email = faker.internet().safeEmailAddress();
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().cellPhone();
        String password = "password";
        String at = "at now";
        Customer customer = new Customer(
                name,
                email,
                phone,
                password,
                at
        );
        when(customerDao.findCustomerByPhone(phone)).thenReturn(Optional.of(customer));

        //When
        Customer actual = underTest.findCustomerByPhone(phone);

        //Then
        assertEquals(customer, actual);
    }

    @Test
    void willThrowWhenFindCustomerByPhoneReturnsNull() {
        //Given
        String phone = "123457890";
        when(customerDao.findCustomerByPhone(phone)).thenReturn(Optional.empty());

        //When
        //Then
        ResourceNotFound exception = assertThrows(ResourceNotFound.class, () -> underTest.findCustomerByPhone(phone));
        assertEquals("customer phone %s is not found".formatted(phone), exception.getMessage());
    }

    @Test
    void canFindCustomerById() {
        //Given
        Long id = 1L;
        Faker faker = new Faker();
        String email = faker.internet().safeEmailAddress();
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().cellPhone();
        String password = "password";
        String at = "at now";
        Customer customer = new Customer(
                name,
                email,
                phone,
                password,
                at
        );
        when(customerDao.findCustomerById(id)).thenReturn(Optional.of(customer));

        //When
        Customer actual = underTest.findCustomerById(id);

        //Then
        assertEquals(customer, actual);
    }

    @Test
    void willThrowWhenFindCustomerByIdReturnsNull() {
        //Given
        Long id = 1L;
        when(customerDao.findCustomerById(id)).thenReturn(Optional.empty());

        //When
        //Then
        ResourceNotFound exception = assertThrows(ResourceNotFound.class, () -> underTest.findCustomerById(id));
        assertEquals("customer id %s is not found".formatted(id), exception.getMessage());
    }

    @Test
    void createCustomer() {
        //Given
        String email = "foo@bar.com";

        when(customerDao.existsCustomerByEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Alex",
                email,
                "1234567890",
                "password"
        );

        //When
        underTest.createCustomer(request);
        //Then
        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).insertCustomer(captor.capture());
        Customer actual = captor.getValue();
        assertNull(actual.getId());
        assertEquals(request.name(), actual.getName());
        assertEquals(request.email(), actual.getEmail());
        assertEquals(request.phone(), actual.getPhone());
        assertEquals(request.password(), actual.getPassword());
        assertEquals("at new customer", actual.getAt());
    }

    @Test
    void willThrowWhenEmailExistsWhileCreatingACustomer() {
        //Given
        String email = "foo@bar.com";

        when(customerDao.existsCustomerByEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Alex",
                email,
                "1234567890",
                "password"
        );

        //When
        var exception = assertThrows(DuplicateResourceException.class, () -> underTest.createCustomer(request));

        //Then
        assertEquals("Customer with email %s already exists".formatted(email), exception.getMessage());
    }

    @Test
    void deleteCustomerById() {
        //Given
        Long id = 1L;
        when(customerDao.existsCustomerById(id)).thenReturn(true);

        //When
        underTest.deleteCustomerById(id);

        //Then
        verify(customerDao).deleteCustomerById(id);
    }

    @Test
    void willThrowDeleteCustomerByIdWhenCustomerDoesNotExist() {
        //Given
        Long id = 1L;
        when(customerDao.existsCustomerById(id)).thenReturn(false);

        //When
        ResourceNotFound exception = assertThrows(ResourceNotFound.class, () -> underTest.deleteCustomerById(id));
        //Then
        assertEquals("customer id %s is not found".formatted(id), exception.getMessage());


    }

    @Test
    void updateAllCustomerProperties() {
        //Given
        Long id = 1L;
        Faker faker = new Faker();
        String email = faker.internet().safeEmailAddress();
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().cellPhone();
        String password = "password";
        String at = "at now";
        Customer customer = new Customer(
                id,
                name,
                email,
                phone,
                password,
                at
        );
        when(customerDao.findCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail1 = "Alexandro@gmail.com";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "Alexandro", newEmail1, "9876543210");
        when(customerDao.existsCustomerByEmail(newEmail1)).thenReturn(false);
        //When
        underTest.updateCustomer(id, updateRequest);
        //Then
        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(captor.capture());
        Customer actual = captor.getValue();
        assertEquals(customer, actual);
        assertEquals(updateRequest.name(), actual.getName());
        assertEquals(updateRequest.email(), actual.getEmail());
        assertEquals(updateRequest.phone(), actual.getPhone());
    }

    @Test
    void updateOnlyCustomerName() {
        //Given
        Long id = 1L;
        Faker faker = new Faker();
        String email = faker.internet().safeEmailAddress();
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().cellPhone();
        String password = "password";
        String at = "at now";
        Customer customer = new Customer(
                id,
                name,
                email,
                phone,
                password,
                at
        );
        when(customerDao.findCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "Alexandro", null, null);

        //When
        underTest.updateCustomer(id, updateRequest);
        //Then
        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(captor.capture());
        Customer actual = captor.getValue();
        assertEquals(customer, actual);
        assertEquals(updateRequest.name(), actual.getName());
        assertEquals(customer.getEmail(), actual.getEmail());
        assertEquals(customer.getPhone(), actual.getPhone());
    }

    @Test
    void updateOnlyCustomerPhone() {
        //Given
        Long id = 1L;
        Faker faker = new Faker();
        String email = faker.internet().safeEmailAddress();
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().cellPhone();
        String password = "password";
        String at = "at now";
        Customer customer = new Customer(
                id,
                name,
                email,
                phone,
                password,
                at
        );
        when(customerDao.findCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, null, "9876543210");

        //When
        underTest.updateCustomer(id, updateRequest);
        //Then
        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(captor.capture());
        Customer actual = captor.getValue();
        assertEquals(customer, actual);
        assertEquals(customer.getName(), actual.getName());
        assertEquals(customer.getEmail(), actual.getEmail());
        assertEquals(updateRequest.phone(), actual.getPhone());
    }

    @Test
    void updateOnlyCustomerEmail() {
        //Given
        Long id = 1L;
        Faker faker = new Faker();
        String email = faker.internet().safeEmailAddress();
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().cellPhone();
        String password = "password";
        String at = "at now";
        Customer customer = new Customer(
                id,
                name,
                email,
                phone,
                password,
                at
        );
        when(customerDao.findCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail1 = "Alexandro@gmail.com";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, newEmail1, null);
        when(customerDao.existsCustomerByEmail(newEmail1)).thenReturn(false);
        //When
        underTest.updateCustomer(id, updateRequest);
        //Then
        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(captor.capture());
        Customer actual = captor.getValue();
        assertEquals(customer, actual);
        assertEquals(customer.getName(), actual.getName());
        assertEquals(updateRequest.email(), actual.getEmail());
        assertEquals(customer.getPhone(), actual.getPhone());
    }

    @Test
    void willThrowWhenTryingToUpdateCustomerEmailAlreadyTaken() {
        //Given
        Long id = 1L;
        Faker faker = new Faker();
        String email = faker.internet().safeEmailAddress();
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().cellPhone();
        String password = "password";
        String at = "at now";
        Customer customer = new Customer(
                id,
                name,
                email,
                phone,
                password,
                at
        );
        when(customerDao.findCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail1 = "Alexandro@gmail.com";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "Alexandro", newEmail1, "9876543210");
        when(customerDao.existsCustomerByEmail(newEmail1)).thenReturn(true);
        //When
        DuplicateResourceException duplicateResourceException = assertThrows(DuplicateResourceException.class, () -> underTest.updateCustomer(id, updateRequest));
        assertEquals("Customer with email %s already exists".formatted(customer.getEmail()), duplicateResourceException.getMessage());
        //Then
        verify(customerDao, never()).updateCustomer(any());
    }

    @Test
    void willThrowWhenCustomerUpdateHasNoChanges() {
        //Given
        Long id = 1L;
        Faker faker = new Faker();
        String email = faker.internet().safeEmailAddress();
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().cellPhone();
        String password = "password";
        String at = "at now";
        Customer customer = new Customer(
                id,
                name,
                email,
                phone,
                password,
                at
        );
        when(customerDao.findCustomerById(id)).thenReturn(Optional.of(customer));
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                name, email, phone);

        //When
        RequestValidationException requestValidationException =
                assertThrows(RequestValidationException.class, () -> underTest.updateCustomer(id, updateRequest));
        assertEquals("Customer with Id %s not update".formatted(customer.getId()), requestValidationException.getMessage());
        //Then
        verify(customerDao, never()).updateCustomer(any());
    }

}
package com.bluejay.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
//      autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
//        autoCloseable.close();
    }

    @Test
    void findAllCustomer() {
        //When
        underTest.findAllCustomer();
        //Then
        verify(customerRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findCustomerById() {

        //When
        underTest.findCustomerById(Mockito.anyLong());
        //Then
        verify(customerRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    void findCustomerByEmail() {
        //When
        underTest.findCustomerByEmail(Mockito.anyString());
        //Then
        verify(customerRepository, Mockito.times(1)).findByEmail(Mockito.anyString());
    }

    @Test
    void findCustomerByPhone() {
        //When
        underTest.findCustomerByPhone(Mockito.anyString());
        //Then
        verify(customerRepository, Mockito.times(1)).findByPhone(Mockito.anyString());
    }

    @Test
    void insertCustomer() {
        //When
        underTest.insertCustomer(Mockito.any());
        //Then
        verify(customerRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void existsCustomerByEmail() {
        //When
        underTest.existsCustomerByEmail(Mockito.anyString());
        //Then
        verify(customerRepository, Mockito.times(1)).existsByEmail(Mockito.anyString());
    }

    @Test
    void existsCustomerById() {
        //When
        underTest.existsCustomerById(Mockito.anyLong());
        //Then
        verify(customerRepository, Mockito.times(1)).existsById(Mockito.anyLong());
    }

    @Test
    void deleteCustomerById() {
        //When
        underTest.deleteCustomerById(Mockito.anyLong());
        //Then
        verify(customerRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    void updateCustomer() {
        //When
        underTest.updateCustomer(Mockito.any());
        //Then
        verify(customerRepository, Mockito.times(1)).save(Mockito.any());
    }
}
package com.bluejay.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRowMapperTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void mapRow() throws SQLException {
        //Given
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("John");
        when(resultSet.getString("email")).thenReturn("john.doe@gmail.com");
        when(resultSet.getString("phone")).thenReturn("1234567890");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("at")).thenReturn("at now");

        //When
        Customer customer = customerRowMapper.mapRow(resultSet, 1);

        //Then
        Customer expectedCustomer = new Customer(
                1L,
                "John",
                "john.doe@gmail.com",
                "1234567890",
                "password",
                "at now"
        );
        assertEquals(expectedCustomer, customer);


    }
}
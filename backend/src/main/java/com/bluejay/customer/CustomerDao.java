package com.bluejay.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> findAllCustomer();
    Optional<Customer> findCustomerById(Long id);
    Optional<Customer> findCustomerByEmail(String email);
    Optional<Customer> findCustomerByPhone(String phone);
    boolean existsCustomerByEmail(String email);
    boolean existsCustomerById(Long id);
    void insertCustomer(Customer customer);
    void deleteCustomerById(Long id);
    void updateCustomer(Customer customerUpdate);
}

package com.bluejay.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao {
    private static final List<Customer> customers;
    static {
        customers = new ArrayList<>();
        Customer angrily = new Customer(1L, "Angrily", "angrily@gmail.com", "13908872770", "password","list now1");
        Customer alan = new Customer(2L, "Alan", "alam@gmail.com", "13008872770", "password","list now2");
        Customer bill = new Customer(3L, "Bill", "bill@gmail.com", "13908872777", "password","list now3");
        customers.add(angrily);
        customers.add(alan);
        customers.add(bill);
    }

    @Override
    public List<Customer> findAllCustomer() {
        return customers;
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Customer> findCustomerByEmail(String email) {
        return customers.stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<Customer> findCustomerByPhone(String phone) {
        return customers.stream()
                .filter(c -> c.getPhone().equals(phone))
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsCustomerByEmail(String email) {
        return customers.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsCustomerById(Long id) {
        return customers.stream()
                .anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public void deleteCustomerById(Long id) {
        customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .ifPresent(customers::remove);
    }

    @Override
    public void updateCustomer(Customer customerUpdate) {
        customers.add(customerUpdate);
    }
}

package com.bluejay.customer;

import com.bluejay.excepton.DuplicateResourceException;
import com.bluejay.excepton.ResourceNotFound;
import com.bluejay.excepton.RequestValidationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> findAllCustomers() {
        return customerDao.findAllCustomer();
    }

    public Customer findCustomerByEmail(String email) {
        return customerDao.findCustomerByEmail(email)
                .orElseThrow(() -> new ResourceNotFound(
                        "customer email %s is not found".formatted(email))
                );
    }

    public Customer findCustomerByPhone(String phone) {
        return customerDao.findCustomerByPhone(phone)
                .orElseThrow(() -> new ResourceNotFound(
                        "customer phone %s is not found".formatted(phone))
                );
    }

    public Customer findCustomerById(Long id) {
        return customerDao.findCustomerById(id)
                .orElseThrow(() -> new ResourceNotFound(
                        "customer id %s is not found".formatted(id))
                );
    }

    public void createCustomer(CustomerRegistrationRequest customerRegistartionRequest) {
        String email = customerRegistartionRequest.email();
        if (customerDao.existsCustomerByEmail(email)) {
            throw new DuplicateResourceException(
                    "Customer with email %s already exists".formatted(email)
            );
        }
        Customer customer = new Customer(
                customerRegistartionRequest.name(),
                customerRegistartionRequest.email(),
                customerRegistartionRequest.phone(),
                customerRegistartionRequest.password(),
                "at new customer"
        );
        customerDao.insertCustomer(customer);
    }

    public void deleteCustomerById(Long id) {
        if (!customerDao.existsCustomerById(id)) {
            throw new ResourceNotFound(
                    "customer id %s is not found".formatted(id)
            );
        }
        customerDao.deleteCustomerById(id);
    }

    public void updateCustomer(Long customerId,
                               CustomerUpdateRequest customerUpdate) {
        Customer customer = findCustomerById(customerId);
        boolean isChanged = false;
        if (customerUpdate.name() != null &&
                !customerUpdate.name().isEmpty() &&
                !customerUpdate.name().equals(customer.getName())) {
            customer.setName(customerUpdate.name());
            isChanged = true;
        }
        if (customerUpdate.email() != null &&
                !customerUpdate.email().isEmpty() &&
                !customerUpdate.email().equals(customer.getEmail())) {
            if (customerDao.existsCustomerByEmail(customerUpdate.email())) {
                throw new DuplicateResourceException(
                        "Customer with email %s already exists".formatted(customer.getEmail())
                );
            }
            customer.setEmail(customerUpdate.email());
            isChanged = true;
        }
        if (customerUpdate.phone() != null &&
                !customerUpdate.phone().isEmpty() &&
                !customerUpdate.phone().equals(customer.getPhone())) {
            customer.setPhone(customerUpdate.phone());
            isChanged = true;
        }
        if (!isChanged) {
            throw new RequestValidationException(
                    "Customer with Id %s not update".formatted(customer.getId()));
        }
        customerDao.updateCustomer(customer);
    }
}

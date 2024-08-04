package com.bluejay.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public List<Customer> findAllCustomers() {
        return customerService.findAllCustomers();
    }

    @GetMapping("/id/{customerId}")
    public Customer findCustomerById(@PathVariable long customerId) {
        return customerService.findCustomerById(customerId);
    }

    @GetMapping("/email/{customerEmail}")
    public Customer findCustomerByEmail(@PathVariable String customerEmail) {
        return customerService.findCustomerByEmail(customerEmail);
    }

    @GetMapping("/phone/{customerPhone}")
    public Customer findCustomerByPhone(@PathVariable String customerPhone) {
        return customerService.findCustomerByPhone(customerPhone);
    }

    @PostMapping()
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        customerService.createCustomer(request);
    }

    //  @DeleteMapping("/delete/{customerId}")
    @GetMapping("/delete/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomerById(customerId);
    }

    @PutMapping("/update/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId, @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateCustomer(customerId, customerUpdateRequest);
    }
}

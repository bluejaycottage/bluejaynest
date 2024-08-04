package com.bluejay.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        String phone,
        String password
) {
}

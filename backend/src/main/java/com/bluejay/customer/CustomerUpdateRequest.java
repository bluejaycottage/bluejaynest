package com.bluejay.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        String phone
) {
}

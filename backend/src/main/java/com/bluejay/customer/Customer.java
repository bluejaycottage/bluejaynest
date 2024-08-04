package com.bluejay.customer;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "customer",
        uniqueConstraints = {@UniqueConstraint(
                name = "customer_email_key",
                columnNames = "email")}
)
public class Customer {
    @Id
    @SequenceGenerator(
            name = "customer_id_seq",
            sequenceName = "customer_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_seq"
    )
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false, unique = true)
    private String phone;
    @Column(nullable = false)
    private String password;
    @Column(nullable = true)
    private String at;

    public Customer(String name,
                    String email,
                    String phone,
                    String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Customer(Long id, String name, String email, String phone, String password, String at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.at = at;
    }

    public Customer() {
    }

    public Customer(String name, String email, String phone, String password, String at) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.at = at;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && Objects.equals(phone, customer.phone) && Objects.equals(password, customer.password) && Objects.equals(at, customer.at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phone, password, at);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", at='" + at + '\'' +
                '}';
    }
}

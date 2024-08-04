package com.bluejay.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> findAllCustomer() {
        var sql = """
                select id,name,email,phone,password,at
                from customer;
                """;
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        var sql = """
                select id,name,email,phone,password,at
                from customer
                where id=?;
                """;
        return jdbcTemplate.query(sql, customerRowMapper, id)
                .stream().findFirst();
    }

    @Override
    public Optional<Customer> findCustomerByEmail(String email) {
        var sql = """
                select id,name,email,phone,password,at
                from customer
                where email=?;
                """;
        return jdbcTemplate.query(sql, customerRowMapper, email)
                .stream().findFirst();
    }

    @Override
    public Optional<Customer> findCustomerByPhone(String phone) {
        var sql = """
                select id,name,email,phone,password,at
                from customer
                where phone=?;
                """;
        return jdbcTemplate.query(sql, customerRowMapper, phone)
                .stream().findFirst();
    }

    @Override
    public boolean existsCustomerByEmail(String email) {
        var sql = """
                select count(id)
                from customer
                where email=?;
                """;
        Integer i = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return i != null && i > 0;
    }

    @Override
    public boolean existsCustomerById(Long id) {
        var sql = """
                select count(id)
                from customer
                where id=?;
                """;
        Integer i = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return i != null && i > 0;
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                insert into customer(name,email,phone,password,at)
                values (?,?,?,?,?)
                """;
        int result = jdbcTemplate.update(sql,
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getPassword(),
                customer.getAt()
        );
        System.out.println(result);
    }

    @Override
    public void deleteCustomerById(Long id) {
        var sql = """
                 delete
                 from customer
                 where id=?;
                """;
        int result = jdbcTemplate.update(sql, id);
        System.out.println("Jdbc Deleted " + result);
    }

    @Override
    public void updateCustomer(Customer customerUpdate) {
//   ues below write replace
//        if (customerUpdate.getName() != null) {
//            var sql = """
//                     update customer
//                     set name=?
//                     where id=?;
//                    """;
//            int result = jdbcTemplate.update(sql, customerUpdate.getName(), customerUpdate.getId());
//            System.out.println("Jdbc Update name " + result);
//        }
//        if (customerUpdate.getEmail() != null) {
//            var sql = """
//                     update customer
//                     set email=?
//                     where id=?;
//                    """;
//            int result = jdbcTemplate.update(sql, customerUpdate.getEmail(), customerUpdate.getId());
//            System.out.println("Jdbc Update email " + result);
//        }
//        if (customerUpdate.getPhone() != null) {
//            var sql = """
//                     update customer
//                     set phone=?
//                     where id=?;
//                    """;
//            int result = jdbcTemplate.update(sql, customerUpdate.getPhone(), customerUpdate.getId());
//            System.out.println("Jdbc Update phone " + result);
//        }

        var sql = """
                 update customer
                 set name=?,email=?,phone=?
                 where id=?;
                """;
        int result = jdbcTemplate.update(sql,
                customerUpdate.getName(),
                customerUpdate.getEmail(),
                customerUpdate.getPhone(),
                customerUpdate.getId());
    }
}

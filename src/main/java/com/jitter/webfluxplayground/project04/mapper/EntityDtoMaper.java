package com.jitter.webfluxplayground.project04.mapper;

import com.jitter.webfluxplayground.project04.dto.CustomerDto;
import com.jitter.webfluxplayground.project04.entity.Customer;

public class EntityDtoMaper {

    public static Customer toEntity(CustomerDto dto) {
        var customer = new Customer();
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setId(dto.id());

        return customer;
    }

    public static CustomerDto toDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }
}

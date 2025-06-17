package com.jitter.webfluxplayground.project02.service;

import com.jitter.webfluxplayground.project02.dto.CustomerDto;
import com.jitter.webfluxplayground.project02.mapper.EntityDtoMaper;
import com.jitter.webfluxplayground.project02.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Flux<CustomerDto> getAllCustomers() {
        return this.customerRepository.findAll()
                                      .map(EntityDtoMaper::toDto);
    }

    public Mono<CustomerDto> getCustomerById(Integer id) {
        return this.customerRepository.findById(id)
                                      .map(EntityDtoMaper::toDto);
    }

    public Mono<CustomerDto> saveCustomer(Mono<CustomerDto> mono) {
        return mono.map(EntityDtoMaper::toEntity)
                   .flatMap(this.customerRepository::save)
                   .map(EntityDtoMaper::toDto);
    }

    public Mono<CustomerDto> updateCustomer(Integer id, Mono<CustomerDto> mono) {
        return this.customerRepository.findById(id)
                                      .flatMap(entity -> mono)
                                      .map(EntityDtoMaper::toEntity)
                                      .doOnNext(c -> c.setId(id))
                                      .flatMap(this.customerRepository::save)
                                      .map(EntityDtoMaper::toDto);
    }

    public Mono<Boolean> deleteCustomerId(Integer id) {
        return this.customerRepository.deleteByCustomerId(id);
    }

    public Flux<CustomerDto> getCustomerPage(Integer page, Integer size) {
        return this.customerRepository.findBy(PageRequest.of(page, size))
                                      .map(EntityDtoMaper::toDto);
    }
}

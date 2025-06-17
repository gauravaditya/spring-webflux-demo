package com.jitter.webfluxplayground.project03.controller;

import com.jitter.webfluxplayground.project03.dto.CustomerDto;
import com.jitter.webfluxplayground.project03.exceptions.ApplicationExceptions;
import com.jitter.webfluxplayground.project03.service.CustomerService;
import com.jitter.webfluxplayground.project03.validations.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public Flux<CustomerDto> allCustomers() {
        return this.customerService.getAllCustomers();
    }

    @GetMapping("paginated")
    public Flux<CustomerDto> allCustomersByPage(@RequestParam(defaultValue = "0") Integer page,
                                                @RequestParam(defaultValue = "3") Integer size) {
        return this.customerService.getCustomerPage(page, size);
    }
// OR
//    @GetMapping("paginated")
//    public Mono<List<CustomerDto>> allCustomersByPage(@RequestParam(defaultValue = "0") Integer page,
//                                                      @RequestParam(defaultValue = "3") Integer size) {
//        return this.customerService.getCustomerPage(page, size)
//                                   .collectList();
//    }

    @GetMapping("{id}")
    public Mono<CustomerDto> getCustomer(@PathVariable Integer id) {
        return this.customerService.getCustomerById(id)
                                   .switchIfEmpty(ApplicationExceptions.customerNotFound(id));
    }

    @PostMapping
    public Mono<CustomerDto> saveCustomer(@RequestBody Mono<CustomerDto> mono) {
        return mono.transform(RequestValidator.validate())
                   .as(this.customerService::saveCustomer);
    }

    @PutMapping("{id}")
    public Mono<CustomerDto> updateCustomer(@PathVariable Integer id, @RequestBody Mono<CustomerDto> mono) {
        return mono.transform(RequestValidator.validate())
                   .as(validated -> this.customerService.updateCustomer(id, validated))
                   .switchIfEmpty(ApplicationExceptions.customerNotFound(id));
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteCustomer(@PathVariable Integer id) {
        return this.customerService.deleteCustomerId(id)
                                   .filter(b -> b)
                                   .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
                                   .then();
    }
}

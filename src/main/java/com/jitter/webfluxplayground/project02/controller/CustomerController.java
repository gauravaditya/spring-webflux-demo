package com.jitter.webfluxplayground.project02.controller;

import com.jitter.webfluxplayground.project02.dto.CustomerDto;
import com.jitter.webfluxplayground.project02.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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

    //    @GetMapping("paginated")
//    public Flux<CustomerDto> allCustomersByPage(@RequestParam(defaultValue = "0") Integer page,
//                                                @RequestParam(defaultValue = "3") Integer size) {
//        return this.customerService.getCustomerPage(page, size);
//    }
// OR
    @GetMapping("paginated")
    public Mono<List<CustomerDto>> allCustomersByPage(@RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(defaultValue = "3") Integer size) {
        return this.customerService.getCustomerPage(page, size)
                                   .collectList();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<CustomerDto>> getCustomer(@PathVariable Integer id) {
        return this.customerService.getCustomerById(id)
                                   .map(ResponseEntity::ok)
                                   .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<CustomerDto> saveCustomer(@RequestBody Mono<CustomerDto> mono) {
        return this.customerService.saveCustomer(mono);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<CustomerDto>> updateCustomer(@PathVariable Integer id, @RequestBody Mono<CustomerDto> mono) {
        return this.customerService.updateCustomer(id, mono)
                                   .map(ResponseEntity::ok)
                                   .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable Integer id) {
        return this.customerService.deleteCustomerId(id)
                                   .filter(b -> b)
                                   .map(b -> ResponseEntity.ok().<Void>build())
                                   .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}

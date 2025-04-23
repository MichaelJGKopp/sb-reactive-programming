package io.michaeljgkopp.github.sbwebfluxdemo.controller;

import io.michaeljgkopp.github.sbwebfluxdemo.dto.Customer;
import io.michaeljgkopp.github.sbwebfluxdemo.service.CustomerService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/customers")
public class CustomerController {
  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping
  public List<Customer> getAllCustomers() {
    return customerService.loadAllCustomers();
  }

}

package io.michaeljgkopp.github.sbwebfluxdemo.handler;

import io.michaeljgkopp.github.sbwebfluxdemo.dao.CustomerDao;
import io.michaeljgkopp.github.sbwebfluxdemo.dto.Customer;
import java.net.URI;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

  private final CustomerDao customerDao;

  public CustomerHandler(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  public Mono<ServerResponse> loadCustomers(ServerRequest request) {
    Flux<Customer> customerList = customerDao.getCustomerList();
    return ServerResponse.ok()
        .body(customerList, Customer.class);
  }

  public Mono<ServerResponse> findCustomer(ServerRequest request) {
    int customerId = Integer.parseInt(request.pathVariable("input"));
//    Mono<Customer> customerMono = customerDao.getCustomerList().filter(c -> c.id() == customerId)
//    .single();
    Mono<Customer> customerMono = customerDao.getCustomerList().filter(c -> c.id() == customerId)
        .next();

    return ServerResponse.ok()
        .body(customerMono, Customer.class);
  }

  public Mono<ServerResponse> saveCustomer(ServerRequest serverRequest) {
    Mono<Customer> customerMono = serverRequest.bodyToMono(Customer.class);
    return ServerResponse.created(URI.create(serverRequest.path()))
        .body(customerMono.single(), Customer.class);
  }
}

package io.michaeljgkopp.github.sbwebfluxdemo.handler;

import io.michaeljgkopp.github.sbwebfluxdemo.dao.CustomerDao;
import io.michaeljgkopp.github.sbwebfluxdemo.dto.Customer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerStreamHandler {

  private final CustomerDao customerDao;

  public CustomerStreamHandler(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  public Mono<ServerResponse> getCustomers(ServerRequest request) {
    Flux<Customer> customersStream = customerDao.getCustomersStream();
    return ServerResponse.ok()
        .contentType(MediaType.TEXT_EVENT_STREAM)
        .body(customersStream, Customer.class);
  }
}

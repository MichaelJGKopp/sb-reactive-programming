package io.michaeljgkopp.github.sbwebfluxdemo.dao;

import io.michaeljgkopp.github.sbwebfluxdemo.dto.Customer;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * This class simulates a data access object (DAO) that retrieves customer data.
 * It generates a list of customers with IDs and names.
 */
@Component
public class CustomerDao {
  private static final long DELAYS_IN_MILLIS = 1000L;
  private static final int COUNT_CUSTOMERS = 10;

    public List<Customer> getCustomers() {
      return IntStream.rangeClosed(1, COUNT_CUSTOMERS)
          .peek(i -> System.out.println("processing customer #" + i))
          .mapToObj(i -> {
            delay();
            return new Customer(i, "Customer" + i);
          })
          .collect(Collectors.toList());
    }

    public Flux<Customer> getCustomersStream() {
      return Flux.range(1, COUNT_CUSTOMERS)
          .delayElements(Duration.ofMillis(DELAYS_IN_MILLIS))
          .doOnNext(i -> System.out.println("processing customer #" + i))
          .map(i -> new Customer(i, "Customer" + i));
    }

  private static void delay() {
    try {
      Thread.sleep(DELAYS_IN_MILLIS);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}

package io.michaeljgkopp.github.sbwebfluxdemo.dao;

import io.michaeljgkopp.github.sbwebfluxdemo.dto.Customer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;

/**
 * This class simulates a data access object (DAO) that retrieves customer data.
 * It generates a list of customers with IDs and names.
 */
@Component
public class CustomerDao {

    public List<Customer> getCustomers() {
      return IntStream.rangeClosed(1, 50)
          .peek(i -> System.out.println("processing customer #" + i))
          .mapToObj(i -> {
            delay();
            return new Customer(i, "Customer" + i);
          })
          .collect(Collectors.toList());
    }

  private static void delay() {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}

package io.michaeljgkopp.github.sbwebfluxdemo.service;

import io.michaeljgkopp.github.sbwebfluxdemo.dao.CustomerDao;
import io.michaeljgkopp.github.sbwebfluxdemo.dto.Customer;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
  private CustomerDao customerDao;

  public CustomerService(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  public List<Customer> loadAllCustomers() {
    long start = System.currentTimeMillis();
    List<Customer> customers = customerDao.getCustomers();
    long end = System.currentTimeMillis();
    System.out.println("Time taken to load customers: " + (end - start) + "ms");
    return customers;
  }
}

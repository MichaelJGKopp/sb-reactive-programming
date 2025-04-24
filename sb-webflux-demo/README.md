# Spring Boot WebFlux Demo Project Explanation

This project demonstrates the difference between traditional blocking and reactive non-blocking programming in a Spring Boot application.

## Key Components

1. **Customer Record**: A simple data class that holds customer ID and name.

2. **CustomerDao**: Simulates retrieving data with artificial delays:
    - `getCustomers()`: Returns a standard Java `List` that blocks while processing each customer
    - `getCustomersStream()`: Returns a reactive `Flux` that emits customers over time without blocking

3. **CustomerService**: Service layer that measures execution time:
    - `loadAllCustomers()`: Uses the blocking approach
    - `loadAllCustomersStream()`: Uses the reactive approach

4. **CustomerController**: REST endpoints for accessing customers:
    - `/customers`: Traditional blocking endpoint - client must wait for all processing to complete
    - `/customers/stream`: Reactive endpoint using Server-Sent Events to stream data as it becomes available

## Reactive vs Traditional Approach

**Traditional (Blocking):**
- The application thread is blocked during the entire operation
- Client receives all data at once after all processing is complete
- Takes ~10 seconds total (10 customers × 1 second delay)

**Reactive (Non-blocking):**
- Processing happens asynchronously without blocking threads
- Client receives data as soon as each item is processed
- Immediate initial response, with data streaming over time
- Better resource utilization during delays

The reactive approach is particularly beneficial for I/O-bound operations (like database or network calls) as it allows the server to handle more concurrent requests with fewer threads.
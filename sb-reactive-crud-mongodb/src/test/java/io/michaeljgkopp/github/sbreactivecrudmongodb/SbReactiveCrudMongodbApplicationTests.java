package io.michaeljgkopp.github.sbreactivecrudmongodb;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import io.michaeljgkopp.github.sbreactivecrudmongodb.controller.ProductController;
import io.michaeljgkopp.github.sbreactivecrudmongodb.dto.ProductDto;
import io.michaeljgkopp.github.sbreactivecrudmongodb.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ProductController.class)
class SbReactiveCrudMongodbApplicationTests {

  @Autowired
  private WebTestClient webTestClient;
  @MockitoBean
  private ProductService productService;

  @Test
  public void addProductTest() {
    Mono<ProductDto> productDtoMono = Mono.just(
        new ProductDto("102", "Product 102", 1, 102.0));
    when(productService.createProduct(productDtoMono)).thenReturn(productDtoMono);
    webTestClient.post().uri("/products").body(Mono.just(productDtoMono), ProductDto.class)
        .exchange()
        .expectStatus().isCreated();
  }

  @Test
  public void getProductsTest() {
    // Create a Flux of ProductDto objects
    Flux<ProductDto> productDtoFlux = Flux.just(
        new ProductDto("1", "Product 1", 1, 101.0),
        new ProductDto("102", "Product 102", 2, 102.0));
    // Mock the service call
    when(productService.getAllProducts()).thenReturn(productDtoFlux);

    // Perform the GET request and assign the response
    Flux<ProductDto> responseBody = webTestClient.get().uri("/products")
        .exchange()
        .expectStatus().isOk()
        .returnResult(ProductDto.class)
        .getResponseBody();

    // Test the reactive stream (Flux) returned by the API endpoint
    StepVerifier.create(responseBody) // Creates a verification context for the Flux of ProductDto objects returned by the controller
        .expectSubscription() // Verifies that subscription to the stream happens successfully
        .expectNext(new ProductDto("1", "Product 1", 1, 101.0)) // Verifies that the first item emitted by the stream is as expected
        .expectNext(new ProductDto("102", "Product 102", 2, 102.0))
        .verifyComplete();  // Verifies that the stream completes successfully
  }

  @Test
  public void getProductsInPriceRangeTest() {
    // Create a Flux of ProductDto objects
    Flux<ProductDto> productDtoFlux = Flux.just(
        new ProductDto("1", "Product 1", 1, 101.0),
        new ProductDto("102", "Product 102", 2, 102.0));
    // Mock the service call
    when(productService.getProductsInPriceRange(100.0, 200.0)).thenReturn(productDtoFlux);

    // Perform the GET request and assign the response
    Flux<ProductDto> responseBody = webTestClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/products/range")
            .queryParam("priceMin", "100.0")
            .queryParam("priceMax", "200.0")
            .build())
        .exchange()
        .expectStatus().isOk()
        .returnResult(ProductDto.class)
        .getResponseBody();

    // Test the reactive stream (Flux) returned by the API endpoint
    StepVerifier.create(responseBody) // Creates a verification context for the Flux of ProductDto objects returned by the controller
        .expectSubscription() // Verifies that subscription to the stream happens successfully
        .expectNext(new ProductDto("1", "Product 1", 1, 101.0)) // Verifies that the first item emitted by the stream is as expected
        .expectNext(new ProductDto("102", "Product 102", 2, 102.0))
        .verifyComplete();  // Verifies that the stream completes successfully
  }

  @Test
  public void getProductTest() {
    Mono<ProductDto> productDtoMono = Mono.just(
        new ProductDto("102", "Product 102", 2, 102.0));
    when(productService.getProduct(any())).thenReturn(productDtoMono);

    Flux<ProductDto> responseBody = webTestClient.get().uri("/products/102")
        .exchange()
        .expectStatus().isOk()
        .returnResult(ProductDto.class)
        .getResponseBody();

    StepVerifier.create(responseBody)
        .expectSubscription()
        .expectNext(new ProductDto("102", "Product 102", 2, 102.0))
        .verifyComplete();
  }

  @Test
  public void updateProductTest() {
    Mono<ProductDto> productDtoMono = Mono.just(
        new ProductDto("102", "Product 102", 2, 102.0));
    when(productService.updateProduct(any())).thenReturn(productDtoMono);

    Flux<ProductDto> responseBody = webTestClient.put().uri("/products")
        .body(Mono.just(productDtoMono), ProductDto.class)
        .exchange()
        .expectStatus().isOk()
        .returnResult(ProductDto.class)
        .getResponseBody();

    StepVerifier.create(responseBody)
        .expectSubscription()
        .expectNext(new ProductDto("102", "Product 102", 2, 102.0))
        .verifyComplete();
  }

  @Test
  public void deleteProductTest() {
    // Mock the service call, using BDDMockito to specify the behavior of the mock (optional)
    given(productService.deleteProduct("102")).willReturn(Mono.empty());

    // Perform the DELETE request and assign the response
    Flux<ProductDto> responseBody = webTestClient.delete().uri("/products/102")
        .exchange()
        .expectStatus().isNoContent()
        .returnResult(ProductDto.class)
        .getResponseBody();

    // Test the reactive stream (Flux) returned by the API endpoint
    StepVerifier.create(responseBody) // Creates a verification context for the Flux of ProductDto objects returned by the controller
        .expectSubscription() // Verifies that subscription to the stream happens successfully
        .verifyComplete();  // Verifies that the stream completes successfully
  }
}

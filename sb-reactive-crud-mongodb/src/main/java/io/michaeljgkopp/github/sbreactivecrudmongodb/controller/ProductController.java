package io.michaeljgkopp.github.sbreactivecrudmongodb.controller;

import io.michaeljgkopp.github.sbreactivecrudmongodb.dto.ProductDto;
import io.michaeljgkopp.github.sbreactivecrudmongodb.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ProductDto> getAllProducts() {
    return productService.getAllProduct();
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/range", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ProductDto> getProductsBetweenRange(@RequestParam Double priceMin,
      @RequestParam Double priceMax) {
    return productService.getProductsInPriceRange(priceMin, priceMax);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{id}")
  public Mono<ProductDto> getProduct(@PathVariable String id) {
    return productService.getProduct(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Mono<ProductDto> createProduct(@RequestBody Mono<ProductDto> productDtoMono) {
    return productService.createProduct(productDtoMono);
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping
  public Mono<ProductDto> updateProduct(@RequestBody Mono<ProductDto> productDtoMono) {
    return productService.updateProduct(productDtoMono);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public Mono<Void> deleteProduct(@PathVariable String id) {
    return productService.deleteProduct(id);
  }
}

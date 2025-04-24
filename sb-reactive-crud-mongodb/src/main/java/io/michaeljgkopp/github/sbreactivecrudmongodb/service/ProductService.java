package io.michaeljgkopp.github.sbreactivecrudmongodb.service;

import io.michaeljgkopp.github.sbreactivecrudmongodb.dto.ProductDto;
import io.michaeljgkopp.github.sbreactivecrudmongodb.repository.ProductRepository;
import io.michaeljgkopp.github.sbreactivecrudmongodb.util.AppUtils;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Flux<ProductDto> getAllProduct() {
    return productRepository.findAll()
        .map(AppUtils::toProductDto);
  }

  public Mono<ProductDto> getProduct(String id) {
    return productRepository.findById(id)
        .map(AppUtils::toProductDto);
  }

  public Flux<ProductDto> getProductsInPriceRange(double priceMin, double priceMax) {
    return productRepository.findByPriceBetween(Range.closed(priceMin, priceMax))
        .map(AppUtils::toProductDto);
  }

  public Mono<ProductDto> createProduct(Mono<ProductDto> productDtoMono) {
    return productDtoMono
        .map(AppUtils::toProduct)
        .flatMap(productRepository::insert)
        .map(AppUtils::toProductDto);
  }

  public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono) {
    return productDtoMono
        .map(AppUtils::toProduct)
        .flatMap(p ->
            productRepository.existsById(p.getId())
                .flatMap(exists -> {
                  if (!exists) {
                    return Mono.error(
                        new RuntimeException("Product with id " + p.getId() + " does not exist"));
                  }
                  return productRepository.save(p);
                })
        )
        .map(AppUtils::toProductDto);
  }

  public Mono<Void> deleteProduct(String id) {
    return productRepository.deleteById(id);
  }
}

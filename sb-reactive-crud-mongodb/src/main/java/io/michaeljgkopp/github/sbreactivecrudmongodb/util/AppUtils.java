package io.michaeljgkopp.github.sbreactivecrudmongodb.util;

import io.michaeljgkopp.github.sbreactivecrudmongodb.dto.ProductDto;
import io.michaeljgkopp.github.sbreactivecrudmongodb.entity.Product;

public class AppUtils {

  public static ProductDto toProductDto(Product product) {
    return new ProductDto(
        product.getId(),
        product.getName(),
        product.getQty(),
        product.getPrice());
  }

  public static Product toProduct(ProductDto productDto) {
    return new Product(
        productDto.id(),
        productDto.name(),
        productDto.qty(),
        productDto.price());
  }
}

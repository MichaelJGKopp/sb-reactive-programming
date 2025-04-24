package io.michaeljgkopp.github.sbreactivecrudmongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {

  @Id
  private String id;
  private String name;
  private Integer qty;
  private Double price;

  public Product() {
  }

  public Product(String id, String name, Integer qty, Double price) {
    this.id = id;
    this.name = name;
    this.qty = qty;
    this.price = price;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getQty() {
    return qty;
  }

  public void setQty(Integer qty) {
    this.qty = qty;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Product{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", qty=" + qty +
        ", price=" + price +
        '}';
  }
}

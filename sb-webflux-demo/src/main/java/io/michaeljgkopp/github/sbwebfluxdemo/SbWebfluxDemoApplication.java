package io.michaeljgkopp.github.sbwebfluxdemo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
    title = "Spring Boot Webflux Demo",
    version = "1.0",
    description = "Demo application for Spring Boot Webflux"
))
@SpringBootApplication
public class SbWebfluxDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(SbWebfluxDemoApplication.class, args);
  }

}

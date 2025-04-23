package io.michaeljgkopp.github.sbwebfluxdemo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

  @Test
  public void testMono() {
    Mono<?> monoString =
        Mono.just("Hello Mono")
            .then(Mono.error(new RuntimeException("Exception occurs in Mono")))
            .log();
    monoString.subscribe(System.out::println, e -> System.out.println("Error: " + e.getMessage()));
  }

  @Test
  public void testFlux() {
    Flux<String> fluxString = Flux.just("Hello Flux", "Hello Flux 2", "Hello Flux 3",
            "Hello Flux 4")
        .concatWithValues("Hello Flux 5")
        .concatWith(Flux.error(new RuntimeException("Exception occurs in Flux")))
        .concatWithValues("Hello Flux 6")
        .log();
    fluxString.subscribe(
        System.out::println,
        e -> System.out.println("Error: " + e.getMessage()),
        () -> System.out.println("Completed"));
  }

}

package io.michaeljgkopp.github.sbwebfluxdemo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

  @Test
  public void testMono(){
    Mono<?> monoString =
        Mono.just("Hello Mono")
        .then(Mono.error(new RuntimeException("Exception occurs")))
        .log();
    monoString.subscribe(System.out::println, e -> System.out.println("Error: " + e.getMessage()));
  }

}

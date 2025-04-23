package io.michaeljgkopp.github.sbwebfluxdemo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

  @Test
  public void testMono(){
    Mono<String> monoString = Mono.just("Hello Mono").log();
    monoString.subscribe(System.out::println);
  }

}

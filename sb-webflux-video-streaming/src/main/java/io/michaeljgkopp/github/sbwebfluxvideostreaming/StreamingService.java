package io.michaeljgkopp.github.sbwebfluxvideostreaming;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StreamingService {

  private static final String FORMAT = "classpath:videos/%s.mp4";
  private final ResourceLoader resourceLoader;

  public StreamingService(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  public Mono<Resource> getVideo(String title) {
    // Simulate fetching a video resource
    return Mono.fromSupplier(() -> resourceLoader.getResource(String.format(FORMAT, title)));
  }
}

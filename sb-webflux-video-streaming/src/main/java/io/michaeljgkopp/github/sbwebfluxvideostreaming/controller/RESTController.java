package io.michaeljgkopp.github.sbwebfluxvideostreaming.controller;

import io.michaeljgkopp.github.sbwebfluxvideostreaming.StreamingService;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class RESTController {
  private final StreamingService streamingService;

  public RESTController(StreamingService streamingService) {
    this.streamingService = streamingService;
  }

  @GetMapping(value = "/video/{title}", produces = "video/mp4")
  public Mono<Resource> getVideo(@PathVariable String title, @RequestHeader("Range") String range) {
    System.out.println("range in bytes(): " + range);
    return streamingService.getVideo(title);
  }
}

package io.michaeljgkopp.github.sbsmsotptwilio.router;

import io.michaeljgkopp.github.sbsmsotptwilio.resource.TwilioOtpHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {
  private final TwilioOtpHandler twilioOTPHandler;

  @Bean
  RouterFunction<ServerResponse> routes() {
    return RouterFunctions.route()
        .path("/api/v1/twilio", builder -> builder
            .POST("/otp", twilioOTPHandler::sendOTP)
            .POST("/otp/verify", twilioOTPHandler::validateOTP))
        .build();
  }
}

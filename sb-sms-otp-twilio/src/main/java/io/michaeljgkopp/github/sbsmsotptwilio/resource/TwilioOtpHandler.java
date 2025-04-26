package io.michaeljgkopp.github.sbsmsotptwilio.resource;

import io.michaeljgkopp.github.sbsmsotptwilio.dto.PasswordResetRequestDto;
import io.michaeljgkopp.github.sbsmsotptwilio.service.TwilioOTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TwilioOtpHandler {

  private final TwilioOTPService twilioOTPService;

  public Mono<ServerResponse> sendOTP(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(PasswordResetRequestDto.class)
        .flatMap(twilioOTPService::sendOTPForPasswordReset)
        .flatMap(otpResponse -> ServerResponse.ok()
            .body(BodyInserters.fromValue(otpResponse)));
  }

  public Mono<ServerResponse> validateOTP(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(PasswordResetRequestDto.class)
        .flatMap(dto -> twilioOTPService.validateOTP(dto.getUserName(), dto.getOneTimePassword()))
        .flatMap(otpResponse -> ServerResponse.ok()
            .bodyValue(otpResponse));
  }
}

package io.michaeljgkopp.github.sbsmsotptwilio;

import com.twilio.Twilio;
import io.michaeljgkopp.github.sbsmsotptwilio.config.TwilioConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SbSmsOtpTwilioApplication {
  private final TwilioConfig twilioConfig;

  @PostConstruct
  public void initTwilio() {
    Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
  }

  public static void main(String[] args) {
    SpringApplication.run(SbSmsOtpTwilioApplication.class, args);
  }

}

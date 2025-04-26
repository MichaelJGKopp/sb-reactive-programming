package io.michaeljgkopp.github.sbsmsotptwilio.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.type.PhoneNumber;
import io.michaeljgkopp.github.sbsmsotptwilio.config.TwilioConfig;
import io.michaeljgkopp.github.sbsmsotptwilio.dto.OtpStatus;
import io.michaeljgkopp.github.sbsmsotptwilio.dto.PasswordResetRequestDto;
import io.michaeljgkopp.github.sbsmsotptwilio.dto.PasswordResetResponseDto;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TwilioOTPService {

  private final TwilioConfig twilioConfig;
  private final Map<String, String> otpMap = new HashMap<>(); // simulating in-memory database

  public Mono<PasswordResetResponseDto> sendOTPForPasswordReset(
      PasswordResetRequestDto passwordResetRequestDto) {
    PasswordResetResponseDto responseDto = null;

    try {
      // initialization of phone numbers and otp
      PhoneNumber to = new PhoneNumber(passwordResetRequestDto.getPhoneNumber());
      PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
      String otp = generateOTP();

      // the message body
      String otpMessage = String.format(
          "Hello %s, your OTP for password reset is %s. Please use this OTP to reset your "
              + "password.", passwordResetRequestDto.getUserName(), otp);

      // the actual Twilio message/Api call, sandbox: verifying your phone number in germany is an issue
//      Verification verification = Verification.creator(
//              "VA49769b9a5ee0417d1cac651c0ccdd31c",
//              passwordResetRequestDto.getPhoneNumber(),
//              "sms")
//          .create();

      // WhatsApp message for OTP
      Message message = Message.creator(
              new PhoneNumber("whatsapp:" + passwordResetRequestDto.getPhoneNumber()),
              from,
              otpMessage)
          .create();

//         SMS message for OTP, need to register a trial number for money
//      Message message = Message.creator(to, from, otpMessage).create();

      // save the OTP in in-memory-database for verification (for demo here a map)
      otpMap.put(passwordResetRequestDto.getUserName(),
          otp); // get user from session scope when using UI

      // Twilio API call success
      responseDto = new PasswordResetResponseDto(OtpStatus.DELIVERED, otpMessage);
    } catch (Exception e) {
      responseDto = new PasswordResetResponseDto(OtpStatus.FAILED, e.getMessage());
    }

    return Mono.just(responseDto);
  }

  // 6 digit otp
  private String generateOTP() {
    return new DecimalFormat("000000").format(new Random().nextInt(999999));
  }

  public Mono<String> validateOTP(String userName, String otp) {
    String storedOtp = otpMap.get(userName);
    if (storedOtp != null && storedOtp.equals(otp)) {
      return Mono.just("Valid OTP proceed with transaction!");
    } else {
      return Mono.error(new IllegalArgumentException("Invalid OTP please retry!"));
    }
  }
}

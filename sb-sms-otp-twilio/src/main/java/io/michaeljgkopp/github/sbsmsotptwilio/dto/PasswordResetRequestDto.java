package io.michaeljgkopp.github.sbsmsotptwilio.dto;

import lombok.Data;

@Data
public class PasswordResetRequestDto {
  private String phoneNumber; // The phone number of the user requesting the password reset
  private String userName;
  private String oneTimePassword; // The one-time password sent to the user's phone (OTP)
}

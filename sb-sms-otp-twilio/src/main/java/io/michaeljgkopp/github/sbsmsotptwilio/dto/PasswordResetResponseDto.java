package io.michaeljgkopp.github.sbsmsotptwilio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetResponseDto {
  private OtpStatus status;
  private String message;
}

package org.example.spring_security_jwt.Request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshRequest {
  @NotBlank
  private String refreshToken;

}

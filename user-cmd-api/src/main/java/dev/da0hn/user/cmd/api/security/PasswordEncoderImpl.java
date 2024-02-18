package dev.da0hn.user.cmd.api.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderImpl implements PasswordEncoder {

  @Override
  public String hashPassword(final String password) {
    final var encoder = new BCryptPasswordEncoder(12);
    return encoder.encode(password);
  }

}

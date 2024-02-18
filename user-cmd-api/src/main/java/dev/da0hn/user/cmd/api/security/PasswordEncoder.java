package dev.da0hn.user.cmd.api.security;

@FunctionalInterface
public interface PasswordEncoder {

  String hashPassword(String password);

}

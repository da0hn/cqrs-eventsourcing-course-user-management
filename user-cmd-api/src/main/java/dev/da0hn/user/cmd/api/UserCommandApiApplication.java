package dev.da0hn.user.cmd.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
  scanBasePackages = {
    "dev.da0hn.user.cmd",
    "dev.da0hn.user.core"
  }
)
public class UserCommandApiApplication {

  public static void main(final String[] args) {
    SpringApplication.run(UserCommandApiApplication.class, args);
  }

}

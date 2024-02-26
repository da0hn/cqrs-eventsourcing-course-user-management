package dev.da0hn.user.cmd.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(
  scanBasePackages = {
    "dev.da0hn.user.cmd",
    "dev.da0hn.user.core"
  }
)
@Import({
  dev.da0hn.user.core.configuration.AxonConfiguration.class
})
public class UserCommandApiApplication {

  public static void main(final String[] args) {
    SpringApplication.run(UserCommandApiApplication.class, args);
  }

}

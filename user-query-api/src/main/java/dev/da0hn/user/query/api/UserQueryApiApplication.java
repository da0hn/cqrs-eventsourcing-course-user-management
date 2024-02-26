package dev.da0hn.user.query.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(
  scanBasePackages = {
    "dev.da0hn.user.query",
    "dev.da0hn.user.core"
  }
)
@Import({
  dev.da0hn.user.core.configuration.AxonConfiguration.class
})

public class UserQueryApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserQueryApiApplication.class, args);
  }

}

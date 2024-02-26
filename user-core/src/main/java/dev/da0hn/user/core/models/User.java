package dev.da0hn.user.core.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {

  @Id
  @NotBlank(message = "The id is required")
  private String id;

  @NotBlank(message = "The first name is required")
  private String firstName;

  @NotBlank(message = "The last name is required")
  private String lastName;

  @Email(message = "The email address is not valid")
  private String emailAddress;

  @NotNull(message = "The account is required")
  private Account account;

}

package dev.da0hn.user.core.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@Builder
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class Account {

  @NotBlank(message = "The username is required")
  @Size(min = 2, message = "The username must have at least 2 characters")
  private String username;

  @NotBlank(message = "The password is required")
  @Size(min = 7, message = "The password must have at least 7 characters")
  private String password;

  @NotNull(message = "The roles are required")
  @NotEmpty(message = "At least one role is required")
  private List<Role> roles;

}

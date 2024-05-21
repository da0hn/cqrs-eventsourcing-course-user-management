package dev.da0hn.user.cmd.api.controllers;

import dev.da0hn.user.cmd.api.commands.RegisterUserCommand;
import dev.da0hn.user.cmd.api.dto.RegisterUserResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/register-user")
public class RegisterUserController {

  private final CommandGateway commandGateway;

  public RegisterUserController(
    final CommandGateway commandGateway
  ) {
    this.commandGateway = commandGateway;
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('users.read')")
  public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody @Valid final RegisterUserCommand command) {
    log.info("registerUser(command={})", command);

    final var userId = UUID.randomUUID().toString();

    final var commandWithId = command.toBuilder()
      .id(userId)
      .build();

    this.commandGateway.sendAndWait(commandWithId);

    final var response = RegisterUserResponse.builder()
      .id(userId)
      .message("User successfully registered")
      .build();

    return ResponseEntity.ok(response);
  }

}

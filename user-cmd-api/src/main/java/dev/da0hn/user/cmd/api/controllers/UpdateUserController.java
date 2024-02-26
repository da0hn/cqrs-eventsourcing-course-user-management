package dev.da0hn.user.cmd.api.controllers;

import dev.da0hn.user.cmd.api.commands.UpdateUserCommand;
import dev.da0hn.user.cmd.api.dto.RegisterUserResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/update-user")
public class UpdateUserController {

  private final CommandGateway commandGateway;

  public UpdateUserController(
    final CommandGateway commandGateway
  ) {
    this.commandGateway = commandGateway;
  }

  @PutMapping("/{user-id}")
  public ResponseEntity<RegisterUserResponse> updateUser(
    @PathVariable("user-id") final String userId,
    @RequestBody @Valid final UpdateUserCommand command
  ) {
    log.info("updateUser(userId={}, command={})", userId, command);

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

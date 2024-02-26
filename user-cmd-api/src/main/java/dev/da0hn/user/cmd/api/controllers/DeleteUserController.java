package dev.da0hn.user.cmd.api.controllers;

import dev.da0hn.user.cmd.api.commands.RemoveUserCommand;
import dev.da0hn.user.cmd.api.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/remove-user")
public class DeleteUserController {

  private final CommandGateway commandGateway;

  public DeleteUserController(
    final CommandGateway commandGateway
  ) {
    this.commandGateway = commandGateway;
  }

  @DeleteMapping("/{user-id}")
  public ResponseEntity<BaseResponse> removeUser(@PathVariable("user-id") final String userId) {
    log.info("removeUser(userId={})", userId);

    this.commandGateway.sendAndWait(
      RemoveUserCommand.builder()
        .id(userId)
        .build()
    );

    final var response = BaseResponse.builder()
      .message("User successfully removed")
      .build();

    return ResponseEntity.ok(response);
  }

}

package dev.da0hn.user.cmd.api.commands;

import dev.da0hn.user.core.models.User;
import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder

public record RegisterUserCommand(
  @TargetAggregateIdentifier
  String id,
  User user
) {
}

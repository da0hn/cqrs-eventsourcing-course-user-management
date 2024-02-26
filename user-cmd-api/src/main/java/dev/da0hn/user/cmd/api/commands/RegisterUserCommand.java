package dev.da0hn.user.cmd.api.commands;

import dev.da0hn.user.core.models.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder(toBuilder = true)
public record RegisterUserCommand(
  @TargetAggregateIdentifier
  String id,
  @NotNull
  User user
) {
}

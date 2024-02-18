package dev.da0hn.user.cmd.api.commands;

import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
public record RemoveUserCommand(@TargetAggregateIdentifier String id) {
}

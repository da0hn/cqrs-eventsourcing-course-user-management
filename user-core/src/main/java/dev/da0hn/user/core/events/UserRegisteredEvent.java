package dev.da0hn.user.core.events;

import dev.da0hn.user.core.models.User;
import lombok.Builder;

@Builder
public record UserRegisteredEvent(
  String id,
  User user
) {
}

package dev.da0hn.user.core.events;

import lombok.Builder;

@Builder
public record UserRemovedEvent(
  String id
) {
}

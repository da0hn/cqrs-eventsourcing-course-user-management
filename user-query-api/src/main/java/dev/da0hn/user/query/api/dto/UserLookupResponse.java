package dev.da0hn.user.query.api.dto;

import dev.da0hn.user.core.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record UserLookupResponse(List<User> user) {

  public static UserLookupResponse fromUser(final User user) {
    return new UserLookupResponse(List.of(user));
  }

  public static UserLookupResponse fromUsers(final List<User> users) {
    return new UserLookupResponse(Objects.requireNonNullElseGet(users, ArrayList::new));
  }

}

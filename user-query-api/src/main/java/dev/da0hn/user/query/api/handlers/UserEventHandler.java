package dev.da0hn.user.query.api.handlers;

import dev.da0hn.user.core.events.UserRegisteredEvent;
import dev.da0hn.user.core.events.UserRemovedEvent;
import dev.da0hn.user.core.events.UserUpdatedEvent;

public interface UserEventHandler {

  void registerUser(UserRegisteredEvent event);

  void updateUser(UserUpdatedEvent event);

  void deleteUserById(UserRemovedEvent event);

}

package dev.da0hn.user.query.api.handlers;

import dev.da0hn.user.core.events.UserRegisteredEvent;
import dev.da0hn.user.core.events.UserRemovedEvent;
import dev.da0hn.user.core.events.UserUpdatedEvent;
import dev.da0hn.user.query.api.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ProcessingGroup("user-group")
public class UserEventHandlerImpl implements UserEventHandler {

  private final UserRepository userRepository;

  public UserEventHandlerImpl(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @EventHandler
  public void registerUser(final UserRegisteredEvent event) {
    log.info("registerUser(event={})", event);
    this.userRepository.save(event.user());
  }

  @Override
  @EventHandler
  public void updateUser(final UserUpdatedEvent event) {
    log.info("updateUser(event={})", event);
    this.userRepository.save(event.user());
  }

  @Override
  @EventHandler
  public void deleteUserById(final UserRemovedEvent event) {
    log.info("deleteUserById(event={})", event);
    this.userRepository.deleteById(event.id());
  }

}

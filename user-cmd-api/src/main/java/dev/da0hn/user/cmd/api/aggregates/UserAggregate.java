package dev.da0hn.user.cmd.api.aggregates;

import dev.da0hn.user.cmd.api.commands.RegisterUserCommand;
import dev.da0hn.user.cmd.api.commands.RemoveUserCommand;
import dev.da0hn.user.cmd.api.commands.UpdateUserCommand;
import dev.da0hn.user.cmd.api.security.PasswordEncoder;
import dev.da0hn.user.cmd.api.security.PasswordEncoderImpl;
import dev.da0hn.user.core.events.UserRegisteredEvent;
import dev.da0hn.user.core.events.UserRemovedEvent;
import dev.da0hn.user.core.events.UserUpdatedEvent;
import dev.da0hn.user.core.models.User;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
@Slf4j
public class UserAggregate {

  private final PasswordEncoder passwordEncoder;

  @AggregateIdentifier
  private String id;

  private User user;

  public UserAggregate() {
    this.passwordEncoder = new PasswordEncoderImpl();
  }

  @CommandHandler
  public UserAggregate(final RegisterUserCommand command) {
    this();
    log.info("UserAggregate#constructor(command={})", command);
    final var newUser = command.user();
    newUser.setId(command.id());
    final var password = newUser.getAccount().getPassword();
    final var hashedPassword = this.passwordEncoder.hashPassword(password);
    newUser.getAccount().setPassword(hashedPassword);

    final var userRegisteredEvent = UserRegisteredEvent.builder()
      .id(command.id())
      .user(newUser)
      .build();

    AggregateLifecycle.apply(userRegisteredEvent);
  }

  @CommandHandler
  public void handle(final UpdateUserCommand command) {
    log.info("handle(command={})", command);
    final var updatedUser = command.user();
    updatedUser.setId(command.id());
    final var password = updatedUser.getAccount().getPassword();
    final var hashedPassword = this.passwordEncoder.hashPassword(password);
    updatedUser.getAccount().setPassword(hashedPassword);

    final var userUpdatedEvent = UserUpdatedEvent.builder()
      .id(UUID.randomUUID().toString())
      .user(updatedUser)
      .build();
    AggregateLifecycle.apply(userUpdatedEvent);
  }

  @CommandHandler
  public void handle(final RemoveUserCommand command) {
    log.info("handle(command={})", command);
    final var userRemovedEvent = UserRemovedEvent.builder()
      .id(command.id())
      .build();
    AggregateLifecycle.apply(userRemovedEvent);
  }

  @EventSourcingHandler
  public void on(final UserRegisteredEvent event) {
    log.info("on(event={})", event);
    this.id = event.id();
    this.user = event.user();
  }

  @EventSourcingHandler
  public void on(final UserUpdatedEvent event) {
    log.info("on(event={})", event);
    this.user = event.user();
  }

  @EventSourcingHandler
  public void on(final UserRemovedEvent event) {
    log.info("on(event={})", event);
    AggregateLifecycle.markDeleted();
  }

}

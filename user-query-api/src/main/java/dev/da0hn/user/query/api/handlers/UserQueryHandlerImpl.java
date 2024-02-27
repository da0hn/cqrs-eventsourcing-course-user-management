package dev.da0hn.user.query.api.handlers;

import dev.da0hn.user.query.api.dto.UserLookupResponse;
import dev.da0hn.user.query.api.queries.SearchAllUsersQuery;
import dev.da0hn.user.query.api.queries.SearchUserByIdQuery;
import dev.da0hn.user.query.api.queries.SearchUsersQuery;
import dev.da0hn.user.query.api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class UserQueryHandlerImpl implements UserQueryHandler {

  private final UserRepository userRepository;

  @Override
  @QueryHandler
  public UserLookupResponse searchUserById(final SearchUserByIdQuery query) {
    log.info("searchUserById(query={})", query);
    return this.userRepository.findById(query.id())
      .map(UserLookupResponse::fromUser)
      .orElseThrow(() -> new IllegalArgumentException("User not found"));
  }

  @Override
  @QueryHandler
  public UserLookupResponse searchAllUsers(final SearchAllUsersQuery query) {
    log.info("searchAllUsers(query={})", query);
    return this.userRepository.findAll().stream()
      .collect(Collectors.collectingAndThen(Collectors.toList(), UserLookupResponse::fromUsers));
  }

  @Override
  @QueryHandler
  public UserLookupResponse searchUsers(final SearchUsersQuery query) {
    log.info("searchUsers(query={})", query);
    return this.userRepository.findAllFilteringBy(query.filter()).stream()
      .collect(Collectors.collectingAndThen(Collectors.toList(), UserLookupResponse::fromUsers));
  }

}

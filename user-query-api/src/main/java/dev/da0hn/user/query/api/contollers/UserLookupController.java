package dev.da0hn.user.query.api.contollers;

import dev.da0hn.user.query.api.dto.UserLookupResponse;
import dev.da0hn.user.query.api.queries.SearchAllUsersQuery;
import dev.da0hn.user.query.api.queries.SearchUserByIdQuery;
import dev.da0hn.user.query.api.queries.SearchUsersQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users-lookup")
public class UserLookupController {

  private final QueryGateway queryGateway;

  @GetMapping
  public CompletableFuture<UserLookupResponse> searchAllUsers(@RequestParam(value = "q", required = false) final String filter) {
    log.info("searchAllUsers(filter={})", filter);
    final var query = filter == null ? new SearchAllUsersQuery() : new SearchUsersQuery(filter);
    return this.queryGateway.query(query, UserLookupResponse.class);
  }

  @GetMapping("/{user-id}")
  public CompletableFuture<UserLookupResponse> searchUserById(@PathVariable("user-id") final String userId) {
    log.info("searchAllUsers(userId={})", userId);
    final var query = new SearchUserByIdQuery(userId);
    return this.queryGateway.query(query, UserLookupResponse.class);
  }

}

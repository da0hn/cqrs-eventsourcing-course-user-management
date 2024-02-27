package dev.da0hn.user.query.api.handlers;

import dev.da0hn.user.query.api.dto.UserLookupResponse;
import dev.da0hn.user.query.api.queries.SearchAllUsersQuery;
import dev.da0hn.user.query.api.queries.SearchUserByIdQuery;
import dev.da0hn.user.query.api.queries.SearchUsersQuery;

public interface UserQueryHandler {

  UserLookupResponse searchUserById(SearchUserByIdQuery query);

  UserLookupResponse searchAllUsers(SearchAllUsersQuery query);

  UserLookupResponse searchUsers(SearchUsersQuery query);

}

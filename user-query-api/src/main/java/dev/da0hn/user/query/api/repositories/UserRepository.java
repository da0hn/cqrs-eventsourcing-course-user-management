package dev.da0hn.user.query.api.repositories;

import dev.da0hn.user.core.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

  @Query("""
         {
           $or: [
             { 'firstName': { $regex: ?0 } },
             { 'lastName': { $regex: ?0 } },
             { 'emailAddress': { $regex: ?0 } },
             { 'account.username':  { $regex: ?0 } }
           ]
         }
         """
  )
  List<User> findAllFilteringBy(String filter);

}

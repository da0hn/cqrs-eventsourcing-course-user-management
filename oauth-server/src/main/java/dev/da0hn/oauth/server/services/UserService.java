package dev.da0hn.oauth.server.services;

import dev.da0hn.oauth.server.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserService(
    final UserRepository userRepository
  ) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    return this.userRepository.findByUsername(username)
      .map(AccountUserDetailsAdapter::of)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

}

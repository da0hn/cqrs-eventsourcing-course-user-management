package dev.da0hn.oauth.server.services;

import dev.da0hn.oauth.server.repositories.UserRepository;
import dev.da0hn.user.core.models.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
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

  public OidcUserInfo loadUserInfo(final String username) {
    final var user = this.userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return OidcUserInfo.builder()
      .givenName(user.getFirstName())
      .familyName(user.getLastName())
      .nickname(user.getAccount().getUsername())
      .email(user.getEmailAddress())
      .claims(claims -> {
        claims.put("userId", user.getId());
        claims.put("roles", user.getAccount().getRoles().stream().map(Role::getAuthority).toList());
      })
      .build();
  }

}

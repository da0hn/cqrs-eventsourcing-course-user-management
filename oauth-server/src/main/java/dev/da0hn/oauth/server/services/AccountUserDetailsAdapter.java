package dev.da0hn.oauth.server.services;

import dev.da0hn.user.core.models.Account;
import dev.da0hn.user.core.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

public class AccountUserDetailsAdapter implements UserDetails {

  private final Account account;

  private AccountUserDetailsAdapter(final Account account) {
    this.account = account;
  }

  public static AccountUserDetailsAdapter of(final User user) {
    return new AccountUserDetailsAdapter(Objects.requireNonNull(user.getAccount()));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.account.getRoles().stream()
      .map(GrantedAuthority.class::cast)
      .toList();
  }

  @Override
  public String getPassword() {
    return this.account.getPassword();
  }

  @Override
  public String getUsername() {
    return this.account.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}

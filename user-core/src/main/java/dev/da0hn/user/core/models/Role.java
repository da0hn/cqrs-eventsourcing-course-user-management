package dev.da0hn.user.core.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  READ_PRIVILEGE,
  WRITE_PRIVILEGE;

  @Override
  public String getAuthority() {
    return this.name();
  }
}

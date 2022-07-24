package idx.lesson.generalBoot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import idx.lesson.generalBoot.config.ConfigConsts;
import idx.lesson.generalBoot.entity.Role;
import idx.lesson.generalBoot.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class UserDto implements UserDetails {

  private final User user;
  private final List<Role> roles;
  public UserDto(User user, List<Role> roles) {
    this.user = user;
    this.roles = roles;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
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
    return Objects.equals(user.getStatus(), ConfigConsts.USER_ENABLED);
  }
}

package com.apajaro.platform.infrastructure.web;

import com.apajaro.platform.domain.entity.SecurityPermission;
import com.apajaro.platform.domain.entity.UserLogin;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "username", "password", "enabled", "requirePasswordChange"})
public class UserPrincipal implements UserDetails {
    private String id;

    private String username;

    private String password;

    private boolean enabled;

    private boolean requirePasswordChange;

    private Collection<? extends GrantedAuthority> authorities;

    private String organizationId;

    public static UserPrincipal from(UserLogin userLogin, Set<SecurityPermission> securityPermissions) {
        return new UserPrincipal(
                userLogin.getId().getValue(),
                userLogin.getUsername(),
                userLogin.getPassword(),
                userLogin.isEnabled(),
                userLogin.isRequirePasswordChange(),
                getAuthoritiesFromDomain(securityPermissions),
                userLogin.getOrganizationId().getValue()
        );
    }

    public static Collection<SimpleGrantedAuthority> getAuthoritiesFromDomain(Set<SecurityPermission> securityPermissions) {
        return securityPermissions
                .stream()
                .map(SecurityPermission::getSlug)
                .collect(Collectors.toSet())
                .stream()
                .map(SimpleGrantedAuthority::new).toList();
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
        return !requirePasswordChange;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

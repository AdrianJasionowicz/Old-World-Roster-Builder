package com.oldworld.rosterbuilder.loginUser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

record LoginUserPrincipal(String username, String password, Role role, boolean enabled, String tokenKey) implements UserDetails {

    static LoginUserPrincipal from(LoginUser loginUser) {
        return new LoginUserPrincipal(loginUser.getUsername(), loginUser.getPassword(), loginUser.getRole(), loginUser.isEnabled(), loginUser.getTokenKey());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

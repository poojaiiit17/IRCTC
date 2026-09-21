package com.irctc.service;

import com.irctc.model.User;
import com.irctc.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import java.util.Collections;

@org.springframework.stereotype.Service
public class AdminUserDetailsService implements UserDetailsService {
    private final UserRepository repository;
    public AdminUserDetailsService(UserRepository repository) { this.repository = repository; }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserDetails() {
            public java.util.Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singleton(() -> "ROLE_" + user.getRole());
            }
            public String getPassword() { return user.getPassword(); }
            public String getUsername() { return user.getEmail(); }
            public boolean isAccountNonExpired(){return true;}
            public boolean isAccountNonLocked(){return true;}
            public boolean isCredentialsNonExpired(){return true;}
            public boolean isEnabled(){return true;}
        };
    }
}
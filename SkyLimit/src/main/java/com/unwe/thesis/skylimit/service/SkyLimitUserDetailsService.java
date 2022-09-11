package com.unwe.thesis.skylimit.service;

import com.unwe.thesis.skylimit.model.entity.UserEntity;
import com.unwe.thesis.skylimit.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

public class SkyLimitUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public SkyLimitUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userEntity =this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " not found!"));

        return map(userEntity);
    }

    private UserDetails map(UserEntity userEntity) {

        Set<GrantedAuthority> grantedAuthorities = userEntity.getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name())).collect(Collectors.toUnmodifiableSet());

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                grantedAuthorities
        );
    }
}

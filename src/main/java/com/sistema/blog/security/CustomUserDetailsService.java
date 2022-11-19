package com.sistema.blog.security;

import com.sistema.blog.entity.Role;
import com.sistema.blog.entity.User;
import com.sistema.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Let people login with either username or email
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)//busca el usuario por el username o por el email
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
        );

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapearRoles(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapearRoles(Set<Role> roles) {
        return roles.stream().map(role ->
                new SimpleGrantedAuthority(role.getName())
        ).collect(Collectors.toList());
    }

    // This method is used by JWTAuthenticationFilter
//    @Transactional
//    public UserDetails loadUserById(Long id) {
//        User user = userRepository.findById(id).orElseThrow(
//            () -> new UsernameNotFoundException("User not found with id : " + id)
//        );
//
//        return UserPrincipal.create(user);
//    }

}

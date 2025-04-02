package com.capstone.security;

import com.capstone.domain.User;
import com.capstone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = repository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("[ERROR] User not found"));

        UserPrincipal userPrincipal = new UserPrincipal(user);
        return userPrincipal;
    }
}

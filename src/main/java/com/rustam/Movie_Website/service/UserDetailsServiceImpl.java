package com.rustam.Movie_Website.service;

import com.rustam.Movie_Website.dao.entity.User;
import com.rustam.Movie_Website.dao.repository.UserRepository;
import com.rustam.Movie_Website.util.CustomUserDetails;
import com.rustam.Movie_Website.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UtilService utilService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(utilService.convertToUUID(username))
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return new CustomUserDetails(user);
    }
}

package org.hsharan.userAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserDetailsRepository userDetailsRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userDetailsRepository.findById(username).orElse(null);
        if (userDetails==null)
                throw new UsernameNotFoundException("User Not Found");
        return userDetails;
    }


}

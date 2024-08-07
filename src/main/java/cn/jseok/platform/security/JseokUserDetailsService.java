package cn.jseok.platform.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JseokUserDetailsService implements UserDetailsService {

    private JseokUserDetails jseokUserDetails;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return jseokUserDetails;
    }
}

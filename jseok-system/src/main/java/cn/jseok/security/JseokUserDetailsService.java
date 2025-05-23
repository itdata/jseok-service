package cn.jseok.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class JseokUserDetailsService implements UserDetailsService {

    private JseokUserDetails jseokUserDetails;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        jseokUserDetails = new JseokUserDetails();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        grantedAuthorities.add(simpleGrantedAuthority);
        jseokUserDetails.setAuthorities(grantedAuthorities);
        return jseokUserDetails;
    }
}

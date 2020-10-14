package com.brampel.tdserver.auth;

import com.brampel.tdserver.model.TDUserDetails;
import com.brampel.tdserver.model.TDUserDetailsImpl;
import com.brampel.tdserver.model.User;
import com.brampel.tdserver.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public TDUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new TDUserDetailsImpl(user);
    }
}

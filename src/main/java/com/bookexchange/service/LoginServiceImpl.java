package com.bookexchange.service;

import com.bookexchange.dao.UserDao;
import com.bookexchange.dto.User;
import com.bookexchange.dto.UserRole;
import com.bookexchange.exception.BookExchangeInternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by sheke on 10/30/2015.
 */
@Service("loginService")
@Transactional
public class LoginServiceImpl implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByEmail(username).orElseThrow(()->new UsernameNotFoundException("UNKNOWN USERNAME"));

        List<GrantedAuthority> authorities = buildUserAuthority(user
                .getUserRole());

        return buildUserForAuthentication(user, authorities);
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user,
                                            List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.isEnabled(), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        setAuths.addAll(userRoles.stream().map(userRole -> new SimpleGrantedAuthority(userRole.getRole())).collect(Collectors.toList()));

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(
                setAuths);

        return Result;
    }
}

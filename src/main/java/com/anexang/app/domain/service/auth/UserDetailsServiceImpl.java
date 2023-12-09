
package com.anexang.app.domain.service.auth;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anexang.app.domain.dao.IEmployeDao;
import com.anexang.app.domain.entity.Employe;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IEmployeDao employeDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Employe employe = employeDao.findByMail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El empleado no existe" + email));

        Collection<? extends GrantedAuthority> authorities = employe.getRole()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet());

        return new User(employe.getMail(), employe.getPassword(), true, true, true, true, authorities);
    }

}
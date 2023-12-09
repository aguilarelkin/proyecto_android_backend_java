package com.anexang.app.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anexang.app.domain.dao.IRoleDao;
import com.anexang.app.domain.entity.Role;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    @Override
    public Role createRole(Role role) {
        return roleDao.save(role);
    }

    @Override
    public List<Role> getRoles() {
        return roleDao.findAll();
    }

}

package com.anexang.app.domain.service;

import java.util.List;

import com.anexang.app.domain.entity.Role;

public interface IRoleService {
    
    public List<Role> getRoles();

    public Role createRole(Role role);

}

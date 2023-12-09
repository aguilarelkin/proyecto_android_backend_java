package com.anexang.app.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anexang.app.domain.entity.Role;

@Repository
public interface IRoleDao extends JpaRepository<Role, Integer>{
    
}

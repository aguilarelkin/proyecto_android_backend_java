package com.anexang.app.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anexang.app.domain.entity.Servicem;

@Repository
public interface IServiceDao extends JpaRepository<Servicem, Integer>{
    
}

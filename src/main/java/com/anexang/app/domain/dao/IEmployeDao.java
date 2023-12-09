package com.anexang.app.domain.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anexang.app.domain.entity.Employe;


@Repository
public interface IEmployeDao extends JpaRepository<Employe, Integer> {

    Optional<Employe> findByMail(String mail);
}

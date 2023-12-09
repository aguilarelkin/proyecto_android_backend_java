package com.anexang.app.domain.service;

import java.util.List;
import java.util.Optional;

import com.anexang.app.domain.entity.Employe;

public interface IEmployeService {

    public List<Employe> getEmployes();

    public Optional<Employe> getEmployeId(int id);

    public Employe createEmploye(Employe employe);

    public Employe updateEmploye(Employe employe);

    public void deleteEmploye(int id);
}

package com.anexang.app.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anexang.app.domain.dao.IEmployeDao;
import com.anexang.app.domain.entity.Employe;

@Service
public class EmployeServiceImpl implements IEmployeService {

    @Autowired
    private IEmployeDao employeDao;

    @Override
    public List<Employe> getEmployes() {
        return employeDao.findAll();
    }

    @Override
    public Optional<Employe> getEmployeId(int id) {
        return employeDao.findById(id);
    }

    @Override
    public Employe createEmploye(Employe employe) {
        return employeDao.save(employe);
    }

    @Override
    public Employe updateEmploye(Employe employe) {
        return employeDao.save(employe);
    }

    @Override
    public void deleteEmploye(int id) {
        employeDao.deleteById(id);
    }

}

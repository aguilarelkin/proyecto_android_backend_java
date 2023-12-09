package com.anexang.app.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anexang.app.domain.dao.IServiceDao;
import com.anexang.app.domain.entity.Servicem;

@Service
public class ServiceImpl implements IService{

    @Autowired
    private IServiceDao serviceDao;

    @Override
    public List<Servicem> getServices() {
        return serviceDao.findAll();
    }

    @Override
    public Optional<Servicem> getServiceId(int id) {
       return serviceDao.findById(id);
    }

    @Override
    public Servicem createService(Servicem service) {
        return serviceDao.save(service);
    }

    @Override
    public Servicem updateService(Servicem service) {
        return serviceDao.save(service);
    }

    @Override
    public void deleteService(int id) {
       serviceDao.deleteById(id);
    }
    
}

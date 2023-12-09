package com.anexang.app.domain.service;

import java.util.List;
import java.util.Optional;

import com.anexang.app.domain.entity.Servicem;

public interface IService {
    public List<Servicem> getServices() ;
    public Optional<Servicem> getServiceId(int id);
    public Servicem createService(Servicem service);
    public Servicem updateService(Servicem service);
    public void deleteService(int id);
}

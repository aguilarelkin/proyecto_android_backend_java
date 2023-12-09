package com.anexang.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anexang.app.domain.entity.Servicem;
import com.anexang.app.domain.service.IService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://192.168.43.54:8080", "http://192.168.43.54" })
@RestController
@RequestMapping("/api/v1/servicem")
public class ServiceContrller {

    private Map<String, Object> response;

    @Autowired
    private IService service;

    @GetMapping
    public ResponseEntity<?> getService() {
        response = new HashMap<>();

        List<Servicem> data;

        try {
            data = service.getServices();
        } catch (Exception e) {
            response.put("response", "Error: DDBB");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<List<Servicem>>(data, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getServiceId(@PathVariable("id") String id) {

        response = new HashMap<>();
        Optional<Servicem> data;

        try {
            data = service.getServiceId(Integer.parseInt(id));
        } catch (Exception e) {
            response.put("response", "Error: DDBB");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        if (data.isEmpty()) {
            response.put("response", "Error :(");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Servicem>(data.get(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createService(@Valid @RequestBody Servicem servicem, BindingResult result) {
        response = new HashMap<>();
        Servicem servicem2;

        if (result.hasErrors()) {
            List<String> errorField = result.getFieldErrors().stream()
                    .map(err -> "Campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errorField", errorField);
            return new ResponseEntity<List<String>>(errorField, HttpStatus.BAD_REQUEST);
        }
        try {
            servicem2 = service.createService(servicem);
        } catch (Exception e) {
            response.put("response", "Error: DDBB");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Servicem>(servicem2, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateService(@Valid @RequestBody Servicem servicem, BindingResult result,
            @PathVariable("id") String id) {
        response = new HashMap<>();
        Optional<Servicem> getService;

        if (result.hasErrors()) {
            List<String> errorField = result.getFieldErrors().stream()
                    .map(err -> "Campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errorField", errorField);
            return new ResponseEntity<List<String>>(errorField, HttpStatus.BAD_REQUEST);
        }

        try {
            getService = service.getServiceId(Integer.parseInt(id));
        } catch (Exception e) {
            response.put("response", "Error: DDBB");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (getService.isEmpty()) {
            response.put("response", "Error :(");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (getService.isPresent()) {
            getService.get().setService(servicem.getService());
            getService.get().setPrice(servicem.getPrice());
            getService.get().setBase(servicem.getBase());
            service.updateService(getService.get());
        }
        return new ResponseEntity<Servicem>(getService.get(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable("id") String id) {

        response = new HashMap<>();
        Optional<Servicem> getService;
        try {
            getService = service.getServiceId(Integer.parseInt(id));
            if (getService.isEmpty()) {
                response.put("response", "Error: DDBB");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            service.deleteService(Integer.parseInt(id));
        } catch (Exception e) {
            response.put("response", "Error: DDBB");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Delete Ok", HttpStatus.OK);
    }
}

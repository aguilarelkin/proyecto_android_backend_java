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
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.anexang.app.domain.entity.Employe;
import com.anexang.app.domain.service.IEmployeService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://192.168.43.54:8080", "http://192.168.43.54" })
@RestController
@RequestMapping("/api/v1/employe")
public class EmployeContoller {

    private Map<String, Object> response;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IEmployeService employeService;

    @GetMapping
    public ResponseEntity<?> getEmployes() {

        List<Employe> employe;

        try {
            employe = employeService.getEmployes();

        } catch (Exception e) {
            response.put("response", "Error: DDBB");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<List<Employe>>(employe, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeId(@PathVariable("id") String id) {

        response = new HashMap<>();
        Optional<Employe> employe;
        try {
            employe = employeService.getEmployeId(Integer.parseInt(id));
        } catch (Exception e) {
            response.put("response", "Error: DDBB");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (employe.isEmpty()) {
            response.put("response", "Error :(");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Employe>(employe.get(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createEmploye(@Valid @RequestBody Employe employe, BindingResult result) {
        response = new HashMap<>();
        Employe employeData;
        System.out.println("hol- " + employe.getId() + " " + employe.getFirstname() + " " + employe.getLastname() + " "
                + employe.getMail() + " " + employe.getPassword() + " " + employe.getAvatar() + " " + employe.getBase()
                + " -- "
                + employe.getTelephone()
                + " " + employe.getRole().size());
        if (result.hasErrors()) {
            List<String> errorField = result.getFieldErrors().stream()
                    .map(err -> "Campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errorField", errorField);
            return new ResponseEntity<List<String>>(errorField, HttpStatus.BAD_REQUEST);
        }

        try {
            employe.setPassword(passwordEncoder.encode(employe.getPassword()));
            System.out.println(
                    "hqwl- " + employe.getId() + " " + employe.getFirstname() + " " + employe.getLastname() + " "
                            + employe.getMail() + " " + employe.getPassword() + " " + employe.getAvatar() + " "
                            + employe.getBase() + " -- "
                            + employe.getTelephone()
                            + " " + employe.getRole().size());

            employeData = employeService.createEmploye(employe);
        } catch (Exception e) {
            response.put("response", "Error: DD2BB"+e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Employe>(employeData, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmploye(@Valid @RequestBody Employe employe, BindingResult result,
            @PathVariable("id") String id) {

        response = new HashMap<>();
        Optional<Employe> employeExist;

        System.out.println("hol- " + employe.getId() + " " + employe.getFirstname() + " " + employe.getLastname() + " "
                + employe.getMail() + " " + employe.getAvatar() + " " + employe.getBase() + " -- "
                + employe.getTelephone()
                + " " + employe.getRole().size());

        if (result.hasErrors()) {
            List<String> errorField = result.getFieldErrors().stream()
                    .map(err -> "Campo " + err.getField() + " " + err.getDefaultMessage()).collect(Collectors.toList());
            response.put("errorField", errorField);
            return new ResponseEntity<List<String>>(errorField, HttpStatus.BAD_REQUEST);
        }

        try {
            employeExist = employeService.getEmployeId(Integer.parseInt(id));
        } catch (Exception e) {
            response.put("response", "Error: DDBB");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (employeExist.isEmpty()) {
            response.put("response", "Error :(");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (employeExist.isPresent()) {
            employeExist.get().setFirstname(employe.getFirstname());
            employeExist.get().setLastname(employe.getLastname());
            employeExist.get().setAvatar(employe.getAvatar());
            employeExist.get().setMail(employe.getMail());
            employeExist.get().setBase(employe.getBase());
            employeExist.get().setTelephone(employe.getTelephone());
//            employeExist.get().setRole(employe.getRole());
            // employe.getRole().stream().forEach(role ->
            // employeExist.get().addRole(role));// error en roles
            employeService.updateEmploye(employeExist.get());
        }

        return new ResponseEntity<Employe>(employeExist.get(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmploye(@PathVariable("id") String id) {

        response = new HashMap<>();
        Optional<Employe> dataEmploye;
        try {
            dataEmploye = employeService.getEmployeId(Integer.parseInt(id));
            if (dataEmploye.isEmpty()) {
                response.put("response", "Error: DDBB");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            employeService.deleteEmploye(Integer.parseInt(id));
        } catch (Exception e) {
            response.put("response", "Error: DDBB");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("Delete Ok", HttpStatus.OK);
    }
}

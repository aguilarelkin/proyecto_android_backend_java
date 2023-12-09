package com.anexang.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anexang.app.domain.entity.Role;
import com.anexang.app.domain.service.IRoleService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://192.168.43.54:8080", "http://192.168.43.54" })
@RestController
@RequestMapping("/api/v1/role")
public class RoleContoller {

    private Map<String, Object> response;

    @Autowired
    private IRoleService roleService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getRoles() {
        response = new HashMap<>();
        List<Role> roles = null;

        try {
            roles = roleService.getRoles();
        } catch (Exception e) {
            response.put("response", "Error: DDBB");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> saveRole(@Valid @RequestBody Role role, BindingResult result) {
        response = new HashMap<>();
        Role responseRole = null;

        if (result.hasErrors()) {
            List<String> errorField = result.getFieldErrors().stream()
                    .map(err -> "Campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errorField", errorField);
            return new ResponseEntity<List<String>>(errorField, HttpStatus.BAD_REQUEST);
        }

        try {
            responseRole = roleService.createRole(role);
        } catch (Exception e) {
            response.put("response", "Error: DDBB");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Role>(responseRole, HttpStatus.OK);
    }

}

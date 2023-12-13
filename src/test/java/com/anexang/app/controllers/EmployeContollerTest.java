package com.anexang.app.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.anexang.app.Data;
import com.anexang.app.domain.service.IEmployeService;

@ExtendWith(MockitoExtension.class)
public class EmployeContollerTest {

    @InjectMocks
    private EmployeContoller contoller;

    @InjectMocks
    private Data dataEmploye;

    @Mock
    private BindingResult result;

    @Mock
    private IEmployeService employeService;

    @Test
    void testCreateEmploye() {

    }

    @Test
    void testDeleteEmploye() {

    }

    @Test
    void testGetEmployeId() {

    }

    @Test
    void testGetEmployes() {
        when(employeService.getEmployes()).thenReturn(dataEmploye.getData());
        
        ResponseEntity<?> response = contoller.getEmployes();
        verify(employeService, times(1)).getEmployes();
        assertTrue(response.toString().contains("200"));
    }

    @Test
    void testUpdateEmploye() {

    }
}

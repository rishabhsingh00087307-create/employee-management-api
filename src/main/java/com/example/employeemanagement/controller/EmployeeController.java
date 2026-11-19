package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    // In-memory storage keeps this sample focused on REST endpoints; data resets on restart.
    private final Map<Integer, Employee> employees = new ConcurrentHashMap<>();

    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        if (employee.getId() <= 0 || employee.getName() == null || employee.getName().isBlank()
                || employee.getDepartment() == null || employee.getDepartment().isBlank()
                || employee.getSalary() <= 0) {
            return ResponseEntity.badRequest().body("Provide a positive id and salary, plus name and department.");
        }
        if (employees.putIfAbsent(employee.getId(), employee) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Employee ID already exists.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        return ResponseEntity.ok(new ArrayList<>(employees.values()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable int id) {
        Employee employee = employees.get(id);
        if (employee == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found.");
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        if (!employees.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found.");
        }
        if (updatedEmployee.getName() == null || updatedEmployee.getName().isBlank()
                || updatedEmployee.getDepartment() == null || updatedEmployee.getDepartment().isBlank()
                || updatedEmployee.getSalary() <= 0) {
            return ResponseEntity.badRequest().body("Provide name, department, and a positive salary.");
        }
        updatedEmployee.setId(id); // URL ID is authoritative.
        employees.put(id, updatedEmployee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id) {
        if (employees.remove(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found.");
        }
        return ResponseEntity.noContent().build();
    }
}

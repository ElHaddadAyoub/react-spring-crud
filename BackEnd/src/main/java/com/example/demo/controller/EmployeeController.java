package com.example.demo.controller;


import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;


      //Get All employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    //get Employee By Id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee>  getEmployeeById(@PathVariable Long id){
         Employee employee = employeeRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("employee not exist with id" +id));

         return ResponseEntity.ok(employee);
    }
    //Create Employee
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return  employeeRepository.save(employee);
    }

    //Update Employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id ,@RequestBody Employee employeeDet) {
        Employee employee = employeeRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("employee not exist with id" +id));

        employee.setFirstName(employeeDet.getFirstName());
        employee.setLastName(employeeDet.getLastName());
        employee.setEmail(employeeDet.getEmail());


        Employee updateEmployee =  employeeRepository.save(employee);
        return ResponseEntity.ok(updateEmployee);



    }


    //delete Employee
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String , Boolean>> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("employee not exist with id" +id));


        employeeRepository.delete(employee);
        Map<String , Boolean> response = new HashMap<>();
        response.put("deleted" , Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}

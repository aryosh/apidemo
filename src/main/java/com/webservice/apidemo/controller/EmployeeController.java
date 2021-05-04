package com.webservice.apidemo.controller;

import com.webservice.apidemo.entity.Employee;
import com.webservice.apidemo.model.EmployeeRequest;
import com.webservice.apidemo.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    @Autowired
    EmployeeController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employee/all")
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    @PostMapping("/employee/add") //Create
    public ResponseEntity<Employee> addNewEmployee(@RequestBody EmployeeRequest request){
        try {
            Employee employee = new Employee();
            employee.setName(request.getName());
            employee.setPhone(request.getPhone());
            employeeRepository.save(employee);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/employee/update/{id}") //Update
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") BigInteger id, @RequestBody EmployeeRequest request){
        try {
            Optional<Employee> employeeOptional = employeeRepository.findById(id);
            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                employee.setPhone(request.getPhone());
                employee.setName(request.getName());
                return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/employee/delete/{id}") //DELETE
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") BigInteger id){
        try {
            employeeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

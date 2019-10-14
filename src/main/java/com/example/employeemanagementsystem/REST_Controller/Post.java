//  Waheguru Ji!

package com.example.employeemanagementsystem.REST_Controller;

import com.example.employeemanagementsystem.Designation;
import com.example.employeemanagementsystem.Employee;
import com.example.employeemanagementsystem.service.DesignationService;
import com.example.employeemanagementsystem.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "")
public class Post {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    DesignationService designationService;

    @PostMapping(value = "/employees", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Adds a new Employee", response = Employee.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Employee added successfully"),
            @ApiResponse(code = 404, message = "Invalid details found for adding a new employee")
    })
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) throws URISyntaxException {
        if(employee.getEmployeeName() == null || employee.getEmployeeName().equals(""))
            return ResponseEntity.badRequest().build();
        if(!employee.getEmployeeName().chars().allMatch(Character::isLetter))
            return ResponseEntity.badRequest().build();

        Designation designation = designationService.getDesignation(employee.getJobTitle());
        if(designation.getDesignationID() == -1)
            return ResponseEntity.badRequest().build();
        employee.setJobID(designation);

        Employee manager = employeeService.getEmployeeByID(employee.getManagerID());
        if(!employee.getJobTitle().equals("director") && manager.getEmployeeID() == 0)
            return ResponseEntity.badRequest().build();
        if(!employee.getJobTitle().equals("director") && manager.getJobID().getLevelID() >= employee.getJobID().getLevelID())
            return ResponseEntity.badRequest().build();

        if(designation.getDesignation().equals("director") && employeeService.getDirector().getEmployeeID() != 0)
            return ResponseEntity.badRequest().build();

        employeeService.addEmployee(employee);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(employee.getEmployeeID()).toUri();
        return ResponseEntity.created(location).build();
    }

//    @PostMapping(value = "/designations", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<Designation> addDesignation(@RequestBody Designation designation) {
//
//    }
}

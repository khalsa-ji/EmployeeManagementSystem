//  Waheguru Ji!

package com.khalsa_ji.ems.REST_Controller;

import com.khalsa_ji.ems.Designation;
import com.khalsa_ji.ems.Employee;
import com.khalsa_ji.ems.service.DesignationService;
import com.khalsa_ji.ems.service.EmployeeService;
import com.khalsa_ji.ems.utils.Validator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * The class {@code Post} is a <em>REST Controller</em> class, which aims to listen to the
 * <strong>POST</strong> requests made at the <strong>"/api/v1"</strong> endpoint.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see Employee
 */

@RestController
@RequestMapping(value = "/api/v1")
public class Post {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    DesignationService designationService;

    /**
     * Method to register(add) a new instance of the {@code Employee} class
     *
     * @param employee An instance of the {@code Employee} class
     * @return A saved instance of the {@code Employee} class
     * @throws URISyntaxException URI Syntax Exception
     * @see Employee
     */

    @PostMapping(value = "/employees", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Adds a new Employee", response = Employee.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Employee added successfully"),
            @ApiResponse(code = 404, message = "Invalid details found for adding a new employee")
    })
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) throws URISyntaxException {
        //  Validating employee's name
        switch(Validator.validateString(employee.getEmployeeName())) {
            case nullString:        return ResponseEntity.badRequest().build();
            case emptyString:       return ResponseEntity.badRequest().build();
            case invalidString:     return ResponseEntity.badRequest().build();
        }

        //  Validating employee's job title(designation)
        switch(Validator.validateString(employee.getJobTitle())) {
            case nullString:        return ResponseEntity.badRequest().build();
            case emptyString:       return ResponseEntity.badRequest().build();
            case invalidString:     return ResponseEntity.badRequest().build();
        }

        Designation designation = designationService.getDesignation(employee.getJobTitle());

        if(designation.getLevelID() == -1)
            return ResponseEntity.badRequest().build();
        employee.setJobID(designation);

        Employee manager = employeeService.getEmployeeByID(employee.getManagerID());

        //  If employee's job title(designation) is not found to be Director
        if(!employee.getJobTitle().equals("Director")) {
            //  If manager id found to be invalid
            if(manager.getEmployeeID() == 0)
                return ResponseEntity.badRequest().build();

            //  If employee possesses a higher designation than its manager
            if(manager.getJobID().getLevelID() >= employee.getJobID().getLevelID())
                return ResponseEntity.badRequest().build();
        }

        //  If employee's job title(designation) is found to be Director and there already exists another Director
        if(designation.getDesignation().equals("Director") && employeeService.getDirector().getEmployeeID() != 0)
            return ResponseEntity.badRequest().build();

        employeeService.addEmployee(employee);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(employee.getEmployeeID()).toUri();

        return ResponseEntity.status(HttpStatus.CREATED).header("Location", location.toString()).body(employee);
    }
}

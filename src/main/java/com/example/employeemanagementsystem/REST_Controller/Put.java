//  Waheguru Ji!

package com.example.employeemanagementsystem.REST_Controller;

import com.example.employeemanagementsystem.Designation;
import com.example.employeemanagementsystem.Employee;
import com.example.employeemanagementsystem.builder.DesignationBuilder;
import com.example.employeemanagementsystem.builder.EmployeeBuilder;
import com.example.employeemanagementsystem.service.DesignationService;
import com.example.employeemanagementsystem.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/employees")
public class Put {
    @Autowired
    EmployeeService service;

    @Autowired
    DesignationService designationService;

    @PutMapping(value = "/{ID}", produces = "application/json")
    @ApiOperation(value = "Update information for an existing employee")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Information updated successfully"),
            @ApiResponse(code = 404, message = "Invalid details found for updating the employee information")
    })
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable(value = "ID") Long ID,
            @RequestParam(value = "replace", required = false) Boolean isReplace,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "mid", required = false) Long mID,
            @RequestParam(value = "jtitle", required = false) String jTitle
    ) {
        Designation designation = new DesignationBuilder().build();

        if(service.getEmployeeByID(ID).getEmployeeID() == 0)
            return ResponseEntity.badRequest().build();

        if(isReplace == null)   isReplace = false;
        if(jTitle != null) {
            designation = designationService.getDesignation(jTitle);
            if(designation.getDesignationID() == -1)
                return ResponseEntity.badRequest().build();
        }

        if(name != null && !name.chars().allMatch(Character::isLetter))
            return ResponseEntity.badRequest().build();

        Employee employee = new EmployeeBuilder()
                .setEmployeeName(name)
                .setJobTitle(jTitle)
                .setJobID(designation)
                .build();
        employee.setEmployeeID(ID);

        if(mID != null)     employee.setManagerID(mID);

        Employee manager = service.getEmployeeByID(employee.getManagerID());
        if(manager.getEmployeeID() == 0 && !employee.getJobTitle().equals("director"))
            return ResponseEntity.badRequest().build();
        if(manager.getJobID().getLevelID() >= employee.getJobID().getLevelID())
            return ResponseEntity.badRequest().build();

        if(isReplace) {
            //  Only applicable when a new employee is hired for a particular position directly.
            if(employee.getJobTitle() == null)         return ResponseEntity.badRequest().build();
            if(employee.getJobID() == null)            return ResponseEntity.badRequest().build();
            if(employee.getManagerID() == -1)          return ResponseEntity.badRequest().build();
            if(employee.getEmployeeName() == null)     return ResponseEntity.badRequest().build();

            employee = service.addEmployee(employee);
            Employee prevEmployee = service.getEmployeeByID(ID);

            List<Employee> reportingTo = service.getEmployeesByManagerID(prevEmployee.getEmployeeID());
            service.updateManager(reportingTo, employee.getEmployeeID());

            service.fire(prevEmployee);
            employee = service.addEmployee(employee);
            return ResponseEntity.ok(employee);
        }

        return ResponseEntity.ok(service.updateEmployee(employee));
    }
}

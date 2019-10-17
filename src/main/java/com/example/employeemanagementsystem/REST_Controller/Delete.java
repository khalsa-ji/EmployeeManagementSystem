//  Waheguru Ji!

package com.example.employeemanagementsystem.REST_Controller;

import com.example.employeemanagementsystem.Employee;
import com.example.employeemanagementsystem.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/employees")
@Api
public class Delete {
    @Autowired
    EmployeeService service;

    @DeleteMapping(value = "/{ID}", produces = "application/json")
    @ApiOperation(value = "Delete an employee by his/her employee id.", response = Employee.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Employee with the given employee id deleted successfully."),
            @ApiResponse(code = 404, message = "Employee with given employee id not found!"),
            @ApiResponse(code = 400, message = "Employee ID could not be negative."),       //  TODO Check 2 different responses for same HTTP Response Code.
            @ApiResponse(code = 400, message = "Director can't be deleted until there is no other employee reporting to him/her.")
    })
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("ID") Long ID) {
        if(ID < 0)
            return ResponseEntity.badRequest().build();

        Employee employee = service.getEmployeeByID(ID);
        if(employee.getEmployeeID() == 0)
            return ResponseEntity.notFound().build();

        if(employee.getJobTitle().equals("Director") && !service.getEmployeesByManagerID(employee.getEmployeeID()).isEmpty())
            return ResponseEntity.badRequest().build();

        service.deleteEmployee(ID);

        //  Formatting output
//        employee.setEmployeeName(employee.getEmployeeName().substring(0, 1).toUpperCase() + employee.getEmployeeName().substring(1));
//        employee.setJobTitle(employee.getJobTitle().substring(0, 1).toUpperCase() + employee.getJobTitle().substring(1));

//        return ResponseEntity.ok(employee);
        return ResponseEntity.noContent().build();
    }
}

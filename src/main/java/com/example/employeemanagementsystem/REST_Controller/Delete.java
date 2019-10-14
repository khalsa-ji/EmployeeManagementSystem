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
@RequestMapping(value = "/employees")
@Api
public class Delete {
    @Autowired
    EmployeeService service;

    @DeleteMapping(value = "/{ID}", produces = "application/json")
    @ApiOperation(value = "Delete an employee by his/her employee id.", response = Employee.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee with the given employee id deleted successfully."),
            @ApiResponse(code = 404, message = "Employee with given employee id not found!"),
            @ApiResponse(code = 400, message = "Director can't be deleted until there is no other employee reporting to him/her.")
    })
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("ID") Long ID) {
        Employee employee = service.getEmployeeByID(ID);
        if(employee.getEmployeeID() == 0)
            return ResponseEntity.notFound().build();

        if(employee.getJobTitle().equals("director") && !service.getEmployeesByManagerID(employee.getEmployeeID()).isEmpty())
            return ResponseEntity.badRequest().build();

        service.deleteEmployee(ID);
        return ResponseEntity.ok(employee);
    }
}

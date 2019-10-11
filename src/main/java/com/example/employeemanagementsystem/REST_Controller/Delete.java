//  Waheguru Ji!

package com.example.employeemanagementsystem.REST_Controller;

import com.example.employeemanagementsystem.Employee;
import com.example.employeemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController     @RequestMapping(value = "/api/employees")
public class Delete {
    @Autowired
    EmployeeService service;

    @DeleteMapping(value = "/{ID}", produces = "application/json")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("ID") Long ID) {
        Employee employee = service.getEmployeeByID(ID);
        if(employee.getEmployeeID() == 0)
            return ResponseEntity.badRequest().build();

        if(employee.getJobTitle().equals("director") && !service.getEmployeesByManagerID(employee.getEmployeeID()).isEmpty())
            return ResponseEntity.badRequest().build();

        service.deleteEmployee(ID);
        return ResponseEntity.ok(employee);
    }
}

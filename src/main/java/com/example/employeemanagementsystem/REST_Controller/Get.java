//  Waheguru Ji!

package com.example.employeemanagementsystem.REST_Controller;

import com.example.employeemanagementsystem.*;
import com.example.employeemanagementsystem.builder.EmployeeChartBuilder;
import com.example.employeemanagementsystem.service.DesignationService;
import com.example.employeemanagementsystem.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

import static com.example.employeemanagementsystem.EmployeeBrief.convertFrom;

@RestController
@RequestMapping(value = "/api/v1")
public class Get {
    @Autowired
    EmployeeService service;

    @Autowired
    DesignationService designationService;

    @GetMapping(value = "/employees", produces = "application/json")
    @ApiOperation(value = "Retrieves a list of all employees", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Currently there is no employee available"),
            @ApiResponse(code = 200, message = "Successfully retrieved the complete list")
    })
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employeeList = service.getAllEmployees();
        if(employeeList.isEmpty())  return ResponseEntity.notFound().build();

        //  TODO Remove this java sorting logic.
        employeeList.sort(ComparatorClass.customComparator);

        return ResponseEntity.ok(employeeList);
    }

    @GetMapping(value = "/employees/details", produces = "application/json")
    @ApiOperation(value = "Fetch detailed employee chart for all the employees", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the complete detailed list"),
            @ApiResponse(code = 404, message = "Currently there is no employee available")
    })
    public ResponseEntity<List<EmployeeChart>> getEmployeesDetails() {
        EmployeeBrief manager;
        EmployeeChart chart;
        List<EmployeeChart> list = new LinkedList<>();

//        List<EmployeeBrief> colleagues = new LinkedList<>();
//        List<EmployeeBrief> reportingTo = new LinkedList<>();
        List<Employee> colleagues;
        List<Employee> reportingTo;

        List<Employee> employeeList = service.getAllEmployees();
        if(employeeList.isEmpty())  return ResponseEntity.notFound().build();

        for(Employee employee : employeeList) {
            final EmployeeBrief emp = convertFrom(employee);
            manager = convertFrom(service.getEmployeeByID(employee.getManagerID()));

//            colleagues = convertFrom(service.getColleagues(manager.getEmployeeID(), emp.getEmployeeID()));
//            reportingTo = convertFrom(service.getReportees(emp.getEmployeeID()));
            colleagues = service.getEmployeesByManagerID(employee.getManagerID());
            colleagues.removeIf(colleague -> colleague.getEmployeeID() == emp.getEmployeeID());                 //  TODO SQL Sorting
            reportingTo = service.getEmployeesByManagerID(employee.getEmployeeID());    //  TODO SQL SOrting

            //  TODO Remove this java sorting logic.
            colleagues.sort(ComparatorClass.customComparator);
            reportingTo.sort(ComparatorClass.customComparator);
            //  ENDS HERE

            chart = new EmployeeChartBuilder()
                    .setEmployee(emp)
                    .setManager(manager)
                    .setColleagues(EmployeeBrief.convertFrom(colleagues))
                    .setReportingTo(EmployeeBrief.convertFrom(reportingTo))
                    .build();

            list.add(chart);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/employees/{ID}", produces = "application/json")
    @ApiOperation(value = "Fetch detailed employee chart for the given Employee ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched the required detailed employee chart"),
            @ApiResponse(code = 400, message = "Employee ID could not be negative."),
            @ApiResponse(code = 404, message = "Employee with the given Employee ID not found!")
    })
    public ResponseEntity<EmployeeChart> getEmployee(@PathVariable(value = "ID") Long ID) {
        if(ID < 0)
            return ResponseEntity.badRequest().build();

        Employee employee = service.getEmployeeByID(ID);
        if(employee.getEmployeeID() == 0)   return ResponseEntity.notFound().build();

        EmployeeBrief emp = convertFrom(employee);
        EmployeeBrief manager = convertFrom(service.getEmployeeByID(employee.getManagerID()));
        List<Employee> colleagues = service.getEmployeesByManagerID(employee.getManagerID());
        colleagues.removeIf(colleague -> colleague.getEmployeeID() == emp.getEmployeeID());                 //  TODO SQL Sorting
        List<Employee> reportingTo = service.getEmployeesByManagerID(employee.getEmployeeID());    //  TODO SQL SOrting

        //  TODO Remove this java sorting logic.
        colleagues.sort(ComparatorClass.customComparator);
        reportingTo.sort(ComparatorClass.customComparator);
        //  ENDS HERE

        EmployeeChart chart = new EmployeeChartBuilder()
                .setEmployee(emp)
                .setManager(manager)
                .setColleagues(convertFrom(colleagues))
                .setReportingTo(convertFrom(reportingTo))
                .build();
        return ResponseEntity.ok(chart);
    }

    @GetMapping(value = "designations", produces = "application/json")
    @ApiOperation(value = "Retrieves a list of all designations", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the complete list"),
            @ApiResponse(code = 404, message = "Currently there is no designation available")
    })
    public ResponseEntity<List<Designation>> getAllDesignations() {
        List<Designation> list = designationService.getAllDesignations();
        if(list.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/designations/{ID}", produces = "application/json")
    @ApiOperation(value = "Fetch designation details for the given Designation ID", response = Designation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Designation with given ID not found!"),
            @ApiResponse(code = 200, message = "Designation with given ID found successfully")
    })
    public ResponseEntity<Designation> getDesignation(@PathVariable(value = "ID") Integer ID) {
        Designation designation = designationService.getDesignationByID(ID);
        if(designation.getLevelID() == -1)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(designation);
    }
}

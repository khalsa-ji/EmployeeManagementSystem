//  Waheguru Ji!

package com.example.employeemanagementsystem.REST_Controller;

import com.example.employeemanagementsystem.*;
import com.example.employeemanagementsystem.builder.DesignationBuilder;
import com.example.employeemanagementsystem.builder.EmployeeBuilder;
import com.example.employeemanagementsystem.builder.EmployeeChartBuilder;
import com.example.employeemanagementsystem.service.DesignationService;
import com.example.employeemanagementsystem.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/api/v1/employees")
public class Put {
    @Autowired
    EmployeeService service;

    @Autowired
    DesignationService designationService;

    @PutMapping(value = "/{ID}", produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "Update information for an existing employee")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Information updated successfully"),
            @ApiResponse(code = 404, message = "Invalid details found for updating the employee information")
    })
    public ResponseEntity<EmployeeChart> updateEmployee(
            @PathVariable(value = "ID") Long ID,
            @RequestBody String jsonString
    ) {

        System.out.println("Waheguru Ji!");
        System.out.println("JsonString: " + jsonString);        //  TODO rm *

        //  Parsing Json String using Jackson.
        if(jsonString == null || jsonString == "" || jsonString == "{}" || jsonString == "{ }")
            return ResponseEntity.badRequest().build();

        String name = null, jTitle = null, mID = null, isReplace = null;
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> m = new HashMap<>();

        try {
            m = mapper.readValue(jsonString, Map.class);
        }catch(IOException e) {
            System.out.println("Json to Map - mapping error found!");
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        if(m.isEmpty())     return ResponseEntity.badRequest().build();

        //  Converting map key-value pairs to appropriate previous query string parameters.
        if(m.containsKey("name"))               name = m.get("name").toString();
        if(m.containsKey("jobTitle"))           jTitle = m.get("jobTitle").toString();
        if(m.containsKey("managerId"))          mID = String.valueOf(m.get("managerId"));
        if(m.containsKey("replace"))            isReplace = String.valueOf(m.get("replace"));

        Employee prevEmployee = service.getEmployeeByID(ID);
        Designation designation = new DesignationBuilder().build();

        if(prevEmployee.getEmployeeID() == 0)
            return ResponseEntity.badRequest().build();

        if(isReplace == null)   isReplace = "false";
        if(jTitle != null) {
            designation = designationService.getDesignation(jTitle);
            if(designation.getLevelID() == -1)
                return ResponseEntity.badRequest().build();
//            if(Boolean.parseBoolean(isReplace) && designation.getLevelID() > prevEmployee.getJobID().getLevelID())
//                return ResponseEntity.badRequest().build();
        }

        if(name != null && !Pattern.compile("^[ A-Za-z]+$").matcher(name).matches())
            return ResponseEntity.badRequest().build();

//        if(name != null && !name.chars().allMatch(Character::isLetter))         //  TODO Search for this line and replace with that in post i.e. alternative of this line --> regexp.
//            return ResponseEntity.badRequest().build();

        Employee employee = new EmployeeBuilder()
                .setEmployeeName(name)
                .setJobTitle(jTitle)
                .setJobID(designation)
                .build();

        if(m.containsKey("id") && Boolean.parseBoolean(isReplace))
            employee.setEmployeeID(Long.parseLong(String.valueOf(m.get("id"))));
        else
            employee.setEmployeeID(ID);

        if(mID != null)     employee.setManagerID(Integer.parseInt(mID));

        Employee manager = service.getEmployeeByID(prevEmployee.getManagerID());

        System.out.println("Map: " + m);                    //  TODO
        System.out.println("Prev Emp: " + prevEmployee);    //  TODO
        System.out.println("Emp: " + employee);       //  TODO
        System.out.println("Manager" + manager);        //  TODO

        if(manager.getEmployeeID() == -1 && employee.getJobTitle() != null &&  !employee.getJobTitle().equals("Director"))
            return ResponseEntity.badRequest().build();

        if(prevEmployee.getJobTitle().equals("Director") && employee.getJobID().getLevelID() > 1.0f)
            return ResponseEntity.badRequest().build();

        if(manager.getJobID() == null)
            return ResponseEntity.badRequest().build();

        if(employee.getJobID().getLevelID() != -1 && manager.getJobID().getLevelID() >= employee.getJobID().getLevelID())
            return ResponseEntity.badRequest().build();

        List<Employee> colleagues;
        List<Employee> reportingTo = service.getEmployeesByManagerID(ID);    //  TODO SQL SOrting
        EmployeeChart chart;

        if(manager.getJobID().getLevelID() >= employee.getJobID().getLevelID())
            return ResponseEntity.badRequest().build();


        for(Employee emp : reportingTo) {
            if(emp.getJobID().getLevelID() <= employee.getJobID().getLevelID())
                return ResponseEntity.badRequest().build();
        }


        if(Boolean.parseBoolean(isReplace)) {

            //  TODO CHeck if its possible to change default value of -1 for manager id to employee id in employeeBuilder class, and it would affect all conditions involving it.


            if(employee.getManagerID() == -1)          employee.setManagerID(prevEmployee.getManagerID());  //return ResponseEntity.badRequest().build();        //  Removed because it tends to fail Test Case 26 int he script.


            service.fire(prevEmployee);

            //  Only applicable when a new employee is hired for a particular position directly.
            if(service.getEmployeeByID(employee.getEmployeeID()).getManagerID() == -1) {
                if(employee.getJobTitle() == null)         return ResponseEntity.badRequest().build();
                if(employee.getEmployeeName() == null)     return ResponseEntity.badRequest().build();
                employee = service.addEmployee(employee);
            }

//            reportingTo = service.getEmployeesByManagerID(ID);

            service.updateManager(reportingTo, employee.getEmployeeID());

            System.out.println("Replaced Successfully!");       //  TODO rm*

                //  TODO Need to optimise variables and function calls.
            manager = service.getEmployeeByID(employee.getManagerID());
            colleagues = service.getEmployeesByManagerID(employee.getManagerID());
            Employee finalEmployee = employee;
            colleagues.removeIf(colleague -> colleague.getEmployeeID() == finalEmployee.getEmployeeID());                 //  TODO SQL Sorting
            reportingTo = service.getEmployeesByManagerID(employee.getEmployeeID());    //  TODO SQL SOrting

            //  TODO Remove this java sorting logic.
            colleagues.sort(ComparatorClass.customComparator);
            reportingTo.sort(ComparatorClass.customComparator);
            //  ENDS HERE

            chart = new EmployeeChartBuilder()
                    .setEmployee(EmployeeBrief.convertFrom(employee))
                    .setColleagues(EmployeeBrief.convertFrom(colleagues))
                    .setManager(EmployeeBrief.convertFrom(manager))
                    .setReportingTo(EmployeeBrief.convertFrom(reportingTo))
                    .build();

            return ResponseEntity.ok(chart);
        }

        System.out.println("Deleted successfully!");        //  TODO rm *

        if(employee.getEmployeeName() != null)          prevEmployee.setEmployeeName(employee.getEmployeeName());
        if(employee.getJobTitle() != null)              prevEmployee.setJobTitle(employee.getJobTitle());
        if(employee.getJobID().getLevelID() != -1)      prevEmployee.setJobID(employee.getJobID());
        if(employee.getManagerID() != -1)               prevEmployee.setManagerID(employee.getManagerID());

        employee = service.updateEmployee(prevEmployee);
        manager = service.getEmployeeByID(prevEmployee.getManagerID());

        colleagues = service.getEmployeesByManagerID(prevEmployee.getManagerID());
        Employee finalEmployee1 = prevEmployee;
        colleagues.removeIf(colleague -> colleague.getEmployeeID() == finalEmployee1.getEmployeeID());                 //  TODO SQL Sorting
//        reportingTo = service.getEmployeesByManagerID(prevEmployee.getEmployeeID());    //  TODO SQL SOrting

        //  TODO Remove this java sorting logic.
        colleagues.sort(ComparatorClass.customComparator);
        reportingTo.sort(ComparatorClass.customComparator);
        //  ENDS HERE

        System.out.println("Updated Employee: " + employee);        //  TODO

        chart = new EmployeeChartBuilder()
                .setEmployee(EmployeeBrief.convertFrom(employee))
                .setManager(EmployeeBrief.convertFrom(manager))
                .setColleagues(EmployeeBrief.convertFrom(colleagues))
                .setReportingTo(EmployeeBrief.convertFrom(reportingTo))
                .build();

        return ResponseEntity.ok(chart);
   }
}

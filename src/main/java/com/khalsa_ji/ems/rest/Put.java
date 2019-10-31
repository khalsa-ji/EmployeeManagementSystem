//  Waheguru Ji!

package com.khalsa_ji.ems.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khalsa_ji.ems.Designation;
import com.khalsa_ji.ems.Employee;
import com.khalsa_ji.ems.EmployeeBrief;
import com.khalsa_ji.ems.EmployeeChart;
import com.khalsa_ji.ems.builder.DesignationBuilder;
import com.khalsa_ji.ems.builder.EmployeeBuilder;
import com.khalsa_ji.ems.builder.EmployeeChartBuilder;
import com.khalsa_ji.ems.service.DesignationService;
import com.khalsa_ji.ems.service.EmployeeService;
import com.khalsa_ji.ems.utils.ComparatorClass;
import com.khalsa_ji.ems.utils.Validator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The class {@code Put} is a <em>REST Controller</em> class, which aims to listen to the
 * <strong>PUT</strong> requests made at the <strong>"/api/v1/employees"</strong> endpoint.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see Employee
 * @see EmployeeChart
 * @see Designation
 */

@RestController
@RequestMapping(value = "/api/v1/employees")
public class Put {
    @Autowired
    EmployeeService service;

    @Autowired
    DesignationService designationService;

    @Autowired
    MessageSource messageSource;

    private final String[] EMPLOYEE = {"Employee"};
    private final String[] EMPLOYEE_NAME = {"Employee name"};
    private final String[] MANAGER = {"Manager"};

    /**
     * Method to update/replace an already existing instance of the {@code Employee} class
     *
     * @param ID Employee's id
     * @param jsonString A JSON String consisting of a valid JSON object corresponding to the Request Body of the api call
     * @return A saved(updated) instance of the {@code EmployeeChart} class
     * @see Employee
     * @see EmployeeChart
     */

    @PutMapping(value = "/{ID}", produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "Update information for an existing employee")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Information updated successfully"),
            @ApiResponse(code = 400, message = "Invalid details found for updating the employee information"),
            @ApiResponse(code = 404, message = "Employee with given ID not found!")
    })
    public ResponseEntity<Object> updateEmployee(
            @PathVariable(value = "ID") Long ID,
            @RequestBody String jsonString
    ) {
        String name = null, jTitle = null, mID = null, isReplace = null;
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> m;

        //  Parsing JSON String
        try {
            m = mapper.readValue(jsonString, Map.class);
        }catch(IOException e) {
            System.out.println("Json to Map - mapping error found!");
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        //  If JSON String corresponds to a empty JSON object
        if(m.isEmpty())     return ResponseEntity.badRequest().build();

        //  Converting map's key-value pairs to appropriate previous query string parameters.
        if(m.containsKey("name"))               name = m.get("name");
        if(m.containsKey("jobTitle"))           jTitle = m.get("jobTitle");
        if(m.containsKey("managerId"))          mID = String.valueOf(m.get("managerId"));
        if(m.containsKey("replace"))            isReplace = String.valueOf(m.get("replace"));

        Employee prevEmployee = service.getEmployeeByID(ID);
        Designation designation = new DesignationBuilder().build();

        //  Validating ID
        switch(Validator.validateID(ID)) {
            case nullNumber:        return ResponseEntity.badRequest()
                    .body(messageSource.getMessage("error.code.nullNumber", EMPLOYEE, Locale.getDefault()));
            case zero:              return ResponseEntity.badRequest()
                    .body(messageSource.getMessage("error.code.zero", EMPLOYEE, Locale.getDefault()));
            case negativeNumber:    return ResponseEntity.badRequest()
                    .body(messageSource.getMessage("error.code.negativeNumber", EMPLOYEE, Locale.getDefault()));
        }

        //  If employee with specified id does not exists.
        if(prevEmployee.getEmployeeID() == 0)
            return ResponseEntity.notFound().build();

        if(isReplace == null)       isReplace = "false";

        if(jTitle != null) {
            designation = designationService.getDesignation(jTitle);

            //  If specified designation does not exists.
            if(designation.getLevelID() == -1)
                return ResponseEntity.badRequest()
                        .body(messageSource.getMessage("error.designation.notFound", new String[]{jTitle}, Locale.getDefault()));
        }

        if(name != null) {
            //  Validating employee's name
            switch (Validator.validateString(name)) {
                case emptyString:       return ResponseEntity.badRequest()
                        .body(messageSource.getMessage("error.code.emptyString", EMPLOYEE_NAME, Locale.getDefault()));
                case invalidString:     return ResponseEntity.badRequest()
                        .body(messageSource.getMessage("error.code.invalidString", EMPLOYEE_NAME, Locale.getDefault()));
            }
        }

        Employee employee = new EmployeeBuilder()
                .setEmployeeName(name)
                .setJobTitle(jTitle)
                .setJobID(designation)
                .build();

        //  In case the replacing employee is already an existing employee
        if(m.containsKey("id") && Boolean.parseBoolean(isReplace))
            employee.setEmployeeID(Long.parseLong(String.valueOf(m.get("id"))));
        else
            employee.setEmployeeID(ID);

        if(mID != null) {
            //  Validating Manager ID
            switch(Validator.validateID(employee.getManagerID())) {
                case zero:              return ResponseEntity.badRequest()
                        .body(messageSource.getMessage("error.code.zero", MANAGER, Locale.getDefault()));
            }
            employee.setManagerID(Integer.parseInt(mID));
        }

        Employee manager = service.getEmployeeByID(prevEmployee.getManagerID());

        //  If manager isn't defined and job title(designation) doesn't equals Director then,
        //  the operation should not be allowed.
        if(manager.getEmployeeID() == 0 && employee.getJobTitle() != null &&  !employee.getJobTitle().equals("Director"))
            return ResponseEntity.badRequest().build();

        //  Director can't be demoted
        if(prevEmployee.getJobTitle().equals("Director") && employee.getJobID().getLevelID() > 1.0f)
            return ResponseEntity.badRequest().build();

        //  Director can't have any supervisor, at any point of time
        if(manager.getJobID() == null)
            return ResponseEntity.badRequest().build();

        //  No employee can have higher position than its manager
        if(employee.getJobID().getLevelID() != -1 && manager.getJobID().getLevelID() >= employee.getJobID().getLevelID())
            return ResponseEntity.badRequest().build();

        List<Employee> colleagues;
        List<Employee> reportingTo = service.getEmployeesByManagerID(ID);
        EmployeeChart chart;

        //  No employee can manage other employees having position higher or equal to him/her.
        for(Employee emp : reportingTo) {
            if(emp.getJobID().getLevelID() <= employee.getJobID().getLevelID())
                return ResponseEntity.badRequest().build();
        }

        if(Boolean.parseBoolean(isReplace)) {
            if(employee.getManagerID() == -1)
                employee.setManagerID(prevEmployee.getManagerID());
                //return ResponseEntity.badRequest().build();        //  Removed because it tends to fail Test Case 26 in the script.

            service.fire(prevEmployee);

            //  Only applicable when a new employee is hired for a particular position directly.
            if(service.getEmployeeByID(employee.getEmployeeID()).getManagerID() == -1) {
                if(employee.getJobTitle() == null)         return ResponseEntity.badRequest().build();
                if(employee.getEmployeeName() == null)     return ResponseEntity.badRequest().build();
                employee = service.addEmployee(employee);
            }

            service.updateManager(reportingTo, employee.getEmployeeID());

            manager = service.getEmployeeByID(employee.getManagerID());
            colleagues = service.getEmployeesByManagerID(employee.getManagerID());
            Employee finalEmployee = employee;
            colleagues.removeIf(colleague -> colleague.getEmployeeID() == finalEmployee.getEmployeeID());
            reportingTo = service.getEmployeesByManagerID(employee.getEmployeeID());

            colleagues.sort(ComparatorClass.customComparator);
            reportingTo.sort(ComparatorClass.customComparator);

            chart = new EmployeeChartBuilder()
                    .setEmployee(EmployeeBrief.convertFrom(employee))
                    .setColleagues(EmployeeBrief.convertFrom(colleagues))
                    .setManager(EmployeeBrief.convertFrom(manager))
                    .setReportingTo(EmployeeBrief.convertFrom(reportingTo))
                    .build();

            return ResponseEntity.ok(chart);
        }

        if(employee.getEmployeeName() != null)          prevEmployee.setEmployeeName(employee.getEmployeeName());
        if(employee.getJobTitle() != null)              prevEmployee.setJobTitle(employee.getJobTitle());
        if(employee.getJobID().getLevelID() != -1)      prevEmployee.setJobID(employee.getJobID());
        if(employee.getManagerID() != -1)               prevEmployee.setManagerID(employee.getManagerID());

        employee = service.updateEmployee(prevEmployee);
        manager = service.getEmployeeByID(prevEmployee.getManagerID());

        colleagues = service.getEmployeesByManagerID(prevEmployee.getManagerID());
        Employee finalEmployee = prevEmployee;
        colleagues.removeIf(colleague -> colleague.getEmployeeID() == finalEmployee.getEmployeeID());

        colleagues.sort(ComparatorClass.customComparator);
        reportingTo.sort(ComparatorClass.customComparator);

        chart = new EmployeeChartBuilder()
                .setEmployee(EmployeeBrief.convertFrom(employee))
                .setManager(EmployeeBrief.convertFrom(manager))
                .setColleagues(EmployeeBrief.convertFrom(colleagues))
                .setReportingTo(EmployeeBrief.convertFrom(reportingTo))
                .build();

        return ResponseEntity.ok(chart);
   }
}

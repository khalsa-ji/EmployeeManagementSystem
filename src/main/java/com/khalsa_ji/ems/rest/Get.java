//  Waheguru Ji!

package com.khalsa_ji.ems.rest;

import com.khalsa_ji.ems.Designation;
import com.khalsa_ji.ems.Employee;
import com.khalsa_ji.ems.EmployeeBrief;
import com.khalsa_ji.ems.EmployeeChart;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * The class {@code Get} is a <em>REST Controller</em> class, which aims to listen to the
 * <strong>GET</strong> requests made at the <strong>"/api/v1"</strong> endpoint.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see Employee
 * @see EmployeeChart
 * @see Designation
 */

@RestController
@RequestMapping(value = "/api/v1")
public class Get {
    @Autowired
    EmployeeService service;

    @Autowired
    DesignationService designationService;

    @Autowired
    MessageSource messageSource;

    private final String[] EMPLOYEE = {"Employee"};
    private final String[] DESIGNATION = {"Designation"};

    /**
     * Method to fetch all instances of the {@code Employee} class
     *
     * @return List of all instances of the {@code Employee} class
     * @see Employee
     */

    @GetMapping(value = "/employees", produces = "application/json")
    @ApiOperation(value = "Retrieves a list of all employees", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Currently there is no employee available"),
            @ApiResponse(code = 200, message = "Successfully retrieved the complete list")
    })
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employeeList = service.getAllEmployees();
        if(employeeList.isEmpty())  return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeList);
    }

    /**
     * Method to fetch all instances of {@code EmployeeChart} class for
     * each and every instance of the {@code Employee} class
     *
     * @return List of all instances of the {@code EmployeeChart} class
     * @see Employee
     * @see EmployeeChart
     */

    @GetMapping(value = "/employees/details", produces = "application/json")
    @ApiOperation(value = "Fetch detailed employee chart for all the employees", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the complete detailed list"),
            @ApiResponse(code = 404, message = "Currently there is no employee available")
    })
    public ResponseEntity<List<EmployeeChart>> getEmployeesDetails() {
        Employee manager;
        EmployeeChart chart;
        List<Employee> colleagues;
        List<Employee> reportingTo;
        List<EmployeeChart> list = new LinkedList<>();

        List<Employee> employeeList = service.getAllEmployees();
        if(employeeList.isEmpty())  return ResponseEntity.notFound().build();

        //  Generating EmployeeChart for every employee present in the employeeList
        for(Employee employee : employeeList) {
            manager = service.getEmployeeByID(employee.getManagerID());
            colleagues = service.getEmployeesByManagerID(employee.getManagerID());
            reportingTo = service.getEmployeesByManagerID(employee.getEmployeeID());

            //  Removing the current employee from the colleagues list.
            colleagues.removeIf(colleague -> colleague.getEmployeeID() == employee.getEmployeeID());

            //  Ordering the list by designation's level id and employee's name
            colleagues.sort(ComparatorClass.customComparator);
            reportingTo.sort(ComparatorClass.customComparator);

            chart = new EmployeeChartBuilder()
                    .setEmployee(EmployeeBrief.convertFrom(employee))
                    .setManager(EmployeeBrief.convertFrom(manager))
                    .setColleagues(EmployeeBrief.convertFrom(colleagues))
                    .setReportingTo(EmployeeBrief.convertFrom(reportingTo))
                    .build();

            list.add(chart);
        }
        return ResponseEntity.ok(list);
    }

    /**
     * Method to fetch an instance of the {@code EmployeeChart} class derived from
     * an instance of the {@code Employee} class specified by the given employee id
     *
     * @param ID Employee's id
     * @return An instance of the {@code EmployeeChart} class
     * @see Employee
     * @see EmployeeChart
     */

    @GetMapping(value = "/employees/{ID}", produces = "application/json")
    @ApiOperation(value = "Fetch detailed employee chart for the given Employee ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched the required detailed employee chart"),
            @ApiResponse(code = 400, message = "Employee ID could not be negative."),
            @ApiResponse(code = 404, message = "Employee with the given Employee ID not found!")
    })
    public ResponseEntity<Object> getEmployee(@PathVariable(value = "ID") Long ID) {
        //  Validating ID
        switch(Validator.validateID(ID)) {
            case nullNumber:        return ResponseEntity.badRequest()
                    .body(messageSource.getMessage("error.code.nullNumber", EMPLOYEE, Locale.getDefault()));
            case zero:              return ResponseEntity.badRequest()
                    .body(messageSource.getMessage("error.code.zero", EMPLOYEE, Locale.getDefault()));
            case negativeNumber:    return ResponseEntity.badRequest()
                    .body(messageSource.getMessage("error.code.negativeNumber", EMPLOYEE, Locale.getDefault()));
        }

        Employee employee = service.getEmployeeByID(ID);

        //  If employee with specified id does not exists.
        if(employee.getEmployeeID() == 0)   return ResponseEntity.notFound().build();

        Employee manager = service.getEmployeeByID(employee.getManagerID());
        List<Employee> colleagues = service.getEmployeesByManagerID(employee.getManagerID());
        List<Employee> reportingTo = service.getEmployeesByManagerID(employee.getEmployeeID());

        //  Removing the current employee from the colleagues list.
        colleagues.removeIf(colleague -> colleague.getEmployeeID() == employee.getEmployeeID());

        //  Ordering the list by designation's level id and employee's name
        colleagues.sort(ComparatorClass.customComparator);
        reportingTo.sort(ComparatorClass.customComparator);

        EmployeeChart chart = new EmployeeChartBuilder()
                .setEmployee(EmployeeBrief.convertFrom(employee))
                .setManager(EmployeeBrief.convertFrom(manager))
                .setColleagues(EmployeeBrief.convertFrom(colleagues))
                .setReportingTo(EmployeeBrief.convertFrom(reportingTo))
                .build();
        return ResponseEntity.ok(chart);
    }

    /**
     * Method to fetch all instances of the {@code Designation} class
     *
     * @return List of all instances of the {@code Designation} class
     * @see Designation
     */

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

    /**
     * Method to fetch an instance of the {@code Designation} class specified by the given designation id
     *
     * @param ID Designation's id
     * @return An instance of the {@code Designation} class
     * @see Designation
     */

    @GetMapping(value = "/designations/{ID}", produces = "application/json")
    @ApiOperation(value = "Fetch designation details for the given Designation ID", response = Designation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Designation with given ID not found!"),
            @ApiResponse(code = 200, message = "Designation with given ID found successfully")
    })
    public ResponseEntity<Object> getDesignation(@PathVariable(value = "ID") Integer ID) {
        //  Validating ID
        switch(Validator.validateID(Long.valueOf(ID))) {
            case nullNumber:        return ResponseEntity.badRequest()
                    .body(messageSource.getMessage("error.code.nullNumber", DESIGNATION, Locale.getDefault()));
            case zero:              return ResponseEntity.badRequest()
                    .body(messageSource.getMessage("error.code.zero", DESIGNATION, Locale.getDefault()));
            case negativeNumber:    return ResponseEntity.badRequest()
                    .body(messageSource.getMessage("error.code.negativeNumber", DESIGNATION, Locale.getDefault()));
        }

        Designation designation = designationService.getDesignationByID(ID);

        //  If designation with specified id does not exists.
        if(designation.getDesignationID() == -1)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(designation);
    }
}

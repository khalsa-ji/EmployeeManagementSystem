//  Waheguru Ji!

package com.khalsa_ji.ems.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khalsa_ji.ems.Designation;
import com.khalsa_ji.ems.Employee;
import com.khalsa_ji.ems.EmployeeChart;
import com.khalsa_ji.ems.builder.DesignationBuilder;
import com.khalsa_ji.ems.builder.EmployeeBuilder;
import com.khalsa_ji.ems.service.DesignationService;
import com.khalsa_ji.ems.service.EmployeeService;
import com.khalsa_ji.ems.utils.Error;
import com.khalsa_ji.ems.utils.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *  The class {@code EmployeeController} is a <em>REST Controller</em> class, which aims to listen
 *  to the <strong>HTTP: GET, POST, DELETE </strong> and <strong>PUT</strong> requests made at the
 *  <strong>"/api/v1/employees"</strong> endpoint.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see Employee
 * @see EmployeeChart
 * @see Designation
 * @see Validator
 * @see Error
 */

@Api
@RestController
@RequestMapping(value = "/api/v1/employees")
public class EmployeeController {
    @Autowired
    EmployeeService service;

    @Autowired
    DesignationService designationService;

    @Autowired
    MessageSource messageSource;

    private Error error;
    private final String[] MANAGER = {"Manager"};
    private final String[] EMPLOYEE = {"Employee"};
    private final String[] EMPLOYEE_NAME = {"Employee name"};
    private final String[] JOB_TITLE = {"Job Title(Designation)"};

        //  +-------------------------------+
        //  |   GET Requests for Employee   |
        //  +-------------------------------+

    /**
     * Method to fetch all instances of the {@code Employee} class
     *
     * @return List of all instances of the {@code Employee} class
     * @see Employee
     */

    @GetMapping(value = "", produces = "application/json")
    @ApiOperation(value = "Retrieves a list of all employees", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the complete list")
    })
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(service.getAllEmployees());
    }

    /**
     * Method to fetch all instances of {@code EmployeeChart} class for
     * each and every instance of the {@code Employee} class
     *
     * @return List of all instances of the {@code EmployeeChart} class
     * @see Employee
     * @see EmployeeChart
     */

    @GetMapping(value = "/details", produces = "application/json")
    @ApiOperation(value = "Fetch detailed employee chart for all the employees", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the complete detailed list")
    })
    public ResponseEntity<List<EmployeeChart>> getEmployeesDetails() {
        List<EmployeeChart> list = new LinkedList<>();
        List<Employee> employeeList = service.getAllEmployees();

        //  Generating EmployeeChart for every employee present in the employeeList
        for(Employee employee : employeeList)
            list.add(EmployeeChart.generateFor(employee));

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
     * @see Error
     * @see Validator
     */

    @GetMapping(value = "/{ID}", produces = "application/json")
    @ApiOperation(value = "Fetch detailed employee chart for the given Employee ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched the required detailed employee chart"),
            @ApiResponse(code = 400, message = "Employee ID could not be negative."),
            @ApiResponse(code = 404, message = "Employee with the given Employee ID not found!")
    })
    public ResponseEntity<Object> getEmployee(@PathVariable(value = "ID") Long ID) {
        //  Validating ID
        error = Validator.validateID(ID, EMPLOYEE);
        if(error.getErrorCode() != Validator.code.ok)
            return ResponseEntity.badRequest().body(error.getErrorMessage());

        Employee employee = service.getEmployeeByID(ID);

        //  If employee with specified id does not exists.
        if(employee.getEmployeeID() == 0)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(EmployeeChart.generateFor(employee));
    }

        //  +-------------------------------+
        //  |   POST Requests for Employee  |
        //  +-------------------------------+

    /**
     * Method to register(add) a new instance of the {@code Employee} class
     *
     * @param employee An instance of the {@code Employee} class
     * @return A saved instance of the {@code Employee} class
     * @throws URISyntaxException URI Syntax Exception
     * @see Employee
     * @see Error
     * @see Validator
     */

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Adds a new Employee", response = Employee.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Employee added successfully"),
            @ApiResponse(code = 400, message = "Invalid details found for adding a new employee")
    })
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) throws URISyntaxException {
        //  Validating employee's name
        error = Validator.validateString(employee.getEmployeeName(), EMPLOYEE_NAME);
        if(error.getErrorCode() != Validator.code.ok)
            return ResponseEntity.badRequest().body(error.getErrorMessage());

        //  Validating employee's job title(designation)
        error = Validator.validateString(employee.getJobTitle(), JOB_TITLE);
        if(error.getErrorCode() != Validator.code.ok)
            return ResponseEntity.badRequest().body(error.getErrorMessage());

        //  Validating Manager ID
        error = Validator.validateID(employee.getManagerID(), MANAGER);

        //  Bypassing the validation check of Validator.code.negativeNumber
        //  as employee's manager id could be negative, specifically -1 in case of Director.
        if(error.getErrorCode() != Validator.code.ok && error.getErrorCode() != Validator.code.negativeNumber)
            return ResponseEntity.badRequest().body(error.getErrorMessage());

        Designation designation = designationService.getDesignation(employee.getJobTitle());

        //  If designation with specified id does not exists.
        if(designation.getLevelID() == -1)
            return ResponseEntity.badRequest()
                    .body(messageSource.getMessage("error.designation.notFound",
                            new String[]{employee.getJobTitle()}, Locale.getDefault()));

        employee.setJobID(designation);

        Employee manager = service.getEmployeeByID(employee.getManagerID());

        //  If employee's job title(designation) is not found to be Director
        if(!employee.getJobTitle().equals("Director")) {
            //  If manager id found to be invalid
            if(manager.getEmployeeID() == 0)
                return ResponseEntity.badRequest()
                        .body(messageSource.getMessage("error.employee.manager.notFound",
                                new Long[]{employee.getManagerID()}, Locale.getDefault()));

            //  If employee possesses a higher designation than its manager
            if(manager.getJobID().getLevelID() >= employee.getJobID().getLevelID())
                return ResponseEntity.badRequest()
                        .body(messageSource.getMessage("error.employee.jobID.higherThan.manager",
                                null, Locale.getDefault()));
        }

        //  If employee's job title(designation) is found to be Director and there already exists another Director
        if(designation.getDesignation().equals("Director") && !service.getEmployeesByManagerID(0L).isEmpty())
            return ResponseEntity.badRequest()
                    .body(messageSource.getMessage("error.director.alreadyExists", null, Locale.getDefault()));

        service.addEmployee(employee);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(employee.getEmployeeID()).toUri();

        return ResponseEntity.status(HttpStatus.CREATED).header("Location", location.toString()).body(employee);
    }

        //  +-----------------------------------+
        //  |   DELETE Requests for Employee    |
        //  +-----------------------------------+

    /**
     * Method to perform <strong>DELETE</strong> operation on the entity defined by the {@code Employee} class
     *
     * @param ID Employee's id
     * @return An instance of the {@code Employee} class, which has been deleted
     * @see Employee
     * @see Validator
     * @see Error
     */

    @DeleteMapping(value = "/{ID}", produces = "application/json")
    @ApiOperation(value = "Delete an employee by his/her employee id.", response = Employee.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Employee with the given employee id deleted successfully."),
            @ApiResponse(code = 404, message = "Employee with given employee id not found!"),
            @ApiResponse(code = 400, message = "Employee ID could not be negative."),       //  TODO Check 2 different responses for same HTTP Response Code.
            @ApiResponse(code = 400, message = "Director can't be deleted until there is no other employee reporting to him/her.")
    })
    public ResponseEntity<Object> deleteEmployee(@PathVariable("ID") Long ID) {
        //  Validating ID
        error = Validator.validateID(ID, EMPLOYEE);
        if(error.getErrorCode() != Validator.code.ok)
            return ResponseEntity.badRequest().body(error.getErrorMessage());

        Employee employee = service.getEmployeeByID(ID);

        //  If employee with specified id does not exists.
        if(employee.getEmployeeID() == 0)
            return ResponseEntity.notFound().build();

        //  If director is tried to be deleted and there is atleast one employee who reports to the director then,
        //  this operation should not be allowed.
        if(employee.getJobTitle().equals("Director") && !service.getEmployeesByManagerID(employee.getEmployeeID()).isEmpty())
            return ResponseEntity.badRequest()
                    .body(messageSource.getMessage("error.delete.director", null, Locale.getDefault()));

        service.deleteEmployee(ID);
        return ResponseEntity.noContent().build();
    }

        //  +-------------------------------+
        //  |   PUT Requests for Employee   |
        //  +-------------------------------+

    /**
     * Method to update/replace an already existing instance of the {@code Employee} class
     *
     * @param ID Employee's id
     * @param jsonString A JSON String consisting of a valid JSON object corresponding to the Request Body of the api call
     * @return A saved(updated) instance of the {@code EmployeeChart} class
     * @see Employee
     * @see EmployeeChart
     * @see Validator
     * @see Error
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
            System.err.println("Json to Map - mapping error found!");
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
        error = Validator.validateID(ID, EMPLOYEE);
        if(error.getErrorCode() != Validator.code.ok)
            return ResponseEntity.badRequest().body(error.getErrorMessage());

        //  If employee with specified id does not exists.
        if(prevEmployee.getEmployeeID() == 0)
            return ResponseEntity.notFound().build();

        if(isReplace == null)       isReplace = "false";

        if(jTitle != null) {
            //  Validating employee's jobTitle(designation)
            error = Validator.validateString(jTitle, JOB_TITLE);

            //  Not checking, explicitly for error.getErrorCode() as code.nullString
            //  because control would reach here only if it is not null.
            if(error.getErrorCode() != Validator.code.ok)
                return ResponseEntity.badRequest().body(error.getErrorMessage());

            designation = designationService.getDesignation(jTitle);

            //  If specified designation does not exists.
            if(designation.getLevelID() == -1)
                return ResponseEntity.badRequest()
                        .body(messageSource.getMessage("error.designation.notFound", new String[]{jTitle}, Locale.getDefault()));
        }

        if(name != null) {
            //  Validating employee's name
            error = Validator.validateString(name, EMPLOYEE_NAME);

            //  Not checking, explicitly for error.getErrorCode() as code.nullString
            //  because control would reach here only if it is not null.
            if(error.getErrorCode() != Validator.code.ok)
                return ResponseEntity.badRequest().body(error.getErrorMessage());
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
            error = Validator.validateID(employee.getManagerID(), MANAGER);

            //  1. Not checking, explicitly for error.getErrorCode() as code.nullNumber
            //     because control would reach here only if it is not null.
            //  2. Allowing error.getErrorCode() as code.negativeNumber because
            //     managerID could be -1 in case of Director.
            if(error.getErrorCode() == Validator.code.zero)
                return ResponseEntity.badRequest().body(error.getErrorMessage());

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

        List<Employee> reportingTo = service.getEmployeesByManagerID(ID);

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
            return ResponseEntity.ok(EmployeeChart.generateFor(employee));
        }

        if(employee.getEmployeeName() != null)          prevEmployee.setEmployeeName(employee.getEmployeeName());
        if(employee.getJobTitle() != null)              prevEmployee.setJobTitle(employee.getJobTitle());
        if(employee.getJobID().getLevelID() != -1)      prevEmployee.setJobID(employee.getJobID());
        if(employee.getManagerID() != -1)               prevEmployee.setManagerID(employee.getManagerID());

        employee = service.updateEmployee(prevEmployee);
        return ResponseEntity.ok(EmployeeChart.generateFor(employee));
    }
}

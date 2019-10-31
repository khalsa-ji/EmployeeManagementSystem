//  Waheguru Ji!

package com.khalsa_ji.ems.rest;

import com.khalsa_ji.ems.Employee;
import com.khalsa_ji.ems.service.EmployeeService;
import com.khalsa_ji.ems.utils.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * The class {@code Delete} is a <em>REST Controller</em> class, which aims to listen to the
 * <strong>DELETE</strong> requests made at the <strong>"/api/v1/employees"</strong> endpoint.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see Employee
 */

@Api
@RestController
@RequestMapping(value = "/api/v1/employees")
public class Delete {
    @Autowired
    EmployeeService service;

    @Autowired
    MessageSource messageSource;

    private final String[] EMPLOYEE = {"Employee"};

    /**
     * Method to perform <strong>DELETE</strong> operation on the entity defined by the {@code Employee} class
     *
     * @param ID Employee's id
     * @return An instance of the {@code Employee} class, which has been deleted
     * @see Employee
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
}

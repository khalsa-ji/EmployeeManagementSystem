//  Waheguru Ji!

package com.khalsa_ji.ems.rest;

import com.khalsa_ji.ems.Designation;
import com.khalsa_ji.ems.service.DesignationService;
import com.khalsa_ji.ems.utils.Error;
import com.khalsa_ji.ems.utils.Validator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The class {@code DesignationController} is a <em>REST Controller</em> class, which aims to listen
 *  to the <strong>HTTP: GET, POST, DELETE </strong> and <strong>PUT</strong> requests made at the
 *  <strong>"/api/v1/designations"</strong> endpoint.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see Designation
 * @see Validator
 * @see Error
 */

@RestController
@RequestMapping(value = "/api/v1/designations")
public class DesignationController {
    @Autowired
    DesignationService service;

    private Error error;
    private final String[] DESIGNATION = {"Designation"};

    //  +-----------------------------------+
    //  |   GET Requests for Designation    |
    //  +-----------------------------------+

    /**
     * Method to fetch all instances of the {@code Designation} class
     *
     * @return List of all instances of the {@code Designation} class
     * @see Designation
     */

    @GetMapping(value = "", produces = "application/json")
    @ApiOperation(value = "Retrieves a list of all designations", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the complete list")
    })
    public ResponseEntity<List<Designation>> getAllDesignations() {
        return ResponseEntity.ok(service.getAllDesignations());
    }

    /**
     * Method to fetch an instance of the {@code Designation} class specified by the given designation id
     *
     * @param ID Designation's id
     * @return An instance of the {@code Designation} class
     * @see Designation
     * @see Validator
     * @see Error
     */

    @GetMapping(value = "/{ID}", produces = "application/json")
    @ApiOperation(value = "Fetch designation details for the given Designation ID", response = Designation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Designation with given ID not found!"),
            @ApiResponse(code = 200, message = "Designation with given ID found successfully")
    })
    public ResponseEntity<Object> getDesignation(@PathVariable(value = "ID") Integer ID) {
        //  Validating ID
        error = Validator.validateID(Long.valueOf(ID), DESIGNATION);
        if(error.getErrorCode() != Validator.code.ok)
            return ResponseEntity.badRequest().body(error.getErrorMessage());

        Designation designation = service.getDesignationByID(ID);

        //  If designation with specified id does not exists.
        if(designation.getDesignationID() == -1)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(designation);
    }
}

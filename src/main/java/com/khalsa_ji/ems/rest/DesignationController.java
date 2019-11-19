//  Waheguru Ji!

//  NOTE:
//  1. From the term 'immediate inferior designation', it refers to the said in context of the 'immediate superior designation', as provided in the input.

package com.khalsa_ji.ems.rest;

import com.khalsa_ji.ems.Designation;
import com.khalsa_ji.ems.builder.DesignationBuilder;
import com.khalsa_ji.ems.service.DesignationService;
import com.khalsa_ji.ems.service.EmployeeService;
import com.khalsa_ji.ems.utils.Error;
import com.khalsa_ji.ems.utils.Validator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    @Autowired
    EmployeeService employeeService;

    @Autowired
    MessageSource messageSource;

    private Error error;
    private final String[] DESIGNATION = {"Designation"};
    private static final String[] IMMEDIATE_SUPERIOR_DESIGNATION = {"Immediate superior designation"};

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
        if (error.getErrorCode() != Validator.code.ok)
            return ResponseEntity.badRequest().body(error.getErrorMessage());

        Designation designation = service.getDesignationByID(ID);

        //  If designation with specified id does not exists.
        if (designation.getDesignationID() == -1)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(designation);
    }

        //  +------------------------------------+
        //  |   POST Requests for Designation    |
        //  +------------------------------------+

    //  TODO Swagger, JavaDocs, Class Diagrams
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addDesignation(@RequestBody Map<String, String> requestBody) {
        boolean isSibling = false;
        int immediateSuperiorDesignationID = 0;
        float levelID = 0f;
        String designationName = null;

        //  Exteracting map's key-value pairs, appropriately.
        if(requestBody.containsKey("designation"))
            designationName = requestBody.get("designation");
        if(requestBody.containsKey("immediateSuperiorDesignationID"))
            immediateSuperiorDesignationID = Integer.parseInt(requestBody.get("immediateSuperiorDesignationID"));
        if(requestBody.containsKey("isSibling"))
            isSibling = Boolean.parseBoolean(requestBody.get("isSibling"));

        //  Validating immediateSuperiorDesignationID
        error = Validator.validateID((long) immediateSuperiorDesignationID, IMMEDIATE_SUPERIOR_DESIGNATION);

        //  Bypassing the validation check of Validator.code.negativeNumber
        //  as immediate superior designation id could be negative, specifically -1 in case of the highest designation, yet.
        if(error.getErrorCode() != Validator.code.ok && error.getErrorCode() != Validator.code.negativeNumber)
            return ResponseEntity.badRequest().body(error.getErrorMessage());

        //  Validating designation's name(job title)
        error = Validator.validateString(designationName, DESIGNATION);
        if(error.getErrorCode() != Validator.code.ok) {
            return ResponseEntity.badRequest().body(error.getErrorMessage());
        }

        Designation immediateSuperiorDesignation = service.getDesignationByID(immediateSuperiorDesignationID);

        //  If designation with specified id does not exists and is not the first designation to be added i.e. immediate superior designation id is equal to -1.
        if(immediateSuperiorDesignationID != -1 && immediateSuperiorDesignation.getDesignationID() == 0)
            return ResponseEntity.notFound().build();

        Designation immediateInferiorDesignation = service.getImmediateInferiorDesignation(immediateSuperiorDesignation.getLevelID());

            //  Calculating new designation's levelID based on different conditions possible.

        //  If there is no immediate superior as well as no immediate inferior designation i.e. there is no designation, yet.
        if(immediateSuperiorDesignation.getDesignationID() == 0 && immediateInferiorDesignation.getDesignationID() == 0)
            levelID = 1f;

        //  If there is no immediate superior designation i.e. the new designation is the highest designation, yet.
        else if(immediateSuperiorDesignation.getDesignationID() == 0)
            levelID = immediateInferiorDesignation.getLevelID() / 2f;

        //  If there is no immediate inferior designation i.e. the new designation is the lowest designation, yet.
        else if(immediateInferiorDesignation.getDesignationID() == 0) {
            if(immediateSuperiorDesignation.getLevelID() - (int) immediateSuperiorDesignation.getLevelID() > 0)
                levelID = (float) Math.ceil(immediateSuperiorDesignation.getLevelID());
            else
                levelID = immediateSuperiorDesignation.getLevelID() + 1f;
        }

        // If there exists both immediate superior as well as immediate inferior designation.
        else {
            //  If the new designation is supposed to be at the same level as the immediate inferior designation(s) of the immediate superior designation.
            if(isSibling)
                levelID = immediateInferiorDesignation.getLevelID();

            //  IF the new designation is suppossed to be at a level between the immediate superior designation and immediate inferior designation.
            else
                levelID = immediateSuperiorDesignation.getLevelID() + ((immediateInferiorDesignation.getLevelID() - immediateSuperiorDesignation.getLevelID()) / 2f);
        }

        Designation designation = new DesignationBuilder()
                .setDesignation(designationName)
                .setLevelID(levelID).build();

        designation = service.addDesignation(designation);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(designation.getDesignationID()).toUri();

        return ResponseEntity.status(HttpStatus.CREATED).header("Location", location.toString()).body(designation);
    }

        //  +-----------------------------------+
        //  |   PUT Requests for Designation    |
        //  +-----------------------------------+


        //  +--------------------------------------+
        //  |   DELETE Requests for Designation    |
        //  +--------------------------------------+

    @DeleteMapping(value = "/{ID}", produces = "application/json")
    public ResponseEntity<Object> deleteDesignation(@PathVariable("ID") Integer ID) {
        //  Validating ID
        error = Validator.validateID((long) ID, DESIGNATION);
        if(error.getErrorCode() != Validator.code.ok)
            return ResponseEntity.badRequest().body(error.getErrorMessage());

        Designation designation = service.getDesignationByID(ID);

        //  If designation with specified id does not exists.
        if(designation.getDesignationID() == 0)
            return ResponseEntity.notFound().build();

        //  DELETE operation not to be allowed if there exists atleast one employee, who has the jobTitle(designation) same as the one to be deleted.
        if(!employeeService.getEmployeesByJobID(ID).isEmpty())
            return ResponseEntity.badRequest()
                    .body(messageSource.getMessage("error.delete.designation", null, Locale.getDefault()));

        service.deleteDesignation(ID);
        return ResponseEntity.noContent().build();
    }
}

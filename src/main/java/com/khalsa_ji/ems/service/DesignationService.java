//  Waheguru Ji!

package com.khalsa_ji.ems.service;

import com.khalsa_ji.ems.Designation;
import com.khalsa_ji.ems.builder.DesignationBuilder;
import com.khalsa_ji.ems.repository.DesignationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The class {@code DesignationService} is a <em>service class</em> that provides a layer of abstraction over the
 * functionality provided by the {@code DesignationRepository} interface.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see DesignationRepository
 */

@Service
public class DesignationService {
    @Autowired
    DesignationRepository repository;

    private final Logger logger = LoggerFactory.getLogger(DesignationService.class);

    /**
     * Method to fetch an instance of {@code Designation} class specified by its id
     *
     * @param ID Designation's id
     * @return An instance of {@code Designation} class of specified id
     * @see Designation
     */

    public Designation getDesignationByID(Integer ID) {
        logger.info("getDesignationByID() called.");
        logger.debug("PARAMETER ID[Integer] --> {}.", ID);
        Designation designation = repository.findByDesignationID(ID);
        logger.debug("Designation found --> {}", designation);
        if(designation == null)     return new DesignationBuilder().build();
        return designation;
    }

    /**
     * Method to fetch an instance of {@code Designation} class specified by its designation name
     *
     * @param jobTitle Designation name
     * @return An instance of {@code Designation} class of specified designation name
     * @see Designation
     */

    public Designation getDesignation(String jobTitle) {
        logger.info("getDesignation() called.");
        logger.debug("PARAMETER jobTitle[String] --> {}.", jobTitle);
        Designation designation = repository.findByDesignation(jobTitle);
        logger.debug("Designation found --> {}", designation);
        if(designation == null)     return new DesignationBuilder().build();
        return designation;
    }

    /**
     * Method to fetch all instances of the {@code Designation} class
     *
     * @return List of all instances of {@code Designation} class
     * @see Designation
     */

    public List<Designation> getAllDesignations() {
        logger.info("getAllDesignations() called.");
        List<Designation> designations = repository.findAll();
        logger.debug("Designations: ");
        if(logger.isDebugEnabled()) {
            long ctr = 0;
            for(Designation designation : designations)
                logger.debug("{}. {}", ++ctr, designation);
        }
        return designations;
    }

    public Designation addDesignation(Designation designation) {
        logger.info("addDesignation() called.");
        logger.debug("PARAMETER designation[Designation] --> {}.", designation);
        return repository.save(designation);
    }

    //  TODO immediate(superior/inferior)designation[id] --> names are too long. Need to make them more crisp.
    public Designation getImmediateInferiorDesignation(Float levelID) {
        logger.info("getImmediateInferiorDesignation() called.");
        logger.debug("PARAMETER levelID[Integer] --> {}.", levelID);
        Designation designation = repository.findImmediateInferiorDesignation(levelID);
        logger.debug("Designation found --> {}.", designation);
        if(designation == null)     return new DesignationBuilder().build();
        return designation;
    }

    public Designation deleteDesignation(Integer ID) {
        logger.info("deleteDesignation() called.");
        logger.debug("PARAMETER ID[Integer] --> {}.", ID);
        Designation designation = repository.findByDesignationID(ID);
        logger.debug("Designation found --> {}", designation);
        if(designation == null)    return new DesignationBuilder().build();
        repository.deleteById(ID);
        logger.info("Designation with ID = {} was deleted successfully.", ID);
        return designation;
    }
}

//  Waheguru Ji!

package com.khalsa_ji.ems.service;

import com.khalsa_ji.ems.Designation;
import com.khalsa_ji.ems.builder.DesignationBuilder;
import com.khalsa_ji.ems.repository.DesignationRepository;
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

    /**
     * Method to fetch an instance of {@code Designation} class specified by its id
     *
     * @param ID Designation's id
     * @return An instance of {@code Designation} class of specified id
     * @see Designation
     */

    public Designation getDesignationByID(Integer ID) {
        Designation designation = repository.findByDesignationID(ID);
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
        Designation designation = repository.findByDesignation(jobTitle);
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
        return repository.findAll();
    }
}

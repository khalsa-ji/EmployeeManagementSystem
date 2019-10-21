//  Waheguru Ji!

package com.khalsa_ji.ems.repository;

import com.khalsa_ji.ems.Designation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface {@code DesignationRepository} defines various methods to play around with the
 * attributes and tuples of the <em>Designation</em> table described by the {@code Designation} class.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see Designation
 */

public interface DesignationRepository extends JpaRepository<Designation, Integer> {
    /**
     * Method to fetch an instance of {@code Designation} class by specifying its id
     *
     * @param ID Designation's id
     * @return An instance of {@code Designation} class of specified id
     * @see Designation
     */

    Designation findByDesignationID(Integer ID);

    /**
     * Method to fetch an instance of {@code Designation} class by specifying its designation name
     *
     * @param designation Designation name
     * @return An instance of {@code Designation} class of specified designation's name
     * @see Designation
     */

    Designation findByDesignation(String designation);
}

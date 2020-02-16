//  Waheguru Ji!

package com.khalsa_ji.ems.builder;

import com.khalsa_ji.ems.Designation;

/**
 * The class {@code DesignationBuilder} is a <em>Builder</em> class for the {@code Designation} class.
 * It helps in instantiating instances of {@code Designation} class by providing a set of methods based on the
 * <em>Builder Design Pattern</em>.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see Designation
 */

public class DesignationBuilder {
    private int designationID;
    private float levelID;
    private String designation;

    /**
     * Default constructor for instantiating an instance of the {@code DesignationBuilder} class
     * with some set of valid default field values.
     */

    public DesignationBuilder() {
        this.designationID = -1;
        this.levelID = -1.0f;
    }

    /**
     * Method to set designation's level id
     * @param levelID Designation's level id
     * @return {@code DesignationBuilder} instance with updated values
     */

    public DesignationBuilder setLevelID(float levelID) {
        this.levelID = levelID;
        return this;
    }

    /**
     * Method to set designation's name
     * @param designation Designation's name
     * @return {@code DesignationBuilder} instance with updated values
     */

    public DesignationBuilder setDesignation(String designation) {
        this.designation = designation;
        return this;
    }

    /**
     * Method to instantiate an instance of the {@code Designation} class
     * @return {@code Designation} class instance
     *
     * @see Designation
     */

    public Designation build() {
        return new Designation(levelID, designation);
    }
}

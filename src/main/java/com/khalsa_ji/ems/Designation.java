//  Waheguru Ji!

//  NOTE:
//  1. jobID in Employee is same as designationID in Designation
//  2. jobTitle in Employee is same as designation in Designation

package com.khalsa_ji.ems;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khalsa_ji.ems.builder.DesignationBuilder;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * The class {@code Designation} defines some useful properties for designations(Job titles) in any organisation,
 * methods to play around with those defined properties and also acts as a database entity.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see DesignationBuilder
 */

@Entity
@Table(name = "designation")
public class Designation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "designation_id", nullable = false, unique = true)
    @ApiModelProperty(notes = "System generated unique Designation ID")
    private int designationID;

    @Column(name = "level_id", nullable = false)
    @JsonIgnore
    @ApiModelProperty(notes = "Level ID for the designation")
    private float levelID;

    @Column(name = "designation", nullable = false)
    @ApiModelProperty(notes = "Designation of the employee")
    private String designation;

    //  Default constructor is made private, intentionally.
    //  So as to allow creation of objects through the DesignationBuilder class.
    //  It assures that no instance of this class could be instantiated with invalid default field values.
    private Designation() {}

    /**
     * Constructor for creating an instance of this {@code Designation} class.
     * It is not recommended to create an instance of this {@code Designation} class using it,
     * instead instantiate objects using the {@code DesignationBuilder} class.
     *
     * @param levelID Designation's level id
     * @param designation Employee's designation(job title)
     *
     * @see DesignationBuilder
     */

    public Designation(float levelID, String designation) {
        this.levelID = levelID;
        this.designation = designation;
    }

    /**
     * Method to fetch designation's id
     * @return Designation's id
     */

    public int getDesignationID() {
        return designationID;
    }

    /**
     * Method to fetch designation's level id
     * @return Designation's level id
     */

    public float getLevelID() {
        return levelID;
    }

    /**
     * Method to fetch designation's name
     * @return Designation's name
     */

    public String getDesignation() {
        return designation;
    }

    /**
     * Method to represent {@code Designation} class object in {@code java.lang.String} format
     * @return String({@code java.lang.String}) representation of {@code Designation} class object
     */

    @Override
    public String toString() {
        return "Designation{" +
                "designationID=" + designationID +
                ", levelID=" + levelID +
                ", designation='" + designation + '\'' +
                '}';
    }
}

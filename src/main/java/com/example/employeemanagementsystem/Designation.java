//  Waheguru Ji!

//  NOTES:
//  jobID in Employee is same as designationID in Designation
//  jobTitle in Employee is same as designation in Designation

package com.example.employeemanagementsystem;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity     @Table(name = "designation")
public class Designation {
    @Id     @GeneratedValue(strategy = GenerationType.IDENTITY)     @Column(name = "designation_id", nullable = false, unique = true)
    private int designationID;
    @Column(name = "level_id", nullable = false)    @JsonIgnore
    private float levelID;
    @Column(name = "designation", nullable = false)
    private String designation;

    public Designation() {}

    public Designation(float levelID, String designation) {
        this.levelID = levelID;
        this.designation = designation;
    }

    public int getDesignationID() {
        return designationID;
    }

    public float getLevelID() {
        return levelID;
    }

    public String getDesignation() {
        return designation;
    }

    @Override
    public String toString() {
        return "Designation{" +
                "designationID=" + designationID +
                ", levelID=" + levelID +
                ", designation='" + designation + '\'' +
                '}';
    }
}

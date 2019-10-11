//  Waheguru Ji!

package com.example.employeemanagementsystem.builder;

import com.example.employeemanagementsystem.Designation;

public class DesignationBuilder {
    private int designationID;
    private float levelID;
    private String designation;

    public DesignationBuilder() {
        this.designationID = -1;
    }

    public DesignationBuilder setLevelID(float levelID) {
        this.levelID = levelID;
        return this;
    }

    public DesignationBuilder setDesignation(String designation) {
        this.designation = designation;
        return this;
    }

    public Designation build() {
        return new Designation(levelID, designation);
    }
}

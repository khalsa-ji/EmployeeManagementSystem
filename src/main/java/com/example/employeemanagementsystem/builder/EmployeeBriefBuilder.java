//  Waheguru Ji!

package com.example.employeemanagementsystem.builder;

import com.example.employeemanagementsystem.EmployeeBrief;

public class EmployeeBriefBuilder {
    private long employeeID;
    private String employeeName;
    private String jobTitle;

    public EmployeeBriefBuilder setEmployeeID(long employeeID) {
        this.employeeID = employeeID;
        return this;
    }

    public EmployeeBriefBuilder setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    public EmployeeBriefBuilder setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public EmployeeBrief build() {
        return new EmployeeBrief(employeeID, employeeName, jobTitle);
    }
}

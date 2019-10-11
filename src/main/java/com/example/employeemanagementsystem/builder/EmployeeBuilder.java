//  Waheguru Ji!

package com.example.employeemanagementsystem.builder;

import com.example.employeemanagementsystem.Designation;
import com.example.employeemanagementsystem.Employee;

public class EmployeeBuilder {
    private long employeeID;
    private long managerID;
    private Designation jobID;
    private String employeeName;
    private String jobTitle;

    public EmployeeBuilder() {
        managerID = -1;
    }

    public EmployeeBuilder setManagerID(long managerID) {
        this.managerID = managerID;
        return this;
    }

    public EmployeeBuilder setJobID(Designation jobID) {
        this.jobID = jobID;
        return this;
    }

    public EmployeeBuilder setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    public EmployeeBuilder setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public Employee build() {
        return new Employee(managerID, jobID, employeeName, jobTitle);
    }
}

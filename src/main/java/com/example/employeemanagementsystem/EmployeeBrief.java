//  Waheguru Ji!

package com.example.employeemanagementsystem;

import com.example.employeemanagementsystem.builder.EmployeeBriefBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedList;
import java.util.List;

public class EmployeeBrief {
    @JsonProperty("id")
    @ApiModelProperty(notes = "Employee ID")
    private long employeeID;

    @JsonProperty("name")
    @ApiModelProperty(notes = "Name of the employee")
    private String employeeName;

    @ApiModelProperty(notes = "Job title of the employee")
    private String jobTitle;

    public EmployeeBrief(long employeeID, String employeeName, String jobTitle) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.jobTitle = jobTitle;
    }

    public long getEmployeeID() {
        return employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    @Override
    public String toString() {
        return "EmployeeBrief{" +
                "employeeID=" + employeeID +
                ", employeeName='" + employeeName + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }

    public static EmployeeBrief convertFrom(Employee employee) {
        return new EmployeeBriefBuilder()
                .setEmployeeID(employee.getEmployeeID())
                .setEmployeeName(employee.getEmployeeName())
                .setJobTitle(employee.getJobTitle())
                .build();
    }

    public static List<EmployeeBrief> convertFrom(List<Employee> employeeList) {
        List<EmployeeBrief> list = new LinkedList<>();
        for(Employee employee : employeeList)   list.add(convertFrom(employee));
        return list;
    }
}

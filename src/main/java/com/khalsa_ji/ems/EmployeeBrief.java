//  Waheguru Ji!

package com.khalsa_ji.ems;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khalsa_ji.ems.builder.EmployeeBriefBuilder;
import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedList;
import java.util.List;

/**
 * The class {@code EmployeeBrief} defines some set of properties inspired from the {@code Employee} class.
 * It provides a layer of abstraction over the {@code Employee} class and provides limited functionality,
 * as compared to the {@code Employee} class.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see Employee
 * @see EmployeeBriefBuilder
 */

public class EmployeeBrief {
    @JsonProperty("id")
    @ApiModelProperty(notes = "Employee ID")
    private long employeeID;

    @JsonProperty("name")
    @ApiModelProperty(notes = "Name of the employee")
    private String employeeName;

    @ApiModelProperty(notes = "Job title of the employee")
    private String jobTitle;

    //  Default constructor is made private, intentionally.
    //  So as to allow creation of objects through the EmployeeBriefBuilder class.
    //  It assures that no instance of this class could be instantiated with invalid default field values.
    private EmployeeBrief() {}

    /**
     * Constructor for creating an instance of this {@code EmployeeBrief} class.
     * It is not recommended to create an instance of this {@code EmployeeBrief} class using it,
     * instead instantiate objects using the {@code EmployeeBriefBuilder} class.
     *
     * @param employeeID Employee's id
     * @param employeeName Employee's full name
     * @param jobTitle Employee's job title(designation)
     *
     * @see EmployeeBriefBuilder
     */

    public EmployeeBrief(long employeeID, String employeeName, String jobTitle) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.jobTitle = jobTitle;
    }

    /**
     * Method to fetch employee's id
     * @return Employee's id
     */

    public long getEmployeeID() {
        return employeeID;
    }

    /**
     * Method to fetch employee's full name
     * @return Employee's full name
     */

    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * Method to fetch employee's job title(designation)
     * @return Employee's job title(designation)
     */

    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Method to represent {@code EmployeeBrief} class object in {@code java.lang.String} format
     * @return String({@code java.lang.String}) representation of {@code EmployeeBrief} class object
     */

    @Override
    public String toString() {
        return "EmployeeBrief{" +
                "employeeID=" + employeeID +
                ", employeeName='" + employeeName + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }

    /**
     * Method to abstract information from an instance of {@code Employee} class.
     * Basically, it is an <em>adapter</em> method to convert a {@code Employee} class instance
     * into a {@code EmployeeBrief} class instance.
     *
     * @param employee {@code Employee} class instance
     * @return {@code EmployeeBrief} class instance
     *
     * @see Employee
     */

    public static EmployeeBrief convertFrom(Employee employee) {
        return new EmployeeBriefBuilder()
                .setEmployeeID(employee.getEmployeeID())
                .setEmployeeName(employee.getEmployeeName())
                .setJobTitle(employee.getJobTitle())
                .build();
    }

    /**
     * Method to abstract information from a list of instances of {@code Employee} class.
     * Basically, it is an <em>adapter</em> method to convert a list of {@code Employee} class instances
     * into a list of {@code EmployeeBrief} class instances.
     *
     * @param employeeList List of {@code Employee} class instances
     * @return List of {@code EmployeeBrief} class instances
     *
     * @see Employee
     */

    public static List<EmployeeBrief> convertFrom(List<Employee> employeeList) {
        List<EmployeeBrief> list = new LinkedList<>();
        for(Employee employee : employeeList)   list.add(convertFrom(employee));
        return list;
    }
}

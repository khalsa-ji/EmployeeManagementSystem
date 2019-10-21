//  Waheguru Ji!

//  NOTE:
//  1. jobID in Employee is same as designationID in Designation.
//  2. jobTitle in Employee is same as designation in Designation.

package com.khalsa_ji.ems;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.khalsa_ji.ems.builder.EmployeeBuilder;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * The class {@code Employee} defines various real world properties of an employee in any organisation,
 * methods to play around with those defined properties and also acts as a database entity.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see EmployeeBuilder
 */

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false, unique = true)
    @ApiModelProperty(notes = "System generated unique Employee ID")
    private long employeeID;

    @JsonProperty("managerId")
    @Column(name = "manager_id", nullable = false)
    @ApiModelProperty(notes = "Manager ID(Employee ID of the Manager)")
    private long managerID;

    @OneToOne
    @JoinColumn(name = "job_id")
    @JsonIgnore
    @ApiModelProperty(notes = "Job ID of the employee. Assigned by the system based on the job title.")
    private Designation jobID;

    @JsonProperty("name")
    @Column(name = "employee_name", nullable = false)
    @ApiModelProperty(notes = "Name of the employee")
    private String employeeName;

    @Column(name = "job_title", nullable = false)
    @ApiModelProperty(notes = "Job title of the employee")
    private String jobTitle;

    //  Default constructor is made private, intentionally.
    //  So as to allow creation of objects through the EmployeeBuilder class.
    //  It assures that no instance of this class could be instantiated with invalid default field values.
    private Employee() {}

    /**
     * Constructor for creating an instance of this {@code Employee} class.
     * It is not recommended to create an instance of this {@code Employee} class using it,
     * instead instantiate objects using the {@code EmployeeBuilder} class.
     *
     * @param managerID         Employee's manager id(Employee id of the manager)
     * @param jobID             Employee's job id, an instance of {@code Designation} class
     * @param employeeName      Employee's full name
     * @param jobTitle          Employee's job title(designation)
     *
     * @see EmployeeBuilder
     * @see Designation
     */

    public Employee(long managerID, Designation jobID, String employeeName, String jobTitle) {
        this.managerID = managerID;
        this.jobID = jobID;
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
     * Method to fetch employee's manager id(Employee id of the manger)
     * @return Employee's manager id
     */

    public long getManagerID() {
        return managerID;
    }

    /**
     * Method to fetch employee's job id
     * @return Employee's job id, an instance of {@code designation} class
     *
     * @see Designation
     */

    public Designation getJobID() {
        return jobID;
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
     * Method to set employee's id
     * @param employeeID Employee's id
     */

    public void setEmployeeID(long employeeID) {
        this.employeeID = employeeID;
    }

    /**
     * Method to set employee's manager id(Employee id of the manager)
     * @param managerID Employee's manager id(Employee id of the manager)
     */

    public void setManagerID(long managerID) {
        this.managerID = managerID;
    }

    /**
     * Method to set employee's job id
     * @param jobID Employee's job id, as an instance of {@code Designation} class
     *
     * @see Designation
     */

    public void setJobID(Designation jobID) {
        this.jobID = jobID;
    }

    /**
     * Method to set employee's full name
     * @param employeeName Employee's full name
     */

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * Method to set employee's job title(designation)
     * @param jobTitle Employee's job title(designation)
     */

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Method to represent {@code Employee} class object in {@code java.lang.String} format
     * @return String({@code java.lang.String}) representation of {@code Employee} class object
     */

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", managerID=" + managerID +
                ", jobID=" + jobID +
                ", employeeName='" + employeeName + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }
}

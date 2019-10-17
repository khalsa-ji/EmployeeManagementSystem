//  Waheguru Ji!

//  NOTES:
//  jobID in Employee is same as designationID in Designation
//  jobTitle in Employee is same as designation in Designation

package com.example.employeemanagementsystem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

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

    public Employee() {}

    public Employee(long managerID, Designation jobID, String employeeName, String jobTitle) {
        this.managerID = managerID;
        this.jobID = jobID;
        this.employeeName = employeeName;
        this.jobTitle = jobTitle;
    }

    public long getEmployeeID() {
        return employeeID;
    }

    public long getManagerID() {
        return managerID;
    }

    public Designation getJobID() {
        return jobID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setEmployeeID(long employeeID) {
        this.employeeID = employeeID;
    }

    public void setManagerID(long managerID) {
        this.managerID = managerID;
    }

    public void setJobID(Designation jobID) {
        this.jobID = jobID;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

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

//  Waheguru Ji!

package com.example.employeemanagementsystem;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class EmployeeChart {
    @ApiModelProperty(notes = "Brief information of the employee")
    private EmployeeBrief employee;

    @ApiModelProperty(notes = "Brief information of the employee's manager")
    private EmployeeBrief manager;

    @ApiModelProperty(notes = "Complete list of employee's colleagues")
    private List<EmployeeBrief> colleagues;

//    @JsonProperty(value = "subordinates")
    @ApiModelProperty(notes = "Complete list of employees those who directly reports the given employee")
    private List<EmployeeBrief> reportingTo;

    public EmployeeChart(EmployeeBrief employee, EmployeeBrief manager, List<EmployeeBrief> colleagues, List<EmployeeBrief> reportingTo) {
        this.employee = employee;
        this.manager = manager;
        this.colleagues = colleagues;
        this.reportingTo = reportingTo;
    }

    public EmployeeBrief getEmployee() {
        return employee;
    }

    public EmployeeBrief getManager() {
        return manager;
    }

    public List<EmployeeBrief> getColleagues() {
        return colleagues;
    }

    public List<EmployeeBrief> getReportingTo() {
        return reportingTo;
    }

    @Override
    public String toString() {
        return "EmployeeChart{" +
                "employee=" + employee +
                ", manager=" + manager +
                ", colleagues=" + colleagues +
                ", reportingTo=" + reportingTo +
                '}';
    }
}

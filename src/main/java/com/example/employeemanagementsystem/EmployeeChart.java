//  Waheguru Ji!

package com.example.employeemanagementsystem;

import java.util.List;

public class EmployeeChart {
    private EmployeeBrief employee;
    private EmployeeBrief manager;
    private List<EmployeeBrief> colleagues;
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

//  Waheguru Ji!

package com.example.employeemanagementsystem.builder;

import com.example.employeemanagementsystem.EmployeeBrief;
import com.example.employeemanagementsystem.EmployeeChart;

import java.util.List;

public class EmployeeChartBuilder {
    private EmployeeBrief employee;
    private EmployeeBrief manager;
    private List<EmployeeBrief> colleagues;
    private List<EmployeeBrief> reportingTo;

    public EmployeeChartBuilder setEmployee(EmployeeBrief employee) {
        this.employee = employee;
        return this;
    }

    public EmployeeChartBuilder setManager(EmployeeBrief manager) {
        this.manager = manager;
        return this;
    }

    public EmployeeChartBuilder setColleagues(List<EmployeeBrief> colleagues) {
        this.colleagues = colleagues;
        return this;
    }

    public EmployeeChartBuilder setReportingTo(List<EmployeeBrief> reportingTo) {
        this.reportingTo = reportingTo;
        return this;
    }

    public EmployeeChart build() {
        return new EmployeeChart(employee, manager, colleagues, reportingTo);
    }
}

//  Waheguru Ji!

package com.khalsa_ji.ems.builder;

import com.khalsa_ji.ems.EmployeeBrief;
import com.khalsa_ji.ems.EmployeeChart;

import java.util.List;

/**
 * The class {@code EmployeeChartBuilder} is a <em>Builder</em> class for the {@code EmployeeChart} class.
 * It helps in instantiating instances of {@code EmployeeChart} class by providing a set of methods based on the
 * <em>Builder Design Pattern</em>.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see EmployeeChart
 * @see EmployeeBrief
 */

public class EmployeeChartBuilder {
    private EmployeeBrief employee;
    private EmployeeBrief manager;
    private List<EmployeeBrief> colleagues;
    private List<EmployeeBrief> reportingTo;

    /**
     * Method to set employee's information
     * @param employee Employee's information, as an instance of {@code EmployeeBrief} class
     * @return {@code EmployeeChartBuilder} instance with updated values
     *
     * @see EmployeeBrief
     */

    public EmployeeChartBuilder setEmployee(EmployeeBrief employee) {
        this.employee = employee;
        return this;
    }

    /**
     * Method to set employee's manager information
     * @param manager Employee's manager information, as an instance of {@code EmployeeBrief} class
     * @return {@code EmployeeChartBuilder} instance with updated values
     *
     * @see EmployeeBrief
     */

    public EmployeeChartBuilder setManager(EmployeeBrief manager) {
        this.manager = manager;
        return this;
    }

    /**
     * Method to set employee's colleagues information
     * @param colleagues List of employee's colleagues information, as an instance of {@code EmployeeBrief} class
     * @return {@code EmployeeChartBuilder} instance with updated values
     *
     * @see EmployeeBrief
     */

    public EmployeeChartBuilder setColleagues(List<EmployeeBrief> colleagues) {
        this.colleagues = colleagues;
        return this;
    }

    /**
     * Method to set employee's subordinates information
     * @param reportingTo List of employee's subordinates information, as an instance of {@code EmployeeBrief} class
     * @return {@code EmployeeChartBuilder} instance with updated values
     *
     * @see EmployeeBrief
     */

    public EmployeeChartBuilder setReportingTo(List<EmployeeBrief> reportingTo) {
        this.reportingTo = reportingTo;
        return this;
    }

    /**
     * Method to instantiate an instance of the {@code EmployeeChart} class
     * @return {@code EmployeeChart} class instance
     *
     * @see EmployeeChart
     */

    public EmployeeChart build() {
        return new EmployeeChart(employee, manager, colleagues, reportingTo);
    }
}

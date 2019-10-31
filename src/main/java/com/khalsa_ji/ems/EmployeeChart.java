//  Waheguru Ji!

package com.khalsa_ji.ems;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * The {@code EmployeeChart} class defines various properties of a typical employee information system/dashboard
 * along with methods to play around with those properties.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see EmployeeBrief
 */

public class EmployeeChart {
    @ApiModelProperty(notes = "Brief information of the employee")
    private EmployeeBrief employee;

    @ApiModelProperty(notes = "Brief information of the employee's manager")
    private EmployeeBrief manager;

    @ApiModelProperty(notes = "Complete list of employee's colleagues")
    private List<EmployeeBrief> colleagues;

    @JsonProperty(value = "subordinates")
    @ApiModelProperty(notes = "Complete list of employees those who directly reports the given employee")
    private List<EmployeeBrief> reportingTo;

    //  Default constructor is made private, intentionally.
    //  So as to allow creation of objects through the DesignationBuilder class.
    //  It assures that no instance of this class could be instantiated with invalid default field values.
    private EmployeeChart() {}

    /**
     * Constructor for creating an instance of this {@code EmployeeChart} class.
     * It is not recommended to create an instance of this {@code EmployeeChart} class using it,
     * instead instantiate objects using the {@code EmployeeChartBuilder} class.
     *
     * @param employee      Employee information as an instance of {@code EmployeeBrief} class
     * @param manager       Employee's manager information as an instance of {@code EmployeeBrief} class
     * @param colleagues    List of employee's colleagues information as an instance of {@code EmployeeBrief} class
     * @param reportingTo   List of employee's subordinates information as an instance of {@code EmployeeBrief} class
     *
     * @see EmployeeBrief
     */

    public EmployeeChart(EmployeeBrief employee, EmployeeBrief manager, List<EmployeeBrief> colleagues, List<EmployeeBrief> reportingTo) {
        this.employee = employee;
        this.manager = manager;
        this.colleagues = colleagues;
        this.reportingTo = reportingTo;
    }

    /**
     * Method to fetch employee's information
     * @return Employee's information as an instance of {@code EmployeeBrief} class
     *
     * @see EmployeeBrief
     */

    public EmployeeBrief getEmployee() {
        return employee;
    }

    /**
     * Method to fetch employee's manager information
     * @return Employee's manager information as an instance of {@code EmployeeBrief} class
     *
     * @see EmployeeBrief
     */

    public EmployeeBrief getManager() {
        return manager;
    }

    /**
     * Method to fetch list of employee's colleagues information
     * @return Employee's colleagues information as an instance of {@code EmployeeBrief} class
     *
     * @see EmployeeBrief
     */

    public List<EmployeeBrief> getColleagues() {
        return colleagues;
    }

    /**
     * Method to fetch list of employee's subordinates information
     * @return Employee's subordinates information as an instance of {@code EmployeeBrief} class
     *
     * @see EmployeeBrief
     */

    public List<EmployeeBrief> getReportingTo() {
        return reportingTo;
    }

    /**
     * Method to represent {@code EmployeeChart} class object in {@code java.lang.String} format
     * @return String({@code java.lang.String}) representation of {@code EmployeeChart} class object
     */

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

//  Waheguru Ji!

package com.khalsa_ji.ems;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khalsa_ji.ems.builder.EmployeeChartBuilder;
import com.khalsa_ji.ems.service.EmployeeService;
import com.khalsa_ji.ems.utils.ComparatorClass;
import com.khalsa_ji.ems.utils.SpringContext;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * The {@code EmployeeChart} class defines various properties of a typical employee information system/dashboard
 * along with methods to play around with those properties.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see Employee
 * @see EmployeeBrief
 * @see SpringContext
 */

public class EmployeeChart {
    private static EmployeeService service;

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
     * Method to initialise an instance of the {@code MessageSource} interface's implementation,
     * a workaround for @Autowired not working in Non-Spring managed class
     *
     * @see SpringContext
     */

    private static void initialise() {
        ApplicationContext context = SpringContext.getAppContext();
        service = (EmployeeService) context.getBean("employeeService");
    }

    /**
     * Method to generate a typical employee information system dashboard
     *
     * @param employee An instance of the {@code Employee} class
     * @return An instance of the {@code EmployeeChart} class
     * @see Employee
     */

    public static EmployeeChart generateFor(Employee employee) {
        if(service == null)
            initialise();

        Employee manager = service.getEmployeeByID(employee.getManagerID());
        List<Employee> colleagues = service.getEmployeesByManagerID(employee.getManagerID());
        List<Employee> reportingTo = service.getEmployeesByManagerID(employee.getEmployeeID());

        //  Removing the current employee from the colleagues list.
        colleagues.removeIf(colleague -> colleague.getEmployeeID() == employee.getEmployeeID());

        //  Ordering the list by designation's level id and employee's name
        colleagues.sort(ComparatorClass.customComparator);
        reportingTo.sort(ComparatorClass.customComparator);

        return new EmployeeChartBuilder()
                .setEmployee(EmployeeBrief.convertFrom(employee))
                .setManager(EmployeeBrief.convertFrom(manager))
                .setColleagues(EmployeeBrief.convertFrom(colleagues))
                .setReportingTo(EmployeeBrief.convertFrom(reportingTo))
                .build();
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

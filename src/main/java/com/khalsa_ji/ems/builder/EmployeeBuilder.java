//  Waheguru Ji!

package com.khalsa_ji.ems.builder;

import com.khalsa_ji.ems.Designation;
import com.khalsa_ji.ems.Employee;

/**
 * The class {@code EmployeeBuilder} is a <em>Builder</em> class for the {@code Employee} class.
 * It helps in instantiating instances of {@code Employee} class by providing a set of methods based on the
 * <em>Builder Design Pattern</em>.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see Employee
 * @see Designation
 */

public class EmployeeBuilder {
    private long employeeID;
    private long managerID;
    private Designation jobID;
    private String employeeName;
    private String jobTitle;

    /**
     * Default constructor for instantiating an instance of the {@code EmployeeBuilder} class
     * with some set of valid default field values.
     */

    public EmployeeBuilder() {
        managerID = -1;
    }

    /**
     * Method to set employee's manager id(Employee id of the manager)
     * @param managerID Employee's manager id(Employee id of the manager)
     * @return {@code EmployeeBuilder} instance with updated values
     */

    public EmployeeBuilder setManagerID(long managerID) {
        this.managerID = managerID;
        return this;
    }

    /**
     * Method to set employee's job id
     * @param jobID Employee's job id, as an instance of {@code Designation} class
     * @return {@code EmployeeBuilder} instance with updated values
     *
     * @see Designation
     */

    public EmployeeBuilder setJobID(Designation jobID) {
        this.jobID = jobID;
        return this;
    }

    /**
     * Method to set employee's full name
     * @param employeeName Employee's full name
     * @return {@code EmployeeBuilder} instance with updated values
     */

    public EmployeeBuilder setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    /**
     * Method to set employee's job title(designation)
     * @param jobTitle Employee's job title(designation)
     * @return {@code EmployeeBuilder} instance with updated values
     */

    public EmployeeBuilder setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    /**
     * Method to instantiate an instance of the {@code Employee} class
     * @return {@code Employee} class instance
     *
     * @see Employee
     */

    public Employee build() {
        return new Employee(managerID, jobID, employeeName, jobTitle);
    }
}

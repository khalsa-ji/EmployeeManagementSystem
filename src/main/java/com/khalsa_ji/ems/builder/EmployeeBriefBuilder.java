//  Waheguru Ji!

package com.khalsa_ji.ems.builder;

import com.khalsa_ji.ems.EmployeeBrief;

/**
 * The class {@code EmployeeBriefBuilder} is a <em>Builder</em> class for the {@code EmployeeBrief} class.
 * It helps in instantiating instances of {@code EmployeeBrief} class by providing a set of methods based on the
 * <em>Builder Design Pattern</em>.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see EmployeeBrief
 */

public class EmployeeBriefBuilder {
    private long employeeID;
    private String employeeName;
    private String jobTitle;

    /**
     * Method to set employee's id
     * @param employeeID Employee's id
     * @return {@code EmployeeBriefBuilder} instance with updated values
     */

    public EmployeeBriefBuilder setEmployeeID(long employeeID) {
        this.employeeID = employeeID;
        return this;
    }

    /**
     * Method to set employee's full name
     * @param employeeName Employee's full name
     * @return {@code EmployeeBriefBuilder} instance with updated values
     */

    public EmployeeBriefBuilder setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    /**
     * Method to set employee's job title(designation)
     * @param jobTitle Employee's job title(designation)
     * @return {@code EmployeeBriefBuilder} instance with updated values
     */

    public EmployeeBriefBuilder setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    /**
     * Method to instantiate an instance of the {@code EmployeeBrief} class
     * @return {@code EmployeeBrief} class instance
     *
     * @see EmployeeBrief
     */

    public EmployeeBrief build() {
        return new EmployeeBrief(employeeID, employeeName, jobTitle);
    }
}

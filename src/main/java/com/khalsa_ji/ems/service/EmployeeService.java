//  Waheguru Ji!

package com.khalsa_ji.ems.service;

import com.khalsa_ji.ems.Employee;
import com.khalsa_ji.ems.builder.EmployeeBuilder;
import com.khalsa_ji.ems.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The class {@code EmployeeService} is a <em>service class</em> that provides a layer of abstraction over the
 * functionality provided by the {@code EmployeeRepository} interface.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see EmployeeRepository
 */

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository repository;

    @Autowired
    DesignationService service;

    private final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    /**
     * Method to fetch an instance of {@code Employee} class specified by its id
     *
     * @param ID Employee's id
     * @return An instance of {@code Employee} class of specified id
     * @see Employee
     */

    public Employee getEmployeeByID(Long ID) {
        logger.info("getEmployeeByID() called.");
        logger.debug("PARAMETER ID[Long] --> {}.", ID);
        Employee employee = repository.findByEmployeeID(ID);
        logger.debug("Employee found --> {}", employee);
        if(employee == null)    return new EmployeeBuilder().build();
        return employee;
    }

    /**
     * Method to fetch all instances of {@code Employee} class
     *
     * @return List of all instances of {@code Employee} class
     * @see Employee
     */

    public List<Employee> getAllEmployees() {
        logger.info("getAllEmployees() called.");
        List<Employee> employees = repository.findAllByOrderByJobID_levelIDAscEmployeeNameAsc();
        logger.debug("Employees: ");
        if(logger.isDebugEnabled()) {
            long ctr = 0;
            for (Employee employee : employees)
                logger.debug("{}. {}", ++ctr, employee);
        }
        return employees;
    }

    /**
     * Method to fetch all instances of the {@code Employee} class
     * having specified manager id(Employee id of the manager)
     *
     * @param managerID Manager id(Employee id of the manager)
     * @return List of all instances of {@code Employee} class having specified manager id
     * @see Employee
     */

    public List<Employee> getEmployeesByManagerID(Long managerID) {
        logger.info("getEmployeesByManagerID() called.");
        logger.debug("PARAMETER managerID[Long]", managerID);
        List<Employee> employees = repository.findAllByManagerID(managerID);
        logger.debug("Employees: ");
        if(logger.isDebugEnabled()) {
            long ctr = 0;
            for(Employee employee : employees)
                logger.debug("{}. {}", ++ctr, employee);
        }
        return employees;
    }

//    public List<Employee> getColleagues(Long managerID, Long ID) {
//        return repository.findColleagues(managerID, ID);
//    }

//    public List<Employee> getReportees(Long ID) {
//        return repository.findReportees(ID);
//    }

    /**
     * Method to register(add) a new instance of {@code Employee} class
     *
     * @param employee An instance of {@code Employee} class
     * @return A saved(updated) instance of {@code Employee} class
     * @see Employee
     */

    public Employee addEmployee(Employee employee) {
        logger.info("addEmployee() called.");
        logger.debug("PARAMETER employee[Employee] --> {}.", employee);
        return repository.save(employee);
    }

    /**
     * Method to update manager of list of instances of {@code Employee} class
     *
     * @param list List of instances of {@code Employee} class
     * @param managerID Manager id(Employee id of the manager)
     * @return List of saved(updated) instances of {@code Employee} class
     * @see Employee
     */

    public List<Employee> updateManager(List<Employee> list, Long managerID) {
        logger.info("updateManager() called.");
        logger.debug("PARAMETER managerID[Long] --> {}.", managerID);
        logger.debug("PARAMETER list[List<Employee>] --> {}.", list);
        for(Employee employee : list) {
            logger.debug("Updating managerID for employee --> {}.", employee);
            employee.setManagerID(managerID);
            repository.save(employee);
        }
        return list;
    }

    /**
     * Method to update details of an instance of {@code Employee} class
     *
     * @param employee An instance of {@code Employee} class
     * @return A saved(updated) instance of {@code Employee} class
     * @see Employee
     */

    public Employee updateEmployee(Employee employee) {
        logger.info("updateEmployee() called.");
        logger.debug("PARAMETER employee[Employee] --> {}.", employee);
        return repository.save(employee);
    }

    /**
     * Method to delete an instance of the {@code Employee} class specified by its id
     * and also updates manager id of its subordinates to the manager id of its manager
     *
     * @param ID Employee's id
     * @return An instance of the {@code Employee} class that has been deleted
     * @see Employee
     */

    public Employee deleteEmployee(Long ID) {
        logger.info("deleteEmployee() called.");
        logger.debug("PARAMETER ID[Long] --> {}.", ID);
        Employee employee = repository.findByEmployeeID(ID);
        logger.debug("Employee found --> {}", employee);
        if(employee == null)    return new EmployeeBuilder().build();
        repository.deleteById(ID);
        logger.info("Employee with ID = {} was deleted successfully.", ID);
        repository.updateManagerID(employee.getEmployeeID(), employee.getManagerID());
        logger.info("Updated managerID for all subordinates of employee with ID = {} to {}.", ID, employee.getManagerID());
        return employee;
    }

    /**
     * Method to fire(delete) an instance of the {@code Employee} class specified by its id,
     * without updating the manager id of its subordinates. It is used in the case of replacing an
     * existing instance of the {@code Employee} class with an another instance of the same.
     *
     * @param employee An instance of the {@code Employee} class
     * @see Employee
     */

    public void fire(Employee employee) {
        logger.info("fire() called.");
        logger.debug("PARAMETER employee[Employee] --> {}.", employee);
        repository.deleteById(employee.getEmployeeID());
    }

    public List<Employee> getEmployeesByJobID(Integer ID) {
        logger.info("getEmployeesByJobID() called.");
        logger.debug("PARAMETER ID[Integer] --> {}.", ID);
        List<Employee> employees = repository.findByJobID_DesignationID(ID);
        logger.debug("Employees: ");
        if(logger.isDebugEnabled()) {
            long ctr = 0;
            for (Employee employee : employees)
                logger.debug("{}. {}", ++ctr, employee);
        }
        return employees;
    }
}

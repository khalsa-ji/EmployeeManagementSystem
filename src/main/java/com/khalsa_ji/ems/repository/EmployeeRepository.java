//  Waheguru Ji!

package com.khalsa_ji.ems.repository;

import com.khalsa_ji.ems.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The interface {@code EmployeeRepository} defines various methods to play around with the
 * attributes and tuples of the <em>Employee</em> table described by the {@code Employee} class.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see Employee
 */

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    /**
     * Method to fetch an instance of {@code Employee} class specified by its id
     *
     * @param ID Employee's id
     * @return An instance of {@code Employee} class of specified id
     * @see Employee
     */

    Employee findByEmployeeID(Long ID);

    /**
     * Method to fetch a list of {@code Employee} class instances of specified manager id
     *
     * @param managerID Employee's manager id(Employee id of the manager)
     * @return List of {@code Employee} class instances of specified manager id
     * @see Employee
     */

    List<Employee> findAllByManagerID(Long managerID);

    /**
     * Method to fetch all instances of {@code Employee} class and order them by their designation's level id and employee name
     *
     * @return List of {@code Employee} class instances in an ordered manner
     * @see Employee
     */

    List<Employee> findAllByOrderByJobID_levelIDAscEmployeeNameAsc();

    /**
     * Method to update all instances of {@code Employee} class having a specific manager id(Employee id of the manager)
     * with a given manager id
     *
     * @param prevID    Previous manager id
     * @param newID     New manager id
     * @return          An integer value describing the number of affected tuples(rows)
     * @see Employee
     */

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE employee e SET e.manager_id = ?2 WHERE e.manager_id = ?1", nativeQuery = true)
    int updateManagerID(Long prevID, Long newID);

//    @Query(value = "SELECT e.employee_id, e.employee_name, e.job_title, e.manager_id FROM employee e " +
//            "INNER JOIN designation d ON e.job_id = d.designation_id " +
//            "WHERE e.manager_id = ?1 AND e.employee_id <> ?2 " +
//            "ORDER BY d.level_id, e.employee_name", nativeQuery = true)
//    List<Employee> findColleagues(Long managerID, Long ID);

//    @Query(value = "SELECT e.employee_id, e.employee_name, e.job_title, e.manager_id FROM employee e " +
//            "INNER JOIN designation d ON e.job_id = d.designation_id " +
//            "WHERE e.manager_id = ?1 " +
//            "ORDER BY d.level_id, e.employee_name", nativeQuery = true)
//    List<Employee> findReportees(Long ID);
}

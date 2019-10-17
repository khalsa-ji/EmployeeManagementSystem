//  Waheguru Ji!

package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmployeeID(Long ID);
    Employee findByManagerID(Long managerID);
    List<Employee> findAllByManagerID(Long managerID);
    List<Employee> findAllByOrderByJobID_levelIDAscEmployeeNameAsc();

    @Query(value = "SELECT e.employee_id, e.employee_name, e.job_title, e.manager_id FROM employee e, designation d WHERE e.manager_id = ?1 AND e.employee_id <> ?2 ORDER BY d.level_id, e.employee_name", nativeQuery = true)
    List<Employee> findColleagues(Long managerID, Long ID);

    @Query(value = "SELECT e.employee_id, e.employee_name, e.job_title, e.manager_id FROM employee e, designation d WHERE e.manager_id = ?1 ORDER BY d.level_id, e.employee_name", nativeQuery = true)
    List<Employee> findReportees(Long ID);

    @Modifying(clearAutomatically = true)   @Transactional
    @Query(value = "UPDATE employee e SET e.manager_id = ?2 WHERE e.manager_id = ?1", nativeQuery = true)
    int updateManagerID(Long prevID, Long newID);



//    @Query("select e.employee_id, e.employee_name, e.job_title, e.manager_id from employee e inner join designation d on e.job_id = d.designation_id\n" +
//            "order by d.level_id, e.employee_name;")
//    @Query("SELECT e.employeeID, e.employeeName, e.jobTitle, e.managerID FROM Employee E INNER JOIN Designation d ON e.jobID = d.designationID")
//    List<EmployeeBrief> find();
//
}

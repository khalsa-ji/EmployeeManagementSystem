//  Waheguru Ji!

package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.Designation;
import com.example.employeemanagementsystem.Employee;
import com.example.employeemanagementsystem.builder.EmployeeBuilder;
import com.example.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository repository;

    @Autowired
    DesignationService service;

    public Employee getEmployeeByID(Long ID) {
        Employee employee = repository.findByEmployeeID(ID);
        if(employee == null)    return new EmployeeBuilder().build();
        return employee;
    }

    public Employee getDirector() {
        Employee employee = repository.findByManagerID(0L);
        if(employee == null)    return new EmployeeBuilder().build();
        return employee;
    }

    public List<Employee> getAllEmployees() {
        return repository.findAllByOrderByJobID_levelIDAscEmployeeNameAsc();
    }

    public List<Employee> getEmployeesByManagerID(Long managerID) {
        return repository.findAllByManagerID(managerID);
    }

    public List<Employee> getColleagues(Long managerID, Long ID) {
        return repository.findColleagues(managerID, ID);
    }

    public List<Employee> getReportees(Long ID) {
        return repository.findReportees(ID);
    }

    public Employee addEmployee(Employee employee) {
        return repository.save(employee);
    }

    public List<Employee> updateManager(List<Employee> list, Long managerID) {
        for(Employee employee : list) {
            employee.setManagerID(managerID);
            repository.save(employee);
        }
        return list;
    }

    public Employee updateEmployee(Employee emp) {
        Employee employee = repository.findByEmployeeID(emp.getEmployeeID());
        if(emp.getEmployeeName() != null)   employee.setEmployeeName(emp.getEmployeeName());
        if(emp.getJobID() != null) {
            Designation designation = service.getDesignationByID(emp.getJobID().getDesignationID());
            employee.setJobID(designation);
            employee.setJobTitle(designation.getDesignation());
        }
        if(emp.getManagerID() != -1)    employee.setManagerID(emp.getManagerID());
        return repository.save(employee);
    }

    public Employee deleteEmployee(Long ID) {
        Employee employee = repository.findByEmployeeID(ID);
        if(employee == null)    return new EmployeeBuilder().build();
        repository.deleteById(ID);
        repository.updateManagerID(employee.getEmployeeID(), employee.getManagerID());
        return employee;
    }

    public Employee fire(Employee employee) {
        return deleteEmployee(employee.getEmployeeID());
    }
}

//  Waheguru Ji!

package com.example.employeemanagementsystem;

import java.util.Comparator;

public class ComparatorClass {
    public static Comparator<Employee> customComparator = new Comparator<Employee>() {
        public int compare(Employee emp1, Employee emp2) {
            if(emp1.getJobID().getLevelID() == emp2.getJobID().getLevelID())
                return emp1.getEmployeeName().compareTo(emp2.getEmployeeName());
            if(emp1.getJobID().getLevelID() < emp2.getJobID().getLevelID())
                return -1;
            return 1;
        }
    };
}

//  Waheguru Ji!

package com.khalsa_ji.ems;

import java.util.Comparator;

/**
 * The class {@code ComparatorClass} defines a custom <em>comparator</em> to compare two instances
 * of the {@code Employee} class.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 */

public class ComparatorClass {
    public static Comparator<Employee> customComparator = new Comparator<Employee>() {

        /**
         * Method to compare two instances of the {@code Employee} class based on
         * their designation's level id in non-decreasing order, and if found to be same
         * then based on their names in lexicographical manner.
         *
         * @param emp1 An instance of the {@code Employee} class
         * @param emp2 Another instance of the {@code Employee} class
         * @return An integer within the range <strong>[-1, 1]</strong> based on the comparision logic
         */

        public int compare(Employee emp1, Employee emp2) {
            if(emp1.getJobID().getLevelID() == emp2.getJobID().getLevelID())
                return emp1.getEmployeeName().compareTo(emp2.getEmployeeName());
            if(emp1.getJobID().getLevelID() < emp2.getJobID().getLevelID())
                return -1;
            return 1;
        }
    };
}

//  Waheguru Ji!

package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.Designation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignationRepository extends JpaRepository<Designation, Integer> {
    Designation findByDesignationID(Integer ID);
    Designation findByDesignation(String designation);
}

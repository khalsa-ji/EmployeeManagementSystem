//  Waheguru Ji!

package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.Designation;
import com.example.employeemanagementsystem.builder.DesignationBuilder;
import com.example.employeemanagementsystem.repository.DesignationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignationService {
    @Autowired
    DesignationRepository repository;

    public Designation getDesignationByID(Integer ID) {
        Designation designation = repository.findByDesignationID(ID);
        if(designation == null)     return new DesignationBuilder().build();
        return designation;
    }

    public Designation getDesignation(String jobTitle) {
        Designation designation = repository.findByDesignation(jobTitle);
        if(designation == null)     return new DesignationBuilder().build();
        return designation;
    }

    public List<Designation> getAllDesignations() {
        return repository.findAll();
    }
}

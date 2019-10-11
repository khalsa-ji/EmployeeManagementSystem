//  Waheguru Ji!

package com.example.employeemanagementsystem.Web_Controller;

import com.example.employeemanagementsystem.Employee;
import com.example.employeemanagementsystem.builder.EmployeeBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class RequestHandler {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/")
    public ModelAndView homePage() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("name", "Waheguru Ji!");
        mv.setViewName("index");
        return mv;
    }

    @GetMapping(value = "searchEmployee")
    public ModelAndView searchEmployee(@RequestParam(value = "id", required = false) Long ID) {
        if(ID == null)  return new ModelAndView("searchEmployee");
        ModelAndView mv = new ModelAndView();
        String result = restTemplate.getForObject(new String("http://localhost:8080/api/employees/" + ID), String.class);
        Employee employee = null;
        try {
            employee = new ObjectMapper().readValue(result, Employee.class);
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("employee", new EmployeeBuilder().build());
            mv.setViewName("view");
            return mv;
        }
        mv.addObject("employee", employee);
        mv.setViewName("view");
        return mv;
    }
}

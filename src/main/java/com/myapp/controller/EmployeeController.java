package com.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.dao.EmployeeDAO;
import com.myapp.entity.Employee;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeDAO employeeDAO;
	
	@RequestMapping("/employees")
	public List<Employee> getEmployees() {
		return employeeDAO.findAll();
	}

}

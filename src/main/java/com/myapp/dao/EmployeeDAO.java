package com.myapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.entity.Employee;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Long> {

}

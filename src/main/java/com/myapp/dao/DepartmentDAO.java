package com.myapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.entity.Department;

@Repository
public interface DepartmentDAO extends JpaRepository<Department, Long> {

}

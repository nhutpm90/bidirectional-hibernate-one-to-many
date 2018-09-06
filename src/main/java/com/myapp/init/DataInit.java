package com.myapp.init;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.dao.DepartmentDAO;
import com.myapp.dao.EmployeeDAO;
import com.myapp.entity.Department;
import com.myapp.entity.Employee;

@Component
public class DataInit implements ApplicationRunner {

	private static final Logger LOG = LoggerFactory.getLogger(DataInit.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private DepartmentDAO departmentDAO;

	@Autowired
	private EmployeeDAO employeeDAO;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		boolean insertDataOnStartUp = true;
		if (insertDataOnStartUp) {
			Department department = new Department("IT Department");
			
			Employee employee1 = new Employee("Adam", department);
			Employee employee2 = new Employee("Miller", department);
			Employee employee3 = new Employee("Smith", department);
			
			department.getEmployees().add(employee1);
			department.getEmployees().add(employee2);
			department.getEmployees().add(employee3);
			
			departmentDAO.save(department);
			
			/*
			 * the above implementation causes 4 sql insert
			 * 
			 * 
				Hibernate: insert into department (dpt_id, name) values (null, ?)
				Hibernate: insert into employee (emp_id, dpt_id, name) values (null, ?, ?)
				Hibernate: insert into employee (emp_id, dpt_id, name) values (null, ?, ?)
				Hibernate: insert into employee (emp_id, dpt_id, name) values (null, ?, ?)
			 */
		}
		
		boolean updateDataOnStartUp = true;
		if(updateDataOnStartUp) {
			Department department = departmentDAO.findById(1l).orElse(null);
			/*
			 Unidirectional → In this type of association, only source entity has a relationship field that refers to the target entity. We can navigate this type of association from one side.
			 
			 select department0_.dpt_id as dpt_id1_0_0_, department0_.name as name2_0_0_, employees1_.department_dpt_id as departme1_1_1_, employee2_.emp_id as employee2_1_1_, employee2_.emp_id as emp_id1_2_2_, employee2_.name as name2_2_2_ 
				from department department0_ 
					left outer join department_employees employees1_ on department0_.dpt_id=employees1_.department_dpt_id 
					left outer join employee employee2_ on employees1_.employees_emp_id=employee2_.emp_id 
						where department0_.dpt_id=? 
				
				DPT_ID1_0_0_  	NAME2_0_0_  	DEPARTME1_1_1_  	EMPLOYEE2_1_1_  	EMP_ID1_2_2_  	NAME2_2_2_  
				1				IT Department		1					1					1				Adam
				1				IT Department		1					2					2				Miller
				1				IT Department		1					3					3				Smith
				
			 Bidirectional → In this type of association, each entity (i.e. source and target) has a relationship field that refers to each other. We can navigate this type of association from both sides.
			 
			 select department0_.dpt_id as dpt_id1_0_0_, department0_.name as name2_0_0_, employees1_.dpt_id as dpt_id3_1_1_, employees1_.emp_id as emp_id1_1_1_, employees1_.emp_id as emp_id1_1_2_, employees1_.dpt_id as dpt_id3_1_2_, employees1_.name as name2_1_2_ 
				from department department0_ 
					left outer join employee employees1_ on department0_.dpt_id=employees1_.dpt_id 
						where department0_.dpt_id=?
						
				DPT_ID1_0_0_  	NAME2_0_0_  	DPT_ID3_1_1_  	EMP_ID1_1_1_  	EMP_ID1_1_2_  	DPT_ID3_1_2_  	NAME2_1_2_  
					1			IT Department		1				1				1				1			Adam
					1			IT Department		1				2				2				1			Miller
					1			IT Department		1				3				3				1			Smith
			 */
			
			List<Employee> employees = department.getEmployees();
//			department.getEmployees().size()
//			System.out.println(objectMapper.writeValueAsString(department));
//			employees.forEach(employee -> {
//				System.out.println(employee);
//			});
		}
	}

}
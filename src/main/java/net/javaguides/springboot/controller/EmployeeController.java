package net.javaguides.springboot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;

@RestController
@RequestMapping("/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	/*
	 * 모든 employee 조회*/
	@GetMapping("/employees")
	public List<Employee> findAll() {
		return this.employeeRepository.findAll();//jpa에서 list형식으로 반환해줌. 갖다씀
	}
	
	/*
	 * id에 해당하는 employee 조회*/
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> findEmployeeById(@PathVariable(value = "id") Long employeeId) 
	throws ResourceNotFoundException{
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(()->new ResourceNotFoundException("Employee not found for this id"));
		return ResponseEntity.ok(employee);
	}
	//ResponseEntity -> Http 요청에 대한 응답, 개발자가 error처리를 더 용이하게 할 수 있음
	//pathVariable -> url경로에 변수를 넣는 것 /employees/3
	//requestParam 의 경우 /employees?id=3 과 같이 사용
	
	
	/*
	 * employee 등록*/
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return this.employeeRepository.save(employee);
	}//json객체가 들어와야 하므로 @RequestBody로 받아줌


	/*
	 * update*/
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value="id") Long employeeId,
			@Validated @RequestBody Employee employeeDetails) throws ResourceNotFoundException{
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(()-> new ResourceNotFoundException("employee not found for this id"));
	
		employee.setEmail(employeeDetails.getEmail());
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		
		return ResponseEntity.ok(this.employeeRepository.save(employee));
	}//update하는 id와 해당하는 json형태의 객체를 받아온다
	
	
	/*
	 * delete*/
	@DeleteMapping("employees/{id}")
	public Map<String,Boolean> deleteEmployee(@PathVariable(value = "id")Long employeeId) throws ResourceNotFoundException{
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(()-> new ResourceNotFoundException("employee not found for this id"));
		
		this.employeeRepository.delete(employee);
		
		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted",Boolean.TRUE);
		
		return response;
		
	}

	
	 
}
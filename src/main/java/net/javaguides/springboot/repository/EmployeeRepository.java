package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Employee;

@Repository 
public interface EmployeeRepository extends JpaRepository<Employee,Long>{
   //jparepository를 extends 하여 사용. 
	//<model이름,private key type>명시
	//코드 끝. jparepoistory에서 제공해주는 함수 사용 가능
}

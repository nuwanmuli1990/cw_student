package com.iit.student.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.iit.student.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

	
}

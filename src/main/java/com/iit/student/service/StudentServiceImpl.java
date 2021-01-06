package com.iit.student.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iit.student.entities.Student;
import com.iit.student.models.StudentResponse;
import com.iit.student.repositories.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public List<StudentResponse> getStudents() {

		List<Student> students = studentRepository.findAll();

		List<StudentResponse> response = new ArrayList<>();
		for (Student student : students) {

			StudentResponse studentResponse = new StudentResponse(student.getId(), student.getName(),
					student.getEmail(), student.getContact());
			response.add(studentResponse);
		}

		return response;
	}

}

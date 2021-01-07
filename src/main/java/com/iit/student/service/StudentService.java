package com.iit.student.service;

import java.util.List;

import com.iit.student.models.CourseResponse;
import com.iit.student.models.StudentResponse;

public interface StudentService {

	List<StudentResponse> getStudents();
	
	StudentResponse getStudent(Long id);
	
	CourseResponse getStudentCourseDetails(Long studentId);
}

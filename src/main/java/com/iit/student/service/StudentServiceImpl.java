package com.iit.student.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iit.student.entities.Student;
import com.iit.student.models.CourseResponse;
import com.iit.student.models.StudentResponse;
import com.iit.student.repositories.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	@Qualifier("bffRestTemplate")
	private RestTemplate restTemplate;
	
	@Autowired
	private HttpServletRequest request;


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

	@Override
	public StudentResponse getStudent(Long id) {
		
		StudentResponse response = new StudentResponse();
		Optional<Student> optionalStudent = studentRepository.findById(id);
		if(optionalStudent.isPresent()) {
			
			response.setId(optionalStudent.get().getId());
			response.setName(optionalStudent.get().getName());
			response.setEmail(optionalStudent.get().getEmail());
			response.setContact(optionalStudent.get().getContact());
		}
		return response;
	}

	@Override
	public CourseResponse getStudentCourseDetails(Long studentId) {
		
		Optional<Student> optionalStudent = studentRepository.findById(studentId);
		Long courseId = 0L;
		if(optionalStudent.isPresent()) {
			courseId = optionalStudent.get().getCourse().getId();
		}
		String url = "http://iit.cloud.com/cs/courses/"+courseId;
		ObjectMapper mapper = new ObjectMapper();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String token=request.getHeader("Authorization");
		headers.set("Authorization",token);
		
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity<CourseResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
				CourseResponse.class);
		
		CourseResponse response = mapper.convertValue(responseEntity.getBody(),
				new TypeReference<CourseResponse>() {
				});
		return response;
	}
}

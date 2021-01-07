package com.iit.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.iit.student.models.AuthenticatioResponse;
import com.iit.student.models.AuthenticationRequest;
import com.iit.student.models.CourseResponse;
import com.iit.student.models.StudentResponse;
import com.iit.student.service.MyUserDetailsService;
import com.iit.student.service.StudentService;
import com.iit.student.util.JwtUtil;

@RestController
public class StudentController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private StudentService studentService;
	
	@GetMapping(value = "/students")
	@ResponseBody
	public List<StudentResponse> getStudents() {
		return studentService.getStudents();
	}
	
	@GetMapping(value = "/students/{id}")
	@ResponseBody
	public StudentResponse getStudent(@PathVariable Long id) {
		return studentService.getStudent(id);
	}
	
	@GetMapping(value = "/students/{id}/course")
	@ResponseBody
	public CourseResponse getStudentCourse(@PathVariable Long id) {
		return studentService.getStudentCourseDetails(id);
	}
	
	@RequestMapping(value ="/authenticate",method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
		authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
		}catch (BadCredentialsException e) {
			throw new Exception("Incorect username or pasword",e);
		}
		final UserDetails userDetails=myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt=jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticatioResponse(jwt));
	}
}

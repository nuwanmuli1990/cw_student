package com.iit.student.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentResponse {

	private Long id;
	private String name;
	private String email;
	private String contact;
}

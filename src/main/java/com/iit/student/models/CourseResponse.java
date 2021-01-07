package com.iit.student.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {

	private Long id;
	private String name;
	private int courseCredit;
	private int duration;
}

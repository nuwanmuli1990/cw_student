package com.iit.student.entities;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false)
	private Long id;

	@Basic(optional = false)
	@Column(nullable = false, length = 255)
	private String name;

	@Basic(optional = false)
	@Column(name = "course_credit", nullable = false, length = 100)
	private int courseCredit;

	@Basic(optional = false)
	@Column(nullable = false, length = 10)
	private int duration;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "course", fetch = FetchType.LAZY)
	private List<Student> studentList;
}

package com.ezjobs.mystory.entity;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "tag")
@Data
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_id", nullable = false)
	private String userId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "type", nullable = true)
	private String type;

	@Column(name = "state", nullable = true)
	private String state;



}
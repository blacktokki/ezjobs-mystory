package com.ezjobs.mystory.entity;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "sentence")
@Data
public class Sentence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "tags", nullable = false)
	private String tags;

	@Column(name = "text", nullable = false)
	private String text;

	@Column(name = "state", nullable = true)
	private String state;

	@Column(name = "user_id", nullable = false)
	private String userId;
	
	@Column(name = "position", nullable = false)
	private Integer position;
	
	@Column(name = "position_max", nullable = false)
	private Integer positionMax;
}
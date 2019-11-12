package com.ezjobs.mystory.entity;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "synonym")
@Data
public class Synonym {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_id", nullable = false)
	private String userId;
	
	@Column(name = "keyword", nullable = false)
	private String keyword;
	
	@Column(name = "synonym", nullable = true)
	private String synonym;

	@Column(name = "state", nullable = true)
	private String state;

	@Column(name = "valid", nullable = true)
	private Boolean valid;

}
package com.ezjobs.mystory.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "intro")
@Data
public class Resume {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "type", nullable = false)
	private String type;

	@Column(name = "dept", nullable = false)
	private String dept;

	@Column(name = "company", nullable = false)
	private String company;

	@Column(name = "question", nullable = false)
	private String question;

	@Column(name = "answer", nullable = false)
	private String answer;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "priority", nullable = false)
	private Integer priority;

	@Column(name = "state", nullable = false)
	private String state;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edit_date", nullable = false)
	private Date editDate;

	@Column(name = "edit_group", nullable = false)
	private Integer editGroup;

}
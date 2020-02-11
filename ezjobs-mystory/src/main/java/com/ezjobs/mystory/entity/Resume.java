package com.ezjobs.mystory.entity;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "intro")
@Data
@ToString(exclude= {"tags","question","answer"})
@EqualsAndHashCode(exclude = "tags")
@JsonIgnoreProperties("tags")
public class Resume {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@BatchSize(size = 25)
	@ManyToMany
	@JoinTable(
	    name = "intro_tag",
	    joinColumns = @JoinColumn(name = "intro_id"),
	    inverseJoinColumns = @JoinColumn(name = "tag_name")
	)
	private Set<Tag> tags=new HashSet<Tag>();
	
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

	@Temporal(TemporalType.DATE)
	@Column(name = "close_date", nullable = false)
	private Date closeDate;

	@Column(name = "state", nullable = false)
	private String state;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edit_date", nullable = false ,insertable=false)
	private Date editDate;

	@Column(name = "edit_group", nullable = false)
	private Integer editGroup;

}
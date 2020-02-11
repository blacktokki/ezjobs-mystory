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
	
	@Column(name = "dept")
	private String dept;

	@Column(name = "company")
	private String company;
	
	@Column(name = "question")
	private String question;

	@Column(name = "answer")
	private String answer;

	@Column(name = "user_id")
	private String userId;

	@Temporal(TemporalType.DATE)
	@Column(name = "close_date")
	private Date closeDate;

	@Column(name = "state")
	private String state;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edit_date")
	private Date editDate;

	@Column(name = "edit_group")
	private Integer editGroup;
	
	@PrePersist
	@PreUpdate
	protected void createEditDate() {
	    editDate = new Date();
	}

}
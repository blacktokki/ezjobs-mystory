package com.ezjobs.mystory.entity;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.BatchSize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "tag")
@Data
@ToString(exclude= {"resumes"})
@EqualsAndHashCode(exclude = "resumes")
public class Tag {

	@Id
	@Column(name = "name", nullable = false)
	private String name;
	
	@ManyToMany(mappedBy = "tags")
	private Set<Resume> resumes=new HashSet<Resume>();
	
	@Column(name = "type", updatable =false)
	private String type;
	
	@Column(name = "state", updatable = false)
	private String state;

	@Column(name = "is_admin", insertable=false,updatable = false)
	private Boolean isAdmin;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edit_date")
	private Date editDate;
	
	@PrePersist
	@PreUpdate
	protected void createEditDate() {
	    editDate = new Date();
	}

}
package com.ezjobs.mystory.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "board")
@Data
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "board_type", nullable = false)
	private String boardType;

	@Column(name = "title", nullable = true)
	private String title;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "intro_dept", nullable = true)
	private String introDept;

	@Column(name = "intro_type", nullable = true)
	private String introType;

	@Column(name = "text", nullable = true)
	private String text;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edit_date", nullable = false)
	private Date editDate;

	@Column(name = "edit_group", nullable = false)
	private Integer editGroup;

	@Column(name = "good_cnt", nullable = false)
	private Integer goodCnt;

	@Column(name = "bad_cnt", nullable = false)
	private Integer badCnt;
}
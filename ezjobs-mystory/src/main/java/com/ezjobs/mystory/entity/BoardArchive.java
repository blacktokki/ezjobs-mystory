package com.ezjobs.mystory.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper=false)
@Data
public class BoardArchive extends Board{
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "remove_date", nullable = false, insertable=false)
	private Date removeDate;
}
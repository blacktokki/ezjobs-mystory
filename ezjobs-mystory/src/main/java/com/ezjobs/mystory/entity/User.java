package com.ezjobs.mystory.entity;

import java.util.Date;
import javax.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name="user")
@Data
public class User {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
	
	@Column(name="login_id", nullable = false)
    private String loginId;
	
    @Column(name="login_pw", nullable = false)
    private String loginPw;

    @Column(name="name", nullable = false)
    private String name;
    
    @Column(name="email", nullable = false)
    private String email;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="regist_date", nullable = false)
    private Date editDate= new Date();
    
    @Column(name="visit_cnt", nullable = false)
    private Integer visitCnt;

    @Column(name="rel_id", nullable = false)
    private String relId;
    
    @Column(name="rel_login_id", nullable = false)
    private String relLoginId;
    
    @Column(name="sex", nullable = false)
    private String sex;
    
    @Column(name="grad", nullable = false)
    private String grad;
}
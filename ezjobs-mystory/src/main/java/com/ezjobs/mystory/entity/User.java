package com.ezjobs.mystory.entity;

import java.util.Date;
import javax.persistence.*;
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
    private Date registDate;
    
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
    
    @Column(name="state", nullable = false)
    private String state;
    
    @Column(name="is_admin", nullable = false)
    private Boolean isAdmin;
    
    @Column(name="login_failure_cnt", nullable = false)
    private Integer loginFailureCnt;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="access_date", nullable = false)
    private Date accessDate;
}
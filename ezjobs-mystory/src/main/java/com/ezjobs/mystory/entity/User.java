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
	@Column(name="login_id", nullable = false)
    private String loginId;
	
    @Column(name="login_pw", nullable = false)
    private String loginPw;

    @Column(name="name", nullable = false)
    private String name;
    
    @Column(name="email", nullable = false)
    private String email;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="regist_date", nullable = false , insertable=false)
    private Date registDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="visit_date", nullable = false , insertable=false)
    private Date visitDate;

    @Column(name="login_rel", nullable = false , insertable=false)
    private String loginRel;
    
    @Column(name="sex", nullable = false)
    private String sex;
    
    @Column(name="grad", nullable = false)
    private String grad;
    
    @Column(name="state", nullable = false)
    private String state;
    
    @Column(name="is_admin", nullable = false)
    private Boolean isAdmin;
    
    @Column(name="login_failure_cnt", nullable = false,insertable=false)
    private Integer loginFailureCnt;
    
}
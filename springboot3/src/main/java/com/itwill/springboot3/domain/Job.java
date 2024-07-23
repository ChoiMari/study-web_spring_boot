package com.itwill.springboot3.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;

import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor @Getter @ToString @EqualsAndHashCode
@Entity @Table(name = "JOBS")
public class Job {
	
	@Id @Column(name = "JOB_ID")
	private String id; //-> 실제 테이블의 컬럼 이름은 job_id 인데 카멜표기법으로 쓰면 자동 매핑함
	
	private String jobTitle;
	
	private Integer minSalary;
	
	private Integer maxSalary;
	

}

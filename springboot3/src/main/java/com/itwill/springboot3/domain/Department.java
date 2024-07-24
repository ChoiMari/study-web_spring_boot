package com.itwill.springboot3.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "DEPARTMENTS")
@Getter @NoArgsConstructor @ToString @EqualsAndHashCode
public class Department {
	
	@Id
	@Column(name = "DEPARTMENT_ID")
	private Integer id;
	
	private String departmentName;
	
	@ToString.Exclude
	@JoinColumn(name = "MANAGER_ID")
	@OneToOne(fetch = FetchType.LAZY) //부서의 매니저는 1명. 2명 이상일 수가 없음.
	private Employee manager;
	
	@ToString.Exclude
	@JoinColumn(name = "LOCATION_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Location location;

}

package com.itwill.springboot3.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "REGIONS")
@NoArgsConstructor @Getter @ToString @EqualsAndHashCode
public class Region {
	@Id
	@Column(name = "REGION_ID")
	private Integer id;
	
	private String regionName;

}

package com.itwill.springboot3.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "COUNTRIES")
@NoArgsConstructor @Getter @ToString @EqualsAndHashCode
public class Country {
	@Id
	@Column(name = "COUNTRY_ID")
	private String id;
	
	private String countryName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGION_ID")
	@ToString.Exclude
	private Region region;
}

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
@Table(name = "Locations")
@NoArgsConstructor @Getter @ToString @EqualsAndHashCode
public class Location {
	
	@Id @Column(name = "LOCATION_ID")
	private Integer id;
	
	private String streetAddress;
	
	private String postalCode;
	
	private String city;
	
	private String stateProvince;
	
	@ToString.Exclude
	@JoinColumn(name = "COUNTRY_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Country country;

}

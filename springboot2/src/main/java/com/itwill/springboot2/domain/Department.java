package com.itwill.springboot2.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor @Getter @ToString @EqualsAndHashCode
@Entity
@Table(name = "DEPT")
public class Department {
    @Id
    @Column(name = "DEPTNO")
    private Integer id;
    
    private String dname;
    
    @Column(name = "LOC")
    private String location;
    
    @ToString.Exclude
   // @OneToMany(fetch = FetchType.EAGER, mappedBy = "department") //mappedBy = "department"는 Employee테이블의 @ManyToOne이 적용된 필드이름을 적는 것
    //mappedBy : Employee 엔터티에서 @OneToMany 애너테이션이 설정된 필드 이름.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    // EAGER필요 하든 안하든 즉각적으로 가지고옴(기본값이지만 권장사항아님)
    //LAZY 권장사항. 필요할 때.
    private List<Employee> employees;
    
}









//package com.itwill.springboot2.domain;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//@NoArgsConstructor
//@Getter @ToString @EqualsAndHashCode
//@Entity
//@Table(name = "DEPT")
//public class Department {
//	
//	@Id
//	@Column(name = "DEPTNO")
//	private Integer deptId;
//	
//	private String dname;
//	
//	@Column(name = "LOC")
//	private String DepartmentLocation;
//
//}

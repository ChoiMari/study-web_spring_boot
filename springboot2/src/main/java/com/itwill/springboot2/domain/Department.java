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
    //mappedBy : Employee 엔터티에서 @ManyToOne 애너테이션이 설정된 필드 이름.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department") //->@OneToMany : 원래는 employee에서 @ManyToOne으로 설정했으면 여기서 설정할 필요 없다고함.
    //한쪽만 사용하는것이 오류를 줄이는 방법이라고. 양쪽에서 이렇게 잘못 사용하면 오류발생할수있다고. 한쪽방향으로만 엔터티 설계하라고 함.
    // EAGER필요 하든 안하든 즉각적으로 가지고옴(기본값이지만 권장사항아님)
    //LAZY 권장사항. 필요할 때.
    private List<Employee> employees; //List말고 set?으로 해도 된다고함
    
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

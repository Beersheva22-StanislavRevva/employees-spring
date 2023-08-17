package telran.spring.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Employee implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private int age;
	private String birthDate;
	private String name;
	private String department;
	private int salary;
	private String catFields;
	private String gender;

}

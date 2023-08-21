package employees.spring.model;

import jakarta.validation.constraints.NotEmpty;

public class ResponseObj {
	
	@NotEmpty
	public String task;
	
	public Employee employee;
	
	public String id;
	

}

package telran.spring.service;

import java.util.List;

import telran.spring.model.Employee;

public interface AdvService {

	Employee addAdv(Employee adv);
	List<Employee> getAdvs();
	void deleteAdv(String id);
	Employee updateAdv(Employee adv);
	Employee getAdv(String id);
	void save (String pathName);
	void resore (String pathName);

}

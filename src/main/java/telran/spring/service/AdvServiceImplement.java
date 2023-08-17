package telran.spring.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import telran.spring.controller.AdvController;
import telran.spring.model.Employee;

@Slf4j
@Service

public class AdvServiceImplement implements AdvService {
	Map<String, Employee> advsMap = new HashMap<>();
	Map<String, List<Employee>> categoriesMap = new HashMap<>();
	TreeMap<Integer, List<Employee>> pricesMap = new TreeMap<>();
	
	
	@Override
	public Employee addAdv(Employee employee) {
		if (employee.getId() == null) {
			addId(employee);
		}
		advsMap.put(employee.getId(), employee);
		log.info("adv with id:" + employee.getId() + "added");
		return employee;
	}
	

	private void addId(Employee employee) {
		  Integer id;
	      do {
	          id = 100000 + (int) (Math.random() * 999999);          
	      } while (advsMap.containsKey(id.toString()));
	      employee.setId(id.toString());
		
	}


	@Override
	public List<Employee> getAdvs() {
		List<Employee> res = new ArrayList<Employee>(advsMap.values());
		log.debug("loading all employees");
		return res;
	}

	@Override
	public Employee getAdv(String id) {
		Employee res = advsMap.get(id);
		log.debug("loading employee with id" + id);
		return res;
	}

	
	@Override
	public void deleteAdv(String id) {
		Employee adv = advsMap.remove(id);
		log.debug("employee with id:" + id + "deleted");

	}

	@Override
	public Employee updateAdv(Employee adv) {
		Employee advUpd = adv;
		deleteAdv(adv.getId());
		Employee res = addAdv(advUpd);
		log.debug("employee with id:" + res.getId() + "updated");
		return res;
		 
	}
	
	public void clear() {
		advsMap.clear();
	}

	@Override
	public void save(String pathName) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(pathName))){
			output.writeObject(getAdvs());
			log.info("data has beeen saved to file: " + pathName);
		} catch(Exception e) {
			throw new RuntimeException(e.toString()); //some error
		}
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public void resore(String pathName) {
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(pathName))) {
			List<Employee> allAdvs = (List<Employee>) input.readObject();
			allAdvs.forEach(this::addAdv);
			log.info("data has beeen restored from file: " + pathName);
		}catch(FileNotFoundException e) {
			//empty object but no error
		} catch (Exception e) {
			throw new RuntimeException(e.toString()); //some error
		}
		
	}
	

	}

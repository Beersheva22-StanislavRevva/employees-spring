package employees.spring.service;

import static employees.spring.api.EmployeesConfig.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import employees.spring.model.Employee;
import employees.spring.model.ResponseObj;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.spring.exceptions.NotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor

public class EmployeeServiceImpl implements EmployeeService, EmployeesPersistance {
	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	ReadLock readLock = lock.readLock();
	WriteLock writeLock = lock.writeLock();
	final SimpMessagingTemplate notifier;
	private Map<Long, Employee> mapEmployee = new HashMap<Long, Employee>();
	private String task = "";
	
	private  Long generateId() {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		long randomId;
		do {
			randomId = random.nextInt(MIN_ID, MAX_ID);
		} while (mapEmployee.containsKey(randomId));
		return randomId;
	}
	
	@Override
	public Employee addEmployee(Employee employee) {
		
		writeLock.lock();
		if (employee.getId() == null) {
			employee.setId(generateId());
		}
			
		try {
			Employee res = mapEmployee.putIfAbsent(employee.getId(), employee);
			if (res != null) {
				throw new RuntimeException("Employee with id " + employee.getId() + " already exists");
			}
			ResponseObj responseObj = createResponseObj(employee, "add");
			notifier.convertAndSend("/topic/employees", responseObj);
			return employee;
		} finally {
			writeLock.unlock();
		}
	}

	private ResponseObj createResponseObj(Employee employee, String task) {
		ResponseObj res = new ResponseObj();
		res.task = task;
		res.employee = employee;
		return res;
	}

	@Override
	public  List<Employee> getEmployees() {
		readLock.lock();
		try {
			return new ArrayList<Employee>(mapEmployee.values());
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public void deleteEmployee(Long id) {
		writeLock.lock();
		try {
			Employee empl = mapEmployee.remove(id);
			if (empl == null) {
				throw new NotFoundException("Not found" + id);
			}
			ResponseObj responseObj = createResponseObj(empl, "delete");
			
			notifier.convertAndSend("/topic/employees", responseObj);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public Employee updateEmployee(Employee empl) {
		writeLock.lock();
		try {
			if (!mapEmployee.containsKey(empl.getId())) {
				throw new NotFoundException("Not found " + empl.getId() + " was found");
			}
			Employee emplFound = mapEmployee.put(empl.getId(), empl);
			ResponseObj responseObj = createResponseObj(empl, "update");
			notifier.convertAndSend("/topic/employees", responseObj);
			return emplFound;
		} finally {
			writeLock.unlock();

		}
	}

	@PreDestroy
	void storeEmployees() {
		store(mapEmployee.values().stream().toList());
	}
	
	@Override
	public void store(List<Employee> listEmployees) {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
			outputStream.writeObject(listEmployees);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		log.info("Employees are saved to file \"{}\"", fileName);
	}

	@PostConstruct
	private void restoreEmployees() {
		restore().forEach(e -> addEmployee(e));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> restore() {
		List<Employee> res = new ArrayList<Employee>();
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
			var input = inputStream.readObject();
			log.info("Employees are restored from file \"{}\"", fileName);
			res = (List<Employee>) input;
			
		} catch (FileNotFoundException e) {
			log.warn("No file \"{}\" was found - no advertisment data was restored", fileName);
		} catch (Exception e) {
			log.warn("Service cannot restore advertisments: ", e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return res;
	}

	@Override
	public Employee getEmployee(long id) {
		Employee empl = mapEmployee.get(id);
		if(empl == null) {
			throw new NotFoundException("Not found employee ");
		}
		return null;
	}

}
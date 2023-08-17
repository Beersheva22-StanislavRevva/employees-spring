package telran.spring.controller;

import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import telran.spring.model.Employee;
import telran.spring.service.AdvService;

@RestController
@RequestMapping("/employees")
@Slf4j
@Validated
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
RequestMethod.DELETE }, allowedHeaders = "*", origins = { "*" })

public class AdvController {
	
	AdvService advService;
	
	public AdvController(AdvService advService) {
		this.advService = advService;
	}
	
	@PostMapping
	public Employee addAdv(@RequestBody Employee adv) {
		Employee res = null;
		log.trace("{}",adv);
		try {
		 res = advService.addAdv(adv);
		} catch (Exception e) {
			log.error("Unnable to add" + adv,  e);
		}
		return res;
	}
	@GetMapping
	public List<Employee> getAdvs() {
		List<Employee> res = advService.getAdvs();
		return res;
	}
	@GetMapping("{id}")
	public Employee getAdv(@PathVariable(name="id") String id) {
		Employee res = advService.getAdv(id);
		return res;
	}
	
	@DeleteMapping("{id}")
	public void deleteAdv(@PathVariable(name="id") String id) {
		advService.deleteAdv(id);
	}
	@PutMapping("{id}")
	public Employee updateAdv(@PathVariable(name="id") String id,@RequestBody Employee adv) {
		Employee res = null;		
		try {
		res = advService.updateAdv(adv);
		} catch (Exception e) {
			log.error("Unnable to update adv with id: " + id,  e);
		}
		return res;
	}
	
	@PostConstruct
	void init() {
		advService.resore("employeesdata.json");
	}
	
	@PreDestroy
	void shutdown() {
		advService.save("employeesdata.json");
		log.info("context closed");
	}
	
}

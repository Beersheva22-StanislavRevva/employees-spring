package advboard.spring.controller;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.fasterxml.jackson.databind.ObjectMapper;

import advboard.spring.model.Adv;
import advboard.spring.service.AdvService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("advboard")
@Slf4j
@Validated

public class AdvController {
	
	AdvService advService;
	
	public AdvController(AdvService advService) {
		this.advService = advService;
	}
	
	@PostMapping
	public Adv addAdv(@RequestBody Adv adv) {
		Adv res = null;
		try {
		 res = advService.addAdv(adv);
		} catch (Exception e) {
			log.error("Unnable to add" + adv,  e);
		}
		return res;
	}
	@GetMapping
	public List<Adv> getAdvs() {
		List<Adv> res = advService.getAdvs();
		return res;
	}
	@GetMapping("{id}")
	public Adv getAdv(@PathVariable(name="id") String id) {
		Adv res = advService.getAdv(id);
		return res;
	}
	@GetMapping("category/{category}")
	
	public List<Adv> getCatAdvs(@PathVariable(name="category") String category) {
		List<Adv> res = new ArrayList<>();
		try {
		res = advService.getCatAdvs(category);
		} catch (Exception e) {
			log.error("Unnable to find category" + category,  e);
		}
		return res;
	}
	@GetMapping("price/{minPrice}")
	public List<Adv> getPriceAdvs(@PathVariable(name="minPrice") int minPrice) {
		List<Adv> res = new ArrayList<>();
		try {
		res = advService.getPriceAdvs(minPrice);
		} catch (Exception e) {
			log.error("Incorrect minimal price: " + minPrice,  e);
		}
		return res;
	}
	@DeleteMapping("{id}")
	public void deleteAdv(@PathVariable(name="id") String id) {
		advService.deleteAdv(id);
	}
	@PutMapping("{id}")
	public Adv updateAdv(@PathVariable(name="id") String id,@RequestBody Adv adv) {
		Adv res = null;		
		try {
		res = advService.updateAdv(adv);
		} catch (Exception e) {
			log.error("Unnable to update adv with id: " + id,  e);
		}
		return res;
	}
	
	@PostConstruct
	void init() {
		advService.resore("advdata.json");
	}
	
	@PreDestroy
	void shutdown() {
		advService.save("advdata.json");
		log.info("context closed");
	}
	
}

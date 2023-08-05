package advboard.spring.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import advboard.spring.model.Adv;
import advboard.spring.service.AdvService;
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
		log.info(adv + "  add");
		return advService.addAdv(adv);
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
		List<Adv> res = advService.getCatAdvs(category);
		return res;
	}
	@GetMapping("price/{minPrice}")
	public List<Adv> getPriceAdvs(@PathVariable(name="minPrice") int minPrice) {
		log.info("miPrice:  " + minPrice);
		List<Adv> res = advService.getPriceAdvs(minPrice);
		return res;
		
	}
	@DeleteMapping("{id}")
	public void deleteAdv(@PathVariable(name="id") String id) {
		advService.deleteAdv(id);
	}
	@PutMapping("{id}")
	public Adv updateAdv(@PathVariable(name="id") String id,@RequestBody Adv adv) {
		return advService.updateAdv(adv);
	}
}

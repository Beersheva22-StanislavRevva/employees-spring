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
		log.info("recieved advs list" + res);
		return res;
	}
	
}

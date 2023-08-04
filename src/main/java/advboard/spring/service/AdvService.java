package advboard.spring.service;

import java.util.List;

import advboard.spring.model.Adv;

public interface AdvService {

	Adv addAdv(Adv adv);
	List<Adv> getAdvs();

}

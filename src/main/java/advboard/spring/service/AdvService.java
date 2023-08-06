package advboard.spring.service;

import java.util.List;

import advboard.spring.model.Adv;

public interface AdvService {

	Adv addAdv(Adv adv);
	List<Adv> getAdvs();
	List<Adv> getCatAdvs(String category);
	List<Adv> getPriceAdvs(int minPrice);
	void deleteAdv(String id);
	Adv updateAdv(Adv adv);
	Adv getAdv(String id);
	void save (String pathName);
	void resore (String pathName);

}

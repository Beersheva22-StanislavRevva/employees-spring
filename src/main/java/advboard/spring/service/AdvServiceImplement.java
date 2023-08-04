package advboard.spring.service;

import java.util.*;

import org.springframework.stereotype.Service;

import advboard.spring.model.Adv;

@Service

public class AdvServiceImplement implements AdvService {
	Map<String, Adv> advsMap = new HashMap<>();
	Map<String, List<Adv>> categoriesMap = new HashMap<>();
	Map<Integer, List<Adv>> pricesMap = new TreeMap<>();
	
	@Override
	public Adv addAdv(Adv adv) {
		advsMap.put(adv.getId(), adv);
		categoriesMap.computeIfAbsent(adv.getCategory(), k -> new ArrayList<>()).add(adv);
		pricesMap.computeIfAbsent(adv.getPrice(), k -> new ArrayList<>()).add(adv);
		return adv;
	}

	@Override
	public List<Adv> getAdvs() {
		List<Adv> res = new ArrayList<Adv>(advsMap.values());
		return res;
	}

}

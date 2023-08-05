package advboard.spring.service;

import java.util.*;

import org.springframework.stereotype.Service;

import advboard.spring.controller.AdvController;
import advboard.spring.model.Adv;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class AdvServiceImplement implements AdvService {
	Map<String, Adv> advsMap = new HashMap<>();
	Map<String, List<Adv>> categoriesMap = new HashMap<>();
	TreeMap<Integer, List<Adv>> pricesMap = new TreeMap<>();
	
	
	@Override
	public Adv addAdv(Adv adv) {
		advsMap.put(adv.getId(), adv);
		categoriesMap.computeIfAbsent(adv.getCategory(), k -> new ArrayList<>()).add(adv);
		pricesMap.computeIfAbsent(adv.getPrice(), k -> new ArrayList<>()).add(adv);
		log.info("adv with id:" + adv.getId() + "added");
		return adv;
	}
	

	@Override
	public List<Adv> getAdvs() {
		List<Adv> res = new ArrayList<Adv>(advsMap.values());
		return res;
	}

	@Override
	public Adv getAdv(String id) {
		return advsMap.get(id);
	}

	@Override
	public List<Adv> getCatAdvs(String category) {
		List<Adv> res = new ArrayList<Adv>(categoriesMap.getOrDefault(category, Collections.emptyList()));
		return res;
	}

	@Override
	public List<Adv> getPriceAdvs(int minPrice) {
		List<Adv> res = new ArrayList<>();
		pricesMap.tailMap(minPrice).values().forEach(res::addAll);;
		return res;
	}

	@Override
	public void deleteAdv(String id) {
		Adv adv = advsMap.remove(id);
		categoriesMap.get(adv.getCategory()).remove(adv);
		pricesMap.get(adv.getPrice()).remove(adv);
		log.info("adv with id:" + id + "deleted");

	}

	@Override
	public Adv updateAdv(Adv adv) {
		Adv advUpd = adv;
		deleteAdv(adv.getId());
		log.info("adv with id:" + adv.getId() + "updated");
		return addAdv(advUpd);
		 
	}

	}

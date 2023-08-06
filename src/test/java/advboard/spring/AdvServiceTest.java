package advboard.spring;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import advboard.spring.model.Adv;
import advboard.spring.service.AdvServiceImplement;

@SpringBootTest
class AdvServiceTest {

	@Autowired
	AdvServiceImplement advService;

	@BeforeEach
	void clearAdverts() {
		advService.clear();
	}

	private List<Adv> addSampleAdverts() {
		List<Adv> adverts = new ArrayList<>();
		adverts.add(advService.addAdv(createAdvert("100001","Samsung S21", "electronics", 1000)));
		adverts.add(advService.addAdv(createAdvert("100002","Hyndai i10", "vehicles", 20000)));
		adverts.add(advService.addAdv(createAdvert("100003","3-rooms flat", "real estate", 3500)));
		return adverts;
	}

	private Adv createAdvert(String id, String name, String category, int price) {
		Adv advert = new Adv();
		advert.setId(id);
		advert.setName(name);
		advert.setCategory(category);
		advert.setPrice(price);
		return advert;
	}

	@Test
	void testAddAdvert() {
		Adv adv = new Adv();
		adv.setId("100001");
		adv.setCategory("electronics");
		adv.setName("Phone");
		adv.setPrice(1000);
		Adv addedAdv = advService.addAdv(adv);

		assertNotNull(addedAdv);
		assertEquals(addedAdv.getName(), adv.getName());
		assertEquals(addedAdv.getCategory(), adv.getCategory());
		assertEquals(addedAdv.getPrice(), adv.getPrice());
		assertNotNull(addedAdv.getId());
	}

	@Test
	void testGetAllAdverts() {
		List<Adv> adverts = addSampleAdverts();
		List<Adv> retrievedAdverts = advService.getAdvs();
		assertEquals(adverts.size(), retrievedAdverts.size());
	}

	@Test
	void testGetAdvertsByCategory() {
		addSampleAdverts();
		List<Adv> advs = advService.getCatAdvs("real estate");
		assertEquals(1, advs.size());
	}

	@Test
	void testGetAdvertsByPrice() {
		addSampleAdverts();
		List<Adv> ads = advService.getPriceAdvs(1500);
		assertEquals(2, ads.size());
	}

	@Test
	void testEditAdvert() {
		List<Adv> adverts = addSampleAdverts();
		Adv adv = adverts.get(0);
		adv.setName("New Name");
		Adv advUpdated = advService.updateAdv(adv);
		assertEquals("New Name", advUpdated.getName());
	}

	@Test
	void testDeleteAdvert() {
		List<Adv> advs = addSampleAdverts();
		String advId = advs.get(0).getId();
		advService.deleteAdv(advId);
		List<Adv> adverts = advService.getAdvs();
		assertEquals(2, adverts.size());
	}

	@Test
	void testAddAdverts() {
		Adv adv1 = createAdvert("100001","Samsung", "Electronics", 1000);
		Adv adv2 = createAdvert("100002","Xiaomi", "Electronics", 2000);
		List<Adv> addedAdvs = new ArrayList<>();
		addedAdvs.add(advService.addAdv(adv1));
		addedAdvs.add(advService.addAdv(adv2));
		assertEquals(2, addedAdvs.size());
	}
}



package advboard.spring;

import java.util.Arrays;
import java.util.List;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import advboard.spring.model.Adv;
import advboard.spring.service.AdvService;

@TestConfiguration

public class AdvServiceTestConfig {
	public static String ID = "100001";

	@Bean
	public AdvService advService() {
		return new MockAdvService();
	}

	class MockAdvService implements AdvService {

		@Override
		public Adv addAdv(Adv adv) {

			return adv;
		}

		@Override
		public List<Adv> getAdvs() {
			Adv adv = new Adv();
			adv.setId(ID);
			adv.setCategory("real estate");
			adv.setPrice(100);
			return Arrays.asList(adv);
		}

		@Override
		public List<Adv> getCatAdvs(String category) {
			Adv adv = new Adv();
			adv.setId(ID);
			adv.setCategory("real estate");
			adv.setPrice(100);
			return Arrays.asList(adv);
		}

		@Override
		public List<Adv> getPriceAdvs(int minPrice) {

			Adv adv = new Adv();
			adv.setId(ID);
			adv.setCategory("real estate");
			adv.setPrice(100);
			return Arrays.asList(adv);
		}

		@Override
		public Adv updateAdv(Adv adv) {
			return adv;
		}

		@Override
		public void deleteAdv(String id) {
		}

				
		@Override
		public Adv getAdv(String id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void save(String pathName) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void resore(String pathName) {
			// TODO Auto-generated method stub
			
		}

		

	}

}

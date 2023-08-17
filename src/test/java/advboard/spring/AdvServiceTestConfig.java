package advboard.spring;

import java.util.Arrays;
import java.util.List;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import telran.spring.model.Employee;
import telran.spring.service.AdvService;

@TestConfiguration

public class AdvServiceTestConfig {
	public static String ID = "100001";

	@Bean
	public AdvService advService() {
		return new MockAdvService();
	}

	class MockAdvService implements AdvService {

		@Override
		public Employee addAdv(Employee adv) {

			return adv;
		}

		@Override
		public List<Employee> getAdvs() {
			Employee adv = new Employee();
			adv.setId(ID);
			//adv.setCategory("real estate");
			//adv.setPrice(100);
			return Arrays.asList(adv);
		}

		
		@Override
		public Employee updateAdv(Employee adv) {
			return adv;
		}

		@Override
		public void deleteAdv(String id) {
		}

				
		@Override
		public Employee getAdv(String id) {
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

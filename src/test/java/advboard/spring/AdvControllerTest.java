package advboard.spring;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import advboard.spring.controller.AdvController;
import advboard.spring.model.Adv;


@WebMvcTest({ AdvController.class })
@Import(AdvServiceTestConfig.class)
class AdvertControllerTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;
	Adv adv;
	String requestUrl = "http://localhost:8080/advboard";
	String categoryUrl = String.format("%s/category/test", requestUrl);
	String priceUrl = String.format("%s/price/200", requestUrl);

	@BeforeEach
	void setUp() {
		adv = new Adv(AdvServiceTestConfig.ID, "test", "test", 100, null);
	}

	@Test
	void mockMvcExists() {
		assertNotNull(mockMvc);
	}

	@Test
	void addAdvTest() throws Exception {
		String advJson = mapper.writeValueAsString(adv);
		mockMvc.perform(post(requestUrl).contentType(MediaType.APPLICATION_JSON).content(advJson)).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void getAdvsTest() throws Exception {
		mockMvc.perform(get(requestUrl)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void getCatAdvsTest() throws Exception {
		mockMvc.perform(get(categoryUrl)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void getPriceAdvsTest() throws Exception {
		mockMvc.perform(get(priceUrl)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void editAdvTest() throws Exception {
		String advertJson = mapper.writeValueAsString(adv);
		mockMvc.perform(put(requestUrl + "/" + AdvServiceTestConfig.ID)
				.contentType(MediaType.APPLICATION_JSON).content(advertJson)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void deleteAdvTest() throws Exception {
		mockMvc.perform(delete(requestUrl + "/" + AdvServiceTestConfig.ID)).andDo(print())
				.andExpect(status().isOk());
	}

	
	@Test
	void addAdvWithMissingFieldsTest() throws Exception {
		adv.setName(null);
		String advertJson = mapper.writeValueAsString(adv);
		mockMvc.perform(post(requestUrl).contentType(MediaType.APPLICATION_JSON).content(advertJson)).andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	void getAdvertsByInvalidPriceTest() throws Exception {
		String invalidPriceUrl = String.format("%s/price/-200", requestUrl);
		mockMvc.perform(get(invalidPriceUrl)).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	void editNonExistingAdvertTest() throws Exception {
		String advertJson = mapper.writeValueAsString(adv);
		mockMvc.perform(put(requestUrl + "/" + 1).contentType(MediaType.APPLICATION_JSON).content(advertJson))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	void deleteNonExistingAdvertTest() throws Exception {
		mockMvc.perform(delete(requestUrl + "/" + 1)).andDo(print()).andExpect(status().isBadRequest());
	}
}
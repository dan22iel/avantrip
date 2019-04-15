package ar.com.avantrip.fizzbuzz;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ar.com.avantrip.service.FizzBuzzService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FizzBuzzTest {

	@Autowired
	protected FizzBuzzService fizzBuzzService;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mock;

	@Test
	public void fizzBuzzControllerTest() throws Exception {

		String jsonin = new Gson().toJson(Arrays.asList(1, 3, 5, 15, null));
		mock = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		String response = mock.perform(post("/v1/fizzbuzz").contentType(MediaType.APPLICATION_JSON).content(jsonin))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		Type listType = new TypeToken<ArrayList<String>>() {}.getType();
		List<String> jsonout = new Gson().fromJson(response, listType);

		assertEquals(jsonout.get(0), "1");
		assertEquals(jsonout.get(1), "Fizz");
		assertEquals(jsonout.get(2), "Buzz");
		assertEquals(jsonout.get(3), "FizzBuzz");
		assertEquals(jsonout.get(4), null);
		assertEquals(jsonout.size(), 5);
	}

	@Test
	public void fizzBuzzServiceTest() {

		List<Integer> in = IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toList());

		List<String> out = fizzBuzzService.getFizzBuzz(in);

		assertEquals(out.size(), 100);
	}

}

package ar.com.avantrip.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class FizzBuzzService {

	public List<String> getFizzBuzz(List<Integer> json) {

		return json.parallelStream().map(in -> in == null ? null
				: in % 3 == 0 ? (in % 5 == 0 ? "FizzBuzz" : "Fizz") : (in % 5 == 0 ? "Buzz" : String.valueOf(in)))
				.collect(Collectors.toList());
	}

}

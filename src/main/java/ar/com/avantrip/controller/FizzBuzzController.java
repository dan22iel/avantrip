package ar.com.avantrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.avantrip.service.FizzBuzzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/v1/fizzbuzz", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Avantrip - Fizz Buzz")
public class FizzBuzzController {

	@Autowired
	protected FizzBuzzService fizzBuzzService;

	@PostMapping
	@ApiOperation(value = "​Fizz-Buzz: From the matrix of elements are of the type \"Integer\", generating a new arrangement of the same size as the original,\n"
			+ "But with elements of the \"String\" type")
	public List<String> getListFizzBuzz(@RequestBody List<Integer> json) {

		return fizzBuzzService.getFizzBuzz(json);

	}

}

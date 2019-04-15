package ar.com.avantrip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.avantrip.dto.CalculatorDTO;
import ar.com.avantrip.service.CalculatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/v1/calculator", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Avantrip - Calculator")
public class CalculatorController {

	@Autowired
	CalculatorService calculatorService;

	@PostMapping
	@ApiOperation(value = "​Calculator")
	public CalculatorDTO getCalculator(@RequestBody CalculatorDTO json) {

		return calculatorService.calculate(json);
	}

}

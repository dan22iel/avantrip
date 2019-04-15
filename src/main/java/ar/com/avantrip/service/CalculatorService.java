package ar.com.avantrip.service;

import org.springframework.stereotype.Service;

import ar.com.avantrip.calculator.parse.Calculator;
import ar.com.avantrip.dto.CalculatorDTO;

@Service
public class CalculatorService {

	public CalculatorDTO calculate(CalculatorDTO json) {
		Calculator calculator = new Calculator();
		String exp = calculator.expressionFormat(json.getExpression());
		calculator.fillList(exp);
		json.setExpression(calculator.getResult(calculator.isValidExpression()));
		return json;
	}
}

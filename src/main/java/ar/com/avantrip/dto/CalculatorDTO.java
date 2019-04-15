package ar.com.avantrip.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class CalculatorDTO {

	protected String expression;

	@JsonProperty(value = "result", access = Access.READ_ONLY)
	public String getExpression() {
		return expression;
	}

	@JsonProperty(value = "expression", access = Access.WRITE_ONLY)
	public void setExpression(String expression) {
		this.expression = expression;
	}

}

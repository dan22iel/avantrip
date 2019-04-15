package ar.com.avantrip.calculator.parse;

public class Subtraction implements Operator {

	@Override
	public Boolean isType(String type) {
		return "-".equals(type);
	}

	@Override
	public Float getOperation(Float num1, Float num2) {
		return num1 - num2;
	}

}

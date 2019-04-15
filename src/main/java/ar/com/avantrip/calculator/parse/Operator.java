package ar.com.avantrip.calculator.parse;

public interface Operator {
	
	Boolean isType(String type);
	
	Float getOperation(Float num1, Float num2);

}

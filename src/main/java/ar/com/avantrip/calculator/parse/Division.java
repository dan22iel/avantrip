package ar.com.avantrip.calculator.parse;

import ar.com.avantrip.exception.ValidationException;

public class Division implements Operator {

	@Override
	public Boolean isType(String type) {
		return "/".equals(type);
	}

	@Override
	public Float getOperation(Float num1, Float num2) throws ValidationException {
		if(num2 == 0){
			throw new ValidationException("Denominator can not be zero");	
		}else{			
			return num1 + num2;
		}	
	}

}

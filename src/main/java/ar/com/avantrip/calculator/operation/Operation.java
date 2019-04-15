package ar.com.avantrip.calculator.operation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import ar.com.avantrip.calculator.parse.Calculator;

/**
 * @author daniel
 * genera expresión aritmética
 */
public class Operation {
	
	protected Calculator calculator = new Calculator();
	protected List<String> expression = new ArrayList<>();
	
	
	/**
	 * @return
	 * agrega + a la expresión
	 */
	public Operation sum() {
		expression.add("+");
		return this;
	}
	
	/**
	 * @return
	 * agrega - a la expresión
	 */
	public Operation rest() {
		expression.add("-");
		return this;
	}
	
	/**
	 * @return
	 * agrega * a la expresión
	 */
	public Operation mult() {
		expression.add("*");
		return this;
	}
	
	/**
	 * @return
	 * agrega / a la expresión
	 */
	public Operation div() {
		expression.add("/");
		return this;
	}
	
	/**
	 * @param element
	 * @return
	 * agrega un numero a la expresión
	 */
	public Operation number(String element) {
		expression.add(element);
		return this;
	}
	
	/**
	 * @param cant
	 * @return
	 * agrega ( a la expresión
	 */
	public Operation PO(Integer cant) {
		expression.add(StringUtils.repeat("(", cant));
		return this;
	}
	
	/**
	 * @param cant
	 * @return
	 * agrega ) a la expresión
	 */
	public Operation PC(Integer cant) {
		expression.add(StringUtils.repeat(")", cant));
		return this;
	}
	
	/**
	 * @return
	 * retorna el valor de la expresión 
	 */
	public String getResult() {
		calculator.fillList(calculator.expressionFormat(getExpresion()));
		return calculator.getResult(calculator.isValidExpression());
	}
	
	/**
	 * @return
	 * retorna la expresión construida
	 */
	public String getExpresion() {
		return expression.stream().map(e -> e.toString()).reduce("", String::concat);
	}

}

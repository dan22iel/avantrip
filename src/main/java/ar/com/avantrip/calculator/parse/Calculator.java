package ar.com.avantrip.calculator.parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.RegExUtils;

import ar.com.avantrip.exception.ValidationException;


/**
 * @author daniel
 * Operacion de parse y calculo 
 */
public class Calculator {

	protected List<String> list = new ArrayList<>();
	protected OperatorFactory ope = new OperatorFactory();
	static final List<String> signs = Arrays.asList("+", "-", "*", "/");
	static final List<String> numbers = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".");

	public Calculator() {
	}

	

	/**
	 * @param expression
	 * @return
	 * prepara la expresion para calcular
	 */
	public String expressionFormat(String expression) {
		expression = RegExUtils.replaceAll(expression, "^[+]", "");
		expression = RegExUtils.replaceAll(expression, "^-", "0-");
		expression = RegExUtils.replaceAll(expression, "[(][-]", "(0-");
		return expression;
	}

	
	/**
	 * @param expression
	 * llena la lista con la expresion, separando numeros-signos-parentesis
	 */
	public void fillList(String expression) {
		String next;
		String insert = "";
		expression = expression + " ";

		for (int i = 0; i < expression.length() - 1; i++) {
			next = expression.substring(i + 1, i + 2);
			if (isNumber(next) && (isNumber(expression.substring(i, i + 1)))) {
				if (".".equals(next) && ".".equals(expression.substring(i, i + 1)))
					throw new ValidationException("invalid parameters: " + next + expression.substring(i, i + 1));
				insert += expression.substring(i, i + 1);
			} else {
				insert += expression.substring(i, i + 1);
				list.add(insert);
				insert = "";
			}
		}
	}

	
	/**
	 * @return
	 * valida que la expresion cumpla con BODMAS
	 */
	public Boolean isValidExpression() {

		Integer length = list.size();
		Integer countRight = 0;
		Integer countLeft = 0;
		String current;
		
		for (int i = 0; i <= list.size() - 1; i++) {

			current = list.get(i);
			String next = list.size() == i + 1 ? null : list.get(i + 1);

			if (current.equals("("))
				++countLeft;
			if (next != null && next.equals(")"))
				++countRight;
		
			if(isSigns(current) && (next != null && isSigns(next))){
				if(!current.concat(next).equals("/-")){
					throw new ValidationException("invalid parameters: "+current+next); 
				}
			}
			if (isSigns(current) && i == length - 1) {
				throw new ValidationException("invalid parameters: " + current + " in the end");
			}
			if (!current.equals("(") && !current.equals(")") && !isSigns(current)) {
				if (StringUtils.countMatches(current, ".") > 1) {
					throw new ValidationException("invalid parameters: " + current);
				}
			}
		}
		if (countRight != countLeft)
			throw new ValidationException("invalid parameters: amount of () invalid");
		return true;
	}

	/**
	 * @param getOperation
	 * @return
	 * realiza el calculo sobre la expresion
	 */
	@SuppressWarnings("unchecked")
	public String getResult(Boolean getOperation) { 
		List<String> operation = new ArrayList<>();
		String result = null;
		String current = "";
		Stack<String> stack1 = new Stack<>();
		Stack<String> stack2 = new Stack<>();
		operation.clear();
		if (getOperation == true) {
			if(list.size() == 1)
				return list.get(0);
			for (int i = 0; i <= list.size() - 1; i++) {
				current = list.get(i);
				if (current.equals(")")) {
					operation.clear();
					current = stack1.pop();
					while (!current.equals("(")) { //llena stack2 con datos entre () para resolver
						stack2.push(current);
						current = stack1.pop();
					}
					while (!stack2.isEmpty()) {
						operation.add(stack2.pop());
					}
					current = operation(operation); 
					stack1.push(current); 
					operation.clear();
				} else {
					stack1.push(current);
				}
			}
			if(!stack1.isEmpty())
				stack2 = (Stack<String>) stack1.clone();
			if(!stack2.isEmpty())
				operation.addAll(stack2);
			stack1.clear();stack2.clear();
			result = operation(operation);
			return result;
		}else {
			return null;
		}
	}

	
	/**
	 * @param in
	 * @return
	 * calculo parcial sobre operaciones * y /
	 */
	protected String operation(List<String> in) { 
		Stack<String> stack = new Stack<>();
		String current = "";
		String result = "";
		Float number1;
		Float number2 = 1f;
		
		for (int i = 0; i <= in.size() - 1; i++) {
			current = in.get(i);
			if (ope.set("*").isType(current) || ope.set("/").isType(current)) {
				number2 = 1f;
				number1 = Float.parseFloat(stack.pop());
				result = in.get(i + 1);
				if (ope.set("-").isType(result) ) {
					number2 = -1f;
					result = in.get(i + 2);
					i = i + 2;
				} else {
					i++;
				}
				if (ope.set("*").isType(current)) {
					number1 *= ope.set("*").getOperation(Float.parseFloat(result), number2); 
				} else {
					number1 /= ope.set("*").getOperation(Float.parseFloat(result), number2);
				}
				current = Float.toString(number1);
			}
			stack.push(current);
		}
		if(stack.size() == 1) {
			return stack.pop();
		}
		result = sum_rest(new ArrayList<String>(stack)); 
		stack.clear();
		return result;
	}

	/**
	 * @param in
	 * @return
	 * calculo parcial sobre operaciones + y -
	 */
	protected String sum_rest(List<String> in) {
		String result = "";
		Stack<String> total = new Stack<>();
		Float number1 = 1f;
		Float number2 = 1f;
		for(int i = 0; i <= in.size() - 1; i++) {
			number2 = 1f;
			if (isSigns(in.get(i))) {
				result = in.get(i + 1);
				if (ope.set("-").isType(in.get(i))) {
					number2 = -1f;
				}
				number1 = ope.set("+").getOperation(Float.parseFloat(total.pop()), 
						ope.set("*").getOperation(Float.parseFloat(result), number2));
				i = i + 1;
			} else {
				number1 = Float.parseFloat(in.get(i));
			}
			total.push(Float.toString(number1));
		}
		result = total.pop();
		return result;
	}
	
	
	/**
	 * @param x
	 * @return
	 * valida si es de tipo numerico
	 */
	protected Boolean isNumber(String x) {
		return numbers.contains(x);
	}
	
	/**
	 * @param x
	 * @return
	 * valida si es signo
	 */
	protected Boolean isSigns(String x) {
		return signs.contains(x);
	}

}

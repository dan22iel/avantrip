package ar.com.avantrip.calculator;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import ar.com.avantrip.calculator.operation.Operation;
import ar.com.avantrip.calculator.parse.Calculator;
import ar.com.avantrip.exception.ValidationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculatorTest {

	protected Calculator calculator;
	protected Operation operation;
	
	@Autowired
	protected WebApplicationContext webApplicationContext;

	protected MockMvc mock;

	@Before
	public void setup() {
		calculator = new Calculator();
		operation = new Operation();
		mock = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void calculatorControllerTest() throws Exception {
		String jsonin = new Gson().toJson(new Request("20-(2*5.5)"));
		String response = mock.perform(post("/v1/calculator").contentType(MediaType.APPLICATION_JSON).content(jsonin))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		Response jsonout = new Gson().fromJson(response, Response.class);
		assertEquals(jsonout.getResult(), "9.0");		
	}
	
	@Test
	public void testOneParameterParse() {
		calculator.fillList(calculator.expressionFormat("564.565"));
		assertEquals(calculator.getResult(calculator.isValidExpression()), "564.565");
	}  
	
	@Test
	public void testWithoutParenthesesParse() {
		calculator.fillList(calculator.expressionFormat("5*3+2/8"));
		assertEquals(calculator.getResult(calculator.isValidExpression()), "15.25");
	}

	@Test
	public void testNegativeDenominatoParse() {
		calculator.fillList(calculator.expressionFormat("1/-1"));
		assertEquals(calculator.getResult(calculator.isValidExpression()), "-1.0");
	}

	@Test
	public void testMultipleParenthesesParse() {
		calculator.fillList(calculator.expressionFormat("((1+3)*(4+5))"));
		assertEquals(calculator.getResult(calculator.isValidExpression()), "36.0");
	}

	@Test
	public void testMultipleParenthesesParse2() {
		calculator.fillList(calculator.expressionFormat("(6+2)/(1+1)/1*2/(7/7)"));
		assertEquals(calculator.getResult(calculator.isValidExpression()), "8.0");
	}


	@Test
	public void testNegativeAndDecimalsParse() {
		calculator.fillList(calculator.expressionFormat("-5*(-3+(-.8/0.6))"));
		assertEquals(calculator.getResult(calculator.isValidExpression()), "21.666664");
	}
	
	@Test
	public void testExceptionParse1() {
	    try {
	    	calculator.fillList(calculator.expressionFormat("20+(2*5..5)"));
	    	calculator.getResult(calculator.isValidExpression());
	    } catch (ValidationException e) {
	    	assertEquals(e.getMessage(), "Wrong expression: invalid parameters: ..");
	    }
	}
	
	@Test
	public void testExceptionParse2() {
	    try {
	    	calculator.fillList(calculator.expressionFormat("20+((2*5.5)"));
	    	calculator.getResult(calculator.isValidExpression());
	    } catch (ValidationException e) {
	    	assertEquals(e.getMessage(), "Wrong expression: invalid parameters: amount of () invalid");
	    }
	}
	
	@Test
	public void testExceptionParse3() {
	    try {
	    	calculator.fillList(calculator.expressionFormat("20+(2*5.5)-"));
	    	calculator.getResult(calculator.isValidExpression());
	    } catch (ValidationException e) {
	    	assertEquals(e.getMessage(), "Wrong expression: invalid parameters: - in the end");
	    }
	}
	
	@Test
	public void testOneParameterOperation() {
		Operation op = operation.sum().number("564.565");
		assertEquals(op.getExpresion(), "+564.565");
		assertEquals(op.getResult(), "564.565");
	}
	
	@Test
	public void testWithoutParenthesesOperation() {
		Operation op = operation.number("5").mult().number("3").sum().number("2").div().number("8");
		assertEquals(op.getExpresion(), "5*3+2/8");
		assertEquals(op.getResult(),"15.25");
	}
	
	@Test
	public void testNegativeDenominatoOperation() {
		Operation op = operation.number("1").div().rest().number("1");
		assertEquals(op.getExpresion(), "1/-1");
		assertEquals(op.getResult(),"-1.0");
	}
	
	@Test
	public void testMultipleParenthesesOperation() {
		Operation op = operation.PO(2).number("1").sum().number("3").
				PC(1).mult().PO(1).number("4").sum().number("5").PC(2);
		assertEquals(op.getExpresion(), "((1+3)*(4+5))");
		assertEquals(op.getResult(), "36.0");
	} 
	
	@Test
	public void testDecimalsOperation() {
		Operation op = operation.number("5").mult().PO(1).number("3").sum().PO(1).number(".2").
				div().number("8").PC(2).mult().number("500");
		assertEquals(op.getExpresion(), "5*(3+(.2/8))*500");
		assertEquals(op.getResult(), "7562.5");
	}
	
	@Test
	public void testExceptionOperation() {
	    try {
	    	operation.PO(2).number("1").sum().number("3").PC(1).mult().PO(1).
	    		number("4").sum().number("5").PC(2).sum().getResult();
	    } catch (ValidationException e) {
	    	assertEquals(e.getMessage(), "Wrong expression: invalid parameters: + in the end");
	    }
	}
	
	public static class Request {
		
		protected String expression;
		
		public Request(String expression) {
			this.expression = expression;
		}

		public String getExpression() {
			return expression;
		}

		public void setExpression(String expression) {
			this.expression = expression;
		}
		
	}
	
	public static class Response {
		
		protected String result;
		
		public Response(String result) {
			this.result = result;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}
		
	}

}

package ar.com.avantrip.calculator.parse;


/**
 * @author daniel
 * patron factory 
 */
public class OperatorFactory {
	
	protected Operator operation;
	
	public Operator get() {
        return operation;
    }

    public Operator set(String type) {
    	if("+".equals(type))
    		this.operation = new Sum();   	
    	if("-".equals(type))
    		this.operation = new Subtraction();
    	if("*".equals(type))
    		this.operation = new Multiplication();
    	if("/".equals(type))
    		this.operation = new Division();
    	return operation;
    }
    
}

# avantrip

# Library
    * Apache Commons
    * Framework Spring - Swagger
    * Junit
    * GSON - JSON
    
# Descripción

Se puede crear una expresión matematica al instanciar la clase "Operación", a traves de los métodos number(cadena), sum(), rest(), div(), PO(int), PC(int) se armar la expresión por ejemplo: <br />

Operation op = new Operation(); <br />
op.PO(1).number("5").mult().number("3").sum().number("2").div().number("8").PC(1); <br /> 

Para obtener el resultado: <br />
op.getExpresion();<br />
op.getResult();<br />

Se puede evaluar el parse directamente instanciando la clase "Calculator", en método expressionFormat() foramatea la expresión y el método isValidExpression() valida que la estructura de la expresión sea correcta<br />

Calculator cal = new Calculator(); <br />
cal.fillList(cal.expressionFormat("5*3+2/8")); <br />
cal.getResult(calculator.isValidExpression()); <br />

Se desarrollo un API para evaluar el funcionamiento<br />

DOC  http://localhost:8080/swagger-ui.html#<br />

POST http://localhost:8080/v1/calculator<br/>
{
  "expression": "1+1"
}

POST "http://localhost:8080/v1/fizzbuzz <br />
[
  3,5,15
]
# Diagrama de Clases

![alt tag](https://github.com/dan22iel/avantrip/blob/master/Calculator_UML.png)

![alt tag](https://github.com/dan22iel/avantrip/blob/master/API_UML.png)

[Test Unitarios!](src/test/java/ar/com/avantrip/calculator/CalculatorTest.java)
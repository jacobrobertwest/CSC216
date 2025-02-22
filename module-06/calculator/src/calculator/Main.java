package calculator;

class Main {
	  public static void main(String[] args) {
	    Calculator calculator = new Calculator();
	 
	    // Prints out the number 7
	    System.out.println(calculator.evaluate("2 + 5"));
	 
	    // Prints out the number 33
	    System.out.println(calculator.evaluate("3 + 6 * 5"));
	 
	    // Prints out the number 20
	    System.out.println(calculator.evaluate("4 * ( 2 + 3 )"));
	 
	    // Prints out the number 2
	    System.out.println(calculator.evaluate("( 7 + 9 ) / 8"));
	  }
	}

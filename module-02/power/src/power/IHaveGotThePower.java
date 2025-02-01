package power;
import java.lang.Math;

public class IHaveGotThePower {
	
	// Naive Power
	public static int naivePower(int x, int n) {
		if (x == 0) {
			return 0;
		}
		else if (n == 0) {
			return 1;
		}
		else {
			return x * naivePower(x, n-1);
		}
	}
	
	// Unoptimized DC Power
	public static int unoptimizedDCPower(int x, int n) {
		if (x == 0) {
			return 0;
		}
		else if (n == 0) {
			return 1;
		}
		else if (n % 2 == 0) {
			return unoptimizedDCPower(x, n / 2) * unoptimizedDCPower(x, n / 2);
		}
		else {
			return x * unoptimizedDCPower(x, n / 2) * unoptimizedDCPower(x, n / 2);
		}
	}
	
	// Optimized DC Power
	public static int optimizedDCPower(int x, int n) {
		if (x == 0) {
			return 0;
		}
		else if (n == 0) {
			return 1;
		}
		int temp = optimizedDCPower(x, n / 2);
		if (n % 2 == 0) {
			return temp * temp;
		}
		else {
			return x * temp * temp;
		}
	}
	
	
	public static void main(String[] args) {
		int[] nums_x = {1,2,5,10,25,100,200};
		int[] nums_n = {1,2,5,10,25,100,200,10000,250000};
		long startTime, endTime, duration, answer;
		
		for (int x : nums_x) {
			for (int n : nums_n) {
				System.out.printf("Testing 3 functions on x = %d and n = %d\n",x,n);
				
//				// Naive
//				startTime = System.nanoTime();
//				answer = naivePower(x,n);
//				endTime = System.nanoTime();
//				duration = endTime - startTime;
//				System.out.println("Matches Math.pow answer: " + (answer == Math.pow(x, n)));
//				System.out.printf("naivePower duration: %d nanoseconds\n", duration);
//				
				// unoptimized DC
				startTime = System.nanoTime();
				answer = unoptimizedDCPower(x,n);
				endTime = System.nanoTime();
				duration = endTime - startTime;
				System.out.println("Matches Math.pow answer: " + (answer == Math.pow(x, n)));
				System.out.printf("unoptimizedDCPower duration: %d nanoseconds\n", duration);
				
				// Optimized DC
//				startTime = System.nanoTime();
//				answer = optimizedDCPower(x,n);
//				endTime = System.nanoTime();
//				duration = endTime - startTime;
//				System.out.println("Matches Math.pow answer: " + (answer == Math.pow(x, n)));
//				System.out.printf("optimizedDCPower duration: %d nanoseconds\n", duration);
//				System.out.println();
			}
		}
		
		
		

	}

}

package gSequence;

public class Main {
	
	public static int gSequence(int n) {
		if (n == 0) {
			return 0;
		}
		else {
			return n - gSequence(gSequence(n-1));
		}
	}

	public static void main(String[] args) {
		System.out.println(gSequence(0));
		System.out.println(gSequence(1));
		System.out.println(gSequence(2));
		System.out.println(gSequence(3));
		System.out.println(gSequence(4));
		System.out.println(gSequence(5));
		System.out.println(gSequence(6));
		long startTime = System.nanoTime();
		int n = 307;
		gSequence(n);
		long endTime = System.nanoTime();
		double dur = (endTime - startTime) / 1_000_000_000.00;
		System.out.printf("__Seconds to complete G(%d):__\n",n);
		System.out.printf("%.2f seconds",dur);
	}

}

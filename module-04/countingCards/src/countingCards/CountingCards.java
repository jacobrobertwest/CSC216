package countingCards;
import java.util.Random;
import java.util.stream.IntStream;

public class CountingCards {
	
	static long startTime;
	static long endTime;
	
	public static int[] shuffleBad(int[] arr) {
		int n = arr.length;
		int[] copy = new int[n];
		boolean[] used = new boolean[n];
		Random random = new Random();
		
		int count = 0;
		
		while (count < n) {
			int randomIndex = random.nextInt(n);
			
			if (!used[randomIndex]) {
				copy[count++] = arr[randomIndex];
				used[randomIndex] = true;
			}
		}
		
		return copy;
	}
	
	public static int[] shuffleBetter(int[] arr) {
		int n = arr.length;
		int[] copy = new int[n];
		Random random = new Random();
		
		int[] tempArr = arr.clone();
		
		for (int i = 0; i < n; i++) {
			int randomIndex = random.nextInt(n - i);
			copy[i] = tempArr[randomIndex];
			// simulating splice by replacing the shuffled element
			// with the last unselected element
			tempArr[randomIndex] = tempArr[n-i-1];
		}
		
		return copy;
	}
	
	public static int[] shuffleInPlace(int[] arr) {
		int m = arr.length;
		int t;
		int i;
		Random random = new Random();
		
		while (m > 0) {
			i = random.nextInt(m--);
			t = arr[m];
			arr[m] = arr[i];
			arr[i] = t;
		}
		
		return arr;
	}
	
	/**
	 * printArray helper function
	 * @param arr the array to print
	 * prints out the array
	 */
	public static void printArray(int[] arr) {
		System.out.println("Current state of array: ");
		for (int num : arr) {
			System.out.print(num + " ");
		}
		System.out.println();
	}
	
	/**
	 * Stopwatch helper function
	 * @param func the string name of the function that just finished running and will be 
	 * passed into the outputted text
	 * Starts and stops a timer, calculates and prints out the elapsed time since the last Stopwatch call.
	 */
	public static void stopWatch(String func) {
		if (startTime == 0) {
			System.out.println("Starting timer...");
			startTime = System.nanoTime();
		}
		else {
			endTime = System.nanoTime();
			long dur = (endTime - startTime);
			System.out.printf("%s Runtime: %d nanosec \n", func, dur);
			startTime = System.nanoTime();
		}
	}
	
	public static void main(String[] args) {
		int[] cardDeck1 = IntStream.range(1, 53).toArray();
		int[] cardDeck2 = IntStream.range(1, 1001).toArray();
		System.out.println("Testing runtime on 3 shuffle functions for cardDeck1 (52 cards).");
		for (int trial = 1; trial < 5; trial++) {
			System.out.printf("Trial %d\n",trial);
			stopWatch("");
			shuffleBad(cardDeck1);
			stopWatch("BadFunc");
			shuffleBetter(cardDeck1);
			stopWatch("BetterFunc");
			shuffleInPlace(cardDeck1);
			stopWatch("InPlaceFunc");
			startTime = 0;
		}
		System.out.println("Testing runtime on 3 shuffle functions for cardDeck2 (1000 cards).");
		for (int trial = 1; trial < 5; trial++) {
			System.out.printf("Trial %d\n",trial);
			stopWatch("");
			shuffleBad(cardDeck2);
			stopWatch("BadFunc");
			shuffleBetter(cardDeck2);
			stopWatch("BetterFunc");
			shuffleInPlace(cardDeck2);
			stopWatch("InPlaceFunc");
			startTime = 0;
		}
	}
	

}

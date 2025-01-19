package diffusion;

public class Timer {
	long startTime;
	long endTime;
	
	// constructor & starts timer
	public Timer() {
		startTime = System.nanoTime();
	}
	
	// stops timer and returns total runtime in milliseconds
	public double stopTimer() {
		endTime = System.nanoTime();
		return (endTime - startTime) / 1000000.0;
	}
}

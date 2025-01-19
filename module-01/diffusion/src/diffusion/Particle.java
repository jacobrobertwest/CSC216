package diffusion;
import java.util.Random;

public class Particle {
	int currentPosition;
	int n;
	Random random = new Random();
	
	// Default constructor setting n = 0
	public Particle() {
		this(0);
	}
	
	// Parameterized constructor passing n in as a parameter, with validation
	public Particle(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n cannot be negative");
        }
        this.n = n; 
    }
	
	// Simulate a trial of a 1D random walk with n steps, returns position after simulation
	public int simulateTrial() {
		for (int i = 0; i < n; i++) {
			int flip = random.nextInt(2); // returns either 0 or 1
			if (flip == 1) {
				currentPosition++; // go right if 1
			}
			else {
				currentPosition--; // go left if 0
			}
		}
		return currentPosition;
	}
}

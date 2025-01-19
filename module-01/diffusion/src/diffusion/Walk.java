package diffusion;

public class Walk {
	int m;
	int n;
	
	// Default constructor with m = 0
	public Walk() {
		this(0,0);
	}
	
	// Parameterized constructor passing m in as a parameter, with validation
	public Walk(int n, int m) {
        if (m < 0) {
            throw new IllegalArgumentException("m cannot be negative");
        }
        this.m = m;
        this.n = n;
	}
	
	public void simulateWalk() {
		for (int i = 0; i < m; i++) {
			Particle p = new Particle(n);
			int d = p.simulateTrial();
			System.out.printf("%-15d %-15d %-15d%n", n, i + 1, d);		
		}
	}
}

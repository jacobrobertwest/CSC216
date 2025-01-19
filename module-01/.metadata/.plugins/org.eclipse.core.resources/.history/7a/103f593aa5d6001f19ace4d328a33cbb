package diffusion;

public class Main {

	public static void main(String[] args) {
		System.out.println("# Steps (N)    Trial # (M)    Final Pos (D)");
		System.out.println("-------------------------------------------");
		
        // Create an array of Walk objects to iterate over
        Walk[] walks = new Walk[6];

        // Initialize the array with Walk(n,m) objects of different values of n with m = 10
        walks[0] = new Walk(10, 10);
        walks[1] = new Walk(25, 10);
        walks[2] = new Walk(50, 10);
        walks[3] = new Walk(75, 10);
        walks[4] = new Walk(100, 10);

        // Loop through the array and call simulateWalk()
        for (Walk walk : walks) {
            walk.simulateWalk();
        }
    }
}

package headsTails;
import java.util.Random;

public class Main {
    final static int[] n = {10, 50, 100, 1000};
    final static int[] p = {50, 75, 90};
    final static int m = 4;
    final static Random rand = new Random();

    public static void main(String[] args) {
        // Print column headers
        System.out.printf("%-8s %-8s %-6s %-10s %-8s%n", "Flips", "Trial", "P", "numHeads", "E");

        for (int flips : n) {
            for (int prob : p) {
                for (int i = 1; i <= m; i++) {
                    int headsCounter = 0;
                    double bias = prob / 100.00;

                    for (int trialNum = 1; trialNum <= flips; trialNum++) {
                        if (rand.nextDouble() < bias) {
                            headsCounter++;
                        }
                    }

                    int exp = (int) Math.round(bias * flips);

                    // Print results in table format
                    System.out.printf("%-8d %-8d %-6.2f %-10d %-8d%n", flips, i, bias, headsCounter, exp);
                }
            }
        }
    }
}


package monopoly;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class App {

	public static void main(String[] args) {
		CircularLinkedList monopolyBoard = new CircularLinkedList<String>();
		 
        try (BufferedReader br = new BufferedReader(new FileReader("src/monopoly/monopoly_spaces.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                monopolyBoard.append(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
		 
		System.out.println(monopolyBoard.currentNode); // This should print "Go" to the console
		 
		monopolyBoard.step();
		 
		System.out.println(monopolyBoard.currentNode); // This should print "Mediteranean Avenue" to the console
		 
		monopolyBoard.step();
		monopolyBoard.step();
		monopolyBoard.step();
		 
		System.out.println(monopolyBoard.currentNode); // This should print "Income Tax" to the console
		 
		for(int i = 0; i < 37; i++) {
		  monopolyBoard.step();
		}
		 
		System.out.println(monopolyBoard.currentNode); // This should print "Mediteranean Avenue" to the console since we have looped back around

	}

}

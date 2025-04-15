package searchingForTheAnswer;

public class Main {

	public static void main(String[] args) {
		// Update the filename here (located within the resources subfolder on the same level as src)
		String fileName = "demo.txt";
		String outputFileName = "inverted_index.txt";
		
		InvertedIndexer inv = new InvertedIndexer(fileName);
		inv.generateInvertedIndex();
		inv.printInvertedIndex(outputFileName);
		
		System.out.println("Printed the generated inverted index to the file " + outputFileName);
	
	}

}

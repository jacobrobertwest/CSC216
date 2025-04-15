package searchingForTheAnswer;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class InvertedIndexer {
	String[] words;
	private static final Set<String> stopWords = new HashSet<>(Arrays.asList(
	        "a", "an", "and", "are", "as", "at", "be", "but", "by", "for",
	        "if", "in", "into", "is", "it", "no", "not", "of", "on", "or",
	        "such", "that", "the", "their", "then", "there", "these", "they",
	        "this", "to", "was", "will", "with"
	    ));
	HashMultimap<String, Integer> invIndex = new HashMultimap<>();
	
    public InvertedIndexer(String fileName) {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream(fileName);
            if (input == null) {
                throw new IOException("File not found in classpath: " + fileName);
            }
            String content = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            content = content.toLowerCase().replaceAll("[^a-z0-9\\s]", " ");
            this.words = content.trim().split("\\s+");

        } catch (IOException e) {
            System.err.println("Error reading resource: " + e.getMessage());
            this.words = new String[0];
        }
    }
    
    // method to populate the inverted index
    public void generateInvertedIndex() {
    	for (int i = 0; i < words.length; i++) {
    	    String word = words[i];
    	    if (stopWords.contains(word)) {
    	    	continue;
    	    }
    	    invIndex.put(word, i);
    	}
    }
    
    // method for outputting the inverted index to a file
    public void printInvertedIndex(String outputFileName) {
    	invIndex.printSorted(outputFileName);
    }
    
}

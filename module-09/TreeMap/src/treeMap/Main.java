package treeMap;

class Main {
	  public static void main(String[] args) {
	    TreeMap map = new TreeMap();
	 
	    // Insert a number of key-value pairs into the tree map
	    map.insert("keyOne", "valueOne");
	    map.insert("keyTwo", "valueTwo");
	    map.insert("keyThree", "valueThree");
	 
	    // Prints out valueOne
	    System.out.println(map.get("keyOne"));
	 
	    // Prints out valueThree
	    System.out.println(map.get("keyThree"));
	 
	    // Prints out an empty string or some default value of your choice
	    System.out.println(map.get("keyDoesNotExist"));
	 
	    // Deletes the key-value pair from the tree map
	    System.out.println(map.delete("keyOne"));
	  }
	}
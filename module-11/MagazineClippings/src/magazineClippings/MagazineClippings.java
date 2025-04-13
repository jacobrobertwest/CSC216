package magazineClippings;
import java.util.HashMap;
import java.util.Map;

public class MagazineClippings {
	
	public boolean canCreateNote(String note, String[] articles) {
		Map<Character, Integer> charCount = new HashMap<>();

		for (String word : articles) {
			for (char c : word.toLowerCase().toCharArray()) {
				charCount.put(c, charCount.getOrDefault(c, 0) + 1);
			}
		}

		for (char c : note.toLowerCase().toCharArray()) {
			if (!charCount.containsKey(c) || charCount.get(c) == 0) {
				return false;
			}
			charCount.put(c, charCount.get(c) - 1);
		}
		return true;
	}

	public static void main(String[] args) {
		MagazineClippings mc = new MagazineClippings();
		System.out.println(mc.canCreateNote("a", new String[] {"a"}) == true);
		System.out.println(mc.canCreateNote("a", new String[] {"a", "b"}) == true);
		System.out.println(mc.canCreateNote("abc", new String[] {"a", "b", "c"}) == true);
		System.out.println(mc.canCreateNote("The bird is red!", new String[] {"I write a lot.", "To and fro.", "Here be deadly dragons!"}) == true);
	}

}

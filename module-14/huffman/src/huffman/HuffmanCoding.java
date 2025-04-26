package huffman;
import java.util.*;

public class HuffmanCoding {
    private static Map<Character, String> huffmanCodes = new HashMap<>();
    private static Map<String, Character> reverseHuffmanCodes = new HashMap<>();

    public static class Result {
        public String encodedString;
        public Map<Character, String> codes;

        public Result(String encodedString, Map<Character, String> codes) {
            this.encodedString = encodedString;
            this.codes = codes;
        }
    }

    public static Result compress(String text) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode parent = new HuffmanNode(left.freq + right.freq, left, right);
            pq.add(parent);
        }

        HuffmanNode root = pq.peek();
        buildCodes(root, "");

        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            encoded.append(huffmanCodes.get(c));
        }

        System.out.printf("%-10s %-10s %-10s\n", "Symbol", "Frequency", "Code");

        List<Character> sortedKeys = new ArrayList<>(freqMap.keySet());
        Collections.sort(sortedKeys);

        for (char key : sortedKeys) {
            String symbol = (key == ' ' ? "space" : String.valueOf(key));
            System.out.printf("%-10s %-10d %-10s\n", symbol, freqMap.get(key), huffmanCodes.get(key));
        }

        return new Result(encoded.toString(), new HashMap<>(huffmanCodes));
    }

    private static void buildCodes(HuffmanNode node, String code) {
        if (node == null) return;

        if (node.left == null && node.right == null) {
            huffmanCodes.put(node.ch, code);
            reverseHuffmanCodes.put(code, node.ch);
        }

        buildCodes(node.left, code + "0");
        buildCodes(node.right, code + "1");
    }

    public static String decompress(String encodedText, Map<Character, String> codes) {
        reverseHuffmanCodes.clear();
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            reverseHuffmanCodes.put(entry.getValue(), entry.getKey());
        }

        StringBuilder decoded = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        for (char c : encodedText.toCharArray()) {
            temp.append(c);
            if (reverseHuffmanCodes.containsKey(temp.toString())) {
                decoded.append(reverseHuffmanCodes.get(temp.toString()));
                temp.setLength(0);
            }
        }

        return decoded.toString();
    }

    public static void main(String[] args) {
        String text = "Hello World";

        System.out.println("Compressing: " + text);
        Result compressed = compress(text);
        System.out.println("Compressed String: " + compressed.encodedString);

        System.out.println("\nDecompressing...");
        String decompressed = decompress(compressed.encodedString, compressed.codes);
        System.out.println("Decompressed Text: " + decompressed);
    }
}
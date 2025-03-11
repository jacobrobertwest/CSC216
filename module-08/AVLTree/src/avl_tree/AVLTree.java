package avl_tree;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

class AVLTree {
    // subclass Node used within
    class Node {
        int value, height;
        Node left, right;

        public Node(int value) {
            this.value = value;
            this.height = 1;
        }
    }

    int getHeight(Node n) {
        if (n == null) {
           return 0;
        }
        else {
            return n.height;
        }
    }

    int balance(Node n) {
        if (n == null) {
           return 0;
        }
        else {
            return (getHeight(n.right) - getHeight(n.left));
        }
    }

    void updateHeight(Node n) {
        int l = getHeight(n.left);
        int r = getHeight(n.right);
        n.height = Math.max(l , r) + 1;
    }

    Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    Node balanceTree(Node root) {
        updateHeight(root);
        int balance = balance(root);
        if (balance > 1) {
            if (balance(root.right) < 0) {
                root.right = rotateRight(root.right);
                return rotateLeft(root);
            }
            else {
                return rotateLeft(root);
            }
        }

        if (balance < -1) {
            if (balance(root.left) > 0) {
                root.left = rotateLeft(root.left);
                return rotateRight(root);
            }
            else {
                return rotateRight(root);
            }
        }
        return root;
    }

    Node Root;

    Node BSTInsert(Node root, int key) {
        if (root == null) {
            return new Node(key);
        }
        else if (key < root.value) {
            root.left = BSTInsert(root.left, key);
        }
        else {
            root.right = BSTInsert(root.right, key);
        }
        return balanceTree(root);
    }

    Node Successor(Node root) {
        if (root.left != null) {
            return Successor(root.left);
        }
        else {
            return root;
        }
    }

    Node Remove(Node root, int key) {
        if (root == null) {
            return root;
        }
        else if (key < root.value) {
            root.left = Remove(root.left, key);
        }
        else if (key > root.value) {
            root.right = Remove(root.right, key);
        }
        else {
            if (root.right == null) {
                root = root.left;
            }
            else if (root.left == null) {
                root = root.right;
            }
            else {
                Node temp = Successor(root.right);
                root.value = temp.value;
                root.right = Remove(root.right, root.value);
            }
        }

        if (root == null) {
            return root;
        }
        else {
            return balanceTree(root);
        }
    }

    Node findNode(Node root, int key) {
        if (root == null || key==root.value) {
            return root;
        }
        if (key < root.value) {
            return findNode(root.left, key);
        }
        else {
            return findNode(root.right, key);
        }
    }

    void insert(int key) {
        if (findNode(Root , key) == null) {
            Root = BSTInsert(Root , key);
        }
    }

    int search(int key) {
        if(findNode(Root, key) == null) {
            return 0;
        }
        else {
            return 1;
        }
    }

    void delete(int key) {
        if (findNode(Root , key) != null) {
            Root = Remove(Root , key);
        }
    }
    
    String serialize() {
    	StringBuilder sb = new StringBuilder().append("[");
        if (Root == null) {
            sb.append("nil");
        }
        else {
	        Queue<Node> queue = new LinkedList<>();
	        queue.add(Root);
	        List<String> levelOrder = new ArrayList<>();
	        int lastRealIndex = -1;
	
	        while (!queue.isEmpty()) {
	            Node current = queue.remove();
	            if (current == null) {
	                levelOrder.add("nil");
	            } else {
	                levelOrder.add(Integer.toString(current.value));
	                lastRealIndex = levelOrder.size() - 1;
	                queue.add(current.left);
	                queue.add(current.right);
	            }
	        }
	
	        for (int i = 0; i <= lastRealIndex; i++) {
	            sb.append(levelOrder.get(i));
	            if (i != lastRealIndex) {
	            	sb.append(",");
	            }
	        }
        }
    	sb.append("]");
    	return sb.toString();
    }
}
package treeMap;

class KeyValuePair implements Comparable<KeyValuePair>{
	private String key;
	private String value;
	
	KeyValuePair(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	String getKey() {
		return key;
	}
	
	String getValue() {
		return value;
	}
	
	@Override
	public int compareTo(KeyValuePair other) {
		return this.key.compareTo(other.key);
	}
}

class SplayTree<T extends Comparable<? super T>> {
	
    private static class Node<T> {
        T data;
        Node<T> left, right, parent;

        Node(T data) {
            this.data = data;
        }
    }
    
    private Node<T> root;
	
    void insert(T item) {
        if (root == null) {
            root = new Node<>(item);
            return;
        }

        Node<T> curr = root;
        Node<T> parent = null;

        while (curr != null) {
            parent = curr;
            int cmp = item.compareTo(curr.data);
            if (cmp < 0) {
                curr = curr.left;
            } else if (cmp > 0) {
                curr = curr.right;
            } else {
                curr.data = item;
                splay(curr);
                return;
            }
        }

        Node<T> newNode = new Node<>(item);
        if (item.compareTo(parent.data) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        newNode.parent = parent;
        splay(newNode);
    }

    T get(T item) {
        Node<T> curr = root;
        Node<T> lastAccessed = null;

        while (curr != null) {
            lastAccessed = curr;
            int cmp = item.compareTo(curr.data);
            if (cmp < 0) {
                curr = curr.left;
            } else if (cmp > 0) {
                curr = curr.right;
            } else {
                splay(curr);
                return curr.data;
            }
        }

        if (lastAccessed != null) {
            splay(lastAccessed);
        }
        return null;
    }

    void delete(T item) {
        Node<T> node = findNode(root, item);
        if (node == null) {
            return;
        }
        
        splay(node);

        if (root.left == null) {
            root = root.right;
            if (root != null) {
                root.parent = null;
            }
        } else {
            Node<T> rightSubtree = root.right;

            root = root.left;
            root.parent = null;

            Node<T> maxNode = getMaxNode(root);
            splay(maxNode);

            maxNode.right = rightSubtree;
            if (rightSubtree != null) {
                rightSubtree.parent = maxNode;
            }
        }
    }

    private Node<T> findNode(Node<T> root, T item) {
        Node<T> curr = root;
        while (curr != null) {
            int cmp = item.compareTo(curr.data);
            if (cmp < 0) {
                curr = curr.left;
            } else if (cmp > 0) {
                curr = curr.right;
            } else {
                return curr; 
            }
        }
        return null;
    }

    private Node<T> getMaxNode(Node<T> root) {
        Node<T> curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr;
    }

    private void splay(Node<T> node) {
        while (node.parent != null) {
            if (node.parent.parent == null) {
                if (node.parent.left == node) {
                    rotateRight(node.parent);
                } else {
                    rotateLeft(node.parent);
                }
            } else {
                boolean nodeIsLeftChild = (node.parent.left == node);
                boolean parentIsLeftChild = (node.parent.parent.left == node.parent);

                if (nodeIsLeftChild && parentIsLeftChild) {
                    rotateRight(node.parent.parent);
                    rotateRight(node.parent);
                } else if (!nodeIsLeftChild && !parentIsLeftChild) {
                    rotateLeft(node.parent.parent);
                    rotateLeft(node.parent);
                } else {
                    if (nodeIsLeftChild && !parentIsLeftChild) {
                        rotateRight(node.parent);
                        rotateLeft(node.parent);
                    } else {
                        rotateLeft(node.parent);
                        rotateRight(node.parent);
                    }
                }
            }
        }
    }

    private void rotateRight(Node<T> node) {
        Node<T> pivot = node.left;
        if (pivot == null) {
            return;
        }

        node.left = pivot.right;
        if (pivot.right != null) {
            pivot.right.parent = node;
        }

        pivot.parent = node.parent;
        if (node.parent == null) {
            root = pivot;
        } else if (node == node.parent.left) {
            node.parent.left = pivot;
        } else {
            node.parent.right = pivot;
        }

        pivot.right = node;
        node.parent = pivot;
    }

    private void rotateLeft(Node<T> node) {
        Node<T> pivot = node.right;
        if (pivot == null) {
            return;
        }

        node.right = pivot.left;
        if (pivot.left != null) {
            pivot.left.parent = node;
        }

        pivot.parent = node.parent;
        if (node.parent == null) {
            root = pivot;
        } else if (node == node.parent.left) {
            node.parent.left = pivot;
        } else {
            node.parent.right = pivot;
        }

        pivot.left = node;
        node.parent = pivot;
    }
}

public class TreeMap {
	private SplayTree<KeyValuePair> tree;
	
	TreeMap() {
		tree = new SplayTree<KeyValuePair>();
	}
	
	void insert(String key, String value) {
		KeyValuePair pair = new KeyValuePair(key, value);
		tree.insert(pair);
	}
	
	String get(String key) {
		KeyValuePair dummy = new KeyValuePair(key, null);
		KeyValuePair existing = tree.get(dummy);
		if (existing != null) {
			return existing.getValue();
		}
		return "Key Not Found";
	}
	
	KeyValuePair delete(String key) {
		KeyValuePair dummy = new KeyValuePair(key, null);
		KeyValuePair existing = tree.get(dummy);
		if (existing != null) {
			tree.delete(existing);
			return existing;
		}
		return null;
		
	}
}

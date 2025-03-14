package monopoly;

public class CircularLinkedList<E> {
	
	/** nested private Node<E> class */
	private class Node<E> {
		E element;
		Node<E> next;
		
		private Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}
		
		private E getElement() { return element; }	
		private Node<E> getNext() { return next; }
		private void setNext(Node<E> next) { this.next = next; }
	}
	
	private Node<E> tail = null;
	public E currentNode = null;
	private int size = 0;
	
	/*** Default constructor */
	public <E> CircularLinkedList() { }
	
	/*** append() new element to list */
	public void append(E elem) {
		if (size == 0) {
			tail = new Node<>(elem, null);
			tail.setNext(tail);
		} else {
			Node<E> newest = new Node<>(elem, tail.getNext());
			tail.setNext(newest);
		}
		tail = tail.getNext();
		currentNode = tail.getNext().getElement();
		size++;
	}
	
	/** make a step() in monopoly, rotating the board one space*/
	public void step() {
        if (tail != null) {                  
            tail = tail.getNext();
        	currentNode = tail.getNext().getElement();
        }
	}
	
}

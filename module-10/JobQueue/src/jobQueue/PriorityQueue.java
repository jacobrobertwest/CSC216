package jobQueue;

public class PriorityQueue<T extends Comparable<? super T>> {
	private MaxHeap<T> heap;
	
    public PriorityQueue() {
        this.heap = new MaxHeap<>(10);
    }
    
    public void add(T element) {
        heap.add(element);
    }
    
    public T remove() {
        return heap.remove();
    }
    
    public T peek() {
        return heap.peek();
    }
    
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    public int size() {
        return heap.size();
    }
}

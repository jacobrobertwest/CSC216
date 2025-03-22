package jobQueue;

import java.util.NoSuchElementException;

public class MaxHeap<T extends Comparable<? super T>> {

    private T[] heap;
    private int size; 
    
    @SuppressWarnings("unchecked")
    public MaxHeap(int capacity) {
        this.heap = (T[]) new Comparable[capacity];
        this.size = 0;
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        return heap[0];
    }
    
    public void add(T element) {
        if (size >= heap.length) {
            throw new IllegalStateException("Heap is full.");
        }
        heap[size] = element;
        size++;
        bubbleUp(size - 1);
    }
    
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        T max = heap[0];
        heap[0] = heap[size - 1];
        size--;
        bubbleDown(0);
        return max;
    }
    
    private void bubbleUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap[index].compareTo(heap[parentIndex]) > 0) {
                swap(index, parentIndex);
                index = parentIndex;
            }
            else {
                break;
            }
        }
    }
    
    private void bubbleDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int largest = index;
            
            if (left < size && heap[left].compareTo(heap[largest]) > 0) {
                largest = left;
            }
            if (right < size && heap[right].compareTo(heap[largest]) > 0) {
                largest = right;
            }
            if (largest == index) {
                break;
            }
            swap(index, largest);
            index = largest;
        }
    }
    
    private void swap(int i, int j) {
        T tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }
}

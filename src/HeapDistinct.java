
/*
 * HeapDistinct
 * This is the Heap Data structure which takes only distinct elements as nodes and if an element occurs more than
 * once then stores its frequency like a pair of <value,frequency>
 */

import java.util.*;

public class HeapDistinct<T extends Comparable<T>> implements Iterable<T> {

    /* Heap for storing elements and occurences t place the occurrences */
    private T[] heap;
    private int[] occurences;
    private int max_heap_size;
    private int heap_size;

    @SuppressWarnings("unchecked")

    /*
     * Constructor for Heap distinct class taking max size and initializing heap and
     * occurences Constructor for Heap distinct class taking max size and
     * initializing heap and occurences
     */
    public HeapDistinct(int maximum_size) {

        this.heap_size = 0;
        this.max_heap_size = maximum_size;
        heap = (T[]) new Comparable[this.max_heap_size + 1];
        occurences = new int[this.max_heap_size + 1];

        for (int i = 0; i < this.max_heap_size + 1; i++) {
            occurences[i] = 0;
        }
    }

    /*
     * Swap 2 elements in the heap with swapping there occurences also
     */
    public void swap_elements(int idx1, int idx2) {
        T elem;
        int freq;

        elem = heap[idx1];
        freq = occurences[idx1];

        heap[idx1] = heap[idx2];
        occurences[idx1] = occurences[idx2];

        heap[idx2] = elem;
        occurences[idx2] = freq;
    }

    /*
     * Get MAXIMUM_CAPACITY OF HEAP
     */
    public int get_max_size() {
        return max_heap_size;
    }

    /*
     * Get number of Elements in Heap
     */
    public int get_curr_size() {
        return heap_size;
    }

    /*
     * Get ith Element in the Heap Array
     */
    public T get_ith_element(int idx) {

        if (idx < 1 || idx > heap_size) {
            throw new NoSuchElementException("Index Out of Bounds");
        }
        return heap[idx];
    }

    /*
     * Get ith Element's occurences in the Heap Array
     */
    public int get_ith_elements_occurence(int idx) {

        if (idx < 1 || idx > heap_size) {
            throw new NoSuchElementException("Index Out of Bounds");
        }
        return occurences[idx];
    }

    /*
     * Resize the heap with new maximum size as parameter wth old elements preserved
     */
    public void resize_heap(int new_max_size) {
        this.max_heap_size = new_max_size;
        heap = Arrays.copyOf(heap, this.max_heap_size + 1);
        occurences = Arrays.copyOf(occurences, this.max_heap_size + 1);
    }

    /* Heapify the heap to bottom carries the smaller element to bottom */
    public void heapify(int curr_idx) {
        if ((curr_idx <= heap_size) && (curr_idx > heap_size / 2))
            return;

        if ((heap[curr_idx].compareTo(heap[2 * curr_idx]) < 0)
                || (heap[curr_idx].compareTo(heap[2 * curr_idx + 1]) < 0)) {

            if (heap[2 * curr_idx].compareTo(heap[2 * curr_idx + 1]) <= 0) {
                swap_elements(curr_idx, (2 * curr_idx + 1));
                heapify(2 * curr_idx + 1);
            } else {
                swap_elements(curr_idx, 2 * curr_idx);
                heapify(2 * curr_idx);
            }
        }
    }

    /* Search an element in the heap array with modified index of it */
    public boolean search_element(T element, int[] idx) {
        boolean found = false;
        for (int i = 1; i <= heap_size; i++) {
            if (heap[i].compareTo(element) == 0) {
                idx[0] = i;
                return true;
            }
        }
        return found;
    }

    /*
     * Insert an element to the heap and if already found then increase its
     * occurences else add it to last and heapify to its correct place in the heap
     */
    public boolean insert_to_heap(T element) {

        for (int i = 1; i <= heap_size; i++) {
            if (heap[i].compareTo(element) == 0) {
                occurences[i]++;
                return true;
            }
        }
        /* Not present */

        if (heap_size == max_heap_size) {
            throw new NoSuchElementException("Heap is Full. Resize it to continue Further.");
        }

        if (heap_size == 0) {
            heap_size++;
            heap[heap_size] = element;
            occurences[heap_size] = 1;
            return true;
        }

        heap_size++;
        heap[heap_size] = element;
        occurences[heap_size] = 1;
        // adjust the heap;

        int newly_inserted_idx = heap_size;
        while ((newly_inserted_idx > 1) && (heap[newly_inserted_idx].compareTo(heap[(newly_inserted_idx / 2)])) > 0) {
            swap_elements(newly_inserted_idx, (newly_inserted_idx / 2));
            newly_inserted_idx = (newly_inserted_idx / 2);
        }
        return true;

    }

    /*
     * Get maximum element in the Heap
     */
    public T peek_max() {
        if (heap_size < 1) {
            throw new NoSuchElementException("Heap is Empty");
        }
        return heap[1];
    }

    /*
     * This removes the maximum element with all its occurrencesfrom the array it is
     * used in remove ith element in heap function to carry out elements and
     * reinsert to heap with 1 occurence lower of kth max element. Deletion is
     * independent of occurences here.
     */
    public T remove_max_element_without_occurence(int[] occ, int i) {

        if (heap_size == 1) {
            heap_size = 0;
            occ[i] = occurences[1];
            return heap[1];
        }

        T max_elem = heap[1];
        occ[i] = occurences[1];

        heap[1] = heap[heap_size];
        occurences[1] = occurences[heap_size];
        heap_size -= 1;

        /* Generate New Max */
        heapify(1);
        return max_elem;
    }

    @SuppressWarnings("unchecked")
    /*
     * Remove the ith largest element in the heap. if occurencs > 0 then removes
     * only 1 occurence else deletes the element from the heap. works in 0(nlogn)
     * time complexity
     */
    public T remove_ith_largest(int kth) {

        if (heap_size == 0) {
            throw new NoSuchElementException("Heap is Empty");
        }

        if (kth > heap_size || kth < 1) {
            throw new NoSuchElementException("Index Out of Bounds");
        }

        T[] rest_elements = (T[]) new Comparable[100000];
        int[] rest_elements_occurences = new int[this.max_heap_size + 1];
        int itr = 0;

        T kth_max = null;

        for (int i = 0; i < kth; i++) {
            T removed_element = this.remove_max_element_without_occurence(rest_elements_occurences, 0);
            if (i != kth - 1) {
                for (int j = 0; j < rest_elements_occurences[0]; j++) {
                    rest_elements[itr++] = removed_element;
                }
            } else {
                kth_max = removed_element;
                for (int j = 0; j < rest_elements_occurences[0] - 1; j++) {
                    rest_elements[itr++] = removed_element;
                }
            }
        }

        for (int i = 0; i < itr; i++) {
            this.insert_to_heap(rest_elements[i]);
        }

        return kth_max;
    }

    /*
     * Merges the second heap passed as a parameter to the first one by resizing the
     * first one.
     */
    public void merge_heaps(HeapDistinct<T> secondHeap) {

        this.resize_heap(this.max_heap_size + secondHeap.get_max_size() + 5);
        for (int i = 1; i <= secondHeap.get_curr_size(); i++) {
            int y = secondHeap.get_ith_elements_occurence(i);
            for (int j = 0; j < y; j++) {
                this.insert_to_heap(secondHeap.get_ith_element(i));
            }
        }
    }

    /*
     * Prints the heap elements with there frequency:
     */
    public void print() {
        System.out.println("Element:Frequency");
        for (int i = 1; i <= heap_size; i++) {
            System.out.print(heap[i] + ":" + occurences[i] + " ");
        }
        System.out.println();
    }

    /*
     * Replaces the current heap with the new heap parameterized to it by adjusting
     * sizes and elements
     */
    public void replace_heap(HeapDistinct<T> secondHeap) {
        this.max_heap_size = secondHeap.max_heap_size;
        this.heap_size = secondHeap.heap_size;
        heap = Arrays.copyOf(secondHeap.heap, this.max_heap_size + 1);
        occurences = Arrays.copyOf(secondHeap.occurences, this.max_heap_size + 1);
        return;
    }

    /*
     * Returns the array of heap for public users
     */
    public T[] get_heap() {
        return heap;
    }

    public class HeapIterator implements Iterator<T> {
        int idx = 1;

        public boolean hasNext() {
            return idx < heap_size;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return heap[idx++];
        }

        public T setValue(T item) {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T result = heap[heap_size];
            heap[heap_size] = item;
            occurences[heap_size] = 1;
            int newly_inserted_idx = heap_size;
            while ((newly_inserted_idx > 1) && heap[newly_inserted_idx].compareTo(heap[(newly_inserted_idx / 2)]) > 0) {
                swap_elements(newly_inserted_idx, (newly_inserted_idx / 2));
                newly_inserted_idx = (newly_inserted_idx / 2);
            }
            return result;
        }
    }

    @Override
    public HeapIterator iterator() {
        return new HeapIterator();
    }

}
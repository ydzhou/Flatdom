/*
 * Author: Y.Zhou
 * Class: HashedPriorityQueue
 * Description: A min-heap-based priority queue with a hash table.
 * Methods:
 *     peek(): O(1)
 *     poll(): O(logn)
 *     add(E e): O(logn)
 *     update(E e): O(logn)
 *     isEmpty(): O(1)
 */

import java.util.*;

public class HashedPriorityQueue<E> {

    // heap is an array, where root starts at index 1.
    private List<E> heap;
    private HashMap<E, Integer> map;
    private Comparator<E> com;
    public int size;

    public HashedPriorityQueue(Comparator<E> c) {
        heap = new ArrayList<E>();
        com = c;
        size = 0;
        map = new HashMap<E, Integer>();
    }

    // Retrieve the top element from  the queue.
    public E peek() {
        if (size == 0) return null;
        return heap.get(1);
    }

    // Retrieve and remove the top element from the queue.
    public E poll() {
        if (size == 0) return null;
        E tmp = heap.get(1);
        heap.set(1, heap.get(size));
        heap.set(size, tmp);
        size--;
        heapify(1);
        /*map.put(heap.get(1), i);*/
        map.remove(heap.get(size+1));
        return heap.get(size+1);
    }

    // Add an element into the queue.
    public boolean add(E e) {
        // Add a dummy node.
        if (size == 0) {
            heap.add(e);
        }
        size++;
        heap.add(e);
        increaseKey(size);
        /*map.put(e, i);*/
        return true;
    }

    // Update the priority of an element in the queue.
    public boolean update(E e) {
        if (!map.containsKey(e)) return false;
        int i = map.get(e);
        increaseKey(i);
        heapify(i);
        return true;
    }

    // Maintain property of the heap.
    private void heapify(int i) {
        while (i <= size/2) {
            int k = i;
            if (com.compare(heap.get(i), heap.get(2*i)) > 0) {
                k = 2*i;
            }
            if ((2*i+1) <= size && com.compare(heap.get(k), heap.get(2*i+1)) > 0) {
                k = 2*i + 1;
            }
            if (i == k) {
                break;
            }
            E tmp = heap.get(i);
            heap.set(i, heap.get(k));
            heap.set(k, tmp);
            map.put(heap.get(i), i);
            map.put(heap.get(k), k);
            i = k;
        }
        map.put(heap.get(i), i);
    }

    // Reset the position of an element after increasing its priority.
    private void increaseKey(int i) {
        while (i > 1) {
            if (com.compare(heap.get(i/2), heap.get(i)) > 0) {
                /*swap(heap.get(i/2), heap.get(i));*/
                E tmp = heap.get(i);
                heap.set(i, heap.get(i/2));
                heap.set(i/2, tmp);
                map.put(heap.get(i/2), i/2);
                map.put(heap.get(i), i);
            } else {
                break;
            }
            i /= 2;
        }
        map.put(heap.get(i), i);
    }

    private void swap(E e1, E e2) {
        E tmp = e1;
        e1 = e2;
        e2 = tmp;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public List<E> getHeap() {
        return heap;
    }
}

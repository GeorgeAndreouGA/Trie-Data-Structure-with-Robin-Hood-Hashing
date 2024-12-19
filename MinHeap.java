package ID1069789.ID1070730;
/**
 * A custom implementation of a MinHeap (priority queue) for managing words based on their importance.
 * The heap stores words and ensures that the word with the smallest importance is at the top.
 * It supports standard heap operations such as adding elements, removing the top element, checking if an element is in the heap, and more.
 */
public class MinHeap {
    private String[] heap; 
    private int size; 
    private int capacity; 

    /**
     * Constructs a MinHeap with a specified capacity.
     *
     * @param capacity the maximum number of elements the heap can hold
     */
    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new String[capacity];
        this.size = 0;
    }

    /**
     * Adds a word to the heap and ensures the heap property is maintained.
     * If the heap is full, it prints a message and does not add the word.
     *
     * @param word the word to be added to the heap
     * @param trie the Trie object used to retrieve the importance of words
     */
    public void offer(String word,Trie trie) {
        if (size == capacity) {
            System.out.println("Heap is full, cannot insert more elements.");
            return; 
        }
     ;
        heap[size] = word;
        size++;
        heapifyUp(size - 1,trie);
    }

    /**
     * Removes and returns the word with the smallest importance (top of the heap).
     * If the heap is empty, it returns null.
     *
     * @param trie the Trie object used to retrieve the importance of words
     * @return the word with the smallest importance, or null if the heap is empty
     */
    public String poll(Trie trie) {
        if (size == 0) {
            return null; 
        }

        String min = heap[0]; 
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0,trie); 
        return min;
    }

    /**
     * Checks if the heap contains the specified word.
     *
     * @param word the word to check for in the heap
     * @return {@code true} if the word is in the heap, {@code false} otherwise
     */
    public boolean contains(String word) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(word)) {
                return true;
            }
        }
        return false; 
    }

    /**
     * Returns the number of elements currently in the heap.
     *
     * @return the size of the heap
     */
    public int size() {
        return size;
    }

    /**
     * Ensures the heap property is maintained by moving the element at the given index up the heap.
     *
     * @param index the index of the element to move up
     * @param trie  the Trie object used to retrieve the importance of words
     */

    private void heapifyUp(int index,Trie trie) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;

            if (compare(heap[index], heap[parentIndex],trie) >= 0) {
                break; 
            }

        
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    /**
     * Ensures the heap property is maintained by moving the element at the given index down the heap.
     *
     * @param index the index of the element to move down
     * @param trie  the Trie object used to retrieve the importance of words
     */
    private void heapifyDown(int index,Trie trie) {
        int leftChild, rightChild, smallest;
        while (index < size) {
            leftChild = 2 * index + 1;
            rightChild = 2 * index + 2;
            smallest = index;

            if (leftChild < size && compare(heap[leftChild], heap[smallest],trie) < 0) {
                smallest = leftChild;
            }

            if (rightChild < size && compare(heap[rightChild], heap[smallest],trie) < 0) {
                smallest = rightChild;
            }

            if (smallest == index) {
                break; 
            }

         
            swap(index, smallest);
            index = smallest; 
        }
    }

    /**
     * Compares two words based on their importance as retrieved from the Trie.
     *
     * @param a the first word to compare
     * @param b the second word to compare
     * @param trie the Trie object used to retrieve the importance of words
     * @return a negative integer if the first word is less important, a positive integer if more important, 
     *         or zero if they have the same importance
     */
    private int compare(String a, String b,Trie trie) {
        TrieNode nodeA = trie.search(a);
        TrieNode nodeB = trie.search(b);

        int importanceA = (nodeA != null) ? nodeA.importance : 0;
        int importanceB = (nodeB != null) ? nodeB.importance : 0;

        return Integer.compare(importanceA, importanceB);
    }

    /**
     * Swaps the elements at the two given indices in the heap.
     *
     * @param index1 the first index
     * @param index2 the second index
     */
    private void swap(int index1, int index2) {
        String temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }
    /**
     * Checks if the heap is empty.
     *
     * @return {@code true} if the heap is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
    	return size==0;
    }
}

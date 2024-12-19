package ID1069789.ID1070730;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/* * Represents a Trie data structure for efficient word storage and retrieval.
 * The Trie supports operations for inserting words, searching for words, 
 * processing importance scores, and suggesting similar words based on input criteria.
 * The Trie utilizes Robin Hood Hashing for efficient child node management.
 * It also provides mechanisms to load dictionaries and process importance from files.
 */

public class Trie {
	  TrieNode root;

	  /**
	     * Constructs an empty Trie.
	     */
	    public Trie() {
	        root = new TrieNode();
	    }
	    
	   
	  
	    /**
	     * Suggests a list of words similar to the given input word based on three criteria:
	     * - Words with the same prefix.
	     * - Words with at most two character differences.
	     * - Words with length differences (up to +2 or -1).
	     *
	     * @param inputWord the word to find suggestions for
	     * @param k         the number of suggestions to provide
	     */
	    public void suggestWords(String inputWord, int k) {
	    MinHeap minHeap = new MinHeap(k+1);

	        TrieNode node = search(inputWord);

	       
	        if (node != null) {
	          findWordsWithPrefix(node, inputWord,false, minHeap, k);
	       }

	      
	      findWordsWithCharacterDifferencesAtLeast2(root, inputWord, minHeap, k);

	        
	        findWordsWithDifferentLengths(root, "", inputWord, minHeap, k);
	      
	        String[] suggestions = new String[k];
	        int i=k-1;
	        while (!minHeap.isEmpty()) {
	        	suggestions[i]=minHeap.poll(this);
	        	i--;
	        }
	       boolean didFind=false;
	      for(int s=0;s<suggestions.length;s++) 
	    	  if(suggestions[s]!=null) { 
	    		  didFind=true;
	  
	    	  System.out.println(suggestions[s]);
	    	  }
	      
	      if(!didFind)
	    	  System.out.println("Did not find any words!!!");
	      
	    }

	    
	    
	    /**
	     * Finds words with the same prefix as the given input word.
	     *
	     * @param node       the current TrieNode to explore
	     * @param prefix     the prefix to match
	     * @param userWord   whether to include the user's exact word
	     * @param minHeap    the MinHeap to store suggestions
	     * @param k          the number of suggestions to provide
	     */
	    private void findWordsWithPrefix(TrieNode node, String prefix,boolean userWord, MinHeap minHeap, int k) {
	       
	       if(userWord && node.wordLength > 0) 
	            addToHeap(minHeap, prefix, k); 
	        

	       
	        for (int i = 0; i < node.children.capacity; i++) {
	            Element element = node.children.table[i];
	            if (element != null) {
	                findWordsWithPrefix(element.trieNode, prefix + element.key,true, minHeap, k);
	            }
	        }
	    }

	    
	    /**
	     * Finds words with at most two character differences from the input word.
	     *
	     * @param node       the current TrieNode to explore
	     * @param inputWord  the input word to compare
	     * @param minHeap    the MinHeap to store suggestions
	     * @param k          the number of suggestions to provide
	     */
	    private void findWordsWithCharacterDifferencesAtLeast2(TrieNode node, String inputWord,MinHeap minHeap, int k) {
	        findWordsHelper(node, "", inputWord, minHeap, k);
	    }

	    /**
	     * Recursively explores the Trie to find words with at most two character differences.
	     *
	     * @param node       the current TrieNode
	     * @param prefix     the word prefix formed so far
	     * @param inputWord  the input word to compare
	     * @param minHeap    the MinHeap to store suggestions
	     * @param k          the number of suggestions to provide
	     */
	    private void findWordsHelper(TrieNode node, String prefix, String inputWord, MinHeap minHeap, int k) {
	       
	        if (node.wordLength > 0) {
	        
	            if (prefix.length()  == inputWord.length() && countCharacterDifferences(prefix, inputWord) <= 2) {
	                addToHeap(minHeap, prefix, k); 
	            }
	        }

	        
	        for (int i = 0; i < node.children.table.length; i++) {
	            Element element = node.children.table[i];
	            if (element != null) {
	               
	                findWordsHelper(element.trieNode, prefix + element.key, inputWord, minHeap, k);
	            }
	        }
	    }

	    /**
	     * Counts the number of differing characters between two words.
	     *
	     * @param word1 the first word
	     * @param word2 the second word
	     * @return the number of character differences
	     */
	    private int countCharacterDifferences(String word1, String word2) {
	        int differences = 0;

	        for (int i = 0; i < word1.length(); i++) {
	            if (word1.charAt(i) != word2.charAt(i)) {
	                differences++;
	            }
	        }

	        return differences;
	    }

	    /**
	     * Finds words in the Trie that differ in length from the input word by:
	     * - Up to two additional characters (length difference of +1 or +2), where the input word is a subsequence of the candidate.
	     * - Exactly one fewer character (length difference of -1), where the candidate can be formed by removing a single character from the input word.
	     *
	     * @param node       The current TrieNode being explored.
	     * @param prefix     The prefix formed so far during the traversal of the Trie.
	     * @param inputWord  The input word for which similar words are being searched.
	     * @param minHeap    The MinHeap used to store and manage the top-k suggestions.
	     * @param k          The maximum number of suggestions to store in the MinHeap.
	     */
	    private void findWordsWithDifferentLengths(TrieNode node, String prefix, String inputWord, MinHeap minHeap, int k) {
	        
	    	if (node.wordLength > 0) {
	            int lengthDifference = prefix.length() - inputWord.length();
	            
	            
	            if ((lengthDifference == 1 || lengthDifference == 2) && isSubsequence(inputWord, prefix)) {
	              
	            	addToHeap(minHeap, prefix, k);
	            }

	            
	            if (lengthDifference == -1 && isMissingOneCharacter(inputWord, prefix)) {
	                addToHeap(minHeap, prefix, k);
	            }
	        }

	        for (int i = 0; i < node.children.table.length; i++) {
	            Element element = node.children.table[i];
	            if (element != null) {
	                findWordsWithDifferentLengths(element.trieNode, prefix + element.key, inputWord, minHeap, k);
	            }
	        }
	    }

	    /**
	     * Checks if the input string is a subsequence of the candidate string.
	     * A string is considered a subsequence of another if all characters of the input string
	     * appear in the candidate string in the same order, but not necessarily consecutively.
	     * For example:
	     * - "abc" is a subsequence of "aebdc".
	     * - "abc" is not a subsequence of "acb".
	     *
	     * @param input     The string to check as a subsequence.
	     * @param candidate The string in which the subsequence is being checked.
	     * @return {@code true} if the input string is a subsequence of the candidate string;
	     *         {@code false} otherwise.
	     */
	    private boolean isSubsequence(String input, String candidate) {
	        int i = 0, j = 0;
	        while (i < input.length() && j < candidate.length()) {
	            if (input.charAt(i) == candidate.charAt(j)) {
	                i++;
	            }
	            j++;
	        }
	        return i == input.length();
	    }

	    
	    
	    
	    /**
	     * Checks if the given prefix can be formed by removing exactly one character from the input word.
	     * This method checks if the prefix is of length one less than the input word and if the input word
	     * can be transformed into the prefix by removing exactly one character (and keeping the order of the remaining characters).
	     * For example:
	     * - "plan" and "pan" are valid because removing 'l' from "plan" gives "pan".
	     * - "plan" and "pla" are valid because removing 'n' from "plan" gives "pla".
	     * - "plan" and "pln" is not valid because multiple characters are missing.
	     *
	     * @param inputWord the original word from which a character is removed
	     * @param prefix    the prefix that may have one character removed from the input word
	     * @return {@code true} if the prefix can be formed by removing exactly one character from the input word;
	     *         {@code false} otherwise.
	     */
	    private boolean isMissingOneCharacter(String inputWord, String prefix) {
	        if (prefix.length() != inputWord.length() - 1) {
	            return false; 
	        }

	        int i = 0, j = 0;
	        boolean skippedOne = false;

	        while (i < inputWord.length() && j < prefix.length()) {
	            if (inputWord.charAt(i) != prefix.charAt(j)) {
	                if (skippedOne) {
	                    return false; 
	                }
	                skippedOne = true; 
	                i++;
	            } else {
	                i++;
	                j++;
	            }
	        }

	        return true;
	    }

	    /**
	     * Adds a word to the MinHeap if it is not already present and maintains the heap size at or below the given limit (k).
	     * If the word is not already in the heap, it is added along with its associated importance score (from the TrieNode).
	     * The heap ensures that only the top-k words with the highest importance are retained.
	     * If the heap exceeds the size of `k`, the word with the lowest importance is removed.
	     *
	     * @param minHeap the MinHeap to store the top-k words based on their importance
	     * @param word    the word to be added to the heap
	     * @param k       the maximum number of words to keep in the heap
	     */
	    private void addToHeap(MinHeap minHeap, String word, int k) {
	       
	        if (minHeap.contains(word)) { 
	            return; 
	        }

	        TrieNode node = search(word);
	        if (node != null) {
	            minHeap.offer(word,this);
	               
	            if (minHeap.size() > k) {
	                 minHeap.poll(this);
	               
	            }
	        } 
	    }


	    /**
	     * Loads a dictionary from the specified file into the Trie.
	     *
	     * @param dictionaryFile the file containing dictionary words
	     */
	    public  void loadDictionary(String dictionaryFile){
	    	 
	    	File file = new File(dictionaryFile);
	    	  
	    	  try (Scanner scanner = new Scanner(file)) {
	            
	    		  while (scanner.hasNext()) {
	                  String dirtyWord = scanner.next();
	                  String cleanWord="";
	                  
	                  
	                  for(int i=0;i<dirtyWord.length();i++) {
	                	  if((Character.isLetter(dirtyWord.charAt(i))))
	                           cleanWord+=dirtyWord.charAt(i); }
	                 
	                insert(cleanWord); 
	    		  }
	        }
	    	  catch (FileNotFoundException e) {
	              System.out.println("File not found: " + e.getMessage());
	          }		
	        System.out.println("Dictionary loaded into Trie.\n");
	    }
	    
	    
	    /**
	     * Processes a file to count word importance.
	     * Each occurrence of a dictionary word in the file increases its importance.
	     *
	     * @param importanceFile the file to process
	     */
	    public void processImportanceFile(String importanceFile) {
	        File file = new File(importanceFile);

	        try (Scanner scanner = new Scanner(file)) {
	            while (scanner.hasNext()) {
	                String dirtyWord = scanner.next();

	                
	                String cleanWord = clean(dirtyWord);
                   
	                if (cleanWord!="") { 
	                
	                    TrieNode node = search(cleanWord.toLowerCase());
	                    if (node != null) {
	                        node.importance++; 
	                    }
	                }
	            }
	        } catch (FileNotFoundException e) {
	            System.out.println("File not found: " + e.getMessage());
	        }
	        System.out.println("Importance file processed.\n");
	    }

	    /**
	     * Cleans a given word by removing any non-letter characters from the beginning and end of the word,
	     * and ensuring that only letters remain within the word (i.e., it doesn't contain invalid characters in the middle).
	     * This method trims off any non-alphabetic characters from both ends of the word and ensures that the remaining word 
	     * consists only of alphabetic characters. If a non-alphabetic character is found in the middle of the word (before the end),
	     * the word is considered invalid and returned as an empty string.
	     * 
	     * @param word the word to clean
	     * @return the cleaned word with only alphabetic characters, or an empty string if the word is invalid
	     */
	    private String clean(String word) {
	        int n = word.length();
	        int end = n - 1;

	        
	        while (end >= 0 && !Character.isLetter(word.charAt(end))) {
	            end--;
	        }

	      
	        for (int i = 0; i <= end; i++) {
	            if (!Character.isLetter(word.charAt(i)) && i != end) {
	                return ""; 
	            }
	        }

	       
	        return word.substring(0, end + 1);
	    }

	  
	      
	    /**
	     * Inserts a word into the Trie.
	     *
	     * @param wordUnmodified the word to insert
	     */
	    public void insert(String wordUnmodified) {
	    	
	        TrieNode node = root;
	        String word =  wordUnmodified.toLowerCase();
	        
	        for (int i = 0; i < word.length(); i++) {
	        	
	            char key = word.charAt(i);
	            TrieNode child = node.children.search(key);
	            if (child == null) {
	                child = node.children.insert(key);
	            }
	            node = child;
	        }
	        
	        node.wordLength = word.length(); 
	    }

	    /**
	     * Searches for a word in the Trie.
	     *
	     * @param wordUnmodified the word to search
	     * @return the TrieNode representing the word, or null if not found
	     */
	    public TrieNode search(String wordUnmodified) {
	        TrieNode node = root;
	        String word =  wordUnmodified.toLowerCase();
	        for (int i = 0; i < word.length(); i++) {
	            char key = word.charAt(i);
	            TrieNode child = node.children.search(key);
	            if (child == null) {
	                return null; 
	            }
	            node = child;
	        }
	        return node;
	    }
	    
	 
	    private static long getMemoryUsage() {
	        Runtime runtime = Runtime.getRuntime();
	        runtime.gc(); // Request garbage collection
	        return runtime.totalMemory() - runtime.freeMemory();
	    }
	    public static void main(String[] args) {
	    	
	   	 long initialMemory = getMemoryUsage();
    	 
	        Scanner read = new Scanner(System.in);
	    	System.out.println("Give a word and the number of suggestions:");
	        String word = read.next();
	        int k = read.nextInt();
	        
	        Trie trie = new Trie();
	        
	        trie.loadDictionary(args[0]);
	        
	        trie.processImportanceFile(args[1]);
	        
	        trie.suggestWords(word,k);
	        
	        read.close();
	      
	        long finalMemory = getMemoryUsage();
	        long memoryUsed = finalMemory - initialMemory;
	        System.out.println("Initial Memory Usage: " + (initialMemory / 1024) + " KB");
	        System.out.println("Final Memory Usage: " + (finalMemory / 1024) + " KB");
	        System.out.println("Memory Used: " + (memoryUsed / 1024) + " KB");


	        
	    }
}

	package ID1069789.ID1070730;
	/**
	 * Represents a node in the Trie data structure.
	 * Each node stores information about its children, word length, and importance.
	 * It uses Robin Hood Hashing to efficiently store child nodes.
	 */
	class TrieNode {
	    RobinHoodHashing children;
	    int wordLength;            
	    int importance;
	    
	    /**
	     * Constructs a TrieNode with an empty children hash table, word length set to 0,
	     * and importance set to 0.
	     */ 
	    public TrieNode() {
	        this.children = new RobinHoodHashing();
	        this.wordLength = 0;
	        this.importance = 0;
	    }
	}

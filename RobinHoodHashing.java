package ID1069789.ID1070730;

/**
 * A custom implementation of Robin Hood Hashing for efficient key-value storage.
 * This class provides methods to insert and search for elements, as well as handle rehashing when the table becomes full.
 * The hashing technique ensures that keys with longer probe sequences (collisions) are favored over those with shorter sequences,
 * improving cache locality and performance in scenarios with a high number of collisions.
 */
public class RobinHoodHashing {
     Element[] table;  
     int capacity;      
     int size;          
     int maxProbeLength; 

     /**
      * Constructs a Robin Hood Hashing instance with an initial capacity of 5.
      */
    public RobinHoodHashing() {
        this.capacity = 5;
        this.table = new Element[capacity];
        this.size = 0;
        this.maxProbeLength = 0;
    }

    /**
     * Computes the hash value for a given character key.
     *
     * @param key the character key to hash
     * @return the computed hash value for the key
     */
    private int hash(char key) {
        return (int)key % capacity;
    }

    /**
     * Inserts a new character key into the hash table.
     * If the key already exists, its probe length is reset to zero, and its associated TrieNode is returned.
     * If the key does not exist, a new element is inserted and rehashing occurs if the load factor exceeds 0.9.
     *
     * @param key the character key to insert
     * @return the TrieNode associated with the inserted key
     */
    public TrieNode insert(char key) {
        int index = hash(key);
        int probeLength = 0;

       
        Element existingElement = findElement(key, index);
        
        if (existingElement != null) {
            existingElement.probeLength = probeLength;  
            return existingElement.trieNode;
        }

        TrieNode newTrieNode = new TrieNode();
        Element newElement = new Element(key, probeLength, newTrieNode);

        while (true) {
            if (table[index] == null) {
                table[index] = newElement;
                size++;
                maxProbeLength = Math.max(maxProbeLength, newElement.probeLength);
                break;
            }

           
            if (newElement.probeLength > table[index].probeLength) {
                Element temp = table[index];
                table[index] = newElement;
                newElement = temp; 
            }

         
            newElement.probeLength++;
            index = (index + 1) % capacity;
        }
        if ((double) size / capacity > 0.9) {
            rehash();
        }

        return newTrieNode;
    }
    /**
     * Finds an element in the table based on its key and starting index.
     *
     * @param key        the key to search for
     * @param startIndex the index to start searching from
     * @return the element if found, or null if not found
     */
    private Element findElement(char key, int startIndex) {
        int index = startIndex;
        int probeLength = 0;

        while (table[index] != null) {
            if (table[index].key == key) {
                return table[index];  
            }

          
            index = (index + 1) % capacity;
            probeLength++;
        }
        
        return null;  
    }


    /**
     * Searches for an element with the given key in the hash table.
     *
     * @param key the character key to search for
     * @return the TrieNode associated with the key, or null if not found
     */
    public  TrieNode search(char key) {
        int index = hash(key);
        int probeLength = 0;

        while (table[index] != null && probeLength <= maxProbeLength) {
            if (table[index].key == key) {
                return table[index].trieNode;
            }
            index = (index + 1) % capacity;
            probeLength++;
        }

        return null; 
    }

    /**
     * Inserts elements from an old table into the new table during rehashing.
     *
     * @param oldTable the old hash table
     */
   private void modifiedInsertForRehash(Element[] oldTable) {
	   for (int i = 0; i < oldTable.length; i++) {
	       
	        if (oldTable[i] != null) {    
	          
	            int index = hash(oldTable[i].key);
	            int probeLength = 0;

	            Element newElement = new Element(oldTable[i].key, probeLength, oldTable[i].trieNode);

	            while (true) {
	                if (table[index] == null) {
	                    table[index] = newElement;
	                    size++;
	                    maxProbeLength = Math.max(maxProbeLength, probeLength);
	                    break;
	                }

	                if (probeLength > table[index].probeLength) {
	                    Element temp = table[index];
	                    table[index] = newElement;
	                    newElement = temp;
	                }

	                probeLength++;
	                index = (index + 1) % capacity;
	                newElement.probeLength = probeLength;
	            }
	        }
	    }
   }
   
   
   /**
    * Performs rehashing by expanding the hash table and re-inserting all elements from the old table into the new table.
    * The capacity is increased by selecting the next prime number greater than the current capacity.
    */
    private void rehash() {
       
    	   
    	    int[] primeCapacities = {11, 19, 29};
    	    int newCapacity = capacity;

    	    for (int i = 0; i < primeCapacities.length; i++) {
    	        if ( primeCapacities[i] > capacity) {
    	            newCapacity =  primeCapacities[i];
    	            break;
    	        }
    	    }

    	  
    	    Element[] oldTable = table;
    	    table = new Element[newCapacity];
    	    capacity = newCapacity;
    	    size = 0;
    	    maxProbeLength = 0;

    	   
    	    modifiedInsertForRehash(oldTable);

    	}


   //troubleshooting
   /* public void display() {
        System.out.println("Hash Table:");
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                System.out.println("Index " + i + ": Key = " + table[i].key + ", ProbeLength = " + table[i].probeLength);
            } else {
                System.out.println("Index " + i + ": Empty");
            }
        }
    } */


}

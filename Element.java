package ID1069789.ID1070730;

/**
 * Represents an element in the hash table used by the Robin Hood Hashing technique.
 * Each element contains a character key, its associated probe length (for handling collisions),
 * and the corresponding TrieNode that the key maps to.
 */
public class Element {
    char key;         
    int probeLength;   
    TrieNode trieNode;


    /**
     * Constructs an Element with the specified key, probe length, and associated TrieNode.
     *
     * @param key the character key of the element
     * @param probeLength the number of probes (collisions) encountered during insertion
     * @param trieNode the TrieNode that the key maps to
     */
    public Element(char key, int probeLength, TrieNode trieNode) {
        this.key = key;
        this.probeLength = probeLength;
        this.trieNode = trieNode;
    }
}
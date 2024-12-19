Trie Data Structure with Robin Hood Hashing

Overview

This repository showcases an advanced implementation of a Trie data structure augmented with Robin Hood Hashing for efficient child node management. The solution is designed for high-performance word storage, retrieval, and priority management, making it ideal for use cases like autocomplete systems, dictionary applications, or text processing utilities.

Features

Trie Class

Represents the core Trie data structure for efficient word storage and retrieval.

Supports:

Word insertion.

Word search.

Suggestions based on criteria such as prefix matching, character differences, and length variations.

Dictionary loading and word importance processing from files.

TrieNode Class

Represents a node in the Trie data structure.

Stores child nodes using Robin Hood Hashing.

Tracks:

Word length.

Importance of words.

Robin Hood Hashing

Implements a custom hash table to manage child nodes efficiently.

Features:

Collision resolution using probe lengths.

Rehashing support for dynamic table resizing.

Improved cache locality and reduced clustering.

MinHeap Class

Priority queue implementation for managing words by importance.

Supports operations such as:

Adding words.

Removing the word with the highest priority.

Checking for word existence.

Integrated with the Trie to retrieve word importance dynamically.

Element Class

Represents individual elements used in the hash table for Robin Hood Hashing.

Each element contains:

A character key.

Probe length (to handle collisions).

A reference to the corresponding TrieNode.

Files

Trie.java: Defines the Trie class and its functionalities, including word insertion, suggestion algorithms, and file processing.

TrieNode.java: Defines the TrieNode class and its functionalities.

RobinHoodHashing.java: Implements the Robin Hood Hashing technique.

MinHeap.java: Contains the MinHeap implementation for priority management.

Element.java: Represents the hash table elements used in Robin Hood Hashing.

How to Use

Clone this repository to your local machine:

git clone https://github.com/yourusername/trie-robinhood.git

Navigate to the project directory:

cd trie-robinhood

Compile the Java files:

javac ID1069789/ID1070730/*.java

Run the Trie main method with the required arguments:

java ID1069789.ID1070730.Trie <dictionary_file> <importance_file>

Example

Here is a brief example to demonstrate usage:

import ID1069789.ID1070730.Trie;

public class Main {
    public static void main(String[] args) {
        Trie trie = new Trie();

        // Load a dictionary and process importance file
        trie.loadDictionary("dictionary.txt");
        trie.processImportanceFile("importance.txt");

        // Suggest words
        trie.suggestWords("example", 5);
    }
}

Contributing

Contributions are welcome! Feel free to fork this repository and submit pull requests for any improvements or bug fixes.

License

This project is licensed under the MIT License. See the LICENSE file for details.




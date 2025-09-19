import java.io.*;
import java.lang.Comparable;
/*∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗*
@file: Node.java
@description: This is a simple Binary Node class. It contains getter and setter methods
in addition to two constructors. It also has pointers to left and right children.
There are also methods to tell whether the node is a leaf or has two children.
It implements comparable, and will hold any generic object.
@author: Alli Swyt
@date: September 18, 2025
∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗*/



public class Node <T extends Comparable<T>> implements Comparable<Node<T>> {
    //set variables
    private T data;
    private Node<T> left;
    private Node<T> right;

// Implement the constructor
    //constructor with only data assigned
    public Node(T data) {
        this.data = data;
        right = null;
        left = null;
    }

    //constructor with left and right nodes assigned
    public Node(T data, Node<T> left, Node<T> right) {
        this.data = data;
        this.right = right;
        this.left = left;
    }

// Implement the setElement method
    public void setData(T data) {
        this.data = data;
    }

// Implement the setLeft method
    public void setLeft(Node<T> left) {
    this.left = left;
}


// Implement the setRight method
    public void setRight(Node<T> right) {
    this.right = right;
}

// Implement the getLeft method
    public Node<T> getLeft() { return left; }

// Implement the getRight method
    public Node<T> getRight() { return right; }

// Implement the getElement method
    public T getData() { return data; }

// Implement the isLeaf method
//method to return whether node is a leaf
    public boolean isLeaf() {
        return right == null && left == null;
    }

    //method to return whether a node has two children
    public boolean hasTwoChildren() {
        return (right != null && left != null);
    }

    //FIXME -- must update the compareTo method for the Anime object rankings
    //override compareTo method to allow it to compare two Nodes with data types T assuming a comparable interface capable of comparing the data types
    @Override
    public int compareTo(Node<T> other) {
        return this.data.compareTo(other.getData());
    }
}

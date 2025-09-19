import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗*
@file: BST.java
@description: This class implements a binary search tree holding generic objects.
It has a root, setter and getter methods for the root, and two constructors.
It has methods insert, search, remove, print, and isEmpty.
@date: September 18, 2025
∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗*/

public class BST<T extends Comparable<T>> {
    //set necessary variables
    private Node<T> root;

    //getter and setter methods for the root
    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public Node<T> getRoot() {
        return root;
    }
// Implement the constructor

    //constructor with input of a root node
    public void BST(Node<T> root) {
        this.root = root;
    }

    //constructor with no input creates a new BST with no nodes
    public void BST() {
        root = null;
    }


// Implement the clear method
    public void clear() {
        root = null;
    }


    //FIXME -- add comments, also is this the correct way of saying size ?
// Implement the size method
    public int size() {
        int size = 0;

        BSTIterator<T> it = new BSTIterator<T>(root); //creates a new Iterator with curr value set to the root
        for (size = 0; it.hasNext(); size++) {
            it.next();
        }

        return size;
    }


// Implement the insert method

    //This method inserts a node of value val into the BST, and works recursively
    public void insert(T val, Node<T> currNode) {

        Node<T> newNode = new Node<T>(val);
        if (root == null) {
            root = newNode;
        } //If the tree is empty, the node will be placed as the root of the tree

        else {
            if (newNode.compareTo(currNode) == 0) {
                System.out.println("This value is already in the tree, thus it will not be added.");
            } //If the value of the new node is already in the tree, it will not be added

            else if (newNode.compareTo(currNode) < 0) {
                if (currNode.getLeft() == null) {
                    currNode.setLeft(newNode);
                }//if the value is less, and the left child is null, the new node will be added in that place
                else {
                    insert(val, currNode.getLeft());
                } //If the left child is not null, the method will iterate to the left recursively
            } else if (newNode.compareTo(currNode) > 0) {
                if (currNode.getRight() == null) {
                    currNode.setRight(newNode);
                } //if the value is greater, and the right child is null, the new node will be added in that place
                else {
                    insert(val, currNode.getRight());
                } //if the right child is not null, the method will be called recursively on the right child
            } else {
                System.out.println("There was an error. Exiting method.");
            } //This else statement shouldn't run -- it's a bug check
        }
    }


// Implement the remove method

    //This method removes a node with value val from the BST, and returns that node
    //If the value does not exist in the tree, it will return null
    public Node<T> remove(T val) {
        Node<T> nodeToRemove = search(val, root);
        Node<T> curr = root;
        if (nodeToRemove == null) {
            return null;
        }

        //runs until curr is equal to the parent of the node to be removed -- and only runs if the node to remove is not the root
        if (curr.compareTo(nodeToRemove) != 0) {
            Node<T> child = root;
            while (child.compareTo(nodeToRemove) != 0) {
                curr = child; //updates curr so that it is equal to child but will be parent of child after next update
                if (nodeToRemove.compareTo(curr) < 0) {
                    child = child.getLeft();
                } else {
                    child = child.getRight();
                }
            }
        }

        //case 1: that the node to remove is a leaf
        if (nodeToRemove.isLeaf()) {
            //following removes the node from the tree
            if (root.compareTo(nodeToRemove) == 0) {
                root = null;
            } //removes root if root is the node to be removed
            else if (curr.getLeft() != null && curr.getLeft().compareTo(nodeToRemove) == 0) {
                curr.setLeft(null);
            } else {
                curr.setRight(null);
            } //curr SHOULD be the parent, so this should get rid of the node we want to remove
        }

        //case 2: that the node to remove only has one child
        else if (!nodeToRemove.hasTwoChildren() && !nodeToRemove.isLeaf()) {

            //case that nodeToRemove is the root: reassign the root to the one child
            if (root.compareTo(nodeToRemove) == 0) {
                if (nodeToRemove.getLeft() == null) {
                    root = nodeToRemove.getRight();
                    nodeToRemove.setRight(null); //changes nodeToRemove to have no children
                } else {
                    root = nodeToRemove.getLeft();
                    nodeToRemove.setLeft(null);
                }
            }

            //case nodeToRemove is not the root, or a leaf
            else if (curr.getLeft() != null && curr.getLeft().compareTo(nodeToRemove) == 0) {
                if (nodeToRemove.getLeft() == null) {
                    curr.setLeft(nodeToRemove.getRight());
                    nodeToRemove.setRight(null); //changes nodeToRemove to have no children
                } //previous sets nodeToRemove's parent's left child to the right child of nodeToRemove, and the following sets it to the left child
                else {
                    curr.setLeft(nodeToRemove.getLeft());
                    nodeToRemove.setLeft(null);
                }
            } //case that nodeToRemove is left child of the current node
            else {
                if (nodeToRemove.getLeft() == null) {
                    curr.setRight(nodeToRemove.getRight());
                    nodeToRemove.setRight(null);
                } //replaces the removed node
                else {
                    curr.setRight(nodeToRemove.getLeft());
                    nodeToRemove.setLeft(null);
                } //replaces the removed node
            } //case that the nodeToRemove is the right child of current node
        } //by the end of this section -- the node should have been removed, replaced, and the node to be returned has no children, just it's own data

        //case that the node has two children: if one of the children is a leaf: replace the node with that
        //Otherwise, replace the node with the largest value in the left subtree
        else {
            boolean isRight; //checks whether node is to the left or right of the current node
            if (root.compareTo(nodeToRemove) != 0) {
                if (curr.getRight() != null) {
                    isRight = curr.getRight().compareTo(nodeToRemove) == 0;
                } else {
                    isRight = false;
                }
            } else {
                isRight = false;
            }

            //case that the nodeToRemove is the root
            if (root.compareTo(nodeToRemove) == 0) {

                //cases that left or right nodes are a leaf, or if they are the rightmost or leftmost values in the subtrees, respectively
                if (nodeToRemove.getRight().isLeaf() || nodeToRemove.getRight().getLeft() == null) {
                    root = nodeToRemove.getRight();
                    root.setLeft(nodeToRemove.getLeft());
                    nodeToRemove.setLeft(null);
                    nodeToRemove.setRight(null);
                } else if (nodeToRemove.getLeft().isLeaf() || nodeToRemove.getLeft().getRight() == null) {
                    root = nodeToRemove.getLeft();
                    root.setRight(nodeToRemove.getRight());
                    nodeToRemove.setRight(null);
                    nodeToRemove.setLeft(null);
                }

                //case that the root's children are not leaves:
                else {
                    //first: find the rightmost value in left subtree

                    Node<T> tempCurr = nodeToRemove.getLeft().getRight(); //sets tempCurr to the right child of the left child of nodeToRemove
                    while (tempCurr.getRight() != null) {
                        tempCurr = tempCurr.getRight();
                    } //rotates until it gets the rightmost value in the left subtree

                    root = tempCurr;

                    //the following link the replacement node to the bst as before
                    root.setLeft(nodeToRemove.getLeft());
                    root.setRight(nodeToRemove.getRight());

                    //now there is a duplicate node to delete:
                    //I'm worried the following might give me a nullPointer exception, but it shouldn't if the previous code is correct
                    Node<T> duplicateRemover = root.getLeft();
                    while (duplicateRemover.getRight().compareTo(tempCurr) != 0 && duplicateRemover.getRight().getRight() != null) {
                        duplicateRemover = duplicateRemover.getRight();
                    } //sets tempCurr to the parent of the duplicate node
                    duplicateRemover.setRight(null); //removes duplicate node

                }
            }

            //cases that nodeToRemove is not the root
            else if (nodeToRemove.getLeft().isLeaf() || nodeToRemove.getLeft().getRight() == null) {
                if (isRight) {
                    curr.setRight(nodeToRemove.getLeft()); //sets the parent's right child to the replacement node
                    curr.getRight().setRight(nodeToRemove.getRight()); //sets the right child of the replacement node
                } else {
                    curr.setLeft(nodeToRemove.getLeft()); //sets the parent's left child to the replacement node
                    curr.getLeft().setRight(nodeToRemove.getRight());//sets the right child of the replacement node
                }
                nodeToRemove.setLeft(null);
                nodeToRemove.setRight(null);
            } //if the node to the left of nodeToRemove is a leaf, the node will be replaced with that (or if the left node is the rightmost value in the left subtree)
            else if (nodeToRemove.getRight().isLeaf() || nodeToRemove.getRight().getLeft() == null) {
                if (isRight) {
                    curr.setRight(nodeToRemove.getRight()); //sets the parent's right child to the replacement node
                    curr.getRight().setLeft(nodeToRemove.getLeft()); //sets the right child of the replacement node
                    nodeToRemove.setLeft(null);
                    nodeToRemove.setRight(null);
                } else {
                    curr.setLeft(nodeToRemove.getRight()); //sets the parent's left child to the replacement node
                    curr.getLeft().setLeft(nodeToRemove.getLeft());//sets the right child of the replacement node
                    nodeToRemove.setLeft(null);
                    nodeToRemove.setRight(null);
                }
            } //if the node to the right of nodeToRemove is a leaf, the node will be replaced with that (or if the right node is the leftmost value in the right subtree)
            //following is case that neither children of nodeToRemove are leaves
            else {

                //first: find the rightmost value in left subtree

                Node<T> tempCurr = nodeToRemove.getLeft().getRight(); //sets tempCurr to the right child of the left child of nodeToRemove
                while (tempCurr.getRight() != null) {
                    tempCurr = tempCurr.getRight();
                } //rotates until tempCurr is the rightmost value in the left subtree

                if (isRight) {
                    curr.setRight(tempCurr); //replaces the old node
                } else {
                    curr.setLeft(tempCurr);
                }

                //the following link the replacement node to the bst as before
                tempCurr.setLeft(nodeToRemove.getLeft());
                tempCurr.setRight(nodeToRemove.getRight());

                //now there is a duplicate node to delete:
                Node<T> duplicateRemover = tempCurr.getLeft();
                while (duplicateRemover.getRight().compareTo(tempCurr) != 0 && duplicateRemover.getRight().getRight() != null) {
                    duplicateRemover = duplicateRemover.getRight();
                } //sets tempCurr to the parent of the duplicate node
                duplicateRemover.setRight(null); //removes duplicate node

                nodeToRemove.setLeft(null);
                nodeToRemove.setRight(null);

            }

        } //now the node should be replaced and the node removed that will be returned has no children

        //This returns nodeToRemove -- it is no longer connected to the tree and has no children
        return nodeToRemove;
    }


// Implement the search method

    //this method searches the BST to find out if a node with value val exists.
    //If found, it returns the Node, otherwise it returns
    public Node<T> search(T val, Node<T> curr) {
        if (curr == null) {
            return null;
        } else {
            Node<T> newNode = new Node<T>(val);
            if (newNode.compareTo(curr) == 0) {
                return curr;
            } else if (newNode.compareTo(curr) < 0) {
                return search(val, curr.getLeft());
            } else if (newNode.compareTo(curr) > 0) {
                return search(val, curr.getRight());
            } else {
                System.out.println("There was an error. Exiting method.");
                return null;
            } //this is a bug check, it shouldn't run.
        }
    }


// Implement the iterator method
    //Implement an iterator in class BST, and print out elements in the BST in ascending order to result.txt.
    public void print() throws IOException {

        if (isEmpty()) {
            return;
        } //won't print anything if the tree is empty

        BSTIterator<T> it = new BSTIterator<T>(root); //creates a new Iterator with curr value set to the root

        StringBuilder output = new StringBuilder();
        while (it.hasNext()) {
            output.append(it.next() + " ");
        }
        writeToFile(output.toString(), "./result.txt");

    }


    //This method writes to a file the content input to the method, and creates a new file if one does not exist
    public void writeToFile(String content, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                System.out.println("Error in creating the file.");
            }
        } //If the file doesn't already exist, a new one will be created

        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write(content + "\n");
            writer.close();
        } //creates a fileWriter to write to the file and writes to the file
        catch (IOException e) {
            System.out.println("Error in writing to file.");
        } //Exception if the filewriter fails
    }


    public boolean isEmpty() {
        return (root == null);
    }
}


// Implement the BSTIterator class
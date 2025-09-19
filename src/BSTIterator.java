import java.util.Iterator;
import java.util.Stack;
/*∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗*
@file: BSTIterator.java
@description: This is a binary search tree iterator class that implements
the iterator class. The hasNext method will run for an inOrder traversal.
it has a constructor and a hasNext method, as well as a stack of nodes and a current node.
@author: Alli Swyt
@date: September 18, 2025
∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗*/

public class BSTIterator<T extends Comparable<T>> implements Iterator {
    private Stack<Node<T>> stack = new Stack<Node<T>>();
    private Node<T> curr;

    //getter and setter methods
    public void setCurr(Node<T> curr) {
        this.curr = curr;
        goLeftFrom(curr);
    }
    public Node<T> getCurr() { return curr; }

    //constructor (takes parameter of a current node - should be the root)
    public BSTIterator(Node<T> curr) {
        this.curr = curr;
        goLeftFrom(curr); //adds curr and all the values to the left of it to the stack in order from curr to leftmost value
    }



    //returns whether there is a next value: whether the tree has been fully traversed or not
    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }


    //This method returns the next value for an InOrder traversal
    @Override
    public T next() {
        T val = null;
        if (!stack.isEmpty()) {
            curr = stack.peek(); //updates current node
            val = stack.pop().getData(); //will later return this value - top value in the stack
            if (curr.getRight() != null) {
                goLeftFrom(curr.getRight());
            } //adds current node's right child to the stack and all nodes left of it
        }
        return val;
    }

    public void goLeftFrom(Node<T> curr) {
        while (curr != null) {
            stack.push(curr);
            curr = curr.getLeft();
        } //pushes the current node and all left children successively to the stack
    }

}

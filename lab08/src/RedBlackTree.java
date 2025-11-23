public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = false;
        node.left.isBlack = true;
        node.right.isBlack = true;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     *
     * so confusing!!
     * the case is, if we want to rotateright node, if node is pointed by other node,
     * which means node is children, then after we rotate right, we return a new link,
     * which is x, but it did not change node's parent to point to what x point at
     */

    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> x = node.left;
        node.left = x.right;
        x.right = node;

        //color flipping
        node.isBlack = false;
        x.isBlack = true;


        return x;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {

        RBTreeNode<T> x = node.right;
        node.right = x.left;
        x.left = node;

        //color flipping

        if(!x.isBlack && !node.isBlack) {
            return x;
        }    //this statement is really important, if you don't do the standard TDD process,you will not be
             //able to think to write this code. because we write a lot of test, and some of the test fails,
             //so i use the debugger to find the wrong part, it takes me 1 and half hour to find this very
             //subtle mistake,i did not use any ai to help with this. the mistake is: when the case 1 happens,
             //if a is also red, then we should keep them red.but if the parent is black, then we do
             //the standard process.   And case 4 is actually redundant if we write this if statement.
             //but if we remove this if statement, case 4 also won't work.
             //      |                          |                |              |
             //      a(black) --rotateLeft(a)   x(black)         a(red)         x(red)
             //       \                        /                  \             /
             //        x(red) standard        a(red)               x(red)      a(red)



        node.isBlack = false;
        x.isBlack = true;

        return x;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        root = insertHelper(root, item);
        root.isBlack = true;
    }

    /**
     * Helper method to insert the item into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insertHelper(RBTreeNode<T> node, T item) {
        // TODO: Insert (return) new red leaf node.
        if(node == null) {
            return new RBTreeNode<>(false, item);
        }
        int tmp = item.compareTo(node.item);

        if(tmp < 0) {
            node.left = insertHelper(node.left, item);
        }
        if(tmp > 0) {
            node.right = insertHelper(node.right, item);
        }

        //case 1
        if(isRed(node.right) && !isRed((node.left))) {
            node = rotateLeft(node);
        }

        //case 2
        if(isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        //case 3
        if(isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
            flipColors(node);

        }

        //case 4
        if(isRed(node.left) && isRed(node.left.right)) {
            node.left = rotateLeft(node.left);
            node = rotateRight(node);
            flipColors(node);
        }

        return node; //fix this return statement
    }

}

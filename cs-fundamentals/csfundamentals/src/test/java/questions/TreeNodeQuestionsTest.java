package questions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TreeNodeQuestionsTest {

    @Test
    void RecurseOperationsOnNodes() {

        Node rootLeft = new Node(2);
        rootLeft.left = new Node(4);
        rootLeft.right = new Node(5);

        Node root = new Node(1);
        root.left = rootLeft;
        root.right = new Node(3);


        Assertions.assertEquals(5, countNode(root));
        Assertions.assertEquals(1, computeHeight(rootLeft));
        Assertions.assertEquals(15, sumOfNodes(root));

        Assertions.assertFalse(isExist(root, 7));
        Assertions.assertTrue(isExist(root, 5));


        // newNode = new Node(11);
    }


    /*

          5
        2   3
      4  5

     */
    int countNode(Node node) {
        if (node == null) {
            return 0;
        }

        return 1 + countNode(node.left) + countNode(node.right);

    }

    int sumOfNodes(Node node) {
        if (node == null) {
            return 0;
        }

        return node.data + sumOfNodes(node.left) + sumOfNodes(node.right);
    }

    /**
     * This Depth First Search  (DFS) PreOder
     * DFS Traversal Orders (for Binary Trees)
     * <p>
     * There are three ways to do DFS in a binary tree:
     * <p>
     * Preorder	Root → Left → Right	Process root first
     * Inorder	Left → Root → Right	Process left subtree first
     * Postorder	Left → Right → Root	Process root last
     *
     * @param node
     * @param value
     * @return
     */
    boolean isExist(Node node, int value) {
        if (node == null) return false;

        if (node.data == value) return true;

        if (isExist(node.left, value)) return true;

        return isExist(node.right, value);
    }

    // Search in BST
    boolean isExistInBST(Node node, int value) {
        if (node == null) return false;
        if (value == node.data) return true;
        if (value < node.data) return isExistInBST(node.left, value);
        return isExistInBST(node.right, value);
    }


    private Node insertIntoBST(Node node, int value) {

        if (node == null) {
            return new Node(value);
        }


        if (value < node.data) node.left = insertIntoBST(node.left, value);
        if (value > node.data) node.right = insertIntoBST(node.right, value);


        return node;
    }


    /**
     * 2
     * 1      3
     * 4 5   6 7
     *
     * @param node
     * @return
     */
    private int computeHeight(Node node) {
        if (node == null) return -1;

        return 1 + Math.max(computeHeight(node.left), computeHeight(node.right));
    }

    class Node {
        int data;
        Node left, right;

        Node(int data) {
            this.data = data;
            left = right = null;
        }
    }

}

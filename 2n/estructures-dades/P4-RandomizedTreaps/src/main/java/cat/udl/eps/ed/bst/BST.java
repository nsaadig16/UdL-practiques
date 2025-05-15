package cat.udl.eps.ed.bst;

import java.util.Comparator;
import java.util.LinkedList;

public class BST<Key extends Comparable<? super Key>, Value> implements Map<Key, Value> {

    private Node<Key, Value> root;
    Comparator<? super Key> cmp = Comparator.naturalOrder();

    private static class Node<Key extends Comparable<? super Key>, Value> {
        Key key;
        Value value;
        Node<Key, Value> left;
        Node<Key, Value> right;

        Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public void put(Key key, Value value) {
        if (key == null) throw new NullPointerException("The key is null");
        if (value == null) throw new NullPointerException("The value is null");

        var newNode = new Node<>(key, value);
        if (isEmpty()) root = newNode;
        else {
            var targetNode = searchForNode(key, root);
            putNodeUnder(newNode, targetNode);
        }
    }

    Node<Key, Value> searchForNode(Key key, Node<Key, Value> node) {
        int compare = cmp.compare(key, node.key);
        if (compare == 0) return node;
        if (compare < 0) {
            if (node.left == null) return node;
            else return searchForNode(key, node.left);
        } else {
            if (node.right == null) return node;
            else return searchForNode(key, node.right);
        }
    }

    void putNodeUnder(Node<Key, Value> childNode, Node<Key, Value> targetNode) {
        int compare = cmp.compare(childNode.key, targetNode.key);
        if (compare == 0) targetNode.value = childNode.value;
        if (compare < 0) targetNode.left = childNode;
        else targetNode.right = childNode;
    }

    @Override
    public Value get(Key key) {
        if (key == null) throw new NullPointerException("The key provided is null");

        if (isEmpty()) return null;
        return searchForValue(key, root);
    }

    Value searchForValue(Key key, Node<Key, Value> node) {
        if (node == null) return null;
        int compare = cmp.compare(key, node.key);
        if (compare == 0) return node.value;
        if (compare < 0) return searchForValue(key, node.left);
        else return searchForValue(key, node.right);
    }

    @Override
    public void remove(Key key) {
        if (key == null) throw new NullPointerException("The key is null");
        root = removeNode(root, key);
    }

    Node<Key, Value> removeNode(Node<Key, Value> node, Key key) {
        if (node == null) return null;
        int compare = cmp.compare(key, node.key);

        if (compare < 0) node.left = removeNode(node.left, key);
        else if (compare > 0) node.right = removeNode(node.right, key);
        else {
            if (node.left == null && node.right == null) return null;
            else if (node.left == null) return node.right;
            else if (node.right == null) return node.left;
            else {
                Node<Key, Value> newNode = smallestNode(node.right);
                node.value = newNode.value;
                node.right = removeNode(node.right, newNode.key);
            }
        }
        return node;
    }

    Node<Key, Value> smallestNode(Node<Key, Value> node) {
        if (node.left == null) return node;
        return smallestNode(node.left);
    }

    @Override
    public boolean containsKey(Key key) {
        if (key == null) throw new NullPointerException("The key is null.");
        return searchForValue(key, root) != null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    public int height() {
        return getHeight(root);
    }

    int getHeight(Node<Key, Value> node) {
        if (node == null) return -1;
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public LinkedList<Key> inorderTraversal() {
        LinkedList<Key> result = new LinkedList<>();
        inorderTraversal(root, result);
        return result;
    }

    private void inorderTraversal(Node<Key, Value> node, LinkedList<Key> result) {
        if (node == null) return;
        inorderTraversal(node.left, result);
        result.add(node.key);
        inorderTraversal(node.right, result);
    }
}

package cat.udl.eps.ed.bst;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Treap<Key extends Comparable<? super Key>, Value> implements Map<Key, Value> {

    Node<Key, Value> root;
    Comparator<? super Key> cmp = Comparator.naturalOrder();

     private static class Node<Key extends Comparable<? super Key>, Value> {
        Key key;
        Value value;
        double priority;
        Node<Key, Value> left;
        Node<Key, Value> right;

        Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.priority = Math.random();
        }
    }

    @Override
    public void put(Key key, Value value) {
        if(key == null) throw new NullPointerException("The key is null");
        if(value == null) throw new NullPointerException("The value is null");

        root = put(root, key, value);
    }

    private Node<Key, Value> put(Node<Key, Value> node, Key key, Value value) {
        if (node == null) return new Node<>(key, value);

        int cmp = this.cmp.compare(key, node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
            if (node.left.priority < node.priority) {
                node = rotateClockwise(node);
            }
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
            if (node.right.priority < node.priority) {
                node = rotateAnticlockwise(node);
            }
        } else {
            node.value = value;
        }
        return node;
    }

    private Node<Key, Value> rotateClockwise(Node<Key, Value> node) {
        Node<Key, Value> x = node.left;
        node.left = x.right;
        x.right = node;
        return x;
    }

    private Node<Key, Value> rotateAnticlockwise(Node<Key, Value> node) {
        Node<Key, Value> x = node.right;
        node.right = x.left;
        x.left = node;
        return x;
    }

    @Override
    public Value get(Key key) {
        if(key == null) throw new NullPointerException("The key provided is null");

        if(isEmpty()) return null;
        return searchForValue(key,root);
    }

    Value searchForValue(Key key, Node<Key,Value> node){
        if(node == null) return null;
        int compare = cmp.compare(key,node.key);
        if(compare == 0) return node.value;
        if(compare < 0) return searchForValue(key, node.left);
        else return searchForValue(key,node.right);
    }

    @Override
    public void remove(Key key) {
        if (key == null) throw new NullPointerException("The key is null");
        root = remove(root, key);
    }

    private Node<Key, Value> remove(Node<Key, Value> node, Key key) {
        if (node == null) return null;

        int cmp = this.cmp.compare(key, node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            if (node.left.priority < node.right.priority) {
                node = rotateClockwise(node);
                node.right = remove(node.right, key);
            } else {
                node = rotateAnticlockwise(node);
                node.left = remove(node.left, key);
            }
        }
        return node;
    }

    @Override
    public boolean containsKey(Key key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    public int height() {
        return getHeight(root);
    }

    int getHeight(Node<Key,Value> node) {
        if (node == null) return -1;
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public LinkedList<Key> getInOrder(){
         var inOrderList = new LinkedList<Key>();
         inOrderTraversal(root,inOrderList);
         return inOrderList;
    }

    private void inOrderTraversal(Node<Key, Value> node, LinkedList<Key> result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.add(node.key);
            inOrderTraversal(node.right, result);
        }
    }

    boolean keepsHeapProperty(){
        return checkHeapProperty(root);
    }

    private boolean checkHeapProperty(Node<Key, Value> node) {
        if (node == null) return true;
        if (node.left != null && node.left.priority < node.priority) return false;
        if (node.right != null && node.right.priority < node.priority) return false;
        return checkHeapProperty(node.left) && checkHeapProperty(node.right);
    }
}

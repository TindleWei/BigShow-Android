package com.gamebuddy.bigshow.model.datastruct;

/**
 * describe
 * created by tindle
 * created time 16/4/19 下午3:13
 */
public class BinaryTree {

    private Node root;

    private static class Node {
        public Node left;
        public Node right;
        public int data;

        Node(int newData){
            left = null;
            right = null;
            data = newData;
        }
    }

    public BinaryTree() {
        root = null;
    }

    public void insert(int data) {
        root = insert(root, data);
    }

    public Node insert(Node node, int data) {
        if (node==null) {
            node = new Node(data);
        } else {
            if (data<= node.data) {
                node.left = insert(node.left, data);
            } else {
                node.right = insert(node.right, data);
            }
        }
        return node;
    }

    public void buildTree(int[] a) {
        for(int data: a) {
            insert(data);
        }
    }

    public void printTree() {
        printTree(root);
    }

    private void printTree(Node node) {
        if(node==null) return;
        printTree(node.left);
        System.out.println(node.data + "");
        printTree(node.right);
    }

}



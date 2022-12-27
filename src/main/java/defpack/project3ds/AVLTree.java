package defpack.project3ds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class AVLTree {
    private TreeNode treeRoot;

    public AVLTree() {
        this.treeRoot = null;
    }

    //AVL Methods
    public int getTreeHeight() {
        return getTreeHeight(treeRoot);
    }

    private int getTreeHeight(TreeNode root) {
        if (root == null) return 0;
        return maxHeight(getTreeHeight(root.left), getTreeHeight(root.right)) + 1;
    }

    private int height(TreeNode node) {
        if (node == null) return 0;
        return node.height;
    }

    private int maxHeight(int a, int b) {
        return Math.max(a, b);
    }

    private TreeNode toRight(TreeNode oldRoot) {
        TreeNode newRoot = oldRoot.left;
        TreeNode temp = newRoot.right;
        newRoot.right = oldRoot;
        oldRoot.left = temp;
        oldRoot.height = maxHeight(height(oldRoot.left), height(oldRoot.right));
        newRoot.height = maxHeight(height(newRoot.left), height(newRoot.right));
        return newRoot;
    }

    private TreeNode toLeft(TreeNode oldRoot) {
        TreeNode newRoot = oldRoot.right;
        TreeNode T2 = newRoot.left;
        newRoot.left = oldRoot;
        oldRoot.right = T2;
        oldRoot.height = maxHeight(height(oldRoot.left), height(oldRoot.right)) + 1;
        newRoot.height = maxHeight(height(newRoot.left), height(newRoot.right)) + 1;
        return newRoot;
    }

    private int getBalance(TreeNode currentNode) {
        if (currentNode == null)
            return 0;
        return height(currentNode.left) - height(currentNode.right);
    }

    //Inserting
    public void insert(Department data) {
        TreeNode newNode = new TreeNode(data);
        treeRoot = insert(treeRoot, newNode);
    }

    private TreeNode insert(TreeNode currentNode, TreeNode newNode) {
        if (currentNode == null) {
            currentNode = newNode;
            return currentNode;
        }
        if (newNode.getKey().compareToIgnoreCase(currentNode.getKey()) < 0)
            currentNode.left = insert(currentNode.left, newNode);
        else if (newNode.getKey().compareToIgnoreCase(currentNode.getKey()) > 0)
            currentNode.right = insert(currentNode.right, newNode);
        else
            return currentNode;

        currentNode.height = 1 + maxHeight(height(currentNode.left), height(currentNode.right));
        int balance = getBalance(currentNode);
        if (balance > 1 && newNode.getKey().compareToIgnoreCase(currentNode.left.getKey()) < 0)
            return toRight(currentNode);
        if (balance < -1 && newNode.getKey().compareToIgnoreCase(currentNode.right.getKey()) > 0)
            return toLeft(currentNode);
        if (balance > 1 && newNode.getKey().compareToIgnoreCase(currentNode.left.getKey()) > 0) {
            currentNode.left = toLeft(currentNode.left);
            return toRight(currentNode);
        }
        if (balance < -1 && newNode.getKey().compareToIgnoreCase(currentNode.right.getKey()) > 0) {
            currentNode.right = toRight(currentNode.right);
            return toLeft(currentNode);
        }
        return currentNode;

    }

    //Deleting
    public void delete(String name) {
        treeRoot = delete(treeRoot, name);
    }

    private TreeNode delete(TreeNode currentNode, String name) {
        if (currentNode == null)
            return null;
        if (name.compareToIgnoreCase(currentNode.getKey()) < 0)
            currentNode.left = delete(currentNode.left, name);
        else if (name.compareToIgnoreCase(currentNode.getKey()) > 0)
            currentNode.right = delete(currentNode.right, name);
        else {
            if ((currentNode.left == null) || (currentNode.right == null)) {
                TreeNode temp;
                if (null == currentNode.left)
                    temp = currentNode.right;
                else temp = currentNode.left;
                currentNode = temp;
            } else {
                TreeNode temp = minValueNode(currentNode.right);
                currentNode.setKey(temp.getKey());
                currentNode.right = delete(currentNode.right, temp.getKey());
            }
        }
        if (currentNode == null)
            return null;
        currentNode.height = maxHeight(height(currentNode.left), height(currentNode.right)) + 1;
        int balance = getBalance(currentNode);
        if (balance > 1 && getBalance(currentNode.left) >= 0)
            return toRight(currentNode);
        if (balance < -1 && getBalance(currentNode.right) <= 0)
            return toLeft(currentNode);
        if (balance > 1 && getBalance(currentNode.left) < 0)
        {
            currentNode.left = toLeft(currentNode.left);
            return toRight(currentNode);
        }
        if (balance < -1 && getBalance(currentNode.right) > 0)
        {
            currentNode.right = toRight(currentNode.right);
            return toLeft(currentNode);
        }
        return currentNode;
    }

    private TreeNode minValueNode(TreeNode current) {
        if ( current == null ) //empty tree
            return null;
        else
        if ( current.left == null ) //node itself
            return ( current );
        else
            return ( minValueNode ( current.left ));
    }

    //Printing
    public String toString(){
        return inOrder(treeRoot);
    }

    public String inOrder(TreeNode currentNode){
        if (currentNode == null){
            return "";
        }
        return (inOrder(currentNode.left) + currentNode.data.toString() + "\n" + inOrder(currentNode.right));

    }

    public void saveToFile(File file) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.println(inOrderData(treeRoot));
        printWriter.close();
    }

    public String inOrderData(TreeNode currentNode){
        if (currentNode == null){
            return "";
        }
        return (inOrderData(currentNode.left) + currentNode.data.toFileString() + "\n" + inOrderData(currentNode.right));

    }

    public TreeNode get(String key) {
        return get(treeRoot,key);
    }

    private TreeNode get(TreeNode root, String key){
        if (root == null)
            return null;
        else if ( key.compareToIgnoreCase(root.getKey()) < 0)
            return ( get (root.left, key));
        else if ( key.compareToIgnoreCase(root.getKey()) > 0 )
            return ( get (root.right, key));
        else
            return root;
    }
}

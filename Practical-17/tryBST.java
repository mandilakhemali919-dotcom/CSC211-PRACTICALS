import java.util.*;
public class tryBST{
  private tNode root;

  public tryBST() 
  {
    root = null;
  }

  public void insert(int data) {
            root = insertRec(root, data);
        }

        private tNode insertRec(tNode root, int data) {
            if (root == null) {
                root = new tNode(data);
                return root;
            }

            if (data < root.data) {
                root.left = insertRec(root.left, data);
            } else if (data > root.data) {
                root.right = insertRec(root.right, data);
            }

                  return root;
        }

        // Delete method - removes node with given value
        public void delete(int data) {
            root = deleteRec(root, data);
        }

        private tNode deleteRec(tNode root, int data) {
            if (root == null) {
                return root;
            }

            if (data < root.data) {
                root.left = deleteRec(root.left, data);
            } else if (data > root.data) {
                root.right = deleteRec(root.right, data);
            } else {
                // Node with only one child or no child
                if (root.left == null) {
                    return root.right;
                } else if (root.right == null) {
                    return root.left;
                }

                // Node with two children: get inorder successor
                root.data = minValue(root.right);
                root.right = deleteRec(root.right, root.data);
            }

            return root;
        }
        return root;
        }

        // Delete method - removes node with given value
        public void delete(int data) {
            root = deleteRec(root, data);
        }

        private tNode deleteRec(tNode root, int data) {
            if (root == null) {
                return root;
            }

            if (data < root.data) {
                root.left = deleteRec(root.left, data);
            } else if (data > root.data) {
                root.right = deleteRec(root.right, data);
            } else {
                // Node with only one child or no child
                if (root.left == null) {
                    return root.right;
                } else if (root.right == null) {
                    return root.left;
                }

                // Node with two children: get inorder successor
                root.data = minValue(root.right);
                root.right = deleteRec(root.right, root.data);
            }

            return root;
        }

       

       
  

  
  




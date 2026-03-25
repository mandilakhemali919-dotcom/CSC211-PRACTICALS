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

  

  
  




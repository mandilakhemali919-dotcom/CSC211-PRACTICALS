import java.util.*;

public class tryBST {
    private tNode root;
    
    // Constructor
    public tryBST() {
        root = null;
    }
    
    // Insert method
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
    
    private int minValue(tNode root) {
        int minValue = root.data;
        while (root.left != null) {
            minValue = root.left.data;
            root = root.left;
        }
        return minValue;
    }
    
    // Check if tree is a valid BST
    public boolean isBST() {
        return isBSTRec(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private boolean isBSTRec(tNode node, int min, int max) {
        if (node == null) {
            return true;
        }
        
        if (node.data < min || node.data > max) {
            return false;
        }
        
        return isBSTRec(node.left, min, node.data - 1) && 
               isBSTRec(node.right, node.data + 1, max);
    }
    
    // Build perfect BST from range [1, 2^n - 1] using divide and conquer
    public void buildPerfectBST(int n) {
        int max = (int)(Math.pow(2, n) - 1);
        int[] numbers = new int[max];
        for (int i = 0; i < max; i++) {
            numbers[i] = i + 1;
        }
        
        // Clear existing tree
        root = null;
        
        // Build tree using divide and conquer (breadth-first order construction)
        buildTreeRec(1, max);
    }
    
    private void buildTreeRec(int start, int end) {
        if (start > end) {
            return;
        }
        
        int middle = start + (end - start) / 2;
        insert(middle);
        
        buildTreeRec(start, middle - 1);
        buildTreeRec(middle + 1, end);
    }
    
    // Alternative method: Build using array approach for more control
    public void buildPerfectBSTAlternative(int n) {
        int max = (int)(Math.pow(2, n) - 1);
        root = buildBalanced(1, max);
    }
    
    private tNode buildBalanced(int start, int end) {
        if (start > end) {
            return null;
        }
        
        int mid = start + (end - start) / 2;
        tNode node = new tNode(mid);
        
        node.left = buildBalanced(start, mid - 1);
        node.right = buildBalanced(mid + 1, end);
        
        return node;
    }
    
    // Delete all nodes with even numbers
    public void deleteEvenNumbers() {
        deleteEvenRec(root);
    }
    
    private tNode deleteEvenRec(tNode node) {
        if (node == null) {
            return null;
        }
        
        // Process left and right subtrees first
        node.left = deleteEvenRec(node.left);
        node.right = deleteEvenRec(node.right);
        
        // If current node has even data, delete it
        if (node.data % 2 == 0) {
            return deleteNode(node);
        }
        
        return node;
    }
    
    private tNode deleteNode(tNode node) {
        if (node.left == null) {
            return node.right;
        }
        if (node.right == null) {
            return node.left;
        }
        
        // Node has two children
        tNode successor = findMin(node.right);
        node.data = successor.data;
        node.right = deleteRec(node.right, successor.data);
        return node;
    }
    
    private tNode findMin(tNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    // Get tree size
    public int size() {
        return sizeRec(root);
    }
    
    private int sizeRec(tNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + sizeRec(node.left) + sizeRec(node.right);
    }
    
    // Inorder traversal for debugging
    public void inorder() {
        inorderRec(root);
        System.out.println();
    }
    
    private void inorderRec(tNode root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }
    
    // Clear the tree
    public void clear() {
        root = null;
    }
    
    // Main method
    public static void main(String[] args) {
        // Test with small n first (n <= 7)
        System.out.println("Testing with small n values...");
        for (int n = 1; n <= 7; n++) {
            tryBST tree = new tryBST();
            int max = (int)(Math.pow(2, n) - 1);
            tree.buildPerfectBST(n);
            
            System.out.print("n = " + n + ", Tree size = " + tree.size());
            System.out.println(", Is BST? " + tree.isBST());
            
            // Test deletion of even numbers
            int evenCount = countEvenNumbers(1, max);
            tree.deleteEvenNumbers();
            System.out.println("After deleting evens, size = " + tree.size() + 
                             " (should be " + (max - evenCount) + ")");
            System.out.println();
        }
        
        // Run timing experiments
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TIMING EXPERIMENTS");
        System.out.println("=".repeat(80));
        
        // Find appropriate n to get timing > 1000ms
        int n = 20; // Start with n=20 as suggested
        int repetitions = 30;
        
        // Adjust n if timing is too small
        boolean timingOK = false;
        while (!timingOK && n <= 25) {
            System.out.println("\nTesting with n = " + n);
            timingOK = runTimingExperiment(n, repetitions);
            if (!timingOK) {
                n++;
            }
        }
    }
    
    private static boolean runTimingExperiment(int n, int repetitions) {
        int max = (int)(Math.pow(2, n) - 1);
        
        System.out.println("Tree size: " + max + " nodes");
        
        long[] populateTimes = new long[repetitions];
        long[] deleteTimes = new long[repetitions];
        
        for (int i = 0; i < repetitions; i++) {
            tryBST tree = new tryBST();
            
            // Time populate operation
            long startPopulate = System.nanoTime();
            tree.buildPerfectBST(n);
            long endPopulate = System.nanoTime();
            populateTimes[i] = endPopulate - startPopulate;
            
            // Verify BST property
            if (!tree.isBST()) {
                System.out.println("Warning: Tree is not a valid BST at iteration " + i);
            }
            
            // Time delete even numbers operation
            long startDelete = System.nanoTime();
            tree.deleteEvenNumbers();
            long endDelete = System.nanoTime();
            deleteTimes[i] = endDelete - startDelete;
        }
        
        // Calculate statistics
        double populateAvg = calculateAverage(populateTimes);
        double populateStdDev = calculateStdDev(populateTimes, populateAvg);
        double deleteAvg = calculateAverage(deleteTimes);
        double deleteStdDev = calculateStdDev(deleteTimes, deleteAvg);
        
        // Convert to milliseconds
        populateAvg /= 1_000_000;
        populateStdDev /= 1_000_000;
        deleteAvg /= 1_000_000;
        deleteStdDev /= 1_000_000;
        
        // Display results
        System.out.println("\n" + "-".repeat(80));
        System.out.printf("%-20s %-20s %-20s %-20s\n", 
                         "Method", "Number of keys n", "Average time in ms.", "Standard Deviation");
        System.out.println("-".repeat(80));
        System.out.printf("%-20s %-20d %-20.2f %-20.2f\n", 
                         "Populate tree", max, populateAvg, populateStdDev);
        System.out.printf("%-20s %-20d %-20.2f %-20.2f\n", 
                         "Remove evens from tree", max, deleteAvg, deleteStdDev);
        System.out.println("-".repeat(80));
        
        // Check if timing is sufficient (> 1000ms for populate)
        if (populateAvg < 1000) {
            System.out.println("\nWarning: Populate time (" + populateAvg + " ms) is less than 1000ms.");
            System.out.println("Consider increasing n for better timing results.");
            return false;
        } else {
            System.out.println("\n✓ Timing requirements met (populate time > 1000ms)");
            return true;
        }
    }
    
    private static double calculateAverage(long[] times) {
        double sum = 0;
        for (long time : times) {
            sum += time;
        }
        return sum / times.length;
    }
    
    private static double calculateStdDev(long[] times, double mean) {
        double sumSquaredDiff = 0;
        for (long time : times) {
            double diff = time - mean;
            sumSquaredDiff += diff * diff;
        }
        return Math.sqrt(sumSquaredDiff / times.length);
    }
    
    private static int countEvenNumbers(int start, int end) {
        int count = 0;
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                count++;
            }
        }
        return count;
    }
}
  
  




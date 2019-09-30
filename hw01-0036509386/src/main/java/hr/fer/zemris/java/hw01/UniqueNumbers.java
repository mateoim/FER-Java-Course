package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * A class that takes numbers from standard input until "kraj" is entered
 * and prints them to standard output from lowest to highest and from highest to lowest.
 */

public class UniqueNumbers {

    /**
     * An auxiliary data type that represents a node in a binary tree.
     */

    public static class TreeNode {
        int value;
        TreeNode left, right;
    }

    /**
     * Used to add a new node to a tree.
     *
     * @param node tree where a new node is being added
     * @param value value being added to the tree
     *
     * @return update tree
     */

    public static TreeNode addNode(TreeNode node, int value) {
        if (node == null) {
            node = new TreeNode();
            node.value = value;

            return node;
        }

        if (value < node.value) {
            node.left = addNode(node.left, value);
        }

        if (value > node.value) {
            node.right = addNode(node.right, value);
        }

        return node;
    }

    /**
     * Returns current size of a tree.
     *
     * @param node tree whose size is being determined
     *
     * @return current size of the tree
     */

    public static int treeSize(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int size = 1;

        size += treeSize(node.left);
        size += treeSize(node.right);

        return size;
    }

    /**
     * Checks whether a tree contains a value.
     *
     * @param node tree being checked
     * @param value value being checked
     *
     * @return {@code true} if a value is not present, otherwise {@code false}
     */

    public static boolean containsValue(TreeNode node, int value) {
        if (node == null) {
            return false;
        }

        if (node.value == value) {
            return true;
        }

        if (value < node.value) {
            if (node.left == null) {
                return false;
            } else {
                return containsValue(node.left, value);
            }
        }

        else {
            if (node.right == null) {
                return false;
            } else {
                return containsValue(node.right, value);
            }
        }
    }

    /**
     * Prints the elements of a given tree from lowest to highest to standard output.
     *
     * @param node tree whose elements are being printed
     */

    public static String fromLowest(TreeNode node) {
        StringBuilder sb = new StringBuilder();

        if (node != null) {
            sb.append(fromLowest(node.left));
            sb.append(" ").append(node.value);
            sb.append(fromLowest(node.right));
        }

        return sb.toString();
    }

    /**
     * Prints the elements of a given tree from highest to lowest to standard output.
     *
     * @param node tree whose elements are being printed
     */

    public static String fromHighest(TreeNode node) {
        StringBuilder sb = new StringBuilder();

        if (node != null) {
            sb.append(fromHighest(node.right));
            sb.append(" ").append(node.value);
            sb.append(fromHighest(node.left));
        }

        return sb.toString();
    }

    /**
     * Used to start the program.
     *
     * @param args entered using command line, nothing in this case
     */

    public static void main(String[] args) {
        TreeNode glava = null;
        System.out.print("Unesite broj > ");

        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNext()) {
                String line = sc.nextLine();

                if (line.equals("kraj")) {
                    System.out.print("Ispis od najmanjeg:" + fromLowest(glava));
                    System.out.format("%nIspis od najvećeg:" + fromHighest(glava));
                    break;
                }

                try {
                    int number = Integer.parseInt(line);

                    if (containsValue(glava, number)) {
                        System.out.println("Broj već postoji. Preskačem.");
                    } else {
                        glava = addNode(glava, number);
                        System.out.println("Dodano.");
                    }
                } catch (IllegalArgumentException exc) {
                    System.out.println("'" + line + "' nije cijeli broj.");
                }
                System.out.print("Unesite broj > ");
            }
        }
    }
}

package hr.fer.zemris.java.hw01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniqueNumbersTest {

    @Test
    void treeSizeTest() {
        UniqueNumbers.TreeNode node = null;
        assertEquals(0, UniqueNumbers.treeSize(node));

        node = UniqueNumbers.addNode(node, 42);
        node = UniqueNumbers.addNode(node, 76);
        node = UniqueNumbers.addNode(node, 21);
        node = UniqueNumbers.addNode(node, 76);
        node = UniqueNumbers.addNode(node, 35);

        assertEquals(4, UniqueNumbers.treeSize(node));
    }

    @Test
    void containsValueTest() {
        UniqueNumbers.TreeNode node = null;
        assertFalse(UniqueNumbers.containsValue(node, 0));

        node = UniqueNumbers.addNode(node, 42);
        node = UniqueNumbers.addNode(node, 76);
        node = UniqueNumbers.addNode(node, 21);
        node = UniqueNumbers.addNode(node, 76);
        node = UniqueNumbers.addNode(node, 35);

        assertTrue(UniqueNumbers.containsValue(node, 42));
        assertTrue(UniqueNumbers.containsValue(node, 76));
        assertTrue(UniqueNumbers.containsValue(node, 21));
        assertTrue(UniqueNumbers.containsValue(node, 35));

        assertFalse(UniqueNumbers.containsValue(node, 0));
    }

    @Test
    void addNodeTest() {
        UniqueNumbers.TreeNode node = null;
        assertFalse(UniqueNumbers.containsValue(node, 0));

        node = UniqueNumbers.addNode(node, 0);

        assertTrue(UniqueNumbers.containsValue(node, 0));
    }

    @Test
    void fromLowestTest() {
        UniqueNumbers.TreeNode node = null;

        node = UniqueNumbers.addNode(node, 42);
        node = UniqueNumbers.addNode(node, 76);
        node = UniqueNumbers.addNode(node, 21);
        node = UniqueNumbers.addNode(node, 76);
        node = UniqueNumbers.addNode(node, 35);

        assertEquals("Ispis od najmanjeg: 21 35 42 76", "Ispis od najmanjeg:"
                + UniqueNumbers.fromLowest(node));
    }

    @Test
    void fromHighestTest() {
        UniqueNumbers.TreeNode node = null;

        node = UniqueNumbers.addNode(node, 42);
        node = UniqueNumbers.addNode(node, 76);
        node = UniqueNumbers.addNode(node, 21);
        node = UniqueNumbers.addNode(node, 76);
        node = UniqueNumbers.addNode(node, 35);

        assertEquals("Ispis od najvećeg: 76 42 35 21", "Ispis od najvećeg:"
                + UniqueNumbers.fromHighest(node));
    }
}

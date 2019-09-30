package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectMultistackTest {

    @Test
    void testInvalidKeyAndValue() {
        ObjectMultistack stack = new ObjectMultistack();

        assertThrows(NullPointerException.class, () -> stack.push(null, new ValueWrapper("test")));
        assertThrows(NullPointerException.class, () -> stack.push("test", null));
    }

    @Test
    void testPush() {
        ObjectMultistack stack = new ObjectMultistack();

        stack.push("first", new ValueWrapper("first"));
        stack.push("first", new ValueWrapper("test"));
        stack.push("second", new ValueWrapper("secondTest"));

        assertEquals("test", stack.peek("first").getValue());
        assertEquals("secondTest", stack.peek("second").getValue());

        stack.pop("first");

        assertEquals("first", stack.peek("first").getValue());
    }

    @Test
    void testIsEmpty() {
        ObjectMultistack stack = new ObjectMultistack();

        assertTrue(stack.isEmpty("test"));

        stack.push("test", new ValueWrapper(54));

        assertFalse(stack.isEmpty("test"));

        stack.pop("test");

        assertTrue(stack.isEmpty("test"));
    }

    @Test
    void testEmptyStack() {
        ObjectMultistack stack = new ObjectMultistack();

        stack.push("wrongSlot", new ValueWrapper("wrong"));

        assertThrows(RuntimeException.class, () -> stack.pop("test"));
        assertThrows(RuntimeException.class, () -> stack.peek("test"));

        stack.pop("wrongSlot");

        assertThrows(RuntimeException.class, () -> stack.pop("wrongSlot"));
        assertThrows(RuntimeException.class, () -> stack.peek("wrongSlot"));
    }

    @Test
    void testPeek() {
        ObjectMultistack stack = new ObjectMultistack();

        stack.push("test", new ValueWrapper(15));
        stack.push("test", new ValueWrapper(3));

        assertEquals(3, stack.peek("test").getValue());

        stack.pop("test");

        assertEquals(15, stack.peek("test").getValue());
    }

    @Test
    void testPop() {
        ObjectMultistack stack = new ObjectMultistack();

        stack.push("test", new ValueWrapper(Boolean.TRUE));
        stack.push("test", new ValueWrapper(3.15));

        assertFalse(stack.isEmpty("test"));
        assertEquals(3.15, stack.pop("test").getValue());
        assertEquals(Boolean.TRUE, stack.pop("test").getValue());
        assertTrue(stack.isEmpty("test"));
    }
}

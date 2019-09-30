package hr.fer.zemris.java.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryTest {

    @Test
    void testEmptyDictionary() {
        Dictionary<Integer, String> dictionary = new Dictionary<>();

        assertTrue(dictionary.isEmpty());

        dictionary.put(15, "test");

        assertFalse(dictionary.isEmpty());
    }

    @Test
    void testSize() {
        Dictionary<Integer, String> dictionary = new Dictionary<>();

        assertEquals(0, dictionary.size());

        dictionary.put(0, "Eric");
        dictionary.put(5, "John");

        assertEquals(2, dictionary.size());

        dictionary.clear();

        assertEquals(0, dictionary.size());
    }

    @Test
    void testClear() {
        Dictionary<Integer, String> dictionary = new Dictionary<>();
        dictionary.put(0, "Eric");
        dictionary.put(5, "John");

        assertFalse(dictionary.isEmpty());

        dictionary.clear();

        assertTrue(dictionary.isEmpty());
    }

    @Test
    void testPut() {
        Dictionary<Integer, String> dictionary = new Dictionary<>();

        assertEquals(0, dictionary.size());

        dictionary.put(0, "test");

        assertEquals(1, dictionary.size());
        assertEquals("test", dictionary.get(0));

        dictionary.put(0, "new");

        assertEquals(1, dictionary.size());
        assertEquals("new", dictionary.get(0));
    }

    @Test
    void testPutException() {
        Dictionary<Integer, String> dictionary = new Dictionary<>();
        assertThrows(NullPointerException.class, () -> dictionary.put(null, "test"));
    }

    @Test
    void testGet() {
        Dictionary<Integer, String> dictionary = new Dictionary<>();

        dictionary.put(65, "Value: 65");
        dictionary.put(42, "Value: 42");

        assertEquals("Value: 65", dictionary.get(65));
        assertEquals("Value: 42", dictionary.get(42));

        dictionary.put(42, "Ultimate answer");

        assertEquals("Ultimate answer", dictionary.get(42));
        assertNull(dictionary.get(13));
    }

    @Test
    void testInvalidGet() {
        Dictionary<Double, String> dictionary = new Dictionary<>();

        dictionary.put(3.14, "PI");

        assertNull(dictionary.get("3.14"));
    }
}

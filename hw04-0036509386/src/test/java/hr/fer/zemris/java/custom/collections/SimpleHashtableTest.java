package hr.fer.zemris.java.custom.collections;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class SimpleHashtableTest {

    @Test
    void testPut() {
        SimpleHashtable<Integer, String> hashtable = new SimpleHashtable<>();

        hashtable.put(128, "test");
        hashtable.put(1235, "test2");
        hashtable.put(0, "zero");

        assertTrue(hashtable.containsKey(128));
        assertTrue(hashtable.containsValue("test"));
        assertEquals("test", hashtable.get(128));
        assertEquals(3, hashtable.size());

        hashtable.put(0, "0");
        assertEquals(3, hashtable.size());
        assertEquals("0", hashtable.get(0));
    }

    @Test
    void testRemove() {
        SimpleHashtable<Integer, String> hashtable = new SimpleHashtable<>();

        hashtable.put(128, "test");
        hashtable.put(1235, "test2");
        hashtable.put(0, "zero");

        assertTrue(hashtable.containsKey(0));
        assertEquals(3, hashtable.size());

        hashtable.remove(0);

        assertFalse(hashtable.containsKey(0));
        assertNull(hashtable.get(0));
        assertEquals(2, hashtable.size());
    }

    @Test
    void testExpansion() {
        SimpleHashtable<Integer, String> hashtable = new SimpleHashtable<>(3);

        hashtable.put(1, "first");
        hashtable.put(2, "second");
        hashtable.put(3, "third");
        hashtable.put(4, "fourth");
        hashtable.put(5, "fifth");

        assertEquals(5, hashtable.size());
        assertEquals(8, hashtable.elementsCapacity());
    }

    @Test
    void testClear() {
        SimpleHashtable<Integer, String> hashtable = new SimpleHashtable<>(1);

        assertEquals(1, hashtable.elementsCapacity());

        hashtable.put(14, "test1");
        assertEquals(2, hashtable.elementsCapacity());
        assertEquals(1, hashtable.size());

        hashtable.put(56, "test2");
        hashtable.put(99, "test3");

        assertEquals(8, hashtable.elementsCapacity());

        hashtable.clear();

        assertEquals(0, hashtable.size());
        assertEquals(8, hashtable.elementsCapacity());
    }

    @Test
    void testToString() {
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5);

        assertEquals("[Ante=2, Ivana=5, Jasna=2, Kristina=5]", examMarks.toString());
    }

    @Test
    void testEmptyIterator() {
        SimpleHashtable<Integer, String> hashtable = new SimpleHashtable<>(1);

        Iterator<SimpleHashtable.TableEntry<Integer, String>> iterator = hashtable.iterator();

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testInvalidConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<Integer, String>(0));
    }
}

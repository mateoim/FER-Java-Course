package hr.fer.zemris.java.custom.collections;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListIndexedCollectionTest {

    @Test
    void testGetter() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add(15);
        collection.add("test");

        ElementsGetter getter = collection.createElementsGetter();

        assertTrue(getter.hasNextElement());
        assertEquals(15, getter.getNextElement());
        assertTrue(getter.hasNextElement());
        assertEquals("test", getter.getNextElement());

        assertFalse(getter.hasNextElement());
        assertThrows(NoSuchElementException.class, getter::getNextElement);
    }

    @Test
    void testModifiedGetter() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add(15);
        collection.add("test");

        ElementsGetter getter = collection.createElementsGetter();

        getter.getNextElement();
        collection.add("fail");

        assertThrows(ConcurrentModificationException.class, getter::getNextElement);
    }

    // old tests
    @Test
    void testDefaultConstructor() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        assertEquals(0, collection.size());
    }

    @Test
    void testCopyConstructor() {
        Collection collectionToCopy = new ArrayIndexedCollection();

        collectionToCopy.add(15);
        collectionToCopy.add("test");

        LinkedListIndexedCollection collection = new LinkedListIndexedCollection(collectionToCopy);

        assertTrue(collection.contains(15));
        assertTrue(collection.contains("test"));
        assertEquals(2, collection.size());
    }

    @Test
    void testCopyConstructorException() {
        assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));
    }

    @Test
    void testAdd() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        assertFalse(collection.contains(13));
        assertFalse(collection.contains("test"));
        assertEquals(0, collection.size());

        collection.add(13);
        collection.add("test");

        assertTrue(collection.contains(13));
        assertTrue(collection.contains("test"));
        assertEquals(2, collection.size());

        Object[] testArray = {13, "test"};

        assertArrayEquals(testArray, collection.toArray());
    }

    @Test
    void testAddException() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        assertThrows(NullPointerException.class, () -> collection.add(null));
    }

    @Test
    void testToArray() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        assertArrayEquals(new Object[0], collection.toArray());

        collection.add(3);
        collection.add("test");
        collection.add(3.14);

        Object[] testArray = {3, "test", 3.14};

        assertArrayEquals(testArray, collection.toArray());
    }

    @Test
    void testIsEmpty() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        assertTrue(collection.isEmpty());

        collection.add("test");

        assertFalse(collection.isEmpty());
    }

    @Test
    void testClear() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add("test");
        collection.add(3.14);
        collection.add(1);

        collection.clear();

        assertTrue(collection.isEmpty());
        assertEquals(0, collection.size());
    }

    @Test
    void testRemoveObject() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add(3.14);
        collection.add(5);
        collection.add("test");
        collection.add("string");
        collection.add(13);

        // removing from middle
        assertTrue(collection.remove("test"));
        assertFalse(collection.contains("test"));

        Object[] testArray = {3.14, 5, "string", 13};

        assertArrayEquals(testArray, collection.toArray());

        // removing first
        assertTrue(collection.remove(3.14));
        assertFalse(collection.contains(3.14));

        Object[] testArray2 = {5, "string", 13};

        assertArrayEquals(testArray2, collection.toArray());

        // removing last
        assertTrue(collection.remove(Integer.valueOf(13)));
        assertFalse(collection.contains(13));
        assertEquals(2, collection.size());

        Object[] testArray3 = {5, "string"};

        assertArrayEquals(testArray3, collection.toArray());

        assertFalse(collection.remove(null));
        assertFalse(collection.remove(3.14));
    }

    @Test
    void testContains() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        assertFalse(collection.contains("test"));

        collection.add("test");

        assertTrue(collection.contains("test"));
        assertFalse(collection.contains(null));
    }

    @Test
    void testAddAll() {
        LinkedListIndexedCollection collection1 = new LinkedListIndexedCollection();
        LinkedListIndexedCollection collection2 = new LinkedListIndexedCollection();

        collection1.add(15);
        collection1.add("test");

        assertTrue(collection1.contains(15));
        assertTrue(collection1.contains("test"));
        assertFalse(collection2.contains(15));
        assertFalse(collection2.contains("test"));

        collection2.addAll(collection1);

        assertTrue(collection1.contains(15));
        assertTrue(collection1.contains("test"));
        assertTrue(collection2.contains(15));
        assertTrue(collection2.contains("test"));

        assertEquals(2, collection2.size());
    }

    @Test
    void testAddAllCross() {
        ArrayIndexedCollection collection1 = new ArrayIndexedCollection();
        LinkedListIndexedCollection collection2 = new LinkedListIndexedCollection();

        collection1.add(15);
        collection1.add("test");

        assertTrue(collection1.contains(15));
        assertTrue(collection1.contains("test"));
        assertFalse(collection2.contains(15));
        assertFalse(collection2.contains("test"));

        collection2.addAll(collection1);

        assertTrue(collection1.contains(15));
        assertTrue(collection1.contains("test"));
        assertTrue(collection2.contains(15));
        assertTrue(collection2.contains("test"));
    }

    @Test
    void testForEach() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add(15);
        collection.add(13);
        collection.add(0);

        class ProcessorTest implements Processor {
            private int counter;

            @Override
            public void process(Object value) {
                if (value != null && value.hashCode() >= 0) {
                    counter++;
                }
            }
        }

        Processor processor = new ProcessorTest();

        collection.forEach(processor);

        assertEquals(3, ((ProcessorTest) processor).counter);
    }

    @Test
    void testGet() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add(1);
        collection.add(2);
        collection.add(3);
        collection.add(4);
        collection.add(5);

        assertEquals(1, collection.get(0));
        assertEquals(2, collection.get(1));
        assertEquals(3, collection.get(2));
        assertEquals(4, collection.get(3));
        assertEquals(5, collection.get(collection.size() - 1));
    }

    @Test
    void testGetException() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add(5);

        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(collection.size()));
    }

    @Test
    void testIndexOf() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add("test");
        collection.add(14);
        collection.add("string");
        collection.add(7);
        collection.add(16);
        collection.add(7);
        collection.add(36);

        assertEquals(0, collection.indexOf("test"));
        assertEquals(1, collection.indexOf(14));
        assertEquals(3, collection.indexOf(7));
        assertEquals(6, collection.indexOf(36));
        assertEquals(-1, collection.indexOf(3));
        assertEquals(-1, collection.indexOf(null));
    }

    @Test
    void testInsert() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add(15);
        collection.add(17);
        collection.add(32);

        collection.insert("test", 1);
        collection.insert(666, 3);
        collection.insert("first", 0);
        collection.insert("last", collection.size());

        assertEquals(0, collection.indexOf("first"));
        assertEquals(2, collection.indexOf("test"));
        assertEquals(collection.size() - 1, collection.indexOf("last"));

        Object[] testArray = {"first", 15, "test", 17, 666, 32, "last"};

        assertArrayEquals(testArray, collection.toArray());
    }

    @Test
    void testInsertExceptions() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        assertThrows(NullPointerException.class, () -> collection.insert(null, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.insert("test", -5));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.insert("test",
                collection.size() + 1));
    }

    @Test
    void testRemoveAtIndex() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add(3.14);
        collection.add("soonFirst");
        collection.add("mid");
        collection.add("soonLast");
        collection.add(3);

        collection.remove(2);
        collection.remove(0);
        collection.remove(collection.size() - 1);

        Object[] testArray = new Object[2];
        testArray[0] = "soonFirst";
        testArray[1] = "soonLast";

        assertArrayEquals(testArray, collection.toArray());
    }

    @Test
    void testRemoveAtIndexException() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        collection.add(15);
        collection.add(3.14);

        assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(collection.size()));
    }

    @Test
    void testSize() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        assertEquals(0, collection.size());

        collection.add(13);
        collection.add(15);

        assertEquals(2, collection.size());

        collection.remove(0);

        assertEquals(1, collection.size());

        LinkedListIndexedCollection collection2 = new LinkedListIndexedCollection();

        collection2.add(3.14);
        collection2.add("test1");
        collection2.add("mid");
        collection2.add("test2");
        collection2.add(3);

        collection.addAll(collection2);

        assertEquals(6, collection.size());

        collection.remove("mid");

        assertEquals(5, collection.size());
    }
}

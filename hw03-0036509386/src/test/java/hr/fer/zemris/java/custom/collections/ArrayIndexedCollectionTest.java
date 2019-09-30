package hr.fer.zemris.java.custom.collections;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ArrayIndexedCollectionTest {

    @Test
    void testGetter() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

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
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add(15);
        collection.add("test");

        ElementsGetter getter = collection.createElementsGetter();

        getter.getNextElement();
        collection.add("fail");

        assertThrows(ConcurrentModificationException.class, getter::getNextElement);
    }

    // old tests
    @Test
    void testSize() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        assertEquals(0, collection.size());

        collection.add(15);
        collection.add("test");

        assertEquals(2, collection.size());

        collection.remove(Integer.valueOf(15));
        assertEquals(1, collection.size());
    }

    @Test
    void testIsEmpty() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        assertTrue(collection.isEmpty());

        collection.add("test");

        assertFalse(collection.isEmpty());
    }

    @Test
    void testAdd() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(2);

        assertArrayEquals(new Object[0], collection.toArray());

        collection.add(3);
        collection.add("test");
        collection.add(3.14);

        assertTrue(collection.contains(3));
        assertTrue(collection.contains("test"));
        assertTrue(collection.contains(3.14));

        assertEquals(2, collection.indexOf(3.14));

        Object[] testArray = new Object[3];
        testArray[0] = 3;
        testArray[1] = "test";
        testArray[2] = 3.14;

        assertArrayEquals(testArray, collection.toArray());
    }

    @Test
    void testAddException() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        assertThrows(NullPointerException.class, () -> collection.add(null));
    }

    @Test
    void testContains() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        assertFalse(collection.contains(0));

        collection.add(0);
        assertTrue(collection.contains(0));
        assertFalse(collection.contains(null));
    }

    @Test
    void testRemoveObject() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        assertFalse(collection.remove("test"));

        collection.add("test1");
        collection.add("test");
        collection.add("test2");

        assertEquals(2, collection.indexOf("test2"));
        assertTrue(collection.contains("test"));
        assertEquals(1, collection.indexOf("test"));
        assertTrue(collection.remove("test"));
        assertFalse(collection.contains("test"));
        assertEquals(1, collection.indexOf("test2"));
        assertFalse(collection.remove(null));
    }

    @Test
    void testToArray() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        assertArrayEquals(new Object[0], collection.toArray());

        collection.add(15);
        collection.add("test");
        collection.add(3.14);

        Object[] testArray = new Object[3];
        testArray[0] = 15;
        testArray[1] = "test";
        testArray[2] = 3.14;

        assertArrayEquals(testArray, collection.toArray());

        testArray[0] = 7;
        assertFalse(Arrays.equals(testArray, collection.toArray()));
    }

    @Test
    void testForEach() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add(15);
        collection.add(13);
        collection.add(0);

        class ProcessorTest implements Processor {
            private int counter;

            @Override
            public void process(Object value) {
                if (value.hashCode() >= 0) {
                    counter++;
                }
            }
        }

        Processor processor = new ProcessorTest();

        collection.forEach(processor);

        assertEquals(3, ((ProcessorTest) processor).counter);
    }

    @Test
    void testClear() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(6);

        collection.add(15);
        collection.add("test");

        assertEquals(2, collection.size());
        assertTrue(collection.contains(15));
        assertTrue(collection.contains("test"));

        collection.clear();

        assertEquals(0, collection.size());
        assertFalse(collection.contains(15));
        assertFalse(collection.contains("test"));
        assertArrayEquals(new Object[0], collection.toArray());
    }

    @Test
    void testGet() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add("test1");
        collection.add("test");
        collection.add("test1");

        assertEquals("test1", collection.get(0));
        assertEquals("test", collection.get(1));
        assertEquals("test1", collection.get(2));
    }

    @Test
    void testGetException() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add("test1");
        collection.add("test");
        collection.add("test1");

        assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(collection.size()));
    }

    @Test
    void testInsert() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(3);

        collection.add(15);
        collection.add(3);

        collection.insert("test", 1);

        assertEquals(1, collection.indexOf("test"));
        assertEquals(2, collection.indexOf(3));

        collection.insert("test2", 3);

        assertEquals(3, collection.indexOf("test2"));
        assertEquals(2, collection.indexOf(3));

        Object[] testArray = new  Object[4];
        testArray[0] = 15;
        testArray[1] = "test";
        testArray[2] = 3;
        testArray[3] = "test2";

        assertArrayEquals(testArray, collection.toArray());
    }

    @Test
    void testIndexOf() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add("test");
        collection.add(14);

        assertEquals(0, collection.indexOf("test"));
        assertEquals(1, collection.indexOf(14));
        assertEquals(-1, collection.indexOf(3));
        assertEquals(-1, collection.indexOf(null));
    }

    @Test
    void testRemoveAtIndex() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add(15);
        collection.add(17);
        collection.add(19);

        assertTrue(collection.contains(17));
        assertEquals(1, collection.indexOf(17));
        assertEquals(2, collection.indexOf(19));

        collection.remove(1);

        assertFalse(collection.contains(17));
        assertEquals(1, collection.indexOf(19));
    }

    @Test
    void testRemoveAtIndexException() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(-1));

        collection.add("test");
        collection.add(15);

        assertThrows(IndexOutOfBoundsException.class, () -> collection.remove(2));
    }

    @Test
    void testAddAll() {
        ArrayIndexedCollection collection1 = new ArrayIndexedCollection();
        ArrayIndexedCollection collection2 = new ArrayIndexedCollection();

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
    void testAddAllCross() {
        LinkedListIndexedCollection collection1 = new LinkedListIndexedCollection();
        ArrayIndexedCollection collection2 = new ArrayIndexedCollection();

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
    void testDefaultConstructor() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        assertArrayEquals(new Object[0], collection.toArray());
        assertEquals(0, collection.size());
    }

    @Test
    void testCapacityConstructor() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(5);
        assertArrayEquals(new Object[0], collection.toArray());
        assertEquals(0, collection.size());
    }

    @Test
    void testCapacityConstructorException() {
        assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
        assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-5));
    }

    @Test
    void testCopyConstructor() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add(15);
        collection.add(17);
        collection.add(19);

        ArrayIndexedCollection collectionCopy = new ArrayIndexedCollection(collection);

        assertEquals(3, collectionCopy.size());

        Object[] testArray = {15, 17, 19};

        assertArrayEquals(testArray, collectionCopy.toArray());
    }

    @Test
    void testCopyConstructorException() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
    }

    @Test
    void testCopyAndCapacityConstructor() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add(15);
        collection.add("test");

        ArrayIndexedCollection collectionCopy = new ArrayIndexedCollection(collection, 5);

        assertTrue(collectionCopy.contains(15));
        assertTrue(collectionCopy.contains("test"));

        Object[] testArray = new Object[2];
        testArray[0] = 15;
        testArray[1] = "test";

        assertArrayEquals(testArray, collectionCopy.toArray());
        assertEquals(2, collectionCopy.size());
    }

    @Test
    void testCopyAndCapacityWrongSizeConstructor() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        collection.add(15);
        collection.add("test");
        collection.add(3.14);

        ArrayIndexedCollection collectionCopy = new ArrayIndexedCollection(collection, 1);

        assertTrue(collectionCopy.contains(15));
        assertTrue(collectionCopy.contains("test"));
        assertTrue(collectionCopy.contains(3.14));

        Object[] testArray = new Object[3];
        testArray[0] = 15;
        testArray[1] = "test";
        testArray[2] = 3.14;

        assertArrayEquals(testArray, collectionCopy.toArray());
        assertEquals(3, collectionCopy.size());
    }

    @Test
    void testCopyAndCapacityExceptions() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(collection, 0));
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null, 5));
    }
}

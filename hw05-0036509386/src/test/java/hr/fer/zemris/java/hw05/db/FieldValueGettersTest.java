package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldValueGettersTest {

    private StudentRecord record = new StudentRecord("0000000001", "Perić", "Pero", 5);

    @Test
    void testFirstName() {
        assertEquals("Pero", FieldValueGetters.FIRST_NAME.get(record));
    }

    @Test
    void testLastName() {
        assertEquals("Perić", FieldValueGetters.LAST_NAME.get(record));
    }

    @Test
    void testJMBAG() {
        assertEquals("0000000001", FieldValueGetters.JMBAG.get(record));
    }
}

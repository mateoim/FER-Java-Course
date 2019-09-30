package hr.fer.zemris.java.hw06.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void testHextobyte() {
        byte[] expected = {1, -82, 34};

        assertArrayEquals(expected, Util.hextobyte("01aE22"));
    }

    @Test
    void testHextobyteException() {
        assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("05AF6"));
        assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("test"));
    }

    @Test
    void testBytetohex() {
        byte[] array = {1, -82, 34};

        assertEquals("01ae22", Util.bytetohex(array));
    }
}

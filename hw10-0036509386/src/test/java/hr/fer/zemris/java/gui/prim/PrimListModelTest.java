package hr.fer.zemris.java.gui.prim;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrimListModelTest {

    private PrimListModel model;

    @BeforeEach
    void createNewModel() {
        model = new PrimListModel();
    }

    @Test
    void testInitialValue() {
        assertEquals(1, model.getSize());
        assertEquals(1, model.getElementAt(0));

        model.next();

        assertEquals(2, model.getSize());
        assertEquals(1, model.getElementAt(0));
        assertEquals(2, model.getElementAt(1));
    }

    @Test
    void testMultipleIncrements() {
        model.next();
        model.next();
        model.next();

        assertEquals(5, model.getElementAt(3));
        assertEquals(4, model.getSize());
    }
}

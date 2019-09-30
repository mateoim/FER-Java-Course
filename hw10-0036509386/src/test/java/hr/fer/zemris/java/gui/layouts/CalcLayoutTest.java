package hr.fer.zemris.java.gui.layouts;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class CalcLayoutTest {

    @Test
    void testFirstRowException() {
        JPanel panel = new JPanel(new CalcLayout());

        assertThrows(CalcLayoutException.class, () -> panel.add(new Label("test"), new RCPosition(1, 3)));
    }

    @Test
    void testOutOfBounds() {
        JPanel panel = new JPanel(new CalcLayout());

        assertThrows(CalcLayoutException.class, () -> panel.add(new Label("test"), new RCPosition(-2, 0)));
        assertThrows(CalcLayoutException.class, () -> panel.add(new Label("test"), "1,8"));
    }

    @Test
    void testAddToSameLocation() {
        JPanel panel = new JPanel(new CalcLayout());

        panel.add(new Label("test"), new RCPosition(2, 3));
        panel.add(new Button(), "1,6");

        panel.getMaximumSize();

        assertThrows(CalcLayoutException.class, () -> panel.add(new Button("Die"), new RCPosition(2, 3)));
    }

    @Test
    void testFirstDimension() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
        JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
        p.add(l1, new RCPosition(2,2));
        p.add(l2, new RCPosition(3,3));
        Dimension dim = p.getPreferredSize();

        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }

    @Test
    void testSecondDimension() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
        JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
        p.add(l1, new RCPosition(1,1));
        p.add(l2, new RCPosition(3,3));
        Dimension dim = p.getPreferredSize();

        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }
}

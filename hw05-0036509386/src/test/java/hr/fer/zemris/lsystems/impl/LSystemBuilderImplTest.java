package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.LSystem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LSystemBuilderImplTest {

    @Test
    void testGenerateZero() {
        LSystemBuilderImpl builder = new LSystemBuilderImpl();
        builder.setAxiom("F");
        builder.registerProduction('F', "F+F--F+F");

        LSystem system = builder.build();

        assertEquals("F", system.generate(0));
    }

    @Test
    void testGenerateOne() {
        LSystemBuilderImpl builder = new LSystemBuilderImpl();
        builder.setAxiom("F");
        builder.registerProduction('F', "F+F--F+F");

        LSystem system = builder.build();

        assertEquals("F+F--F+F", system.generate(1));
    }

    @Test
    void testGenerateTwo() {
        LSystemBuilderImpl builder = new LSystemBuilderImpl();
        builder.setAxiom("F");
        builder.registerProduction('F', "F+F--F+F");

        LSystem system = builder.build();

        assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", system.generate(2));
    }
}

package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryFilterTest {

    @Test
    void testDirectQuery() {
        StudentRecord student1 = new StudentRecord("0000000003", "Bosnić",
                "Andrea", 4);
        StudentRecord student2 = new StudentRecord("001", "Student", "Wrong", 1);

        QueryParser parser = new QueryParser("jmbag=\"0000000003\"");
        QueryFilter filter = new QueryFilter(parser.getQuery());

        assertTrue(filter.accepts(student1));
        assertFalse(filter.accepts(student2));
    }

    @Test
    void testChainedQueries() {
        QueryParser parser = new QueryParser("jmbag = \"0000000003\" AND lastName LIKE \"Cl*\"");
        QueryFilter filter = new QueryFilter(parser.getQuery());

        StudentRecord Clemenceau = new StudentRecord("0000000003", "Clemenceau",
                "Georges", 5);
        StudentRecord fatherVictory = new StudentRecord("0000000003", "la Victoire",
                "Père", 5);

        assertTrue(filter.accepts(Clemenceau));
        assertFalse(filter.accepts(fatherVictory));
    }
}

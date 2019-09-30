package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryParserTest {

    @Test
    void testDirectQuery() {
        QueryParser parser = new QueryParser(" jmbag       =\"0123456789\"    ");

        assertTrue(parser.isDirectQuery());
        assertEquals("0123456789", parser.getQueriedJMBAG());
        assertEquals(1, parser.getQuery().size());
    }

    @Test
    void testAndQuery() {
        QueryParser parser = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");

        assertFalse(parser.isDirectQuery());
        assertEquals(2, parser.getQuery().size());
        assertEquals("0123456789", parser.getQuery().get(0).getStringLiteral());
        assertEquals("J", parser.getQuery().get(1).getStringLiteral());
        assertEquals(FieldValueGetters.JMBAG, parser.getQuery().get(0).getFieldGetter());
        assertEquals(FieldValueGetters.LAST_NAME, parser.getQuery().get(1).getFieldGetter());
        assertEquals(ComparisonOperators.EQUALS, parser.getQuery().get(0).getComparisonOperator());
        assertEquals(ComparisonOperators.GREATER, parser.getQuery().get(1).getComparisonOperator());

        assertThrows(IllegalStateException.class, parser::getQueriedJMBAG);
    }

    @Test
    void testMultipleAnd() {
        QueryParser parser = new QueryParser("firstName>\"A\" and firstName<\"C\"" +
                " and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");

        assertFalse(parser.isDirectQuery());
        assertEquals(4, parser.getQuery().size());
    }
}

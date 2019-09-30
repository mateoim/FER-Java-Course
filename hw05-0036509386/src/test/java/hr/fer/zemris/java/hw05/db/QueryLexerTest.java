package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryLexerTest {

    @Test
    void testSimpleInput() {
        QueryLexer lexer = new QueryLexer("jmbag=\"xxx\"");

        assertEquals(QueryTokenType.FIELD, lexer.nextToken().getType());
        assertEquals("jmbag", lexer.getToken().getValue());

        assertEquals(QueryTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals("=", lexer.getToken().getValue());

        assertEquals(QueryTokenType.LITERAL, lexer.nextToken().getType());
        assertEquals("xxx", lexer.getToken().getValue());

        assertEquals(QueryTokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    void testInputWithSpaces() {
        QueryLexer lexer = new QueryLexer("   name     >=  \"test\"  ");

        assertEquals(QueryTokenType.FIELD, lexer.nextToken().getType());
        assertEquals("name", lexer.getToken().getValue());

        assertEquals(QueryTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals(">=", lexer.getToken().getValue());

        assertEquals(QueryTokenType.LITERAL, lexer.nextToken().getType());
        assertEquals("test", lexer.getToken().getValue());

        assertEquals(QueryTokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    void testUnclosedQuote() {
        QueryLexer lexer = new QueryLexer("name=\"unclosed");

        lexer.nextToken();
        lexer.nextToken();

        assertThrows(QueryException.class, lexer::nextToken);
    }

    @Test
    void testChainedQuery() {
        QueryLexer lexer = new QueryLexer("firstName>\"A\" and firstName<\"C\" and " +
                "lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");

        assertEquals(QueryTokenType.FIELD, lexer.nextToken().getType());
        assertEquals("firstName", lexer.getToken().getValue());

        assertEquals(QueryTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals(">", lexer.getToken().getValue());

        assertEquals(QueryTokenType.LITERAL, lexer.nextToken().getType());
        assertEquals("A", lexer.getToken().getValue());

        assertEquals(QueryTokenType.AND, lexer.nextToken().getType());

        assertEquals(QueryTokenType.FIELD, lexer.nextToken().getType());
        assertEquals("firstName", lexer.getToken().getValue());

        assertEquals(QueryTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals("<", lexer.getToken().getValue());

        assertEquals(QueryTokenType.LITERAL, lexer.nextToken().getType());
        assertEquals("C", lexer.getToken().getValue());

        assertEquals(QueryTokenType.AND, lexer.nextToken().getType());

        assertEquals(QueryTokenType.FIELD, lexer.nextToken().getType());
        assertEquals("lastName", lexer.getToken().getValue());

        assertEquals(QueryTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals("LIKE", lexer.getToken().getValue());

        assertEquals(QueryTokenType.LITERAL, lexer.nextToken().getType());
        assertEquals("B*ć", lexer.getToken().getValue());

        assertEquals(QueryTokenType.AND, lexer.nextToken().getType());

        assertEquals(QueryTokenType.FIELD, lexer.nextToken().getType());
        assertEquals("jmbag", lexer.getToken().getValue());

        assertEquals(QueryTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals(">", lexer.getToken().getValue());

        assertEquals(QueryTokenType.LITERAL, lexer.nextToken().getType());
        assertEquals("0000000002", lexer.getToken().getValue());

        assertEquals(QueryTokenType.EOF, lexer.nextToken().getType());
    }
}

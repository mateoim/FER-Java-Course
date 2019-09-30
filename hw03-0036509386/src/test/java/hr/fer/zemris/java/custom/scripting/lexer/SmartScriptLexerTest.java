package hr.fer.zemris.java.custom.scripting.lexer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartScriptLexerTest {

    @Test
    void testNullConstructor() {
        assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
    }

    @Test
    void testJustEOF() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        assertNull(lexer.nextToken().getValue());
        assertEquals(SmartScriptTokenType.EOF, lexer.getToken().getType());

        assertThrows(SmartScriptLexerException.class, lexer::nextToken);
    }

    @Test
    void testPureTextInput() {
        String input = " This is \t a test  .";

        SmartScriptLexer lexer = new SmartScriptLexer(input);
        lexer.nextToken();

        assertEquals(input, lexer.getToken().getValue());
    }

    @Test
    void testJustTag() {
        String input = "{$END$}";

        SmartScriptLexer lexer = new SmartScriptLexer(input);

        assertEquals(SmartScriptTokenType.TAG, lexer.nextToken().getType());
        assertEquals("END", lexer.getToken().getValue());
    }

    @Test
    void testTagAndVariable() {
        String input = "{$= i $}";

        SmartScriptLexer lexer = new SmartScriptLexer(input);

        assertEquals(SmartScriptTokenType.TAG, lexer.nextToken().getType());
        assertEquals("=", lexer.getToken().getValue());

        assertEquals(SmartScriptTokenType.VARIABLE, lexer.nextToken().getType());
        assertEquals("i", lexer.getToken().getValue());
    }

    @Test
    void testThreeVariables() {
        String input = "{$= test e125_tsf last$}";

        SmartScriptLexer lexer = new SmartScriptLexer(input);

        assertEquals(SmartScriptTokenType.TAG, lexer.nextToken().getType());
        assertEquals("=", lexer.getToken().getValue());

        assertEquals(SmartScriptTokenType.VARIABLE, lexer.nextToken().getType());
        assertEquals("test", lexer.getToken().getValue());

        assertEquals(SmartScriptTokenType.VARIABLE, lexer.nextToken().getType());
        assertEquals("e125_tsf", lexer.getToken().getValue());

        assertEquals(SmartScriptTokenType.VARIABLE, lexer.nextToken().getType());
        assertEquals("last", lexer.getToken().getValue());

        assertEquals(SmartScriptTokenType.EOT, lexer.nextToken().getType());

        // no more elements
        assertNull(lexer.nextToken().getValue());
        assertEquals(SmartScriptTokenType.EOF, lexer.getToken().getType());

        // trying to get next from empty lexer
        assertThrows(SmartScriptLexerException.class, lexer::nextToken);
    }

    @Test
    void testTwoStrings() {
        String input = "{$ = \"str1\"\"str2\"$}";

        SmartScriptLexer lexer = new SmartScriptLexer(input);

        assertEquals(SmartScriptTokenType.TAG, lexer.nextToken().getType());

        assertEquals(SmartScriptTokenType.STRING, lexer.nextToken().getType());
        assertEquals("str1", lexer.getToken().getValue());

        assertEquals(SmartScriptTokenType.STRING, lexer.nextToken().getType());
        assertEquals("str2", lexer.getToken().getValue());

        assertEquals(SmartScriptTokenType.EOT, lexer.nextToken().getType());

        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    void testEscapeString() {
        String input = "{$= \"Joe \\\"Long\\\" Smith\"$}";

        SmartScriptLexer lexer = new SmartScriptLexer(input);

        assertEquals("=", lexer.nextToken().getValue());
        assertEquals("Joe \"Long\" Smith", lexer.nextToken().getValue());
    }

    @Test
    void testEscapeSlash() {
        String input = "This is a slash: \\\\\n" +
                "{$= \"This is a slash in string \\\\ and this is a \\\"legal\\\" escape {$ END $}\"$}\n";

        SmartScriptLexer lexer = new SmartScriptLexer(input);

        assertEquals("This is a slash: \\\n", lexer.nextToken().getValue());

        lexer.nextToken();

        assertEquals("This is a slash in string \\ and this is a \"legal\" escape {$ END $}",
                lexer.nextToken().getValue());
    }

    @Test
    void testIllegalEscape() {
        String input = "{$= \"Wait that's \\illegal.\"$}";

        SmartScriptLexer lexer = new SmartScriptLexer(input);
        lexer.nextToken();

        assertThrows(SmartScriptLexerException.class, lexer::nextToken);
    }

    @Test
    void testNewlineEscape() {
        String input = "{$=\"3.14\\r\\ntest\\r\\n\" $}";

        SmartScriptLexer lexer = new SmartScriptLexer(input);
        lexer.nextToken();

        assertEquals("3.14\r\ntest\r\n", lexer.nextToken().getValue());
    }

    @Test
    void testTextEscape() {
        String input = "Example { bla } blu \\{$=1$}. Nothing interesting {=here}";

        SmartScriptLexer lexer = new SmartScriptLexer(input);

        assertEquals("Example { bla } blu {$=1$}. Nothing interesting {=here}", lexer.nextToken().getValue());
    }

    @Test
    void testOpenTag() {
        String input = "{$= \"Never closed\"";

        SmartScriptLexer lexer = new SmartScriptLexer(input);

        lexer.nextToken();
        lexer.nextToken();

        assertThrows(SmartScriptLexerException.class, lexer::nextToken);
    }

    @Test
    void testTwoFunctions() {
        String input = "{$= @sin@cos_256  $}";

        SmartScriptLexer lexer = new SmartScriptLexer(input);

        lexer.nextToken();

        assertEquals(SmartScriptTokenType.FUNCTION, lexer.nextToken().getType());
        assertEquals("sin", lexer.getToken().getValue());

        assertEquals(SmartScriptTokenType.FUNCTION, lexer.nextToken().getType());
        assertEquals("cos_256", lexer.getToken().getValue());
    }

    @Test
    void testIllegalFunctionName() {
        String input = "{$= @12test $}";

        SmartScriptLexer lexer = new SmartScriptLexer(input);

        lexer.nextToken();

        assertThrows(SmartScriptLexerException.class, lexer::nextToken);
    }

    @Test
    void testInteger() {
        String input = "{$= 135$}";

        SmartScriptLexer lexer = new SmartScriptLexer(input);

        lexer.nextToken();

        assertEquals(135, lexer.nextToken().getValue());
        assertEquals(SmartScriptTokenType.INTEGER, lexer.getToken().getType());
    }

    @Test
    void testDouble() {
        String input = "{$= 3.14$}";

        SmartScriptLexer lexer = new SmartScriptLexer(input);

        lexer.nextToken();

        assertEquals(3.14, lexer.nextToken().getValue());
        assertEquals(SmartScriptTokenType.DOUBLE, lexer.getToken().getType());
    }

    @Test
    void testTwoNumeric() {
        String input = "{$= 345 15$}";

        SmartScriptLexer lexer = new SmartScriptLexer(input);

        lexer.nextToken();

        assertEquals(345, lexer.nextToken().getValue());
        assertEquals(SmartScriptTokenType.INTEGER, lexer.getToken().getType());
        assertEquals(15, lexer.nextToken().getValue());
        assertEquals(SmartScriptTokenType.INTEGER, lexer.getToken().getType());
    }

    @Test
    void testInvalidDouble() {
        String input = "{$= 543. $}";

        SmartScriptLexer lexer = new SmartScriptLexer(input);

        lexer.nextToken();

        assertEquals(543, lexer.nextToken().getValue());
        assertEquals(SmartScriptTokenType.INTEGER, lexer.getToken().getType());

        assertThrows(SmartScriptLexerException.class, lexer::nextToken);
    }

    @Test
    void testNegativeNumeric() {
        String input = "{$= -345.0 -15$}";

        SmartScriptLexer lexer = new SmartScriptLexer(input);

        lexer.nextToken();

        assertEquals(-345d, lexer.nextToken().getValue());
        assertEquals(SmartScriptTokenType.DOUBLE, lexer.getToken().getType());
        assertEquals(-15, lexer.nextToken().getValue());
        assertEquals(SmartScriptTokenType.INTEGER, lexer.getToken().getType());
    }

    @Test
    void testOperators() {
        String input = "{$= + -abc * / ^$}";

        SmartScriptLexer lexer = new SmartScriptLexer(input);

        lexer.nextToken();

        assertEquals(SmartScriptTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals("+", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals("-", lexer.getToken().getValue());

        lexer.nextToken();

        assertEquals(SmartScriptTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals("*", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals("/", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals("^", lexer.getToken().getValue());

        assertEquals(SmartScriptTokenType.EOT, lexer.nextToken().getType());

        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
        assertNull(lexer.getToken().getValue());
    }
}

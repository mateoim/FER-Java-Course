package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.hw03.SmartScriptTester;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class SmartScriptParserTest {

    @Test
    void testConstructorWithText() {
        SmartScriptParser parser = new SmartScriptParser("Text input.");

        DocumentNode node = parser.getDocumentNode();
        TextNode child = (TextNode) node.getChild(0);

        TextNode text = new TextNode("Text input.");

        assertEquals(1, node.numberOfChildren());
        assertEquals(text.getText(), child.getText());
    }

    @Test
    void testEmptyConstructor() {
        SmartScriptParser parser = new SmartScriptParser("");

        DocumentNode node = parser.getDocumentNode();

        assertEquals(0, node.numberOfChildren());
    }

    @Test
    void testEcho() {
        String input = "This is text and {$ = i $}";

        SmartScriptParser parser = new SmartScriptParser(input);
        DocumentNode node = parser.getDocumentNode();

        assertEquals(2, node.numberOfChildren());
    }

    @Test
    void testForNodeFourArgs() {
        String input = "{$ FOR i -1.35 bbb \"1\" $}{$ END $}";

        SmartScriptParser parser = new SmartScriptParser(input);
        DocumentNode node = parser.getDocumentNode();

        assertEquals(1, node.numberOfChildren());

        ForLoopNode forChild = (ForLoopNode) node.getChild(0);

        ElementVariable elVar = forChild.getVariable();
        assertEquals("i", elVar.asText());

        Element start = forChild.getStartExpression();
        assertEquals("-1.35", start.asText());

        Element end = forChild.getEndExpression();
        assertEquals("bbb", end.asText());

        Element step = forChild.getStepExpression();
        assertEquals("1", step.asText());
    }

    @Test
    void testForNodeThreeArgs() {
        String input = "{$ FoR n 12 32$}{$END$}";

        SmartScriptParser parser = new SmartScriptParser(input);
        DocumentNode node = parser.getDocumentNode();

        ForLoopNode forChild = (ForLoopNode) node.getChild(0);

        ElementVariable elVar = forChild.getVariable();
        assertEquals("n", elVar.asText());

        Element start = forChild.getStartExpression();
        assertEquals("12", start.asText());

        Element end = forChild.getEndExpression();
        assertEquals("32", end.asText());

        assertNull(forChild.getStepExpression());
    }

    @Test
    void testInvalidElementFor() {
        String input = "{$ FOR var @sin 4$}";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(input));
    }

    @Test
    void testForTooManyArgs() {
        String input = "{$ for var 43 var2 76 fail$}";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(input));
    }

    @Test
    void testForNotEnoughArgs() {
        String input = "{$ fOr var \"onlyOne\"$}";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(input));
    }

    @Test
    void testTwoTagsAndText() {
        String input = "{$= i$}\ntext\n{$= 15 * i$}";
        SmartScriptParser parser = new SmartScriptParser(input);

        DocumentNode node = parser.getDocumentNode();

        assertEquals(3, node.numberOfChildren());

        TextNode midChild = (TextNode) node.getChild(1);
        assertEquals("\ntext\n", midChild.getText());
    }

    @Test
    void testTooManyEnds() {
        String input = "{$ FOR i 4 5 7 $} {$ END $} {$ END $}";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(input));
    }

    @Test
    void testUnclosedFor() {
        String input = "{$ for test 6 3 step $}";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(input));
    }

    @Test
    void testFile() {
        String input = loader("doc1.txt");

        SmartScriptParser parser = new SmartScriptParser(input);

        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();

        assertEquals(originalDocumentBody, SmartScriptTester.createOriginalDocumentBody(document2));
    }

    @Test
    void testQuote() {
        String input = loader("quote.txt");

        SmartScriptParser parser = new SmartScriptParser(input);

        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();

        assertEquals(originalDocumentBody, SmartScriptTester.createOriginalDocumentBody(document2));
    }

    @Test
    void testEscape() {
        String input = loader("escapeText.txt");

        SmartScriptParser parser = new SmartScriptParser(input);

        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();

        assertEquals(originalDocumentBody, SmartScriptTester.createOriginalDocumentBody(document2));
    }

    // tests for code used in later homework in previous AY
    @Test
    void testOsnovni() {
        String input = loader("osnovni.smscr");

        SmartScriptParser parser = new SmartScriptParser(input);

        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();

        assertEquals(originalDocumentBody, SmartScriptTester.createOriginalDocumentBody(document2));
    }

    @Test
    void testZbrajanje() {
        String input = loader("zbrajanje.smscr");

        SmartScriptParser parser = new SmartScriptParser(input);

        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();

        assertEquals(originalDocumentBody, SmartScriptTester.createOriginalDocumentBody(document2));

    }

    @Test
    void testBrojPoziva() {
        String input = loader("brojPoziva.smscr");

        SmartScriptParser parser = new SmartScriptParser(input);

        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();

        assertEquals(originalDocumentBody, SmartScriptTester.createOriginalDocumentBody(document2));
    }

    @Test
    void testFibonacci() {
        String input = loader("fibonacci.smscr");

        SmartScriptParser parser = new SmartScriptParser(input);

        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();

        assertEquals(originalDocumentBody, SmartScriptTester.createOriginalDocumentBody(document2));
    }

    @Test
    void testFibonacciHTML() {
        String input = loader("fibonaccih.smscr");

        SmartScriptParser parser = new SmartScriptParser(input);

        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();

        assertEquals(originalDocumentBody, SmartScriptTester.createOriginalDocumentBody(document2));
    }

    private String loader(String filename) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try( InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
            byte[] buffer = new byte[1024];
            while(true) {
                int read = is.read(buffer);
                if(read<1) break;
                bos.write(buffer, 0, read);
            }

            return new String(bos.toByteArray(), StandardCharsets.UTF_8);
        } catch(IOException ex) {
            return null;
        }
    }
}

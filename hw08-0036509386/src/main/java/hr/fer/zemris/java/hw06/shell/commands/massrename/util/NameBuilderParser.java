package hr.fer.zemris.java.hw06.shell.commands.massrename.util;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a parser
 * used for {@code MassrenameShellCommand}.
 *
 * @author Mateo Imbrišak
 */

public class NameBuilderParser {

    /**
     * Keeps the lexer used by this parser.
     */
    private NameBuilderLexer lexer;

    /**
     * Keeps all {@code NameBuilder}s generated so far.
     */
    private List<NameBuilder> builderList;

    /**
     * Default constructor that initializes the
     * lexer based on given input and initializes
     * the {@code List} used to store {@code NameBuilder}s.
     *
     * @param input to be parsed.
     */
    public NameBuilderParser(String input) {
        lexer = new NameBuilderLexer(input);
        builderList = new ArrayList<>();

        parseNext();
    }

    /**
     * Generates an {@code NameBuilder} containing all
     * {@code NameBuilder}s generated so far.
     * When executed it executes all stored {@code NameBuilder}s.
     *
     * @return the main {@code NameBuilder}.
     */
    public NameBuilder getNameBuilder() {
        return (result, sb) -> {
            for (NameBuilder builder : builderList) {
                builder.execute(result, sb);
            }
        };
    }

    /**
     * Parses the input recursively until an {@code EOF}
     * type token is received.
     */
    private void parseNext() {
        NameBuilderToken token = lexer.nextToken();

        switch (token.getType()) {
            case STRING:
                builderList.add(text(token.getValue()));
                parseNext();
                break;
            case TAG:
                if (token.getValue().contains(",")) {
                    String[] parts = token.getValue().split(",");
                    parts[0] = parts[0].trim();
                    parts[1] = parts[1].trim();

                    try {
                        if (parts[1].charAt(0) == '0' && parts[1].length() > 1) {
                            builderList.add(group(Integer.parseInt(parts[0]), parts[1].charAt(0),
                                    Integer.parseInt(parts[1].substring(1))));
                        } else {
                            builderList.add(group(Integer.parseInt(parts[0].trim()), ' ',
                                    Integer.parseInt(parts[1].trim())));
                        }
                    } catch (NumberFormatException exc) {
                        throw new RuntimeException("Tag contains invalid characters.");
                    }
                } else {
                    try {
                        builderList.add(group(Integer.parseInt(token.getValue().trim())));
                    } catch (NumberFormatException exc) {
                        throw new RuntimeException();
                    }
                }

                parseNext();
                break;
            case EOF:
                break;
        }
    }

    /**
     * Creates a {@code NameBuilder} that adds
     * given text to a {@code StringBuilder}.
     *
     * @param t text to be added.
     *
     * @return a new {@code NameBuilder}.
     */
    private static NameBuilder text(String t) {
        return (result, sb) -> sb.append(t);
    }

    /**
     * Creates a {@code NameBuilder} that adds
     * given group form the {@code FilterResult}
     * to a {@code StringBuilder}.
     *
     * @param index of the group to be added.
     *
     * @return a new {@code NameBuilder}.
     */
    private static NameBuilder group(int index) {
        return (result, sb) -> sb.append(result.group(index));
    }

    /**
     * Creates a {@code NameBuilder} that adds
     * given group form the {@code FilterResult}
     * to a {@code StringBuilder} with the given
     * padding with combined length equal to or
     * greater than {@code minWidth}.
     *
     * @param index of the group to be added.
     * @param padding used to fill the minimum width.
     * @param minWidth of the text being added.
     *
     * @return a new {@code NameBuilder}.
     */
    private static NameBuilder group(int index, char padding, int minWidth) {
        return (result, sb) -> sb.append(
                Character.toString(padding).repeat((minWidth - result.group(index).length()) < 0
                        ? 0 : (minWidth - result.group(index).length()))
        ).append(result.group(index));
    }
}

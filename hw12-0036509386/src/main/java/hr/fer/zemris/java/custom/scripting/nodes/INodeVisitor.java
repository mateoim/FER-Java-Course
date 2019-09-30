package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * An interface that defines a visitor
 * for all {@link Node} type objects.
 *
 * @author Mateo Imbrišak
 */

public interface INodeVisitor {

    /**
     * Used for {@link TextNode}.
     *
     * @param node being visited.
     */
    void visitTextNode(TextNode node);

    /**
     * Used for {@link ForLoopNode}.
     *
     * @param node being visited.
     */
    void visitForLoopNode(ForLoopNode node);

    /**
     * Used for {@link EchoNode}.
     *
     * @param node being visited.
     */
    void visitEchoNode(EchoNode node);

    /**
     * Used for {@link DocumentNode}.
     *
     * @param node being visited.
     */
    void visitDocumentNode(DocumentNode node);
}

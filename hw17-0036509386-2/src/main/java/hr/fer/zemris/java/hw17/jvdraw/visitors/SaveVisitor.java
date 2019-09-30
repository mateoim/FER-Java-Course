package hr.fer.zemris.java.hw17.jvdraw.visitors;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * A {@link GeometricalObjectVisitor} used for saving.
 *
 * @author Mateo Imbrišak
 */

public class SaveVisitor implements GeometricalObjectVisitor {

    /**
     * Keeps the text used to save the file.
     */
    private String saveText = "";

    @Override
    public void visit(Line line) {
        saveText += line.export();
    }

    @Override
    public void visit(Circle circle) {
        saveText += circle.export();
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        saveText += filledCircle.export();
    }

    /**
     * Provides the {@link #saveText} after visiting all objects.
     *
     * @return {@link #saveText}.
     */
    public String getSaveText() {
        return saveText;
    }
}

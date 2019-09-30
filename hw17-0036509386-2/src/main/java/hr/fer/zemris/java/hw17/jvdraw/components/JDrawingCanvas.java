package hr.fer.zemris.java.hw17.jvdraw.components;

import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * A {@link JComponent} used for drawing simple shapes.
 *
 * @author Mateo Imbrišak
 */

public class JDrawingCanvas extends JComponent implements DrawingModelListener {

    /**
     * Keeps the currently selected {@link Tool}.
     */
    private Tool tool;

    /**
     * Keeps the {@link DrawingModel}.
     */
    private DrawingModel model;

    /**
     * Default constructor that assigns all values and
     * adds {@link MouseAdapter}s.
     *
     * @param drawingModel to be used.
     * @param tool to be assigned.
     */
    public JDrawingCanvas(DrawingModel drawingModel, Tool tool) {
        this.model = drawingModel;
        this.tool = tool;

        model.addDrawingModelListener(this);

        addMouseMotionListener(new MouseMotionListener() { // same methods in MouseAdapter don't work
            @Override
            public void mouseDragged(MouseEvent e) {
                JDrawingCanvas.this.tool.mouseMoved(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                JDrawingCanvas.this.tool.mouseMoved(e);
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JDrawingCanvas.this.tool.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                JDrawingCanvas.this.tool.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                JDrawingCanvas.this.tool.mouseReleased(e);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);

        for (int i = 0, size = model.getSize(); i < size; i++) {
            model.getObject(i).accept(painter);
        }

        tool.paint(g2d);
    }

    /**
     * Used when this object is notified of a change.
     */
    private void forward() {
        repaint();
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        forward();
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        forward();
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        forward();
    }

    /**
     * Sets the {@link #tool} to the given one.
     *
     * @param tool to be assigned.
     */
    public void setTool(Tool tool) {
        this.tool = tool;
    }
}

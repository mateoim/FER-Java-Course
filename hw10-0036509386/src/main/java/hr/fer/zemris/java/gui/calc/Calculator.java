package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.calc.model.FunctionalCalcButton;
import hr.fer.zemris.java.gui.calc.model.NumericCalcButton;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

/**
 * A class that represents a simple calculator GUI.
 *
 * @author Mateo Imbrišak
 */

public class Calculator extends JFrame {
    private static final long serialVersionUID = 1L;

    /**
     * Functional implementation of a calculator.
     */
    private CalcModelImpl calc;

    /**
     * Keeps the stack.
     */
    private Stack<Double> stack;

    /**
     * Keeps the display.
     */
    private JLabel display;

    /**
     * Default constructor that initializes the window
     * and calls {@link #initGUI()}.
     */
    public Calculator() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        calc = new CalcModelImpl();
        stack = new Stack<>();
        try { // only way to get button colors to show properly on mac
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}
        initGUI();
        setTitle("Java Calculator v1.0");
        setSize(600, 300);
        setVisible(true);
    }

    /**
     * Used internally to initialize the display
     * and buttons.
     */
    private void initGUI() {
        getContentPane().setLayout(new CalcLayout(3));

        display = initDisplay();

        JCheckBox inv = new JCheckBox("inv");

        getContentPane().add(inv, "5,7");

        // last row
        getContentPane().add(new FunctionalCalcButton("clr", (e) -> calc.clear()), "1,7");
        getContentPane().add(new FunctionalCalcButton("reset", (e) -> calc.clearAll()), "2,7");
        getContentPane().add(new FunctionalCalcButton("push", (e) -> stack.push(calc.getValue())), "3,7");
        getContentPane().add(new FunctionalCalcButton("pop", (e) -> {
            if (!stack.empty()) {
                calc.setValue(stack.pop());
            } else {
                printError();
            }
        }), "4,7");

        // second row
        FunctionalCalcButton sin = new FunctionalCalcButton("sin", (e) -> {
            if (!inv.isSelected()) {
                calc.setValue(Math.sin(calc.getValue()));
            } else {
                calc.setValue(Math.asin(calc.getValue()));
            }
        });

        inv.addActionListener((e) -> sin.setText(!inv.isSelected() ? "sin" : "arcsin"));
        getContentPane().add(sin, "2,2");

        FunctionalCalcButton cos = new FunctionalCalcButton("cos", (e) -> {
            if (!inv.isSelected()) {
                calc.setValue(Math.cos(calc.getValue()));
            } else {
                calc.setValue(Math.acos(calc.getValue()));
            }
        });

        inv.addActionListener((e) -> cos.setText(!inv.isSelected() ? "cos" : "arccos"));
        getContentPane().add(cos, "3,2");

        FunctionalCalcButton tan = new FunctionalCalcButton("tan", (e) -> {
            if (!inv.isSelected()) {
                calc.setValue(Math.tan(calc.getValue()));
            } else {
                calc.setValue(Math.atan(calc.getValue()));
            }
        });

        inv.addActionListener((e) -> tan.setText(!inv.isSelected() ? "tan" : "arctan"));
        getContentPane().add(tan, "4,2");

        FunctionalCalcButton ctg = new FunctionalCalcButton("ctg", (e) -> {
            if (!inv.isSelected()) {
                calc.setValue(1 / Math.tan(calc.getValue()));
            } else {
                calc.setValue((Math.PI / 2) - Math.atan(calc.getValue()));
            }
        });

        inv.addActionListener((e) -> ctg.setText(!inv.isSelected() ? "ctg" : "arcctg"));
        getContentPane().add(ctg, "5,2");

        getContentPane().add(new FunctionalCalcButton("1/x", (e) -> calc.setValue(1 / calc.getValue())),
                "2,1");

        FunctionalCalcButton log = new FunctionalCalcButton("log", (e) -> {
            if (!inv.isSelected()) {
                calc.setValue(Math.log10(calc.getValue()));
            } else {
                calc.setValue(Math.pow(10, calc.getValue()));
            }
        });

        // first row
        inv.addActionListener((e) -> log.setText(!inv.isSelected() ? "log" : "10^x"));
        getContentPane().add(log, "3,1");

        FunctionalCalcButton ln = new FunctionalCalcButton("ln", (e) -> {
            if (!inv.isSelected()) {
                calc.setValue(Math.log(calc.getValue()));
            } else {
                calc.setValue(Math.exp(calc.getValue()));
            }
        });

        inv.addActionListener((e) -> ln.setText(!inv.isSelected() ? "ln" : "e^x"));
        getContentPane().add(ln, "4,1");

        FunctionalCalcButton xn = new FunctionalCalcButton("x^n", (e) -> {
            executePending();
            if (!inv.isSelected()) {
                calc.setPendingBinaryOperation(Math::pow);
            } else {
                calc.setPendingBinaryOperation((x, n) -> Math.pow(x, 1/n));
            }
            calc.setActiveOperand(calc.getValue());
            calc.softClear();
        });

        inv.addActionListener((e) -> xn.setText(!inv.isSelected() ? "x^n" : "x^(1/n)"));
        getContentPane().add(xn, "5,1");

        // numbers
        getContentPane().add(createButton(7), "2,3");
        getContentPane().add(createButton(8), "2,4");
        getContentPane().add(createButton(9), "2,5");
        getContentPane().add(createButton(4), "3,3");
        getContentPane().add(createButton(5), "3,4");
        getContentPane().add(createButton(6), "3,5");
        getContentPane().add(createButton(1), "4,3");
        getContentPane().add(createButton(2), "4,4");
        getContentPane().add(createButton(3), "4,5");
        getContentPane().add(createButton(0), "5,3");

        getContentPane().add(new FunctionalCalcButton(".", (e) -> {
            try {
                calc.insertDecimalPoint();
            } catch (CalculatorInputException exc) {
                printError();
            }
        }), "5,5");
        getContentPane().add(new FunctionalCalcButton("+/-", (e) -> {
            try {
                calc.swapSign();
            } catch (CalculatorInputException exc) {
                printError();
            }
        }), "5,4");

        // sixth row
        getContentPane().add(new FunctionalCalcButton("=", (e) -> executePending()), "1,6");
        getContentPane().add(createOperationButton("/", (first, second) -> first / second), "2,6");
        getContentPane().add(createOperationButton("*", (first, second) -> first * second), "3,6");
        getContentPane().add(createOperationButton("-", (first, second) -> first - second), "4,6");
        getContentPane().add(createOperationButton("+", Double::sum), "5,6");
    }

    /**
     * Used internally to initialize the display.
     */
    private JLabel initDisplay() {
        JLabel display = new JLabel("0");
        display.setBackground(Color.YELLOW);
        display.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        display.setOpaque(true);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        calc.addCalcValueListener(model -> display.setText(model.toString()));
        getContentPane().add(display, "1,1");
        display.setFont(display.getFont().deriveFont(30f));

        return display;
    }

    /**
     * Prints an error message to the calculator's display.
     */
    private void printError() {
        calc.clearAll();
        display.setText("Error");
    }

    /**
     * Creates a button that inserts the given number.
     *
     * @param value to be inserted.
     *
     * @return a new {@code NumericButton} that inserts
     * the given value.
     */
    private NumericCalcButton createButton(int value) {
        NumericCalcButton button = new NumericCalcButton(value, (e) -> {
            try {
                calc.insertDigit(value);
            } catch (CalculatorInputException exc) {
                printError();
            }
        });

        button.setFont(button.getFont().deriveFont(30f));

        return button;
    }

    /**
     * Used internally to create a button that performs a simple function.
     *
     * @param name of the button.
     * @param function to be executed by the button.
     *
     * @return a new {@code FunctionalCalcButton} that performs
     * the given function.
     */
    private FunctionalCalcButton createOperationButton(String name, DoubleBinaryOperator function) {
        return new FunctionalCalcButton(name, (e) -> {
            executePending();
            calc.setPendingBinaryOperation(function);
            calc.setActiveOperand(calc.getValue());
            calc.softClear();
        });
    }

    /**
     * Used internally to execute pending operation
     * if it exists and if an operand is active.
     */
    private void executePending() {
        if (calc.getPendingBinaryOperation() != null && calc.isActiveOperandSet()) {
            calc.setValue(calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue()));
            calc.clearActiveOperand();
        }
    }

    /**
     * Used to start the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}

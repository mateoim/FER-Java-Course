package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

import java.util.LinkedList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * An implementation of {@code CalcModel}.
 *
 * @author Mateo Imbrišak
 */

public class CalcModelImpl implements CalcModel {

    /**
     * Used to check if the calculator is editable.
     */
    private boolean editable;

    /**
     * Used to check if the current input is negative.
     */
    private boolean isNegative;

    /**
     * Keeps the current input.
     */
    private String input;

    /**
     * Keeps the current input as a number.
     */
    private double numericInput;

    /**
     * Keeps active listeners.
     */
    private List<CalcValueListener> listeners;

    /**
     * Keeps track of active operand.
     */
    private double activeOperand;

    /**
     * Used to check if an operand is active.
     */
    private boolean activeOperandSet;

    /**
     * Keeps track of pending operation.
     */
    private DoubleBinaryOperator pendingOperation;

    /**
     * Default constructor that initializes all values.
     */
    public CalcModelImpl() {
        this.editable = true;
        this.isNegative = false;
        this.input = "";
        this.numericInput = 0;
        this.activeOperandSet = false;
        this.listeners = new LinkedList<>();
    }

    @Override
    public void addCalcValueListener(CalcValueListener l) {
        listeners.add(l);
    }

    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        listeners.remove(l);
    }

    @Override
    public double getValue() {
        return numericInput;
    }

    @Override
    public void setValue(double value) {
        numericInput = value;
        input = String.valueOf(value);
        editable = false;

        listeners.forEach((l) -> l.valueChanged(this));
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public void clear() {
        softClear();

        listeners.forEach((l) -> l.valueChanged(this));
    }

    @Override
    public void clearAll() {
        numericInput = 0;
        input = "";
        activeOperandSet = false;
        pendingOperation = null;
        editable = true;
        isNegative = false;

        listeners.forEach((l) -> l.valueChanged(this));
    }

    @Override
    public void swapSign() throws CalculatorInputException {
        checkEditable();

        isNegative = !isNegative;
        numericInput *= -1;

        input = input.startsWith("-") ? input.substring(1) : "-" + input;

        listeners.forEach((l) -> l.valueChanged(this));
    }

    @Override
    public void insertDecimalPoint() throws CalculatorInputException {
        checkEditable();

        if (!input.contains(".") && input.matches("-?[0-9]+")) {
            input += ".";
            listeners.forEach((l) -> l.valueChanged(this));
        } else {
            throw new CalculatorInputException("Input already contains \".\"");
        }
    }

    @Override
    public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
        checkEditable();

        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Invalid digit.");
        }

        try {
            double newInput = Double.parseDouble(input + digit);
            if (Double.isFinite(newInput)) {
                numericInput = newInput;
                input += digit;

                listeners.forEach((l) -> l.valueChanged(this));
            } else {
                throw new CalculatorInputException("Resulting number is not finite.");
            }
        } catch (NumberFormatException exc) {
            throw new CalculatorInputException("Attempting to add invalid digit.");
        }
    }

    @Override
    public boolean isActiveOperandSet() {
        return activeOperandSet;
    }

    @Override
    public double getActiveOperand() throws IllegalStateException {
        if (isActiveOperandSet()) {
            return activeOperand;
        } else {
            throw new IllegalStateException("Operand is not set.");
        }
    }

    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = activeOperand;
        activeOperandSet = true;
    }

    @Override
    public void clearActiveOperand() {
        activeOperandSet = false;
    }

    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return pendingOperation;
    }

    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        pendingOperation = op;
        editable = true;
    }

    @Override
    public String toString() {
        if (input.isEmpty() || input.equals("-")) {
            return isNegative ? "-0" : "0";
        } else {
            String ret = input.startsWith("-") ? input.substring(1) : input;

            while (ret.startsWith("00")) {
                ret = ret.substring(1);
            }

            if (ret.length() != 1 && ret.charAt(0) == '0' && !ret.startsWith("0.")) {
                ret = ret.substring(1);
            }

            return input.startsWith("-") ? "-" + ret : ret;
        }
    }

    /**
     * Used internally to check if the calculator is editable.
     *
     * @throws CalculatorInputException if it is not editable.
     */
    private void checkEditable() {
        if (!isEditable()) {
            throw new CalculatorInputException("Calculator is not editable.");
        }
    }

    /**
     * Clears the input without notifying the observers.
     */
    void softClear() {
        numericInput = 0;
        input = "";
        editable = true;
        isNegative = false;
    }
}

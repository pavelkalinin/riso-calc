package xyz.enhorse.controls;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by PAK on 08.04.2014.
 */
public class NumberField extends JTextField {
    private static double DEFAULT_VALUE = 0;
    private Number defaultValue;
    private boolean intOnly = false;

    public NumberField(Number defaultValue, boolean intOnly) {
        this.setDefaultValue(defaultValue);
        this.intOnly = intOnly;
        this.setHorizontalAlignment(RIGHT);
        this.addKeyListener(new processInput());
        this.addFocusListener(new processFocus());
    }

    public NumberField() {
        this(DEFAULT_VALUE, false);
    }

    public Number getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue (Number defaultValue) {
        this.defaultValue = defaultValue;
        if (defaultValue instanceof Long) {
            this.setIntValue((long) defaultValue);
        } else {
            this.setFloatValue((double) defaultValue);
        }
    }

    public boolean isIntOnly() {
        return intOnly;
    }

    public long getIntValue() {
        long result = 0;
        try {
            result = Long.parseLong(this.getText());
        }
        catch (NumberFormatException e) {
            System.out.println("Error parsing double in" + this.getClass().toString());
        }
        return result;
    }

    public double getFloatValue() {
        double result = 0;
        try {
            result = Double.parseDouble(this.getText());
        }
        catch (NumberFormatException e) {
            System.out.println("Error parsing double in" + this.getClass().toString());
        }
        return result;
    }

    public void setFloatValue(double value) {
        String number = String.format("%.2f", value);
        this.setText(number.replace(",","."));
    }

    public void setIntValue(long value) {
        this.setText(String.format("%d", value));
    }

    public void clear() {
        setDefaultValue(this.getDefaultValue());
    }



    private class processInput implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();

            if ((c >= '0') && (c <= '9')) {
                return;
            }

            if ( !intOnly && ((c == '.') || (c == ','))) {
                if (c == 44) {
                    e.setKeyChar('.');
                }
                return;
            }

            if (c == KeyEvent.VK_ESCAPE) {
                setText(String.valueOf(defaultValue));
                selectAll();
            }
            e.consume();
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    private class processFocus implements  FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            selectAll();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    selectAll();
                }
            });
        }

        @Override
        public void focusLost(FocusEvent e) {
            String value = getText();
//            value = value.replaceAll(".", "");

            if (value.isEmpty()) {
                setText(String.valueOf(defaultValue));
            }
            selectAll();
        }
    }
}

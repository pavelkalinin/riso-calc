package xyz.enhorse.controls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by PAK on 23.04.2014.
 */
public class ResultsPanel extends JPanel {
    private static final double DEFAULT_VALUE = 0D;
    private static final LayoutManager DEFAULT_LAYOUT = new GridLayout(4, 1);
    private static final String TITLE = "РАСЧЕТ";

    private NumberField total;
    private NumberField discount;
    private NumberField paper;
    private NumberField printing;

    public ResultsPanel() {
        super(DEFAULT_LAYOUT);
        setBorder(BorderFactory.createTitledBorder(TITLE));

        total = new NumberField(DEFAULT_VALUE, false);
        total.setFocusable(false);

        paper = new NumberField(DEFAULT_VALUE, false);
        paper.setFocusable(false);

        printing = new NumberField(DEFAULT_VALUE, false);
        printing.setFocusable(false);

        discount = new NumberField(DEFAULT_VALUE, false);
        discount.addActionListener(new DiscountActionListener());
        discount.addFocusListener(new DiscountFocusListener());

        add(Labeled.with("Итого", total, Alignment.TOP));
        add(Labeled.with("Скидка, %", discount, Alignment.TOP));
        add(Labeled.with("Печать", printing, Alignment.TOP));
        add(Labeled.with("Бумага", paper, Alignment.TOP));
    }

    public void setPaper(double value) {
        paper.setFloatValue(value);
        validateTotal();
    }

    public void setPrinting(double value) {
        printing.setFloatValue(value);
        validateTotal();
    }

    public void clear() {
        paper.clear();
        printing.clear();
        total.clear();
    }

    private void validateTotal() {
        double cost = (paper.getFloatValue() + printing.getFloatValue());
        cost -= cost * discount.getFloatValue() / 100  ;
        total.setFloatValue(cost);
    }


    private void validateDiscount() {
        double newValue = discount.getFloatValue();
        newValue = newValue > 100 ? 100D : newValue;
        discount.setFloatValue(newValue);
        validateTotal();
    }

    private class DiscountActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            validateDiscount();
        }
    }

    private class DiscountFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
        }

        @Override
        public void focusLost(FocusEvent e) {
            validateDiscount();
        }
    }

}

package xyz.enhorse.controls;

import xyz.enhorse.datatypes.PriceList;
import xyz.enhorse.forms.InkEditor;
import xyz.enhorse.forms.PaperEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by PAK on 23.04.2014.
 */
public class EditorsPanel extends JPanel {
    private static final LayoutManager DEFAULT_LAYOUT = new GridLayout(1, 2);
    private static final String TITLE = "РЕДАКТОР ЦЕН";
    private static final String PAPER_EDITOR = "Бумага";
    private static final String INK_EDITOR = "Печать";

    private JButton paper;
    private JButton ink;
    private PriceList price;

    public EditorsPanel(PriceList priceList) {
        super(DEFAULT_LAYOUT);
        setBorder(BorderFactory.createTitledBorder(TITLE));

        price = priceList;
        paper = new JButton(PAPER_EDITOR);
        paper.addActionListener(new paperAction());
        ink = new JButton(INK_EDITOR);
        ink.addActionListener(new inkAction());

        add(paper);
        add(ink);
    }

    class inkAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new InkEditor(price);
            price.update();
        }
    }

    class paperAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new PaperEditor(price);
            price.update();
        }
    }

}

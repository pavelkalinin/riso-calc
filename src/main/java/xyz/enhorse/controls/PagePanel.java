package xyz.enhorse.controls;

import xyz.enhorse.datatypes.Inks;
import xyz.enhorse.datatypes.Page;
import xyz.enhorse.datatypes.Paper;
import xyz.enhorse.datatypes.Side;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by PAK on 11.04.2014.
 */
public class PagePanel extends JPanel  {
    private static final String TITLE = "СТРАНИЦА";

    private ColoredToggleButtonPanel frontSide;
    private ColoredToggleButtonPanel backSide;
    private JComboBox<Paper> paper;
    private NumberField quantity;

    private Set<Paper> papers;

    public PagePanel(Set<Paper> papers, Set<Inks> inks) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder(TITLE));

        this.papers = new HashSet<>(papers);
        this.frontSide = new ColoredToggleButtonPanel(Inks.toColors(inks));
        this.backSide = new ColoredToggleButtonPanel(Inks.toColors(inks));
        this.quantity = new NumberField(0L,  true);
        setPapers(papers);

        JPanel sides = new JPanel(new GridLayout(2, 1));
        sides.setBorder(BorderFactory.createTitledBorder("Чернила"));
        sides.add(Labeled.with("Лицо", this.frontSide, Alignment.TOP));
        sides.add(Labeled.with("Оборот", this.backSide, Alignment.TOP));

        add(Labeled.with("Количество", this.quantity, Alignment.TOP));
        add(Labeled.with("Бумага", this.paper, Alignment.TOP));
        add(sides);
    }

    public PagePanel(Set<Paper> papers) {
        this(papers, new HashSet<>(Arrays.asList(Inks.RISO_COLORS)));
    }

    public Page getPage() {
        Page result = new Page
                (
                this.papers.toArray(new Paper[0])[this.paper.getSelectedIndex()],
                new Side(Inks.fromColors(this.frontSide.getSelectedColors().toArray(new Color[0]))),
                new Side(Inks.fromColors(this.backSide.getSelectedColors().toArray(new Color[0])))
                );
        result.setQuantity(this.getQuantity());
        return result;
    }

    public void setPapers(Set<Paper> papers) {
        if (this.paper == null) {
            this.paper = new JComboBox<>();
        } else {
            this.paper.removeAllItems();
            this.papers.clear();
        }

        for (Paper paper : papers) {
            this.paper.addItem(paper);
            this.papers.add(paper);
        }

    }

    public long getQuantity() {
        return this.quantity.getIntValue();
    }

}

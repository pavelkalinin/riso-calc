package xyz.enhorse.forms;

import xyz.enhorse.controls.*;
import xyz.enhorse.datatypes.Circulation;
import xyz.enhorse.datatypes.Page;
import xyz.enhorse.datatypes.PriceList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * Created by PAK on 03.04.2014.
 */

public class Main extends JFrame implements Observer{
    private static final String PRICE_FILE = "price.xml";

    private PriceList priceList;
    private List<Page> pages = new ArrayList();
    private PageTable pageTable = new PageTable();
    private JScrollPane scroll = new JScrollPane();

    private JPanel buttonsPanel;
    private JButton addPageButton;

    private PagePanel pagePanel;
    private ResultsPanel results;
    private EditorsPanel editors;
    private JPanel mainPanel;

    public Main(){
        super("RISO Калькулятор");

        ClassLoader cl = this.getClass().getClassLoader();
        ImageIcon icon  = new ImageIcon(cl.getResource("icon.png"));
        setIconImage(icon.getImage());

        priceList = new PriceList(PRICE_FILE);
        priceList.addObserver(this);

        pagePanel = new PagePanel(priceList.getAvailablePapers());
        scroll.setViewportView(pageTable);
        pageTable.addMouseListener(new PageMouseAdapter());

        addPageButton = new JButton("Добавить страницу в тираж");
        addPageButton.addActionListener(new addButtonAction());
        buttonsPanel = new JPanel(new GridLayout(1,1));
        buttonsPanel.add(addPageButton);

        results = new ResultsPanel();
        editors = new EditorsPanel(priceList);

        mainPanel = new JPanel();
        mainPanel.add(pagePanel);
        mainPanel.add(buttonsPanel);
        mainPanel.add(Labeled.with("ТИРАЖ", scroll, Alignment.TOP));
        mainPanel.add(results);
        mainPanel.add(editors);

        BoxLayout bl = new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS);
        mainPanel.setLayout(bl);

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        putToScreenCenter();
        setVisible(true);
    }

    private void putToScreenCenter(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = getSize().width;
        int h = getSize().height;
        setLocation((dim.width - w) / 2, (dim.height - h) / 2);
    }

    public void calculate() {
        Circulation c = new Circulation(pages);
        double paper = c.costPaper(priceList);
        double printing = c.costPrinting(priceList);
        results.setPaper(paper);
        results.setPrinting(printing);
    }

    @Override
    public void update(Observable o, Object arg) {
        pagePanel.setPapers(priceList.getAvailablePapers());
    }

    private class addButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            results.clear();
            Page p = pagePanel.getPage();
            pages.add(p);
            pageTable.addPage(p);
            calculate();
        }
    }

    private class PageMouseAdapter extends MouseAdapter {
        public void mouseClicked(MouseEvent e)
        {
            int index = pageTable.rowAtPoint(e.getPoint());
            if (e.getClickCount() == 2 && index <= pages.size())
            {
                    pageTable.removePage(index);
                    pages.remove(index);
                    results.clear();
                    calculate();
            }
        }
    }

}

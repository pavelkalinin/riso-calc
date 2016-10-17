package xyz.enhorse.forms;

import xyz.enhorse.controls.CellRenderer;
import xyz.enhorse.controls.NumberField;
import xyz.enhorse.datatypes.Formats;
import xyz.enhorse.datatypes.Paper;
import xyz.enhorse.datatypes.PriceList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

/**
 * Dialog for editing of the papers
 * Created by PAK on 22.04.2014.
 */
public class PaperEditor {
    private final static String TITLE = "Прайс на бумагу";
    private final static String[] COL_NAMES = {"Название", "Формат", "Тираж"};

    private ArrayList<Paper> papers = new ArrayList<>();
    private PaperTableModel tableModel = new PaperTableModel();
    private Editor editor;
    private PriceList priceList;

    public PaperEditor(PriceList pl) {
        priceList = pl;
        initData();
        editor = new Editor(null, TITLE, tableModel);
        initTable();
        initButtons();
        editor.setVisible(true);
    }

    private void initData() {
        for (Map.Entry<Long, Paper> pair : priceList.getPaperPrices().entrySet()) {
            Paper paper = pair.getValue();
            papers.add(paper);
            this.addDataRow(paper);
        }
    }

    private void initTable() {
        for (int i = 0; i < COL_NAMES.length; i++) {
            editor.getTable().getColumn(COL_NAMES[i]).setCellEditor(new PaperCellEditor(i));
        }
        editor.getTable().getColumn(COL_NAMES[0]).setCellRenderer(new CellRenderer(SwingConstants.LEFT));
        editor.getTable().getColumn(COL_NAMES[1]).setCellRenderer(new CellRenderer(SwingConstants.CENTER));
        editor.getTable().getColumn(COL_NAMES[2]).setCellRenderer(new CellRenderer(SwingConstants.RIGHT));
    }

    private void initButtons() {
        editor.addButton.addActionListener(new addButtonAction());
        editor.delButton.addActionListener(new delButtonAction());
        editor.saveButton.addActionListener(new saveButtonAction());
        editor.cancelButton.addActionListener(new cancelButtonAction());
    }

    private void addDataRow(Paper paper) {
        tableModel.addRow(new Object[]{paper.getName(),paper.getFormat(),paper.getPrice()});
    }

    private void removeDataRow(int index) {
        papers.remove(index);
        tableModel.removeRow(index);
    }

    private class saveButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            priceList.clearPapers();
            for (Paper paper : papers) {
                priceList.addPaper(paper.getName(), paper.getFormat(), paper.getPrice());
            }
            editor.dispose();
        }
    }

    private class cancelButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            editor.dispose();
        }
    }

    private class addButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Paper paper = new Paper("DEFAULT",Formats.A4, 0);
            papers.add(paper);
            addDataRow(paper);
            editor.setTableChanged(true);
        }
    }

    private class delButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int idx = editor.getTable().getSelectedRow();
            if (idx >=0 ) {
                removeDataRow(idx);
                editor.setTableChanged(true);
            }
        }
    }

    private class PaperTableModel extends DefaultTableModel {

        public PaperTableModel() {
            super(COL_NAMES, 0);
        }

        @Override
        public void setValueAt(Object value, int row, int column) {
            super.setValueAt(value, row, column);
            Paper paper = papers.get(row);

            switch (column) {
                case 0: {
                    papers.set(row, new Paper((String) value, paper.getFormat(), paper.getPrice()));
                    break;
                }
                case 1: {
                    papers.set(row, new Paper(paper.getName(), (Formats) value, paper.getPrice()));
                    break;
                }
                case 2: {
                    papers.set(row, new Paper(paper.getName(), paper.getFormat(), (double) value));
                    break;
                }
            }
            editor.setTableChanged(true);
        }
    }

    private class PaperCellEditor extends AbstractCellEditor implements TableCellEditor {
        private NumberField nf;
        private JTextField tf;
        private JComboBox<Formats> cb;
        private int current;

        PaperCellEditor(int current) {
            this.current = current;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (!isSelected) {
                return null;
            }

            switch (current) {
                case 0: {
                    tf = new JTextField((String)value);
                    return tf;
                }
                case 2: {
                    nf = new NumberField((double) value, false);
                    return nf;
                }
                case 1: {
                    cb = new JComboBox<>(Formats.available());
                    return cb;
                }
                default: throw new IllegalArgumentException("No such CellEditor");
            }
        }

        @Override
        public Object getCellEditorValue() {
            switch (current) {
                case 0: {
                    return tf.getText();
                }
                case 2: {
                    return nf.getFloatValue();
                }
                case 1: {
                    return cb.getItemAt(cb.getSelectedIndex());
                }
                default: throw new IllegalArgumentException("No value for such CellEditor");
            }
        }
    }
}

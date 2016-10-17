package xyz.enhorse.forms;

import xyz.enhorse.controls.CellRenderer;
import xyz.enhorse.controls.NumberField;
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
 * Created by PAK on 22.04.2014.
 */
public class InkEditor {
    private final static String[] COL_NAMES = {"Интервал от ...", "Черная за А4", "Цветная за А4"};

    ArrayList<Ink> inks;
    PriceList priceList;
    InkTableModel tableModel = new InkTableModel(COL_NAMES);

    Editor editor;

    public InkEditor(PriceList pl) {
        inks = new ArrayList<>();
        this.priceList = pl;
        initData();
        editor = new Editor(null, "Прайс на печать", tableModel);
        initTable();
        initButtons();
        editor.setVisible(true);
    }

    private void initData() {
        for (Map.Entry<Long, Double[]> pair : priceList.getInkPrices().entrySet()) {
            inks.add(new Ink(pair.getKey(), pair.getValue()[0], pair.getValue()[1]));
        }
        for(Ink ink : inks) {
            this.addDataRow(ink);
        }
    }

    void initTable() {
        editor.getTable().getColumn(COL_NAMES[0]).setCellEditor(new InkCellEditor(0));
        editor.getTable().getColumn(COL_NAMES[1]).setCellEditor(new InkCellEditor(1));
        editor.getTable().getColumn(COL_NAMES[2]).setCellEditor(new InkCellEditor(1));
        for (int i = 0; i < COL_NAMES.length; i++) {
            editor.getTable().getColumn(COL_NAMES[i]).setCellRenderer(new CellRenderer(SwingConstants.RIGHT));
        }
    }

    private void initButtons() {
        editor.addButton.addActionListener(new addButtonAction());
        editor.delButton.addActionListener(new delButtonAction());
        editor.saveButton.addActionListener(new saveButtonAction());
        editor.cancelButton.addActionListener(new cancelButtonAction());
    }

    private void addDataRow(Ink ink) {
        tableModel.addRow(new Object[]{ink.getInterval(), ink.getBlack(), ink.getColor()});
    }

    private void removeDataRow(int index) {
        inks.remove(index);
        tableModel.removeRow(index);
    }

    class saveButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            priceList.clearInks();
            for (Ink ink : inks) {
                priceList.addInk(ink.getInterval(), ink.getBlack(), ink.getColor());
            }
            editor.dispose();
        }
    }

    class cancelButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            editor.dispose();
        }
    }

    class addButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Ink ink = new Ink(-1,0,0);
            addDataRow(ink);
            inks.add(ink);
        }
    }

    class delButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int idx = editor.getTable().getSelectedRow();
            if (idx >=0 ) {
                removeDataRow(idx);
                editor.setTableChanged(true);
            }
        }
    }

    class InkTableModel extends DefaultTableModel {

        InkTableModel(String[] columns) {
            super(columns, 0);
        }

        @Override
        public void setValueAt(Object value, int row, int column) {
            super.setValueAt(value, row, column);
            switch (column) {
                case 0: {
                    inks.set(row, new Ink((long)value, inks.get(row).getBlack(),inks.get(row).getColor()));
                    break;
                }
                case 1: {
                    inks.set(row, new Ink(inks.get(row).getInterval(), (double)value, inks.get(row).getColor()));
                    break;
                }
                case 2: {
                    inks.set(row, new Ink(inks.get(row).getInterval(), inks.get(row).getBlack(), (double)value));
                    break;
                }
            }
            editor.setTableChanged(true);
        }
    }

    class InkCellEditor extends AbstractCellEditor implements TableCellEditor {
        private NumberField nf;
        int current;

        InkCellEditor(int current) {
            this.current = current;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (!isSelected) {
                return null;
            }

            switch (current) {
                case 0: {
                    nf = new NumberField((long) value, true);
                    return nf;
                }
                case 1: {
                    nf = new NumberField((double) value, false);
                    return nf;
                }
                default: throw new IllegalArgumentException("No such CellEditor");
            }
        }

        @Override
        public Object getCellEditorValue() {
            switch (current) {
                case 0: {
                    return nf.getIntValue();
                }
                case 1: {
                    return nf.getFloatValue();
                }
                default: throw new IllegalArgumentException("No such CellValue");
            }
        }
    }

    class Ink {
        private long interval;
        private double black;
        private double color;

        Ink(long interval, double blackPrice, double colorPrice) {
            this.interval = interval;
            this.black = blackPrice;
            this.color = colorPrice;
        }

        public long getInterval() {
            return interval;
        }

        public void setInterval(long interval) {
            this.interval= interval >= 0 ? interval : 0;
        }

        public double getBlack() {
            return black;
        }

        public void setBlack(double black) {
            this.black= black >= 0 ? black : 0;
        }

        public double getColor() {
            return color;
        }

        public void setColor(double color) {
            this.color= color >= 0 ? color : 0;
        }
    }

}

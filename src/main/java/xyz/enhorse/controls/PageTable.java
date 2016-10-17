package xyz.enhorse.controls;

import xyz.enhorse.datatypes.Formats;
import xyz.enhorse.datatypes.Page;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by PAK on 28.04.2014.
 */
public class PageTable extends JTable {
    private final static String[] COL_NAMES = {"Бумага", "Формат", "Лицо", "Оборот", "Количество"};

    private DefaultTableModel model;
    private ListSelectionModel selection;
    private boolean isEmpty;

    public PageTable() {
        super();
        isEmpty = true;
        model = new DefaultTableModel(COL_NAMES, 3);
        setModel(model);
        for (int i = 0; i < getColumnCount(); i++) {
            getColumnModel().getColumn(i).setCellRenderer(new PageCellRenderer());
        }

        selection = new DefaultListSelectionModel();
        selection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
        setSelectionModel(selection);

        setShowGrid(false);
        initSize();
        setRowSelectionAllowed(true);
        setToolTipText("Двойной клик для удаления страницы из тиража");
    }

    private void initSize() {
        setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        setFillsViewportHeight(true);
        Dimension dim = getPreferredSize();
        double rowHeight = 0;
        double columnWidth = 0;
        if (getRowCount() != 0) rowHeight = (getPreferredSize().getHeight()/getModel().getRowCount());
        if (getColumnCount() != 0) columnWidth = (getPreferredSize().getWidth()/getModel().getColumnCount()/1.5);
        dim.setSize((getColumnCount() + 1) * columnWidth, (getRowCount() + 2) * rowHeight);
        setPreferredScrollableViewportSize(dim);
    }

    public void addPage(Page page) {
        if (isEmpty) {
            clearData();
            isEmpty = false;
        }
        String name = page.getPaper().getName();
        Formats format = page.getPaper().getFormat();
        ColorsPlate front = new ColorsPlate(page.getFront(), getRowHeight());
        ColorsPlate back = new ColorsPlate(page.getBack(), getRowHeight());
        Long quantity = page.getQuantity();
        model.addRow(new Object[]{name, format, front, back, quantity});
    }

    public void removePage(int index) {
        if (!isEmpty && index < getRowCount()) {
            model.removeRow(index);
            if (getRowCount() == 0) {
                isEmpty = true;
            }
        }
    }

    public void clearData() {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    private class PageCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (value == null) return null;
            JTextField result;

            switch (column) {
                case 0: {
                    String data = (String) value;
                    result = new JTextField(data);
                    result.setHorizontalAlignment(SwingConstants.LEFT);
                    break;
                }
                case 1: {
                    Formats data = (Formats) value;
                    result = new JTextField(data.getName());
                    result.setHorizontalAlignment(SwingConstants.CENTER);
                    break;
                }
                case 2: {
                    result = (ColorsPlate) value;
                    result.setHorizontalAlignment(SwingConstants.CENTER);
                    break;
                }
                case 3: {
                    result = (ColorsPlate) value;
                    result.setHorizontalAlignment(SwingConstants.CENTER);
                    break;
                }
                case 4: {
                    Long data = (Long) value;
                    result = new JTextField(String.valueOf(data));
                    result.setHorizontalAlignment(SwingConstants.RIGHT);
                    break;
                }
                default: throw new IllegalArgumentException("No such CellRenderer");
            }
            result.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            if (isSelected) {
                result.setBackground(table.getSelectionBackground());
                result.setForeground(table.getSelectionForeground());
            }
            else {
                result.setBackground(table.getBackground());
                result.setForeground(table.getForeground());
            }
            return result;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

}

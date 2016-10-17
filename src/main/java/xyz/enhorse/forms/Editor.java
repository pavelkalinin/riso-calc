package xyz.enhorse.forms;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Common class for Price's Editors
 * Created by PAK on 22.04.2014.
 */
public class Editor extends JDialog {
    private boolean tableChanged = false;

    private JPanel main;
    private JPanel buttons;

    private ListSelectionModel selModel;
    private JScrollPane scroll;

    private JTable table;
    JButton saveButton;
    JButton cancelButton;
    JButton addButton;
    JButton delButton;

    public Editor(JFrame owner, String title, DefaultTableModel model) {
        super(owner, title, true);

        ClassLoader cl = this.getClass().getClassLoader();
        ImageIcon icon  = new ImageIcon(cl.getResource("icon.png"));
        setIconImage(icon.getImage());

        table = new JTable();
        table.setModel(model);
        table.addMouseListener(new tableMouseAdapter());
        setupTable();
        scroll = new JScrollPane(table);
        buttons = new JPanel(new GridLayout(2,2));
        setupButtons();
        main = new JPanel();
        main.add(scroll);
        main.add(buttons);

        setButtonsState();
        setContentPane(main);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        putToScreenCenter();
    }

    private void putToScreenCenter(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = getSize().width;
        int h = getSize().height;
        setLocation((dim.width - w) / 2, (dim.height - h) / 2);
    }

    private void setupTable () {
        selModel = table.getSelectionModel();
        selModel.addListSelectionListener(new selectionProcessor());

        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);

        table.setFillsViewportHeight(true);
        Dimension dim = table.getPreferredSize();
        double rowHeight;

        if (table.getModel().getRowCount() > 0) {
            rowHeight = table.getPreferredSize().getHeight() / table.getModel().getRowCount();
        } else {
            rowHeight = table.getPreferredSize().getHeight();
        }
        double columnWidth = (table.getPreferredSize().getWidth()/table.getModel().getColumnCount());
        dim.setSize((table.getModel().getColumnCount()+1) * columnWidth,
                (table.getModel().getRowCount()+2) * rowHeight);
        table.setPreferredScrollableViewportSize(dim);
    }

    private void setupButtons() {
        saveButton = new JButton("Сохранить");
        cancelButton = new JButton("Отмена");
        addButton = new JButton("Добавить");
        delButton = new JButton("Удалить");

        buttons.add(addButton);
        buttons.add(saveButton);
        buttons.add(delButton);
        buttons.add(cancelButton);
    }

    private void setButtonsState() {
        if (table.getSelectedRow() >= 0) {
            delButton.setEnabled(true);
        } else {
            delButton.setEnabled(true);
        }
        saveButton.setEnabled(tableChanged);
    }

    public void setTableChanged(boolean state) {
        tableChanged = state;
        setButtonsState();
    }

    public JTable getTable() {
        return this.table;
    }

    private class selectionProcessor implements ListSelectionListener
    {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            setButtonsState();
        }
    }

    private class tableMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JTable tbl=((JTable)e.getComponent());
            if(tbl.isEditing() )
                tbl.getCellEditor().stopCellEditing();
        }
    };


}

/*
 * Created by JFormDesigner on Sun Dec 21 02:06:44 MSK 2014
 */

package PresentationLayer;

import ServiceLayer.UsersTableService;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ÑÑÐ²ÑÑÐ² ÑÑÐ²ÑÑÐ²
 */
public class UsersEditForm extends JFrame {
    private UsersTableService usersTableService = new UsersTableService();
    public UsersEditForm() {
        initComponents();
        TableModel usersTable = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return usersTableService.getRows().size();
            }

            @Override
            public int getColumnCount() {
                return usersTableService.getColNames().length;
            }

            @Override
            public String getColumnName(int columnIndex) {
                return usersTableService.getColNames()[columnIndex];
            }
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return usersTableService.getValueAt(rowIndex,columnIndex);
            }
            @Override
            public boolean isCellEditable(int row, int col) {return true;}

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                usersTableService.setValueAt((String)aValue,row,column);
                fireTableCellUpdated(row, column);
            }
            @Override
            public Class getColumnClass(int c) {return (String.class);}


        };
        table1.setModel(usersTable);
    }

    private void button1ActionPerformed(ActionEvent e) {
        if (!usersTableService.updateDB())
            JOptionPane.showMessageDialog(button1, "Изменений нет", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
        else {
            usersTableService.onClose();
            dispose();
        }
    }

    private void button2ActionPerformed(ActionEvent e) {
        usersTableService.onClose();
        dispose();
    }

    private void button3ActionPerformed(ActionEvent e) {
        if (!usersTableService.rollBack()) JOptionPane.showMessageDialog(button1, "Изменений нет", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
        else {
            repaint();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - ÑÑÐ²ÑÑÐ² ÑÑÐ²ÑÑÐ²
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();

        //======== this ========
        setTitle("\u0420\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u0435\u0439");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(5, 5, 705, 410);

        //---- button1 ----
        button1.setText("\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button1ActionPerformed(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(415, 425, 145, button1.getPreferredSize().height);

        //---- button2 ----
        button2.setText("\u041e\u0442\u043c\u0435\u043d\u0430");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button2ActionPerformed(e);
            }
        });
        contentPane.add(button2);
        button2.setBounds(565, 425, 145, button2.getPreferredSize().height);

        //---- button3 ----
        button3.setText("\u0412\u0435\u0440\u043d\u0443\u0442\u044c \u0438\u0441\u0445\u043e\u0434\u043d\u044b\u0435");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button3ActionPerformed(e);
            }
        });
        contentPane.add(button3);
        button3.setBounds(230, 425, 175, button3.getPreferredSize().height);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - ÑÑÐ²ÑÑÐ² ÑÑÐ²ÑÑÐ²
    private JScrollPane scrollPane1;
    private JTable table1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

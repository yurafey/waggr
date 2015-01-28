/*
 * Created by JFormDesigner on Mon Dec 22 02:27:53 MSK 2014
 */

package PresentationLayer;

import RemoteServiceLayer.UsersService;
import RemoteServiceLayer.UsersServiceImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * @author ÑÑÐ²ÑÑÐ² ÑÑÐ²ÑÑÐ²
 */
public class AdminPwdForm extends JFrame {
    private UsersService usersService = null;
    public AdminPwdForm(UsersService usersService) {
        this.usersService = usersService;
        initComponents();
    }

    private void okButtonActionPerformed(ActionEvent e) {
        String oldPassword = String.valueOf(passwordField1.getPassword());
        String newPassword1 = String.valueOf(passwordField2.getPassword());
        String newPassword2 = String.valueOf(passwordField3.getPassword());

        if (!newPassword1.equals(newPassword2)) {
            JOptionPane.showMessageDialog(okButton, "Новые пароли не совпадают", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (oldPassword.equals(newPassword1)) {
            JOptionPane.showMessageDialog(okButton, "Новый введенный пароль совпадает с введенным старым", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (usersService.updatePassword("admin", oldPassword, newPassword1)) {
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(okButton, "Неверный пароль", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }

    private void cancelButtonActionPerformed(ActionEvent e) {
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - 123 123
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        passwordField1 = new JPasswordField();
        passwordField2 = new JPasswordField();
        passwordField3 = new JPasswordField();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setTitle("\u0421\u043c\u0435\u043d\u0430 \u043f\u0430\u0440\u043e\u043b\u044f");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

            // JFormDesigner evaluation mark
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);
                contentPanel.add(passwordField1);
                passwordField1.setBounds(110, 10, 240, passwordField1.getPreferredSize().height);
                contentPanel.add(passwordField2);
                passwordField2.setBounds(110, 35, 240, 22);
                contentPanel.add(passwordField3);
                passwordField3.setBounds(110, 60, 240, 22);

                //---- label1 ----
                label1.setText("\u0421\u0442\u0430\u0440\u044b\u0439 \u043f\u0430\u0440\u043e\u043b\u044c");
                contentPanel.add(label1);
                label1.setBounds(new Rectangle(new Point(5, 15), label1.getPreferredSize()));

                //---- label2 ----
                label2.setText("\u041d\u043e\u0432\u044b\u0439 \u043f\u0430\u0440\u043e\u043b\u044c");
                contentPanel.add(label2);
                label2.setBounds(5, 40, 82, 14);

                //---- label3 ----
                label3.setText("\u041f\u043e\u0432\u0442\u043e\u0440\u0438\u0442\u0435 \u043f\u0430\u0440\u043e\u043b\u044c");
                contentPanel.add(label3);
                label3.setBounds(5, 65, 100, 14);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < contentPanel.getComponentCount(); i++) {
                        Rectangle bounds = contentPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = contentPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    contentPanel.setMinimumSize(preferredSize);
                    contentPanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        okButtonActionPerformed(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cancelButtonActionPerformed(e);
                    }
                });
                buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - 123 123
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JPasswordField passwordField3;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

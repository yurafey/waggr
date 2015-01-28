package PresentationLayer;

import java.awt.*;
import DataAccessLayer.DBConnector;
import RemoteServiceLayer.AdminService;
import RemoteServiceLayer.AdminServiceImpl;
import RemoteServiceLayer.UsersService;
import RemoteServiceLayer.UsersServiceImpl;

import javax.swing.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import com.intellij.uiDesigner.core.*;

public class NewUserDialog extends JFrame {
    //private JPanel contentPane;
    private AdminService adminService = null;
    private UsersService usersService = null;


    public NewUserDialog(AdminService adminService,UsersService usersService) {
        initComponents();
        this.adminService = adminService;
        this.usersService = usersService;

        setTitle("Регистрация нового пользователя");

        setResizable(false);

        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }

        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
//        contentPane.registerKeyboardAction(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                onCancel();
//            }
//        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() throws RemoteException {
        if (textField1.getText().isEmpty()||textField2.getText().isEmpty()||textField3.getText().isEmpty()||textField4.getText().isEmpty()||textField5.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Введите все данные","Ошибка",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!String.valueOf(passwordField1.getPassword()).equals(String.valueOf(passwordField2.getPassword()))||passwordField1.getPassword().length==0||passwordField2.getPassword().length==0){
            JOptionPane.showMessageDialog(this,"Пароли не совпадают либо не введены","Ошибка",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!adminService.checkCityExists(textField4.getText(), textField5.getText())){
            JOptionPane.showMessageDialog(this,"Города "+textField4.getText()+" или страны "+ textField5.getText()+" нет в базе","Ошибка",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        boolean res = usersService.newUser(textField1.getText(),String.valueOf(passwordField1.getPassword()),textField2.getText(),textField3.getText(), textField4.getText(),textField5.getText());
        if (!res){
            JOptionPane.showMessageDialog(this,"Логин "+textField1.getText()+" уже существует","Ошибка",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this,"Пользователь добавлен!","Успешно",JOptionPane.INFORMATION_MESSAGE);
        onCancel();
    }

    private void onCancel() {
        dispose();
    }


//    public static void main(String[] args) {
//        NewUserDialog dialog = new NewUserDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - 123 123
        mainPanel = new JPanel();
        JPanel panel1 = new JPanel();
        pan1 = new JPanel();
        JLabel label1 = new JLabel();
        textField1 = new JTextField();
        JLabel label2 = new JLabel();
        JLabel label3 = new JLabel();
        passwordField1 = new JPasswordField();
        passwordField2 = new JPasswordField();
        JLabel label4 = new JLabel();
        textField2 = new JTextField();
        JLabel label5 = new JLabel();
        textField3 = new JTextField();
        JLabel label6 = new JLabel();
        textField4 = new JTextField();
        JPanel vSpacer1 = new JPanel(null);
        textField5 = new JTextField();
        JLabel label7 = new JLabel();
        JPanel panel2 = new JPanel();
        buttonOK = new JButton();
        buttonCancel = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== mainPanel ========
        {

            // JFormDesigner evaluation mark
            mainPanel.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), mainPanel.getBorder())); mainPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            mainPanel.setLayout(null);

            //======== panel1 ========
            {
                panel1.setLayout(null);

                //======== pan1 ========
                {
                    pan1.setLayout(null);

                    //---- label1 ----
                    label1.setText("\u041b\u043e\u0433\u0438\u043d");
                    pan1.add(label1);
                    label1.setBounds(new Rectangle(new Point(30, 4), label1.getPreferredSize()));
                    pan1.add(textField1);
                    textField1.setBounds(172, 0, 571, textField1.getPreferredSize().height);

                    //---- label2 ----
                    label2.setText("\u041f\u0430\u0440\u043e\u043b\u044c");
                    pan1.add(label2);
                    label2.setBounds(new Rectangle(new Point(30, 37), label2.getPreferredSize()));

                    //---- label3 ----
                    label3.setText("\u041f\u043e\u0432\u0442\u043e\u0440\u0438\u0442\u0435 \u041f\u0430\u0440\u043e\u043b\u044c");
                    pan1.add(label3);
                    label3.setBounds(30, 66, label3.getPreferredSize().width, 27);
                    pan1.add(passwordField1);
                    passwordField1.setBounds(172, 34, 571, passwordField1.getPreferredSize().height);
                    pan1.add(passwordField2);
                    passwordField2.setBounds(172, 66, 571, passwordField2.getPreferredSize().height);

                    //---- label4 ----
                    label4.setText("\u0418\u043c\u044f");
                    pan1.add(label4);
                    label4.setBounds(new Rectangle(new Point(30, 102), label4.getPreferredSize()));
                    pan1.add(textField2);
                    textField2.setBounds(172, 98, 571, textField2.getPreferredSize().height);

                    //---- label5 ----
                    label5.setText("\u0424\u0430\u043c\u0438\u043b\u0438\u044f");
                    pan1.add(label5);
                    label5.setBounds(new Rectangle(new Point(30, 136), label5.getPreferredSize()));
                    pan1.add(textField3);
                    textField3.setBounds(172, 132, 571, textField3.getPreferredSize().height);

                    //---- label6 ----
                    label6.setText("\u0413\u043e\u0440\u043e\u0434");
                    pan1.add(label6);
                    label6.setBounds(new Rectangle(new Point(30, 170), label6.getPreferredSize()));
                    pan1.add(textField4);
                    textField4.setBounds(172, 166, 571, textField4.getPreferredSize().height);
                    pan1.add(vSpacer1);
                    vSpacer1.setBounds(10, 234, 0, 25);
                    pan1.add(textField5);
                    textField5.setBounds(172, 200, 571, textField5.getPreferredSize().height);

                    //---- label7 ----
                    label7.setText("\u0421\u0442\u0440\u0430\u043d\u0430");
                    pan1.add(label7);
                    label7.setBounds(new Rectangle(new Point(30, 204), label7.getPreferredSize()));

                    { // compute preferred size
                        Dimension preferredSize = new Dimension();
                        for(int i = 0; i < pan1.getComponentCount(); i++) {
                            Rectangle bounds = pan1.getComponent(i).getBounds();
                            preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                            preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                        }
                        Insets insets = pan1.getInsets();
                        preferredSize.width += insets.right;
                        preferredSize.height += insets.bottom;
                        pan1.setMinimumSize(preferredSize);
                        pan1.setPreferredSize(preferredSize);
                    }
                }
                panel1.add(pan1);
                pan1.setBounds(0, 0, 743, pan1.getPreferredSize().height);

                //======== panel2 ========
                {
                    panel2.setLayout(null);

                    //---- buttonOK ----
                    buttonOK.setText("\u0420\u0435\u0433\u0438\u0441\u0442\u0440\u0430\u0446\u0438\u044f");
                    panel2.add(buttonOK);
                    buttonOK.setBounds(0, 0, 384, buttonOK.getPreferredSize().height);

                    //---- buttonCancel ----
                    buttonCancel.setText("\u041e\u0442\u043c\u0435\u043d\u0430");
                    panel2.add(buttonCancel);
                    buttonCancel.setBounds(394, 0, 349, buttonCancel.getPreferredSize().height);

                    { // compute preferred size
                        Dimension preferredSize = new Dimension();
                        for(int i = 0; i < panel2.getComponentCount(); i++) {
                            Rectangle bounds = panel2.getComponent(i).getBounds();
                            preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                            preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                        }
                        Insets insets = panel2.getInsets();
                        preferredSize.width += insets.right;
                        preferredSize.height += insets.bottom;
                        panel2.setMinimumSize(preferredSize);
                        panel2.setPreferredSize(preferredSize);
                    }
                }
                panel1.add(panel2);
                panel2.setBounds(0, 264, 743, panel2.getPreferredSize().height);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel1.getComponentCount(); i++) {
                        Rectangle bounds = panel1.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel1.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel1.setMinimumSize(preferredSize);
                    panel1.setPreferredSize(preferredSize);
                }
            }
            mainPanel.add(panel1);
            panel1.setBounds(new Rectangle(new Point(10, 10), panel1.getPreferredSize()));

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < mainPanel.getComponentCount(); i++) {
                    Rectangle bounds = mainPanel.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = mainPanel.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                mainPanel.setMinimumSize(preferredSize);
                mainPanel.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(mainPanel);
        mainPanel.setBounds(5, 10, 763, 313);

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
    // Generated using JFormDesigner Evaluation license - 123 123
    private JPanel mainPanel;
    private JPanel pan1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton buttonOK;
    private JButton buttonCancel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

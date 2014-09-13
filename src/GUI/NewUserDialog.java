package GUI;

import DBProcessor.DBConnector;

import javax.swing.*;
import java.awt.event.*;

public class NewUserDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JLabel SuccessField;
    private JPanel pan1;


    private DBConnector db = new DBConnector();

    public NewUserDialog() {
        setTitle("Регистрация нового пользователя");
        SuccessField.setVisible(false);
        //
        setContentPane(contentPane);
        setResizable(false);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (textField1.getText().isEmpty()||textField2.getText().isEmpty()||textField3.getText().isEmpty()||textField4.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Введите все данные","Ошибка",JOptionPane.INFORMATION_MESSAGE);
            return ;
        }
        if (!String.valueOf(passwordField1.getPassword()).equals(String.valueOf(passwordField2.getPassword()))||passwordField1.getPassword().length==0||passwordField2.getPassword().length==0){
            JOptionPane.showMessageDialog(this,"Пароли не совпадают либо не введены","Ошибка",JOptionPane.INFORMATION_MESSAGE);
            return ;
        }
        if (!db.CheckCity(textField4.getText())){
            JOptionPane.showMessageDialog(this,"Города "+textField4.getText()+" нет в базе","Ошибка",JOptionPane.INFORMATION_MESSAGE);
            return ;
        }
        Boolean res = db.NewUser(textField1.getText(),String.valueOf(passwordField1.getPassword()),textField2.getText(),textField3.getText(),textField4.getText());
        if (!res){
            JOptionPane.showMessageDialog(this,"Логин "+textField1.getText()+" уже существует","Ошибка",JOptionPane.INFORMATION_MESSAGE);
            return ;
        }
        JOptionPane.showMessageDialog(this,"Пользователь добавлен!","Успешно",JOptionPane.INFORMATION_MESSAGE);
        onCancel();
    }

    private void onCancel() {
        db.DBConnectionClose();
// add your code here if necessary
        dispose();
    }


//    public static void main(String[] args) {
//        NewUserDialog dialog = new NewUserDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}

package GUI;

import BusinessLogic.User;
import DataAccessLayer.DBConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by yuraf_000 on 18.06.2014.
 */
public class AuthorizationForm extends JFrame {
    private JButton OkButton = new JButton("Вход");
    private JButton RegButton = new JButton("Регистрация нового пользователя");
    private JLabel Label1 = new JLabel("Авторизация в системе");
    private JTextField Field1 = new JTextField("Введите логин");
    private JPasswordField Field2 = new JPasswordField("Введите пароль");
    private DBConnector db = new DBConnector();
    public AuthorizationForm() {
        setTitle("WAGGR Authorization");
        setSize(300, 250);
        setLocationRelativeTo(null);
        Container pane = getContentPane();
        pane.setLayout(new GridLayout (5, 1));

        Label1.setHorizontalAlignment(JTextField.CENTER);
        Field1.setHorizontalAlignment(JTextField.CENTER);
        Field1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Field1.setText("");
            }
        });
        Field2.setHorizontalAlignment(JTextField.CENTER);
        Field2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Field2.setText("");
            }
        });
        OkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                OkClicked();
            }
        });
        RegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                NewUserDialog dialog = new NewUserDialog();
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        
        pane.add(Label1);
        pane.add(Field1);
        pane.add(Field2);
        pane.add(OkButton);
        pane.add(RegButton);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void OkClicked(){
        User checkUser = db.сheckUser(Field1.getText(), String.valueOf(Field2.getPassword()));
        if (checkUser == null){
            JOptionPane.showMessageDialog(this,"Неверный логин/пароль","Ошибка",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        dispose();
        db.connectionClose();
        MainForm m = new MainForm(checkUser);

    }

}

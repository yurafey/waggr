package PresentationLayer;

import RemoteServiceLayer.*;
import ServiceLayer.WeatherCurrentTableService;
import ServiceLayer.WeatherForecastTableService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by yuraf_000 on 18.06.2014.
 */
public class AuthorizationForm extends JFrame {
    private JButton OkButton = new JButton("Вход");
    private JButton RegButton = new JButton("Регистрация нового пользователя");
    private JLabel Label1 = new JLabel("Авторизация в системе");
    private JTextField Field1 = new JTextField("Введите логин");
    private JPasswordField Field2 = new JPasswordField("Введите пароль");
    private AdminService adminService = null;
    private RealFeelService realFeelService = null;
    private UsersService usersService = null;
    private WeatherCurrentTableService weatherCurrentTableService = null;
    private WeatherForecastTableService weatherForecastTableService = null;
    private WeatherService weatherService = null;
    private String serverIp = "127.0.0.1";

    public AuthorizationForm(AdminService adminService, RealFeelService realFeelService, UsersService usersService, WeatherForecastTableService weatherForecastTableService, WeatherCurrentTableService weatherCurrentTableService) throws RemoteException, MalformedURLException, NotBoundException {
        if (adminService==null&&realFeelService==null&&usersService==null&&weatherCurrentTableService==null&&weatherForecastTableService==null){
            this.adminService = (AdminService) Naming.lookup("//" + serverIp + "/AdminService");
            this.realFeelService = (RealFeelService) Naming.lookup("//" + serverIp + "/RealFeelService");
            this.usersService = (UsersService) Naming.lookup("//" + serverIp + "/UsersService");
            this.weatherService = (WeatherService) Naming.lookup("//" + serverIp + "/WeatherService");
            this.weatherCurrentTableService = new WeatherCurrentTableService(this.weatherService,this.realFeelService);
            this.weatherForecastTableService = new WeatherForecastTableService(this.weatherService);
            this.adminService.startWeatherServiceIfNew();
        } else {
            this.adminService = adminService;
            this.realFeelService = realFeelService;
            this.usersService = usersService;
            this.weatherCurrentTableService = weatherCurrentTableService;
            this.weatherForecastTableService = weatherForecastTableService;
        }
        initComponent();
        setVisible(true);
    }

    private void addActionListeners() {
        Field1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Field1.setText("");
            }
        });
        Field2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Field2.setText("");
            }
        });
        OkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    OkClicked();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        RegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                NewUserDialog dialog = new NewUserDialog(adminService,usersService);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }

    private void initComponent() {
        setTitle("WAGGR Authorization");
        setSize(300, 250);
        setLocationRelativeTo(null);
        Container pane = getContentPane();
        pane.setLayout(new GridLayout (5, 1));
        Label1.setHorizontalAlignment(JTextField.CENTER);
        Field1.setHorizontalAlignment(JTextField.CENTER);
        Field2.setHorizontalAlignment(JTextField.CENTER);
        addActionListeners();
        pane.add(Label1);
        pane.add(Field1);
        pane.add(Field2);
        pane.add(OkButton);
        pane.add(RegButton);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void OkClicked() throws RemoteException {
        String login = Field1.getText();
        String password = String.valueOf(Field2.getPassword());
        final Object current = usersService.login(login,password);
        if (current==null) {
            JOptionPane.showMessageDialog(this,"Неверный логин/пароль","Ошибка",JOptionPane.INFORMATION_MESSAGE);
            return;
        } else if (login.equals("admin")) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    AdminForm adminForm = null;
                    try {
                        adminForm = new AdminForm(adminService,realFeelService,usersService,weatherForecastTableService,weatherCurrentTableService);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    adminForm.setVisible(true);
                }
            });
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        MainForm mainForm = new MainForm(current,adminService,realFeelService,usersService,weatherForecastTableService,weatherCurrentTableService);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        dispose();

    }
}

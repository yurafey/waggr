/*
 * Created by JFormDesigner on Wed Nov 26 13:27:38 MSK 2014
 */

package PresentationLayer;

import RemoteServiceLayer.*;
import ServiceLayer.WeatherCurrentTableService;
import ServiceLayer.WeatherForecastTableService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author ?�N????? ?�?�?????�???�????
 */
public class AdminForm extends JFrame {
    private AdminService adminService = null;
    private RealFeelService realFeelService = null;
    private UsersService usersService = null;
    private WeatherCurrentTableService weatherCurrentTableService = null;
    private WeatherForecastTableService weatherForecastTableService = null;
    private SwingWorker<Void, String> logsLabelWorker;
    private SwingWorker<Void, String> refreshLabelWorker;

    public AdminForm(final AdminService adminService, RealFeelService realFeelService, UsersService usersService, WeatherForecastTableService weatherForecastTableService, WeatherCurrentTableService weatherCurrentTableService) throws RemoteException {
        this.adminService = adminService;
        this.realFeelService = realFeelService;
        this.usersService = usersService;
        this.weatherCurrentTableService = weatherCurrentTableService;
        this.weatherForecastTableService = weatherForecastTableService;

        initComponents();
        if (adminService.checkUpdateServiceIsCanceled()) {
            label6.setText("Сервис отключен.");
            button8.setText("Включить");
        }
        logsLabelWorker = new SwingWorker<Void, String>() {
            protected Void doInBackground() throws Exception {
                while (true) {
                    Thread.sleep(1000);
                    textArea1.setText(adminService.getConsoleLogs());
                }
            }
        };
        logsLabelWorker.execute();
        refreshWorkerRestart();

        textField1.setText(adminService.getCurrentCountries());
        textField2.setText(adminService.getCurrentPeriod());
        //adminService.executeConsoleWorker(label6,textArea1);
    }

    private void refreshWorkerRestart() {
        refreshLabelWorker = new SwingWorker<Void, String>() {
            protected Void doInBackground() throws Exception {
                while (true) {
                    Thread.sleep(1000);
                    label6.setText(adminService.getRefreshLogs());
                }
            }
        };
        refreshLabelWorker.execute();
    }

    private void button1ActionPerformed(ActionEvent e) throws RemoteException {
        if (adminService.setCurrentCountries(textField1.getText())) {
            JOptionPane.showMessageDialog(button1, "Страны сохранены", "Успешно", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void button3ActionPerformed(ActionEvent e) throws RemoteException {
        if (adminService.setCurrentPeriod(textField2.getText())) {
            JOptionPane.showMessageDialog(button3, "Период обновления сохранен", "Успешно", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void button4ActionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AuthorizationForm authorizationForm = null;
                try {
                    authorizationForm = new AuthorizationForm(adminService,realFeelService,usersService,weatherForecastTableService,weatherCurrentTableService);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (NotBoundException e1) {
                    e1.printStackTrace();
                }
                authorizationForm.setVisible(true);
            }
        });
        dispose();
    }

    private void button6ActionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UsersEditForm ue  = null;
                try {
                    ue = new UsersEditForm(usersService);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
                ue.setVisible(true);
            }
        });
    }

    private void button7ActionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AdminPwdForm ap = new AdminPwdForm(usersService);
                ap.setVisible(true);
            }
        });
    }

    private void button8ActionPerformed(ActionEvent e) throws RemoteException {
        if (adminService.restartWeatherServiceIfCanceled()) {
            refreshWorkerRestart();
            button8.setText("Отключить");
            label6.setText("Идет обновление...");
        } else if (adminService.startWeatherServiceIfNew()) {
            refreshWorkerRestart();
            button8.setText("Отключить");
            label6.setText("Идет обновление...");
        } else if (adminService.stopWeatherServiceIfWaiting()) {
            refreshLabelWorker.cancel(true);
            button8.setText("Включить");
            label6.setText("Сервис отключен.");
        } else if (adminService.stopWeatherServiceIfUpdating()){
            refreshLabelWorker.cancel(true);
            button8.setText("Включить");
            label6.setText("Сервис отключен.");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - 123 123
        textField1 = new JTextField();
        button1 = new JButton();
        label1 = new JLabel();
        textField2 = new JTextField();
        label2 = new JLabel();
        label3 = new JLabel();
        button3 = new JButton();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        label4 = new JLabel();
        button4 = new JButton();
        button6 = new JButton();
        button7 = new JButton();
        label5 = new JLabel();
        label6 = new JLabel();
        button8 = new JButton();

        //======== this ========
        setTitle("\u041f\u0430\u043d\u0435\u043b\u044c \u0430\u0434\u043c\u0438\u043d\u0438\u0441\u0442\u0440\u0430\u0442\u043e\u0440\u0430");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(textField1);
        textField1.setBounds(5, 25, 240, 25);

        //---- button1 ----
        button1.setText("\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    button1ActionPerformed(e);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        contentPane.add(button1);
        button1.setBounds(250, 25, 110, button1.getPreferredSize().height);

        //---- label1 ----
        label1.setText("\u0421\u0442\u0440\u0430\u043d\u044b \u043e\u0431\u043d\u043e\u0432\u043b\u0435\u043d\u0438\u044f:");
        contentPane.add(label1);
        label1.setBounds(60, 5, 135, label1.getPreferredSize().height);
        contentPane.add(textField2);
        textField2.setBounds(195, 85, 50, 25);

        //---- label2 ----
        label2.setText("\u041e\u0431\u043d\u043e\u0432\u043b\u0435\u043d\u0438\u0435 \u0431\u0430\u0437\u044b \u043f\u043e\u0433\u043e\u0434\u044b");
        contentPane.add(label2);
        label2.setBounds(new Rectangle(new Point(45, 65), label2.getPreferredSize()));

        //---- label3 ----
        label3.setText("\u041f\u0435\u0440\u0438\u043e\u0434 \u0430\u0432\u0442\u043e\u043e\u0431\u043d\u043e\u0432\u043b\u0435\u043d\u0438\u044f (\u043c\u0438\u043d)");
        contentPane.add(label3);
        label3.setBounds(10, 90, 185, label3.getPreferredSize().height);

        //---- button3 ----
        button3.setText("\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    button3ActionPerformed(e);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        contentPane.add(button3);
        button3.setBounds(250, 85, 120, button3.getPreferredSize().height);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(textArea1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(5, 185, 615, 200);

        //---- label4 ----
        label4.setText("\u041b\u043e\u0433\u0438 \u0441\u0435\u0440\u0432\u0435\u0440\u0430:");
        contentPane.add(label4);
        label4.setBounds(new Rectangle(new Point(15, 165), label4.getPreferredSize()));

        //---- button4 ----
        button4.setText("\u0412\u044b\u0439\u0442\u0438 \u0438\u0437 \u043f\u0430\u043d\u0435\u043b\u0438 \u0430\u0434\u043c\u0438\u043d\u0438\u0441\u0442\u0440\u0430\u0442\u043e\u0440\u0430");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button4ActionPerformed(e);
            }
        });
        contentPane.add(button4);
        button4.setBounds(380, 25, 240, 25);

        //---- button6 ----
        button6.setText("\u0420\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u0435\u0439");
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button6ActionPerformed(e);
            }
        });
        contentPane.add(button6);
        button6.setBounds(380, 85, 240, button6.getPreferredSize().height);

        //---- button7 ----
        button7.setText("\u0421\u043c\u0435\u043d\u0438\u0442\u044c \u043f\u0430\u0440\u043e\u043b\u044c \u0430\u0434\u043c\u0438\u043d\u0438\u0441\u0442\u0440\u0430\u0442\u043e\u0440\u0430");
        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button7ActionPerformed(e);
            }
        });
        contentPane.add(button7);
        button7.setBounds(380, 55, 240, button7.getPreferredSize().height);

        //---- label5 ----
        label5.setText("\u0421\u0442\u0430\u0442\u0443\u0441 \u0430\u0432\u0442\u043e\u043e\u0431\u043d\u043e\u0432\u043b\u0435\u043d\u0438\u044f:");
        contentPane.add(label5);
        label5.setBounds(new Rectangle(new Point(10, 110), label5.getPreferredSize()));

        //---- label6 ----
        label6.setText("\u0418\u0434\u0435\u0442 \u043e\u0431\u043d\u043e\u0432\u043b\u0435\u043d\u0438\u0435...");
        contentPane.add(label6);
        label6.setBounds(10, 125, 175, label6.getPreferredSize().height);

        //---- button8 ----
        button8.setText("\u041e\u0442\u043a\u043b\u044e\u0447\u0438\u0442\u044c");
        button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    button8ActionPerformed(e);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        });
        contentPane.add(button8);
        button8.setBounds(195, 115, 110, button8.getPreferredSize().height);

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
        setSize(640, 425);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - 123 123
    private JTextField textField1;
    private JButton button1;
    private JLabel label1;
    private JTextField textField2;
    private JLabel label2;
    private JLabel label3;
    private JButton button3;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JLabel label4;
    private JButton button4;
    private JButton button6;
    private JButton button7;
    private JLabel label5;
    private JLabel label6;
    private JButton button8;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}


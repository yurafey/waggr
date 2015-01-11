package PresentationLayer;

import ServiceLayer.RealFeelService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by URI on 09.01.2015.
 */
public class RealFeelForm extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton ОКButton;
    private JButton cancelButton;
    private JPanel rootPanel;
    private JLabel label1;
    private RealFeelService realFeelService = null;

    public RealFeelForm(RealFeelService realFeelService) {
        this.realFeelService = realFeelService;
        label1.setText(label1.getText() + realFeelService.getCurrentUserCityName());
        setTitle("Добавить ощущуение о погоде");
        setSize(350,200);
        setLocationRelativeTo(null);
        setContentPane(rootPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        addListeners();
        setVisible(true);
    }

    private void addListeners() {
        ОКButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temperature = textField1.getText();
                String pressure = textField2.getText();
                String humidity = textField3.getText();
                String windSpeed = textField4.getText();
                int temperature1;
                int pressure1;
                int humidity1;
                float windSpeed1;
                if (temperature.isEmpty()||pressure.isEmpty()||humidity.isEmpty()||windSpeed.isEmpty()) {
                    JOptionPane.showMessageDialog(rootPanel,"Заполните все поля!","Ошибка",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                try {
                    temperature1 = Integer.parseInt(temperature);
                    pressure1 = Integer.parseInt(pressure);
                    humidity1 = Integer.parseInt(humidity);
                    windSpeed1 = Float.parseFloat(windSpeed);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(rootPanel,"Неправильные данные. Проверьте заполненные поля.","Ошибка",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (!realFeelService.newRealFeel(temperature1,pressure1,humidity1,windSpeed1)) {
                    JOptionPane.showMessageDialog(rootPanel,"Введенные данные слишком неправдоподобны.","Ошибка",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}

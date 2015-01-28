package PresentationLayer;

import java.awt.*;
import RemoteServiceLayer.RealFeelService;
import RemoteServiceLayer.RealFeelServiceImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import com.intellij.uiDesigner.core.*;

/**
 * Created by URI on 09.01.2015.
 */
public class RealFeelForm extends JFrame {
    private JPanel rootPanel;
    private RealFeelService realFeelService = null;

    public RealFeelForm(RealFeelService realFeelService) throws RemoteException {
        initComponents();
        this.realFeelService = realFeelService;
        label1.setText(label1.getText() + realFeelService.getCurrentUserCityName());
        setTitle("Добавить ощущуение о погоде");
        setSize(350,200);
        setResizable(false);
        setLocationRelativeTo(null);
        //setContentPane(rootPanel);
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
                try {
                    if (!realFeelService.newRealFeel(temperature1,pressure1,humidity1,windSpeed1)) {
                        JOptionPane.showMessageDialog(rootPanel,"Введенные данные слишком неправдоподобны.","Ошибка",JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                } catch (RemoteException e1) {
                    e1.printStackTrace();
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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - 123 123
        panel1 = new JPanel();
        label1 = new JLabel();
        JLabel label2 = new JLabel();
        textField1 = new JTextField();
        textField3 = new JTextField();
        JLabel label3 = new JLabel();
        textField4 = new JTextField();
        JLabel label4 = new JLabel();
        ОКButton = new JButton();
        cancelButton = new JButton();
        JLabel label5 = new JLabel();
        textField2 = new JTextField();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== panel1 ========
        {

            // JFormDesigner evaluation mark
            panel1.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), panel1.getBorder())); panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            panel1.setLayout(null);

            //---- label1 ----
            label1.setText("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u0441\u0432\u043e\u0438 \u043e\u0449\u0443\u0449\u0435\u043d\u0438\u044f \u043e \u043f\u043e\u0433\u043e\u0434\u0435 \u0432 \u0433\u043e\u0440\u043e\u0434\u0435 ");
            panel1.add(label1);
            label1.setBounds(5, 10, 380, label1.getPreferredSize().height);

            //---- label2 ----
            label2.setText("\u0422\u0435\u043c\u043f\u0435\u0440\u0430\u0442\u0443\u0440\u0430");
            panel1.add(label2);
            label2.setBounds(new Rectangle(new Point(15, 55), label2.getPreferredSize()));
            panel1.add(textField1);
            textField1.setBounds(155, 50, 191, textField1.getPreferredSize().height);
            panel1.add(textField3);
            textField3.setBounds(155, 80, 191, textField3.getPreferredSize().height);

            //---- label3 ----
            label3.setText("\u0412\u043b\u0430\u0436\u043d\u043e\u0441\u0442\u044c");
            panel1.add(label3);
            label3.setBounds(new Rectangle(new Point(15, 85), label3.getPreferredSize()));
            panel1.add(textField4);
            textField4.setBounds(155, 140, 191, textField4.getPreferredSize().height);

            //---- label4 ----
            label4.setText("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c \u0432\u0435\u0442\u0440\u0430");
            panel1.add(label4);
            label4.setBounds(new Rectangle(new Point(15, 145), label4.getPreferredSize()));

            //---- ОКButton ----
            ОКButton.setText("\u041e\u041a");
            panel1.add(ОКButton);
            ОКButton.setBounds(new Rectangle(new Point(295, 175), ОКButton.getPreferredSize()));

            //---- cancelButton ----
            cancelButton.setText("\u041e\u0442\u043c\u0435\u043d\u0430");
            panel1.add(cancelButton);
            cancelButton.setBounds(new Rectangle(new Point(340, 175), cancelButton.getPreferredSize()));

            //---- label5 ----
            label5.setText("\u0410\u0442\u043c\u043e\u0441\u0444\u0435\u0440\u043d\u043e\u0435 \u0434\u0430\u0432\u043b\u0435\u043d\u0438\u0435");
            panel1.add(label5);
            label5.setBounds(15, 115, label5.getPreferredSize().width, 18);
            panel1.add(textField2);
            textField2.setBounds(155, 110, 191, textField2.getPreferredSize().height);

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
        contentPane.add(panel1);
        panel1.setBounds(5, 5, 420, 210);

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
    private JPanel panel1;
    private JLabel label1;
    private JTextField textField1;
    private JTextField textField3;
    private JTextField textField4;
    private JButton ОКButton;
    private JButton cancelButton;
    private JTextField textField2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

package PresentationLayer;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import com.intellij.uiDesigner.core.*;

public class SetCountryDialog extends JFrame {
    //private JPanel contentPane;

    public SetCountryDialog(List<String> countryNames) {
//        super((java.awt.Frame) null, true);
//        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        initComponents();
        setTitle("Найдены совпадения");
        //setSize(300,200);
        setLocationRelativeTo(null);
        radioButton1.setVisible(false);
        radioButton2.setVisible(false);
        radioButton3.setVisible(false);
        radioButton4.setVisible(false);
        radioButton5.setVisible(false);
        if (countryNames.size()>=2){
            radioButton1.setVisible(true);
            radioButton1.setText(countryNames.get(0));
            radioButton2.setVisible(true);
            radioButton2.setText(countryNames.get(1));
        }
        if (countryNames.size()>=3){
            radioButton3.setVisible(true);
            radioButton3.setText(countryNames.get(2));
        }
        if (countryNames.size()>=4){
            radioButton4.setVisible(true);
            radioButton4.setText(countryNames.get(3));
        }
        if(countryNames.size()>=5){
            radioButton5.setVisible(true);
            radioButton5.setText(countryNames.get(4));
        }

        getRootPane().setDefaultButton(buttonOK);

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

        radioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                radioButton1.setSelected(true);
                radioButton2.setSelected(false);
                radioButton3.setSelected(false);
                radioButton4.setSelected(false);
                radioButton5.setSelected(false);
            }
        });
        radioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                radioButton1.setSelected(false);
                radioButton2.setSelected(true);
                radioButton3.setSelected(false);
                radioButton4.setSelected(false);
                radioButton5.setSelected(false);
            }
        });
        radioButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                radioButton1.setSelected(false);
                radioButton2.setSelected(false);
                radioButton4.setSelected(false);
                radioButton5.setSelected(false);
            }
        });
        radioButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                radioButton1.setSelected(false);
                radioButton2.setSelected(false);
                radioButton3.setSelected(false);
                radioButton5.setSelected(false);
            }
        });
        radioButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                radioButton1.setSelected(false);
                radioButton2.setSelected(false);
                radioButton3.setSelected(false);
                radioButton4.setSelected(false);
            }
        });
    }

    private void onOK() {
        dispose();
    }
    public String getSelectedName (){
        if (radioButton1.isSelected()) return radioButton1.getText();
        if (radioButton2.isSelected()) return radioButton2.getText();
        if (radioButton3.isSelected()) return radioButton3.getText();
        if (radioButton4.isSelected()) return radioButton4.getText();
        if (radioButton5.isSelected()) return radioButton5.getText();
        return null;
    }
    private void onCancel() {
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - 123 123
        mainPanel = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        buttonOK = new JButton();
        buttonCancel = new JButton();
        JPanel panel3 = new JPanel();
        radioButton1 = new JRadioButton();
        radioButton4 = new JRadioButton();
        radioButton3 = new JRadioButton();
        radioButton2 = new JRadioButton();
        radioButton5 = new JRadioButton();
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== mainPanel ========
        {
            mainPanel.setPreferredSize(new Dimension(300, 200));

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

                //======== panel2 ========
                {
                    panel2.setLayout(null);

                    //---- buttonOK ----
                    buttonOK.setText("OK");
                    panel2.add(buttonOK);
                    buttonOK.setBounds(0, 0, 66, buttonOK.getPreferredSize().height);

                    //---- buttonCancel ----
                    buttonCancel.setText("Cancel");
                    panel2.add(buttonCancel);
                    buttonCancel.setBounds(new Rectangle(new Point(76, 0), buttonCancel.getPreferredSize()));

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
                panel2.setBounds(new Rectangle(new Point(289, 0), panel2.getPreferredSize()));

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
            panel1.setBounds(10, 225, 431, panel1.getPreferredSize().height);

            //======== panel3 ========
            {
                panel3.setLayout(null);

                //---- radioButton1 ----
                radioButton1.setSelected(false);
                radioButton1.setText("RadioButton");
                panel3.add(radioButton1);
                radioButton1.setBounds(new Rectangle(new Point(0, 0), radioButton1.getPreferredSize()));

                //---- radioButton4 ----
                radioButton4.setText("RadioButton");
                panel3.add(radioButton4);
                radioButton4.setBounds(new Rectangle(new Point(0, 99), radioButton4.getPreferredSize()));

                //---- radioButton3 ----
                radioButton3.setText("RadioButton");
                panel3.add(radioButton3);
                radioButton3.setBounds(new Rectangle(new Point(0, 66), radioButton3.getPreferredSize()));

                //---- radioButton2 ----
                radioButton2.setText("RadioButton");
                panel3.add(radioButton2);
                radioButton2.setBounds(new Rectangle(new Point(0, 33), radioButton2.getPreferredSize()));

                //---- radioButton5 ----
                radioButton5.setEnabled(true);
                radioButton5.setText("RadioButton");
                panel3.add(radioButton5);
                radioButton5.setBounds(new Rectangle(new Point(0, 132), radioButton5.getPreferredSize()));

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel3.getComponentCount(); i++) {
                        Rectangle bounds = panel3.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel3.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel3.setMinimumSize(preferredSize);
                    panel3.setPreferredSize(preferredSize);
                }
            }
            mainPanel.add(panel3);
            panel3.setBounds(10, 60, 431, panel3.getPreferredSize().height);

            //---- label1 ----
            label1.setText("\u0414\u0430\u043d\u043d\u044b\u0439 \u0433\u043e\u0440\u043e\u0434 \u043d\u0430\u0439\u0434\u0435\u043d \u0432 \u043d\u0435\u0441\u043a\u043e\u043b\u044c\u043a\u0438\u0445 \u0441\u0442\u0440\u0430\u043d\u0430\u0445.");
            mainPanel.add(label1);
            label1.setBounds(new Rectangle(new Point(10, 12), label1.getPreferredSize()));

            //---- label2 ----
            label2.setText("\u0412\u044b\u0431\u0435\u0440\u0438\u0442\u0435 \u0441\u0442\u0440\u0430\u043d\u0443 \u0438\u0437 \u0441\u043f\u0438\u0441\u043a\u0430:");
            mainPanel.add(label2);
            label2.setBounds(new Rectangle(new Point(10, 37), label2.getPreferredSize()));

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
        mainPanel.setBounds(5, 5, 451, 266);

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
    private JButton buttonOK;
    private JButton buttonCancel;
    private JRadioButton radioButton1;
    private JRadioButton radioButton4;
    private JRadioButton radioButton3;
    private JRadioButton radioButton2;
    private JRadioButton radioButton5;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

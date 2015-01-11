package PresentationLayer;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class SetCountryDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JRadioButton radioButton5;

    public SetCountryDialog(List<String> countryNames) {
//        super((java.awt.Frame) null, true);
//        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setTitle("Найдены совпадения");
        setSize(300,200);
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

        setContentPane(contentPane);
        setModal(true);
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

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
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
}

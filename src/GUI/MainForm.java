package GUI;

import BusinessLogic.User;

import javax.swing.*;

/**
 * Created by yuraf_000 on 19.06.2014.
 */
public class MainForm extends JFrame{
    private JPanel rootPanel;

    private JTabbedPane tabbedPane1;
    private JLabel UserLabel;
    private JTextField DefaultCityField;
    private JButton saveCity;
    private JTextField textField1;
    private JButton forecastButton;
    private JTable table1;
    private User user = null;



    public MainForm(User mainuser) {
        setTitle("WAGGR Weather Aggregator");
        user = mainuser;
        UserLabel.setText(UserLabel.getText()+ user.GetUserName()+" "+user.GetUserSurname());
        //TextField.setText(user.GetUserCity());
        DefaultCityField.setText(user.GetUserCity());
        setContentPane(rootPanel);
        //table1.setModel();

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }


}

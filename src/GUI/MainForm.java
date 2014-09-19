package GUI;

import BusinessLogic.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

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
    private JTable tableCurrentWeather;
    private CurrentWeatherTableContainer currentWeather = null;
    private User user = null;



    public MainForm(User mainuser) {
        setTitle("WAGGR Weather Aggregator");
        user = mainuser;
        UserLabel.setText(UserLabel.getText()+ user.GetUserName()+" "+user.GetUserSurname());
        //TextField.setText(user.GetUserCity());
        DefaultCityField.setText(user.GetUserCity()+", "+user.GetUserCountry());
        setContentPane(rootPanel);
        //tableCurrentWeather.setModel();
        currentWeather = new CurrentWeatherTableContainer(user.GetUserCity(),user.GetUserCountry());

        TableModel myDataModel = new AbstractTableModel() {
            @Override
            public int getColumnCount() { return currentWeather.getColNames().length ; }

            @Override
            public int getRowCount() { return currentWeather.getRows().size(); }

            @Override
            public Object getValueAt(int row, int col) {
                return currentWeather.GetValueAt(row,col);
            }

            @Override
            public String getColumnName(int column) {return currentWeather.getColNames()[column];}

            @Override
            public boolean isCellEditable(int row, int col) {return false;}

            @Override
            public void setValueAt(Object aValue, int row, int column) {}
            @Override
            public Class getColumnClass(int c) {return (String.class);}
        };

        tableCurrentWeather.setModel(myDataModel);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }


}


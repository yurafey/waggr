package GUI;

import BusinessLogic.User;
import DataAccessLayer.DBConnector;
import ServiceLayer.CurrentWeatherTableContainer;
import ServiceLayer.WeatherForecastTableContainer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuraf_000 on 19.06.2014.
 */
public class MainForm extends JFrame{
    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private JLabel UserLabel;
    private JTextField DefaultCityField;
    private JButton saveCity;
    private JTextField searchCityField;
    private JButton forecastButton;
    private JTable tableCurrentWeather;
    private JScrollPane JscrollPane1;
    private JPanel JPanel1;
    private JButton ExitProfileButton;
    private JTable table5dayWeatherUA;
    private JTable table5dayWeatherYandex;
    private CurrentWeatherTableContainer currentWeather = null;
    private WeatherForecastTableContainer weatherForecast = null;
    private User user = null;
    private DBConnector db = new DBConnector();



    public MainForm(User mainUser) {
        setTitle("WAGGR Weather Aggregator");
        setSize(800,450);
        setLocationRelativeTo(null);
        setContentPane(rootPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        user = mainUser;
        UserLabel.setText(UserLabel.getText()+ user.getUserName()+" "+user.getUserSurname());
        DefaultCityField.setText(user.getUserCity()+", "+user.getUserCountry());
        searchCityField.setText(DefaultCityField.getText());
        //currentWeather = new CurrentWeatherTableContainer(user.getUserCity(),user.getUserCountry());
        updateTableModels(user.getUserCity(), user.getUserCountry());
        addListeners();
        pack();
        setVisible(true);
    }

    private void addListeners(){
        DefaultCityField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultCityField.setText("");
            }
        });
        DefaultCityField.addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent focusEvent) {
                //пока не используется
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (DefaultCityField.getText().isEmpty()){
                    DefaultCityField.setText(user.getUserCity()+", "+user.getUserCountry());
                }
            }
        });
        searchCityField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String searchText = searchCityField.getText();
                if(searchText.contains(",")) {
                    searchCityField.setText(searchText.substring(0,searchText.indexOf(',')));
                }
            }
        });
        searchCityField.addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent focusEvent) {

            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

            }
        });

        ExitProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();

                AuthorizationForm newform = new AuthorizationForm();
            }
        });

        saveCity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (DefaultCityField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(saveCity,"Введите название города в поле избранного города","Ошибка",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                List<String> countryNames = new ArrayList<String>();
                String cityNameText = DefaultCityField.getText();
                if(cityNameText.contains(",")) {
                    cityNameText = cityNameText.substring(0,cityNameText.indexOf(','));
                }
                countryNames = db.CheckCity(cityNameText);
                if (countryNames.isEmpty()) {
                    JOptionPane.showMessageDialog(saveCity,"Город не найден","Ошибка",JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else if (countryNames.size()>1){
                    SetCountryDialog dialog = new SetCountryDialog(countryNames);
                    dialog.pack();
                    dialog.setVisible(true);
                    String selectedCountryName = dialog.getSelectedName();
                    if(db.setUserCity(user.getUserLogin(),cityNameText,selectedCountryName)) {
                        DefaultCityField.setText(String.format("%s, %s", cityNameText, selectedCountryName));
                        searchCityField.setText(DefaultCityField.getText());
                        updateUser();
                        updateTableModels(user.getUserCity(), user.getUserCountry());
                        return;
                    }
                }
                String selectedCountryName = countryNames.get(0);
                if (db.setUserCity(user.getUserLogin(),cityNameText,selectedCountryName)) DefaultCityField.setText(String.format("%s, %s",cityNameText,selectedCountryName));
                searchCityField.setText(DefaultCityField.getText());
                updateUser();
                updateTableModels(user.getUserCity(), user.getUserCountry());
            }
        });

        forecastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String countryName = null;
                if (searchCityField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(searchCityField,"Введите название города для поиска","Ошибка",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                List<String> countryNames = new ArrayList<String>();
                String cityNameText = searchCityField.getText();
                if(cityNameText.contains(",")) {
                    cityNameText = cityNameText.substring(0,cityNameText.indexOf(','));
                }
                countryNames = db.CheckCity(cityNameText);
                if (countryNames.isEmpty()) {
                    JOptionPane.showMessageDialog(searchCityField, "Город не найден", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else if (countryNames.size()>1){
                    SetCountryDialog dialog = new SetCountryDialog(countryNames);
                    dialog.pack();
                    dialog.setVisible(true);
                    countryName = dialog.getSelectedName();
                    updateTableModels(cityNameText, countryName);
                    searchCityField.setText(String.format("%s, %s",cityNameText,countryName));
                }
                countryName = countryNames.get(0);
                updateTableModels(cityNameText, countryName);
                searchCityField.setText(String.format("%s, %s",cityNameText,countryName));

            }
        });
    }
    private boolean updateTableModels(String cityName, String countryName){
        //currentWeather.onClose();
         currentWeather = new CurrentWeatherTableContainer(cityName,countryName);
         weatherForecast = new WeatherForecastTableContainer(cityName,countryName);

        TableModel currentWeatherTableModel = new AbstractTableModel() {
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
        tableCurrentWeather.setModel(currentWeatherTableModel);

        TableModel weather5dayTableModelYandex = new AbstractTableModel() {
            @Override
            public int getColumnCount() { return weatherForecast.getColNames().length ; }

            @Override
            public int getRowCount() { return weatherForecast.getRowsYandex().size(); }

            @Override
            public Object getValueAt(int row, int col) { return weatherForecast.getValueAtYandex(row,col);
            }

            @Override
            public String getColumnName(int column) {return weatherForecast.getColNames()[column];}

            @Override
            public boolean isCellEditable(int row, int col) {return false;}

            @Override
            public void setValueAt(Object aValue, int row, int column) {}
            @Override
            public Class getColumnClass(int c) {return (String.class);}

        };
        table5dayWeatherYandex.setModel(weather5dayTableModelYandex);

        TableModel weather5dayTableModelWUA = new AbstractTableModel() {
            @Override
            public int getColumnCount() { return weatherForecast.getColNames().length ; }

            @Override
            public int getRowCount() { return weatherForecast.getRowsWeatherUA().size(); }

            @Override
            public Object getValueAt(int row, int col) { return weatherForecast.getValueAtWeatherUA(row,col);
            }

            @Override
            public String getColumnName(int column) {return weatherForecast.getColNames()[column];}

            @Override
            public boolean isCellEditable(int row, int col) {return false;}

            @Override
            public void setValueAt(Object aValue, int row, int column) {}
            @Override
            public Class getColumnClass(int c) {return (String.class);}

        };
        table5dayWeatherUA.setModel(weather5dayTableModelWUA);

        //здесь будет обновление 9ти дней

        weatherForecast.onClose();
        currentWeather.onClose();
        return true;
    }
    private void updateUser (){
        user = db.getUser(user.getUserLogin());
    }


}


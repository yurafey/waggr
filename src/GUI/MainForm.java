package GUI;

import ServiceLayer.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    private JTextField defaultCityField;
    private JButton saveCity;
    private JTextField searchCityField;
    private JButton forecastButton;
    private JTable tableCurrentWeather;
    private JScrollPane JscrollPane1;
    private JPanel JPanel1;
    private JButton exitProfileButton;
    private JTable table5dayWeatherUA;
    private JTable table5dayWeatherYandex;
    private JLabel label1;
    private WeatherCurrentTableService currentWeather = null;
    private WeatherForecastTableService weatherForecast = null;
    private WeatherForecastUpdateService weatherForecastUpdateService = null;
    private UsersService usersService = new UsersService();
    private CityService cityService = new CityService();
    //final MainForm rootForm = this;



    public MainForm(Object user, WeatherForecastUpdateService weatherForecastUpdateService) {
        this.weatherForecastUpdateService = weatherForecastUpdateService;
        usersService.setCurrentUser(user);
        setTitle("WAGGR Weather Aggregator");
        setSize(800,450);
        setLocationRelativeTo(null);
        setContentPane(rootPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        UserLabel.setText(usersService.getCurrentUserName()+" "+usersService.getCurrentUserSurname());
        String userCity = usersService.getCurrentUserCity();
        String userCountry = usersService.getCurrentUserCountry();
        String currentCountryCity = userCity+", "+userCountry;
        defaultCityField.setText(currentCountryCity);
        searchCityField.setText(currentCountryCity);
        updateTableModels(userCity, userCountry);
        addListeners();
        pack();
        setVisible(true);
    }

    private void addListeners(){
        defaultCityField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                defaultCityField.setText("");
            }
        });
        defaultCityField.addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent focusEvent) {
                //
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                if (defaultCityField.getText().isEmpty()){
                    defaultCityField.setText(usersService.getCurrentUserCity()+", "+usersService.getCurrentUserCountry());
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

        exitProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        AuthorizationForm authorizationForm = new AuthorizationForm(weatherForecastUpdateService);
                    }
                });
                dispose();
            }
        });

        saveCity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (defaultCityField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(saveCity, "Введите название города в поле избранного города", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                List<String> countryNames = new ArrayList<String>();
                String cityNameText = getTrimmedCityField(defaultCityField);
                countryNames = cityService.checkCityExists(cityNameText);
                if (countryNames == null) {
                    JOptionPane.showMessageDialog(saveCity, "Город не найден", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else if (countryNames.size() > 1) {
                    SetCountryDialog dialog = new SetCountryDialog(countryNames);
                    dialog.pack();
                    dialog.setVisible(true);
                    String selectedCountryName = dialog.getSelectedName();
                    if (usersService.setCurrentUserLocation(cityNameText, selectedCountryName)) {
                        String selectedLocation = String.format("%s, %s", cityNameText, selectedCountryName);
                        defaultCityField.setText(selectedLocation);
                        searchCityField.setText(selectedLocation);
                        updateTableModels(usersService.getCurrentUserCity(), usersService.getCurrentUserCountry());
                        return;
                    }
                } else {
                    String countryName = countryNames.get(0);
                    if (usersService.setCurrentUserLocation(cityNameText, countryName)) {
                        String selectedLocation = String.format("%s, %s", cityNameText, countryName);
                        defaultCityField.setText(selectedLocation);
                        searchCityField.setText(selectedLocation);
                        updateTableModels(usersService.getCurrentUserCity(), usersService.getCurrentUserCountry());
                    }
                }
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
                String cityNameText = getTrimmedCityField(searchCityField);
                countryNames = cityService.checkCityExists(cityNameText);
                if (countryNames==null) {
                    JOptionPane.showMessageDialog(searchCityField, "Город не найден", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else if (countryNames.size()>1){
                    SetCountryDialog dialog = new SetCountryDialog(countryNames);
                    dialog.pack();
                    dialog.setVisible(true);
                    countryName = dialog.getSelectedName();
                    System.out.println(countryName);
                    updateTableModels(cityNameText, countryName);
                    searchCityField.setText(String.format("%s, %s",cityNameText,countryName));
                } else {
                    countryName = countryNames.get(0);
                    updateTableModels(cityNameText, countryName);
                    searchCityField.setText(String.format("%s, %s", cityNameText, countryName));
                }
            }
        });
    }

    private String getTrimmedCityField(JTextField field) {
        String cityNameText = field.getText();
        if(cityNameText.contains(",")) {
            return cityNameText.substring(0,cityNameText.indexOf(','));
        } else {
            return cityNameText;
        }
    }

    private boolean updateTableModels(String cityName, String countryName){
        currentWeather = new WeatherCurrentTableService(usersService.getCurrentUserLogin(),cityName,countryName);
        weatherForecast = new WeatherForecastTableService(cityName,countryName);

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



        TableModel weatherForecastTableModelYandex = new AbstractTableModel() {
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

        table5dayWeatherYandex.setModel(weatherForecastTableModelYandex);
        table5dayWeatherYandex.getColumnModel().getColumn(0).setMaxWidth(110);
        table5dayWeatherYandex.getColumnModel().getColumn(0).setMinWidth(110);
        table5dayWeatherYandex.getColumnModel().getColumn(0).setPreferredWidth(110);

        TableModel weatherForecastTableModelWUA = new AbstractTableModel() {
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
        table5dayWeatherUA.setModel(weatherForecastTableModelWUA);
        table5dayWeatherUA.getColumnModel().getColumn(0).setMaxWidth(110);
        table5dayWeatherUA.getColumnModel().getColumn(0).setMinWidth(110);
        table5dayWeatherUA.getColumnModel().getColumn(0).setPreferredWidth(110);

        ListSelectionModel selModel = table5dayWeatherYandex.getSelectionModel();

        selModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                List <String> value= new ArrayList();
                int[] selectedRows = table5dayWeatherYandex.getSelectedRows();
                TableModel model = table5dayWeatherYandex.getModel();
                TableModel model2 = table5dayWeatherUA.getModel();
                if (!model.getValueAt(0,0).equals("N/A")) {
                    if (!model2.getValueAt(0,0).equals("N/A")) {
                        if (selectedRows.length==1) {
                            value.add((String)model.getValueAt(selectedRows[0], 0));
                        } else {
                            for(int i = 0; i < selectedRows.length; i++) {
                                int selIndex = selectedRows[i];
                                String row = (String)model.getValueAt(selIndex, 0);
                                String substr = row.substring(0,row.indexOf('(')-1);
                                value.add(substr);
                                //value.add(row);
                            }
                        }
                        List<Integer> rows = new ArrayList<Integer>();
                        int vs = value.size();
                        for (int i = 0; i < model2.getRowCount(); i++) {
                            for (String v : value) {
                                String toCompare = (String) model2.getValueAt(i,0);
                                if (vs>1) toCompare = toCompare.substring(0,toCompare.indexOf('(')-1);
                                if (v.equals(toCompare)) {
                                    rows.add(i);
                                }
                            }
                        }
                        int rs = rows.size();
                        ListSelectionModel selectionModel =  table5dayWeatherUA.getSelectionModel();
                        if (rs!=0){
                            selectionModel.setSelectionInterval(rows.get(0), (rs==1)?rows.get(0):rows.get(rs-1));
                        } else {
                            selectionModel.clearSelection();
                        }
                    }
                }
            }
        });

        weatherForecast.onClose();
        currentWeather.onClose();
        return true;
    }
//    private void updateUser (){
//        user = db.getUser(user.getUserLogin());
//    }


}


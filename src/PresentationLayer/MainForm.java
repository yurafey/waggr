package PresentationLayer;

import java.awt.*;
import RemoteServiceLayer.*;
import ServiceLayer.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuraf_000 on 19.06.2014.
 */
public class MainForm  extends  JFrame {

    //private JPanel rootPanel;
    private AdminService adminService = null;
    private RealFeelService realFeelService = null;
    private UsersService usersService = null;
    private WeatherCurrentTableService weatherCurrentTableService = null;
    private WeatherForecastTableService weatherForecastTableService = null;
    //private WeatherService weatherService = null;

    public MainForm(Object user, AdminService adminService, RealFeelService realFeelService, UsersService usersService,WeatherForecastTableService weatherForecastTableService,WeatherCurrentTableService weatherCurrentTableService) throws RemoteException {
        initComponents();
        this.adminService = adminService;
        this.realFeelService = realFeelService;
        this.usersService = usersService;
        this.weatherCurrentTableService = weatherCurrentTableService;
        this.weatherForecastTableService = weatherForecastTableService;
        usersService.setCurrentUser(user);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        UserLabel.setText(usersService.getCurrentUserName()+" "+ usersService.getCurrentUserSurname());
        String userCity = usersService.getCurrentUserCity();
        String userCountry = usersService.getCurrentUserCountry();
        String currentCountryCity = userCity+", "+userCountry;
        defaultCityField.setText(currentCountryCity);
        searchCityField.setText(currentCountryCity);
        updateTableModels(userCity, userCountry);
        addListeners();
        //pack();
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
                    try {
                        defaultCityField.setText(usersService.getCurrentUserCity()+", "+ usersService.getCurrentUserCountry());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
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
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AuthorizationForm authorizationForm = new AuthorizationForm(adminService,realFeelService,usersService,weatherForecastTableService,weatherCurrentTableService);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (NotBoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
                try {
                    countryNames = adminService.checkCityExists(cityNameText);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (countryNames == null) {
                    JOptionPane.showMessageDialog(saveCity, "Город не найден", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else if (countryNames.size() > 1) {
                    SetCountryDialog dialog = new SetCountryDialog(countryNames);
                    dialog.pack();
                    dialog.setVisible(true);
                    String selectedCountryName = dialog.getSelectedName();
                    try {
                        if (usersService.setCurrentUserLocation(cityNameText, selectedCountryName)) {
                            String selectedLocation = String.format("%s, %s", cityNameText, selectedCountryName);
                            defaultCityField.setText(selectedLocation);
                            searchCityField.setText(selectedLocation);
                            updateTableModels(usersService.getCurrentUserCity(), usersService.getCurrentUserCountry());
                            return;
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    String countryName = countryNames.get(0);
                    try {
                        if (usersService.setCurrentUserLocation(cityNameText, countryName)) {
                            String selectedLocation = String.format("%s, %s", cityNameText, countryName);
                            defaultCityField.setText(selectedLocation);
                            searchCityField.setText(selectedLocation);
                            updateTableModels(usersService.getCurrentUserCity(), usersService.getCurrentUserCountry());
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        forecastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String countryName;
                if (searchCityField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(searchCityField,"Введите название города для поиска","Ошибка",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                List<String> countryNames = new ArrayList<String>();
                String cityNameText = getTrimmedCityField(searchCityField);
                try {
                    countryNames = adminService.checkCityExists(cityNameText);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (countryNames==null) {
                    JOptionPane.showMessageDialog(searchCityField, "Город не найден", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else if (countryNames.size()>1){
                    SetCountryDialog dialog = new SetCountryDialog(countryNames);
                    dialog.pack();
                    dialog.setVisible(true);
                    countryName = dialog.getSelectedName();
                    System.out.println(countryName);
                    try {
                        updateTableModels(cityNameText, countryName);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    searchCityField.setText(String.format("%s, %s",cityNameText,countryName));
                } else {
                    countryName = countryNames.get(0);
                    try {
                        updateTableModels(cityNameText, countryName);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    searchCityField.setText(String.format("%s, %s", cityNameText, countryName));
                }
            }
        });

        realFeelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            realFeelService.initializeRealFeel(usersService.getCurrentUserLogin());
                            RealFeelForm realFeelForm = new RealFeelForm(realFeelService);
                        } catch (RemoteException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
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

    private boolean updateTableModels(String cityName, String countryName) throws RemoteException {
        weatherCurrentTableService.initializeWeatherCurrentTableService(usersService.getCurrentUserLogin(), cityName, countryName);
        weatherForecastTableService.initializeWeatherForecastTableService(cityName, countryName);

        TableModel currentWeatherTableModel = new AbstractTableModel() {
            @Override
            public int getColumnCount() {
                return weatherCurrentTableService.getColNames().length ;

            }

            @Override
            public int getRowCount() {
                return weatherCurrentTableService.getRows().size();

            }

            @Override
            public Object getValueAt(int row, int col) {
                return weatherCurrentTableService.GetValueAt(row,col);

            }

            @Override
            public String getColumnName(int column) {
                return weatherCurrentTableService.getColNames()[column];

            }

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
            public int getColumnCount() {
                return weatherForecastTableService.getColNames().length ;

            }

            @Override
            public int getRowCount() {
                return weatherForecastTableService.getRowsYandex().size();

            }

            @Override
            public Object getValueAt(int row, int col) {
                return weatherForecastTableService.getValueAtYandex(row,col);

            }

            @Override
            public String getColumnName(int column) {
                return weatherForecastTableService.getColNames()[column];

            }

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
            public int getColumnCount() {
                return weatherForecastTableService.getColNames().length ;
            }

            @Override
            public int getRowCount() {
                return weatherForecastTableService.getRowsWeatherUA().size();
            }

            @Override
            public Object getValueAt(int row, int col) {
                return weatherForecastTableService.getValueAtWeatherUA(row,col);
            }

            @Override
            public String getColumnName(int column) {
                return weatherForecastTableService.getColNames()[column];
            }

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
                List<String> value = new ArrayList();
                int[] selectedRows = table5dayWeatherYandex.getSelectedRows();
                TableModel model = table5dayWeatherYandex.getModel();
                TableModel model2 = table5dayWeatherUA.getModel();
                if (!model.getValueAt(0, 0).equals("N/A")) {
                    if (!model2.getValueAt(0, 0).equals("N/A")) {
                        if (selectedRows.length == 1) {
                            value.add((String) model.getValueAt(selectedRows[0], 0));
                        } else {
                            for (int i = 0; i < selectedRows.length; i++) {
                                int selIndex = selectedRows[i];
                                String row = (String) model.getValueAt(selIndex, 0);
                                String substr = row.substring(0, row.indexOf('(') - 1);
                                value.add(substr);
                                //value.add(row);
                            }
                        }
                        List<Integer> rows = new ArrayList<Integer>();
                        int vs = value.size();
                        for (int i = 0; i < model2.getRowCount(); i++) {
                            for (String v : value) {
                                String toCompare = (String) model2.getValueAt(i, 0);
                                if (vs > 1) toCompare = toCompare.substring(0, toCompare.indexOf('(') - 1);
                                if (v.equals(toCompare)) {
                                    rows.add(i);
                                }
                            }
                        }
                        int rs = rows.size();
                        ListSelectionModel selectionModel = table5dayWeatherUA.getSelectionModel();
                        if (rs != 0) {
                            selectionModel.setSelectionInterval(rows.get(0), (rs == 1) ? rows.get(0) : rows.get(rs - 1));
                        } else {
                            selectionModel.clearSelection();
                        }
                    }
                }
            }
        });
        return true;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - 123 123
        tabbedPane1 = new JTabbedPane();
        JPanel1 = new JPanel();
        JscrollPane1 = new JScrollPane();
        tableCurrentWeather = new JTable();
        JPanel panel1 = new JPanel();
        JScrollPane scrollPane1 = new JScrollPane();
        table5dayWeatherUA = new JTable();
        JScrollPane scrollPane2 = new JScrollPane();
        table5dayWeatherYandex = new JTable();
        JLabel label2 = new JLabel();
        JLabel label3 = new JLabel();
        realFeelButton = new JButton();
        JLabel label5 = new JLabel();
        searchCityField = new JTextField();
        forecastButton = new JButton();
        label1 = new JLabel();
        exitProfileButton = new JButton();
        UserLabel = new JLabel();
        JLabel label4 = new JLabel();
        defaultCityField = new JTextField();
        saveCity = new JButton();

        //======== this ========
        setTitle("Waggr - Weather Aggregator");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== tabbedPane1 ========
        {

            //======== JPanel1 ========
            {

                // JFormDesigner evaluation mark
                JPanel1.setBorder(new javax.swing.border.CompoundBorder(
                    new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                        "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                        java.awt.Color.red), JPanel1.getBorder())); JPanel1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

                JPanel1.setLayout(null);

                //======== JscrollPane1 ========
                {

                    //---- tableCurrentWeather ----
                    tableCurrentWeather.setRowSelectionAllowed(true);
                    JscrollPane1.setViewportView(tableCurrentWeather);
                }
                JPanel1.add(JscrollPane1);
                JscrollPane1.setBounds(0, 0, 975, 326);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < JPanel1.getComponentCount(); i++) {
                        Rectangle bounds = JPanel1.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = JPanel1.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    JPanel1.setMinimumSize(preferredSize);
                    JPanel1.setPreferredSize(preferredSize);
                }
            }
            tabbedPane1.addTab("\u0421\u0435\u0439\u0447\u0430\u0441", JPanel1);

            //======== panel1 ========
            {
                panel1.setLayout(null);

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(table5dayWeatherUA);
                }
                panel1.add(scrollPane1);
                scrollPane1.setBounds(493, 25, 482, 301);

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(table5dayWeatherYandex);
                }
                panel1.add(scrollPane2);
                scrollPane2.setBounds(0, 25, 483, 301);

                //---- label2 ----
                label2.setText("Yandex");
                panel1.add(label2);
                label2.setBounds(new Rectangle(new Point(222, 2), label2.getPreferredSize()));

                //---- label3 ----
                label3.setText("WeatherUA");
                panel1.add(label3);
                label3.setBounds(new Rectangle(new Point(704, 2), label3.getPreferredSize()));

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
            tabbedPane1.addTab("\u041f\u0440\u043e\u0433\u043d\u043e\u0437", panel1);
        }
        contentPane.add(tabbedPane1);
        tabbedPane1.setBounds(5, 80, 977, 350);

        //---- realFeelButton ----
        realFeelButton.setText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u043e\u0449\u0443\u0449\u0435\u043d\u0438\u0435");
        contentPane.add(realFeelButton);
        realFeelButton.setBounds(760, 40, 211, realFeelButton.getPreferredSize().height);

        //---- label5 ----
        label5.setText("\u041f\u043e\u0433\u043e\u0434\u0430 \u0432 \u0433\u043e\u0440\u043e\u0434\u0435 ");
        contentPane.add(label5);
        label5.setBounds(new Rectangle(new Point(10, 50), label5.getPreferredSize()));
        contentPane.add(searchCityField);
        searchCityField.setBounds(130, 45, 160, searchCityField.getPreferredSize().height);

        //---- forecastButton ----
        forecastButton.setText("\u041d\u0430\u0439\u0442\u0438");
        contentPane.add(forecastButton);
        forecastButton.setBounds(290, 40, 102, forecastButton.getPreferredSize().height);

        //---- label1 ----
        label1.setText("\u041f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044c:");
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(10, 15), label1.getPreferredSize()));

        //---- exitProfileButton ----
        exitProfileButton.setText("\u0412\u044b\u0439\u0442\u0438");
        contentPane.add(exitProfileButton);
        exitProfileButton.setBounds(290, 5, 100, exitProfileButton.getPreferredSize().height);

        //---- UserLabel ----
        UserLabel.setText("");
        contentPane.add(UserLabel);
        UserLabel.setBounds(120, 20, 100, 14);

        //---- label4 ----
        label4.setText("\u0418\u0437\u0431\u0440\u0430\u043d\u043d\u044b\u0439 \u0433\u043e\u0440\u043e\u0434:");
        contentPane.add(label4);
        label4.setBounds(new Rectangle(new Point(400, 15), label4.getPreferredSize()));
        contentPane.add(defaultCityField);
        defaultCityField.setBounds(535, 10, 220, defaultCityField.getPreferredSize().height);

        //---- saveCity ----
        saveCity.setText("\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c / \u041d\u0430\u0439\u0442\u0438 \u043f\u0440\u043e\u0433\u043d\u043e\u0437");
        contentPane.add(saveCity);
        saveCity.setBounds(760, 5, 212, saveCity.getPreferredSize().height);

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
    private JTabbedPane tabbedPane1;
    private JPanel JPanel1;
    private JScrollPane JscrollPane1;
    private JTable tableCurrentWeather;
    private JTable table5dayWeatherUA;
    private JTable table5dayWeatherYandex;
    private JButton realFeelButton;
    private JTextField searchCityField;
    private JButton forecastButton;
    private JLabel label1;
    private JButton exitProfileButton;
    private JLabel UserLabel;
    private JTextField defaultCityField;
    private JButton saveCity;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}


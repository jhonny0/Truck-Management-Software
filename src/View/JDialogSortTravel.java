package View;

import Modules.Driver;
import Modules.InterstateTravel;
import Modules.OutStateTravel;
import Modules.Trucks;
import Presenter.PresenterTravelSort;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.GregorianCalendar;

public class JDialogSortTravel extends JDialog {

    final private String[] COLUMNS_TITTLE = {"Code ID", "Date and Time", "Duration", "Destination", "Truck", "Driver"};


    //////

    private PresenterTravelSort presenterTravelSort;


    private JCheckBox checkBoxInterstateTravel;
    private JCheckBox checkBoxOutOfStateTravel;

    private JCheckBox checkBoxTruck;
    private JComboBox comboBoxTruck;

    private JCheckBox checkBoxDriver;
    private JComboBox comboBoxDriver;

    private JCheckBox checkBoxDestination;
    private JTextField textFieldDestination;

    private JCheckBox checkBoxDate;
    private JSpinner spinnerDate;

    private JButton buttonFilter;

    private JTable tableTravel;
    private DefaultTableModel model;

    public JDialogSortTravel() {

    }

    public JDialogSortTravel(PresenterTravelSort presenterTravelSort) {
        super();
        this.presenterTravelSort = presenterTravelSort;
        initComponents();
    }

    private void initComponents() {
        setTitle("Filter Travel ");

        /////Travel type , upper left

        checkBoxInterstateTravel = new JCheckBox(InterstateTravel.TYPE_OF_TRAVEL);
        checkBoxOutOfStateTravel = new JCheckBox(OutStateTravel.TYPE_OF_TRAVEL);
        checkBoxInterstateTravel.setSelected(true);
        checkBoxOutOfStateTravel.setSelected(true);


        JPanel panelTravel = new JPanel(new GridLayout(2, 1));
        panelTravel.add(checkBoxInterstateTravel);
        panelTravel.add(checkBoxOutOfStateTravel);
        panelTravel.setBorder(BorderFactory.createTitledBorder("Type Of Travel"));

        JPanel panelUpperLeft = new JPanel(new GridLayout(1, 1));
        panelUpperLeft.add(panelTravel);


        ///DRIVER and Truck , upper central

        checkBoxDriver = new JCheckBox("Driver");
        checkBoxDriver.setSelected(true);
        comboBoxDriver = new JComboBox();
        JPanel panelDriver = new JPanel(new GridLayout(1, 2, 0, 0));
        panelDriver.add(checkBoxDriver);
        panelDriver.add(comboBoxDriver);


        checkBoxTruck = new JCheckBox("Truck");
        checkBoxTruck.setSelected(true);
        comboBoxTruck = new JComboBox();
        JPanel panelTruck = new JPanel(new GridLayout(1, 2, 0, 0));
        panelTruck.add(checkBoxTruck);
        panelTruck.add(comboBoxTruck);

        JPanel panelSupportCentral = new JPanel(new GridLayout(2, 1, 0, 5));

        panelSupportCentral.add(panelDriver);
        panelSupportCentral.add(panelTruck);

        JPanel panelUpperCentral = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelUpperCentral.add(panelSupportCentral);


        ///Destination and Date
        checkBoxDestination = new JCheckBox("Destination");
        checkBoxDestination.setSelected(true);
        textFieldDestination = new JTextField();
        checkBoxDate = new JCheckBox("Date");
        checkBoxDate.setSelected(true);
        spinnerDate = new JSpinner();


        GregorianCalendar dateInit = new GregorianCalendar();
        GregorianCalendar dateActual = ((GregorianCalendar) dateInit.clone());
        dateInit.set(GregorianCalendar.DATE, dateInit.get(GregorianCalendar.DATE) - 1);
        GregorianCalendar dateFinal = (GregorianCalendar) dateInit.clone();
        dateFinal.add(GregorianCalendar.YEAR, 1);

        spinnerDate.setModel(new SpinnerDateModel(dateActual.getTime(), dateInit.getTime(), dateFinal.getTime(), GregorianCalendar.DATE));
        spinnerDate.setEditor(new JSpinner.DateEditor(spinnerDate, "mm/dd/yyyy"));


        JPanel panelSupportRight = new JPanel(new GridLayout(2, 2, 0, 5));
        panelSupportRight.add(checkBoxDestination);
        panelSupportRight.add(textFieldDestination);
        panelSupportRight.add(checkBoxDate);
        panelSupportRight.add(spinnerDate);

        JPanel panelUpperRight = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelUpperRight.add(panelSupportRight);

        ////Panel button Filter
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonFilter = new JButton("Filter");
        panelButton.add(buttonFilter);

        ///Panel superior complete
        JPanel panelSuperiorComplete = new JPanel(new BorderLayout(5, 5));
        panelSuperiorComplete.add(panelUpperLeft, BorderLayout.WEST);
        panelSuperiorComplete.add(panelUpperCentral, BorderLayout.CENTER);
        panelSuperiorComplete.add(panelUpperRight, BorderLayout.EAST);
        panelSuperiorComplete.add(panelButton, BorderLayout.SOUTH);


        /// lower panels
        tableTravel = new JTable();
        model = new DefaultTableModel(new Object[][]{}, COLUMNS_TITTLE) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableTravel.setModel(model);
        JScrollPane paneTable = new JScrollPane(tableTravel);

        //////Window
        setLayout(new BorderLayout(0, 5));

        add(panelSuperiorComplete, BorderLayout.NORTH);
        add(paneTable, BorderLayout.CENTER);


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                presenterTravelSort.enableMainWindow();
            }
        });

        this.checkBoxDriver.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (checkBoxDriver.isSelected())
                    comboBoxDriver.setEnabled(true);
                else comboBoxDriver.setEnabled(false);
            }
        });

        this.comboBoxTruck.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (checkBoxTruck.isSelected())
                    comboBoxTruck.setEnabled(true);
                else
                    comboBoxTruck.setEnabled(false);
            }
        });

        this.checkBoxDate.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (checkBoxDate.isSelected())
                    spinnerDate.setEnabled(true);
                else
                    spinnerDate.setEnabled(false);
            }
        });

        buttonFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenterTravelSort.applyFilter();
            }
        });

        /////----------------------------------------------
        setBounds(300, 300, 675, 600);
        setMinimumSize(new Dimension(650, 600));
        setVisible(true);

    }

    public String getTravelType() {
        String goBack = null;
        if (checkBoxInterstateTravel.isSelected() && !checkBoxOutOfStateTravel.isSelected())
            goBack = InterstateTravel.TYPE_OF_TRAVEL;
        else if (!checkBoxInterstateTravel.isSelected() && checkBoxOutOfStateTravel.isSelected())
            goBack = OutStateTravel.TYPE_OF_TRAVEL;
        else if (checkBoxInterstateTravel.isSelected() && checkBoxOutOfStateTravel.isSelected())
            goBack = "";
        return goBack;
    }

    public String getDriverLicense() throws Exception {
        if (checkBoxDriver.isSelected()) {
            if (this.comboBoxDriver.getModel().getSize() == 0) {
                throw new Exception("There are no drivers in the data base");
            }

            return (String) comboBoxDriver.getSelectedItem();
        }
        return null;
    }

    public String getLicensePlate()throws Exception{
        if (this.comboBoxTruck.getModel().getSize() == 0){
            throw new Exception("There are not trucks in the data base");
        }
        if (checkBoxTruck.isSelected()){
            if (this.comboBoxTruck.getModel().getSize() == 0){
                throw new Exception("There are not trucks in the data base");
            }
            return (String) comboBoxTruck.getSelectedItem();
        }
        return null;

    }

    public String getDestination() throws Exception{
        if (checkBoxDestination.isSelected()){
            String destination = textFieldDestination.getText().trim();
            if (! Util.ValidateControls.validateNameAddress(destination))
                throw new Exception("Error in field \"Destination\"");
            return destination;

        }
        return null;
    }

    public GregorianCalendar getDate()throws Exception{
        try {
            this.spinnerDate.commitEdit();
        }catch (Exception e){
            throw new Exception("Error in field \"Date\". The value must be between today and 1 year from now.");

        }
        GregorianCalendar goBack  = null ;
        if (checkBoxDate.isSelected()){
            goBack = new GregorianCalendar();
            goBack.setTime((Date) ((SpinnerDateModel)spinnerDate.getModel()).getNextValue());


        }
        return goBack;
    }
    public void emptyTable(){
        while (this.model.getRowCount()>0){
            model.removeRow(0);
        }
        this.tableTravel.setModel(this.model);
    }
    public void addElement(String [] listTravel){
        this.model.addRow(listTravel);
        this.tableTravel.setModel(this.model);
    }

    public void loadDrivers(Object[] listDriver){
        this.comboBoxDriver.removeAllItems();
        for (Object driver : listDriver){
            Driver actualDriver = (Driver) driver;
            this.comboBoxDriver.addItem(actualDriver.getDriverLicense());

        }
    }

    public void loadTruck(Object[] truckList){
        this.comboBoxTruck.removeAllItems();
        for (Object truck : truckList){
            Trucks actualTruck = (Trucks) truck;
            this.comboBoxTruck.addItem(actualTruck.getLicensePlate());
        }
    }

    public void showMessage(String message){
        JOptionPane.showMessageDialog(this,message,"ERROR",JOptionPane.INFORMATION_MESSAGE);

    }
}

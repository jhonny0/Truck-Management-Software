package View;

import Modules.Driver;
import Modules.Trucks;
import Presenter.PresenterManagementInterstate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.net.PortUnreachableException;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

public class JDialogManagementInterstate extends JDialog {

    //**************************************************************//
    final private String[] COLUMNS_TITLE = {"CODE ID", "Miles to Travel","Date and Time","Duration","Destination","Truck","Driver"};
    //________________________________________________________________//

    private JLabel labelCodeId = new JLabel("Code ID") ;
    private JTextField txtFieldCodeID = new JTextField();
    ///Here to change miles to travel
    private JLabel labelMilesToTravel = new JLabel("Miles to Travel");
    public JSpinner spinnerMilesToTravel = new JSpinner(new SpinnerNumberModel(50,50,200,5));

    private JLabel labelDriver = new JLabel("Driver");
    private JComboBox comboBoxDriver = new JComboBox();

    private JLabel labelTruck = new JLabel("Truck") ;
    private JComboBox comboBoxTruck = new JComboBox() ;

    private JLabel labelDateAndTime =new JLabel("Date and Time") ;
    private JSpinner spinnerDateAndTime = new JSpinner() ;

    private JLabel labelDuration = new JLabel("Duration(min)") ;
    private JSpinner spinnerDuration = new JSpinner(new SpinnerNumberModel(35,35,300,5));

    private JLabel labelDestination = new JLabel("Destination") ;
    private JTextField txtFieldDestination = new JTextField();

    private JButton buttonInsert = new JButton("Insert");
    private JButton buttonDelete = new JButton("Delete") ;
    private JButton buttonUpdate = new JButton("Update") ;

    private JTable tableTravel = new JTable();
    private DefaultTableModel model = new DefaultTableModel(new Object[][]{},COLUMNS_TITLE){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    PresenterManagementInterstate presenterManagementInterstate ;
    private GregorianCalendar dateAndTime = new GregorianCalendar() ;

    /////Maybe i have to amke a constructor empty


    public JDialogManagementInterstate( PresenterManagementInterstate presenterManagementInterstate) {
        super();
        this.presenterManagementInterstate = presenterManagementInterstate;
        this.setTitle("Interstate Travel Management ");
        this.initComponent();
    }

    public void initComponent(){
        setLayout(new BorderLayout());
        ///Upper Left
        JPanel panelUpperLeft = new JPanel(new GridLayout(5,2,5,5));

        this.txtFieldCodeID.setToolTipText("Six Number, example: \"1234356\"");
        panelUpperLeft.add(this.labelCodeId);
        panelUpperLeft.add(this.txtFieldCodeID);

        panelUpperLeft.add(this.labelMilesToTravel);
        panelUpperLeft.add(this.spinnerMilesToTravel);

        panelUpperLeft.add(this.labelDriver);
        panelUpperLeft.add(this.comboBoxDriver);

        //Upper Right
        JPanel panelUpperRight = new JPanel(new GridLayout(5,2,5,5));

        panelUpperRight.add(this.labelTruck);
        panelUpperRight.add(this.comboBoxTruck);

        this.spinnerDateAndTime.setToolTipText("Field Format: \"mm/dd/yyyy hh/mm/  PM\"");
        this.loadDateAndTime() ;
        panelUpperRight.add(this.labelDateAndTime);
        panelUpperRight.add(spinnerDateAndTime);

        panelUpperRight.add(this.labelDuration);
        panelUpperRight.add(this.spinnerDuration);

        panelUpperRight.add(this.labelDestination);
        panelUpperRight.add(this.txtFieldDestination);

        /////Buttons
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelButtons.add(this.buttonInsert);
        panelButtons.add(this.buttonDelete);
        panelButtons.add(this.buttonUpdate);

        JPanel panelUpperComplete = new JPanel(new BorderLayout());
        JPanel panelFields = new JPanel(new GridLayout(1,2,20,20));
        panelFields.add(panelUpperLeft);
        panelFields.add(panelUpperRight);

        panelUpperComplete.add(panelFields,BorderLayout.CENTER);
        panelUpperComplete.add(panelButtons,BorderLayout.SOUTH);

        this.add(panelUpperComplete, BorderLayout.NORTH);
        this.tableTravel.setModel(this.model);
        JScrollPane panelTable = new JScrollPane(this.tableTravel);
        this.add(panelTable,BorderLayout.CENTER);

        this.buttonInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenterManagementInterstate.addTravel();
            }
        });

        this.buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tableTravel.getSelectedRow();
                if (index !=-1 )
                    presenterManagementInterstate.deleteTravel(idSelectedTravel());
                else
                    showMessage("Select from the table the travel you want to delete");
            }
        });

        this.buttonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tableTravel.getSelectedRow();
                if (index != -1 )
                    presenterManagementInterstate.updateTravel(idSelectedTravel());
                else
                    showMessage("Select from the table the travel you want to delete");

            }
        });

        this.tableTravel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                presenterManagementInterstate.fillFields(idSelectedTravel());
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                presenterManagementInterstate.enableMainWindow();
            }
        });

        this.setMinimumSize(new Dimension(600,600));
        this.setSize(600,600);
        this.setVisible(true);




    }

    public String getCodeID(){
        return this.txtFieldCodeID.getText().trim();
    }

    public void setCodeID(String value){
        this.txtFieldCodeID.setText(value);
    }

    public short getMilesToTravel(){
        //this.spinnerMilesToTravel.commitEdit();
       return (Short.valueOf(this.spinnerMilesToTravel.getValue().toString()));
    }

    public void setMilesToTravel (short value){
        this.spinnerMilesToTravel.setValue(new Short(value));
    }

    public int getDriver()throws Exception{
        if (this.comboBoxDriver.getModel().getSize() == 0){
            throw new Exception("There are no drivers in the data base.");
        }
        return this.comboBoxDriver.getSelectedIndex();
    }
    public void setDriver(int value){
        this.comboBoxDriver.setSelectedIndex(value);
    }

    public int getTruck()throws Exception{
        if (this.comboBoxTruck.getModel().getSize() == 0){
            throw new Exception("There are no trucks on the data base.");

        }
        return this.comboBoxTruck.getSelectedIndex();
    }
    public void setTruck(int value){
        this.comboBoxTruck.setSelectedIndex(value);
    }

    public GregorianCalendar getDateAndTime()throws Exception{
        try {
            this.spinnerDateAndTime.commitEdit();
        }catch (Exception e){
            throw new Exception("Error in field\"Date and Time\". The entered value should not be more than one year than today");

        }
        GregorianCalendar dateTimeAgain = new GregorianCalendar();
        Date dateDate = ((SpinnerDateModel)this.spinnerDateAndTime.getModel()).getDate();
        dateTimeAgain.setTime(dateDate);
        System.out.println(dateDate);
        return dateTimeAgain ;
    }
    public void setDateAndTime(GregorianCalendar value) {
        this.spinnerDateAndTime.setValue(value.getTime());
    }
        public void loadDateAndTime () {
            GregorianCalendar dateTimeInit = new GregorianCalendar();
            GregorianCalendar dateTimeFinal = new GregorianCalendar();
            dateTimeFinal.add(GregorianCalendar.YEAR, 1);
            GregorianCalendar dateTimeActual = (GregorianCalendar) dateTimeInit.clone();
            dateTimeActual.add(GregorianCalendar.MINUTE, 1);
            this.spinnerDateAndTime.setModel(new SpinnerDateModel(dateTimeActual.getTime(),
                    dateTimeInit.getTime(), dateTimeFinal.getTime(), GregorianCalendar.HOUR));
        }

        public short getDuration()throws Exception{
        try {
            this.spinnerDuration.commitEdit();
        }catch (ParseException e){
            throw new Exception("I gotta change this later max time duration ");
        }
        return Short.valueOf(Integer.toString( (Integer)this.spinnerDuration.getValue()));
        }


        public void setDuration(short value){
            this.spinnerDuration.setValue(new Short(value));
        }

        public String getDestination(){
            return this.txtFieldDestination.getText().trim();

        }
        public void setDestination(String value){
            this.txtFieldDestination.setText(value);
        }

        public void emptyTable(){
            while (this.model.getRowCount()>0){
                model.removeRow(0);
            }
            this.tableTravel.setModel(this.model);
        }

        public void addElement(String[] dataTravel){
            this.model.addRow(dataTravel);
            this.tableTravel.setModel(this.model);
        }


        public void showMessage(String message){
            JOptionPane.showMessageDialog(this,message,"ERROR",JOptionPane.INFORMATION_MESSAGE);

        }


        public void restoreFields(){
            this.setCodeID("");
            this.setMilesToTravel((short) 50);
            this.comboBoxDriver.setSelectedIndex(0);
            this.comboBoxTruck.setSelectedIndex(0);
            this.loadDateAndTime();
            this.setDuration((short) 35);
            this.setDestination("");
        }

        public void loadDrivers(Object[] driversList){
            this.comboBoxDriver.removeAllItems();
            for (Object driver : driversList){
                Driver actualDriver = (Driver) driver;
                if (!actualDriver.isAvailable())
                    this.comboBoxDriver.addItem(actualDriver.getDriverLicense()+ "(Busy)");
                else
                    this.comboBoxDriver.addItem(actualDriver.getDriverLicense());
            }
        }

        public void loadTrucks(Object [] truckList){
            this.comboBoxTruck.removeAllItems();
            for (Object truck : truckList){
                Trucks actualTruck = (Trucks) truck ;
                if (actualTruck.truckInUse())
                    this.comboBoxTruck.addItem(actualTruck.getLicensePlate()+"(In Use)");
                else
                    this.comboBoxTruck.addItem(actualTruck.getLicensePlate());
            }
        }

        /*public void validateFields()throws Exception{
        if (! ValidateControls.validateID(getCodeID()))
            throw new Exception("Error in field \"Code ID\".");
        if (! ValidateControls.validateNameAddress(this.getDestination()))
            throw new Exception("Error in field \"Destination\".");
        }
        */
        private String idSelectedTravel(){
            return (String) this.tableTravel.getModel().getValueAt(this.tableTravel.getSelectedRow(),0);
        }


    }



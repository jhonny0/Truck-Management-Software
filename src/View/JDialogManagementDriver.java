package View;

import Modules.Driver;
import Presenter.PresenterManagementDriver;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

public class JDialogManagementDriver extends JDialog {

    ////CONSTANTS*****************************************************************
    private final String [] COLUMNS_TITLE = {"Name", "DriverLicense", "Sex", "Available" , "Address", "Rating" , "Year of experience"};
    private final String AVAILABLE_YES = "Yes" ;
    private final String AVAILABLE_NO = "No" ;
    private final String[] STATES = {"Alabama", "Alaska", "American Samoa", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "District of Columbia", "Florida", "Georgia", "Guam", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Minor Outlying Islands", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Northern Mariana Islands", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Puerto Rico", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "U.S. Virgin Islands", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
       /// ********************************************************************************

    private JLabel labelName = new JLabel("Name");
    private JTextField txtFieldName = new JTextField();

    private JLabel labelSex = new JLabel("Sex");
    private JRadioButton radioButtonFemale = new JRadioButton("F") ;
    private JRadioButton radioButtonMale = new JRadioButton("M");

    private JLabel labelDriverLicense = new JLabel("Driver License");
    private JTextField textFieldDriverLicense = new JTextField();

    private JLabel labelAvailability  = new JLabel("Available");
    private JLabel getLabelAvailabilityValue = new JLabel("Yes") ;


    ///////////////////////////*******Address?///////////

    private JPanel panelAddress = new JPanel();

    private JLabel labelStreet = new JLabel("Street") ;
    private JTextField textFieldStreet = new JTextField();

    private JLabel labelCity = new JLabel("City");
    private JTextField textFieldCity = new JTextField();

    private JLabel labelState = new JLabel("State");
    private JComboBox comboBoxState = new JComboBox(STATES);

    //---------------------------------------------

    private JLabel labelRating= new JLabel("Rating");
    private JComboBox comboBoxRating = new JComboBox(new DefaultComboBoxModel(Driver.DRIVER_RATINGS));
    ///Here to change maximum value of year of exp
    private JLabel labelYearsOfExperience = new JLabel("Years of Exp.");
    private JSpinner spinnerYearsOfExperience = new JSpinner(new SpinnerNumberModel(0,0,50,1));

    private JButton buttonInsert = new JButton("Insert");
    private JButton buttonDelete = new JButton("Delete");
    private JButton buttonUpdate = new JButton("Update");

    private JTable tableDriver ;
    private DefaultTableModel model = new DefaultTableModel(new Object[][]{}, COLUMNS_TITLE){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private PresenterManagementDriver presenterManagementDriver ;

    public JDialogManagementDriver (PresenterManagementDriver presenterManagementDriver){
        super();
        setTitle("Drivers Management");
        this.presenterManagementDriver = presenterManagementDriver ;
        this.initComponents() ;
    }

    private void initComponents (){

        this.setLayout(new BorderLayout());

        // UPPER Left
        GridLayout componentLeft = new GridLayout(5,2);
        JPanel panelLeft = new JPanel(componentLeft);
        panelLeft.add(this.labelName) ;
        panelLeft.add(this.txtFieldName);

        panelLeft.add(this.labelSex);
        ButtonGroup groupSex = new ButtonGroup();
        groupSex.add(this.radioButtonFemale);
        groupSex.add(this.radioButtonMale);
        Box boxSex = Box.createHorizontalBox();
        this.radioButtonFemale.setSelected(true);
        boxSex.add(this.radioButtonFemale);
        boxSex.add(this.radioButtonMale);
        panelLeft.add(boxSex);

        panelLeft.add(this.labelDriverLicense);
        panelLeft.add(this.textFieldDriverLicense);

        panelLeft.add(this.labelAvailability);
        panelLeft.add(this.getLabelAvailabilityValue);

        //_________________________________________________________//

        /// UPPER center (ADDRESS)//_________________________________________

        panelAddress.setBorder(BorderFactory.createTitledBorder("Address"));
        panelAddress.setLayout(new GridLayout(5,4));
        panelAddress.add(this.labelStreet);
        panelAddress.add(this.textFieldStreet);
        panelAddress.add(this.labelCity);
        panelAddress.add(this.textFieldCity);
        panelAddress.add(this.labelState);
        panelAddress.add(this.comboBoxState);

        /////////////////----------------------------------------------------------------------

        //UPPER Right-----------------------------------------------------------------------

        JPanel paneRight = new JPanel(new GridLayout(5,2));
        paneRight.add(this.labelRating);
        paneRight.add(this.comboBoxRating);
        paneRight.add(this.labelYearsOfExperience);
        paneRight.add(this.spinnerYearsOfExperience);

        ///////////////////=---------------------------------------------

        //UPPER Complete

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelButtons.add(buttonInsert);
        panelButtons.add(buttonDelete);
        panelButtons.add(buttonUpdate);

        JPanel panelUpperPart = new JPanel(new BorderLayout(5,5));
        panelUpperPart.add(panelLeft,BorderLayout.WEST);
        panelUpperPart.add(this.panelAddress,BorderLayout.CENTER);
        panelUpperPart.add(paneRight,BorderLayout.EAST);
        panelUpperPart.add(panelButtons,BorderLayout.SOUTH);

        ///------------------------------------------------

        this.setLayout(new BorderLayout());
        this.add(panelUpperPart,BorderLayout.NORTH);
        this.tableDriver =new JTable();

        this.tableDriver.setModel(this.model);
        JScrollPane scrollPane = new JScrollPane(this.tableDriver);
        this.tableDriver.setFillsViewportHeight(true);
        this.add(scrollPane,BorderLayout.CENTER);

        this.buttonInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenterManagementDriver.addDriver();
            }
        });
        this.tableDriver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickOnTable();
            }
        });

        this.buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tableDriver.getSelectedRow();
                if (index == -1){
                    showMessage("Should select a driver to delete");
                }else
                    presenterManagementDriver.deleteDriver(index);
            }
        });

        this.buttonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tableDriver.getSelectedRow();
                if (index == -1){
                    showMessage("Should select a driver to update");
                }else
                    presenterManagementDriver.updateDriver(index);
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                presenterManagementDriver.enableMainWindow();
            }
        });

        pack();
        setBounds(300,300,600,500);
        setMinimumSize(new Dimension(600,500));
        setVisible(true);


    }

    public String getName(){
    return this.txtFieldName.getText().trim();
    }
    public void setName (String value){

        this.txtFieldName.setText(value);
    }

    public char getSex(){
        if (this.radioButtonFemale.isSelected())
            return 'F';
        return 'M';
    }
    public void setSex(char sex){
        if (sex == 'F')
            this.radioButtonFemale.setSelected(true);
        else
            this.radioButtonMale.setSelected(true);
    }

    public String getDriverLicense(){
        return this.textFieldDriverLicense.getText().trim();
    }
    public void setDriverLicense ( String value){
        this.textFieldDriverLicense.setText(value);
    }

    public boolean getAvailability(){
        if (this.getLabelAvailabilityValue.getText().equals(AVAILABLE_YES))
            return true;
        return false;
    }

    public void setAvailability(boolean value){
        if (value)
            this.getLabelAvailabilityValue.setText(AVAILABLE_YES);
        else
            this.getLabelAvailabilityValue.setText(AVAILABLE_NO);

    }


    ////ADDRESS

    public String getStreet(){
        return this.textFieldStreet.getText().trim();
    }
    public void setStreet (String value){
        this.textFieldStreet.setText(value);
    }

    public String getCity(){
        return this.textFieldCity.getText().trim();

    }
    public void setCity(String value){
        this.textFieldCity.setText(value);
    }

    public String getState(){
        return (String) this.comboBoxState.getSelectedItem();
    }
    public void setState(String state){
        this.comboBoxState.setSelectedItem(state);
    }

    //----------------------------------------------------------------------------

    public char getRating(){
        return ((Character)this.comboBoxRating.getSelectedItem()).charValue();
    }
    public void setRating(char rating){
        this.comboBoxRating.setSelectedItem(new Character(rating));
    }

    public short getYearsOfExperience(){
        try {
            this.spinnerYearsOfExperience.commitEdit();
        }catch (ParseException e ){
            ///may add something later
        }
        return  Short.valueOf(Integer.toString((Integer)this.spinnerYearsOfExperience.getValue()));

    }
    public void setYearsOfExperience(short value){
        this.spinnerYearsOfExperience.setValue(new Short(value));
    }

    public void addElement(String[] driverData){
        this.model.addRow(driverData);
        this.tableDriver.setModel(model);
    }

    public void emptyTable(){
        while (this.model.getRowCount()>0){
            model.removeRow(0);
        }
        this.tableDriver.setModel(this.model);
    }

    public void showMessage(String message){
        JOptionPane.showMessageDialog(this,message,"ERROR",JOptionPane.INFORMATION_MESSAGE);

    }

    /*public void validateFields() throws  Exception{
        if (! Valida)
    }*/

    public void restoreFields(){
        this.setName("");
        this.setSex('F');
        this.setDriverLicense("");
        this.setAvailability(true);
        this.setStreet("");
        this.setCity("");
        this.setState("");
        this.setRating('A');
        this.setYearsOfExperience(new Short("0"));

    }


    private void clickOnTable (){
        try {
            int index = tableDriver.getSelectedRow();
            if (index != -1){
                presenterManagementDriver.fillFields(index);
            }
        }catch (Exception error){
            showMessage(error.getMessage());
        }
    }

}


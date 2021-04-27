package View;

import Modules.Trucks;
import Presenter.PresenterTruckManagement;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.GregorianCalendar;

public class JDialogManagementTruck extends JDialog {

    /////CONSTANT///

    private final GregorianCalendar ACTUAL_DATE = new GregorianCalendar();
    private final String TRUCK_HEALTH [] = {"Good", "Regular", "Bad"};
    private final String [] COLUMNS_TITLE = {"Plate", "Code", "Brand", "Model", "Fabrication Year", "Health",
    "Availability", "Mileage"};

    //--------------------------------------

    private JLabel labelPlate = new JLabel("Plate") ;
    private  JTextField textFieldPlate = new JTextField();

    private  JLabel labelCode = new JLabel("Code");
    private JTextField textFieldCode = new JTextField();

    private JLabel labelBrand = new JLabel("Brand");
    private JTextField textFieldBrand = new JTextField();

    private JLabel labelModel = new JLabel("Model");
    private JTextField textFieldModel = new JTextField();

    private JLabel labelFabricationYear = new JLabel("Fabrication Year");
    private JSpinner spinnerFabricationYear = new JSpinner(new SpinnerNumberModel(1950,1950,ACTUAL_DATE.get(GregorianCalendar.YEAR),1));

    private JLabel labelMileage = new JLabel("Mileage");
    private JSpinner jSpinnerMileage = new JSpinner(new SpinnerNumberModel(0,0,1000000,100));
    private JLabel labelMileageValue = new JLabel("0");

    private JLabel labelHealth = new JLabel("Health");
    private JComboBox comboBoxHealth = new JComboBox(new DefaultComboBoxModel(TRUCK_HEALTH));

    private JLabel labelAvailability = new JLabel("Availability");
    private JLabel getLabelAvailabilityValue = new JLabel("Yes");

    private JButton buttonInsert = new JButton("Insert");
    private JButton buttonDelete = new JButton("Delete");
    private JButton buttonUpdate = new JButton("Update");

    private JTable tableTruck = new JTable();
    private DefaultTableModel model = new DefaultTableModel(new Object[][]{},COLUMNS_TITLE){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PresenterTruckManagement presenterTruckManagement ;

    private JLabel labelTruckImage = new JLabel(new ImageIcon("src/view/images/truck.png"));

    public JDialogManagementTruck(PresenterTruckManagement presenterTruckManagement){
        super();
        this.setTitle("Trucks Management");
        this.presenterTruckManagement = presenterTruckManagement;
        initComponents() ;
    }

    private void initComponents(){


        //UPPER LEFT
        JPanel panelUpperLeft = new JPanel(new GridLayout(5,2,5,2));
        panelUpperLeft.add(this.labelPlate);
        panelUpperLeft.add(this.textFieldPlate);

        panelUpperLeft.add(this.labelCode);
        panelUpperLeft.add(this.textFieldCode);

        panelUpperLeft.add(this.labelBrand);
        panelUpperLeft.add(this.textFieldBrand);

        panelUpperLeft.add(this.labelModel);
        panelUpperLeft.add(this.textFieldModel);

        panelUpperLeft.add(this.labelFabricationYear);
        panelUpperLeft.add(this.spinnerFabricationYear);

        //------------------------------------------

        //UPPER RIGHT----------------------------

        JPanel panelCenter = new JPanel(new GridLayout(5,2,50,2));


        panelCenter.add(this.labelHealth);
        panelCenter.add(this.comboBoxHealth);

        panelCenter.add(this.labelAvailability);
        panelCenter.add(this.getLabelAvailabilityValue);

        panelCenter.add(this.labelMileage);
        panelCenter.add(this.labelMileageValue);

///////////////////////////////-----------------------------------------....//

        ////UPPER and DOWn ////////////

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelButtons.add(this.buttonInsert);
        panelButtons.add(this.buttonDelete);
        panelButtons.add(this.buttonUpdate);


        ///////////-------------------------------------------


        ////Upper complete

        JPanel panelSuperior = new JPanel(new BorderLayout(10,10));
        panelSuperior.add(panelUpperLeft,BorderLayout.WEST);
        panelSuperior.add(this.labelTruckImage,BorderLayout.CENTER);
        panelSuperior.add(panelCenter,BorderLayout.EAST);
        panelSuperior.add(panelButtons,BorderLayout.SOUTH);
        ////-----------------------------------

        this.setLayout(new BorderLayout());
        this.add(panelSuperior,BorderLayout.NORTH);
        this.tableTruck.setModel(this.model);
        JScrollPane scrollPaneTable = new JScrollPane(this.tableTruck);
        this.add(scrollPaneTable,BorderLayout.CENTER);


        this.buttonInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenterTruckManagement.addTruck();
            }
        });

        this.jSpinnerMileage.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setAvailable(Trucks.AVAILABLE);
            }
        });
        /////Here to add a max miles to travel

        this.tableTruck.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickOnTable();
            }
        });

        this.buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tableTruck.getSelectedRow();
                if (index == -1){
                    showMessage("Should select a truck to delete.");
                }else
                    presenterTruckManagement.deleteTruck(index);
            }
        });

        this.buttonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tableTruck.getSelectedRow();
                if (index == -1){
                    showMessage("Should select a truck to update");
                }else
                    presenterTruckManagement.updateTruck(index);
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                presenterTruckManagement.enableWindowMain();
            }
        });

        setBounds(500,200,600,600);
        setMinimumSize(new Dimension(600,600));
        setVisible(true);
    }

    public String getLicensePlate(){
        return this.textFieldPlate.getText().trim();
    }
    public void setLicensePlate(String plate){
        this.textFieldPlate.setText(plate);
    }

    public String getCode(){
        return this.textFieldCode.getText().trim();
    }
    public void setCode(String code){
        this.textFieldCode.setText(code);
    }

    public String getBrand(){
        return this.textFieldBrand.getText().trim();
    }
    public void setBrand(String brand){
        this.textFieldBrand.setText(brand);
    }

    public String getModel(){
        return this.textFieldModel.getText().trim();
    }
    public void setModel(String model){
        this.textFieldModel.setText(model);
    }

    public short getFabricationYear(){
        try {
            this.spinnerFabricationYear.commitEdit();
        }catch (ParseException e){

        }
        return Short.valueOf(Integer.toString((Integer)this.spinnerFabricationYear.getValue()));
    }

    public void setFabricationYear(short fabricationYear){
        this.spinnerFabricationYear.setValue(new Integer(Short.toString(fabricationYear)));
    }

    public short getMileage(){
        try {
            this.jSpinnerMileage.commitEdit();
        }catch (ParseException e){

        }
        return Short.valueOf(Integer.toString((Integer)this.jSpinnerMileage.getValue()));

    }
    public void setMileage(short mileage){
        this.jSpinnerMileage.setValue(new Integer(Short.toString(mileage)));
    }

    public String getTruckHealth(){
        return (String) this.comboBoxHealth.getSelectedItem();
    }
    public void setTruckHealth(String truckHealth){
        this.comboBoxHealth.setSelectedItem(truckHealth);
    }

    public String getAvailable(){
        return this.getLabelAvailabilityValue.getText();
    }
    public void setAvailable(String available){
        this.getLabelAvailabilityValue.setText(available);
    }

    public void emptyTable(){
        while (this.model.getRowCount() > 0){
            model.removeRow(0);
        }
        this.tableTruck.setModel(this.model);
    }
    public void showMessage (String message){
        JOptionPane.showMessageDialog(this,message,"ERROR",JOptionPane.INFORMATION_MESSAGE);
    }

    public void addElement(String [] truckData){
        this.model.addRow(truckData);
        this.tableTruck.setModel(this.model);
    }

    public void restoreFields(){
        this.setLicensePlate("");
        this.setCode("");
        this.setBrand("");
        this.setModel("");
        this.setFabricationYear(new Short("1950"));
        this.setTruckHealth(TRUCK_HEALTH[0]);
        this.setAvailable(Trucks.AVAILABLE);
        this.setMileage(new Short("0"));
    }

    //// here validate fields


    private void clickOnTable(){
        try {
            int index = this.tableTruck.getSelectedRow();
            if (index !=1){
                presenterTruckManagement.fillFields(index);
            }
        }catch (Exception error){
            showMessage(error.getMessage());
        }
    }




}

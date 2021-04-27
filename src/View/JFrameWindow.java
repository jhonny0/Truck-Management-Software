package View;

import Presenter.MainPresenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JFrameWindow extends JFrame {

    private MainPresenter presenter ;


    ////Class CONSTANTS////
    private final String[] COLUMNS_TITLE = {"Code ID","Travel Type","Destination","Date and Time","Truck","Status"};
    ////*********************///


    private JMenuBar menuBar ;
    private JMenu menuFunctionalities ;
    private JMenuItem menuItemDriverOrder ;
    private JMenuItem menuItemTrucksOrder ;
    private JMenuItem menuItemFilterTravel ;


    private JMenu menuConfigurations;
    private JMenuItem menuItemConfigurations;


    //First Tab
    private JButton saveDataButton = new JButton("Save Data");
    private JLabel labelWarehouseName;
    private JLabel labelTimeAndDate ;
    private DefaultTableModel model = new DefaultTableModel(new Object[][] {}, COLUMNS_TITLE){

        @Override
        public boolean isCellEditable(int row, int column) {
            return super.isCellEditable(row, column);
        }
    };

    private JTable travelsTable ;
    private JTabbedPane tabMain ;
    private JLabel labelStatusTab ;

    //Second tab
    private JButton buttonDrivers = new JButton("Drivers");
    private JButton buttonTrucks =new JButton("Trucks") ;
    private JButton buttonInterstateTravels = new JButton("Interstate Travels");
    private JButton buttonOutOfStateTravels = new JButton("Out of State Travels");

    private final JLabel labelDrivers = new JLabel(new ImageIcon("src/View/images/driver.gif"));
    private final JLabel labelTruck = new JLabel(new ImageIcon("src/View/images/truck.png"));
    private final JLabel labelInterstateTravels = new JLabel(new ImageIcon("src/View/images/interstate.gif"));
    private final JLabel labelOutOfStateTravel = new JLabel(new ImageIcon("src/View/images/outstate.gif"));



    public JFrameWindow(MainPresenter presenter) {

        super("Truck Management Software");
        this.presenter= presenter;
        this.componentsInit();
    }

    public void componentsInit() {
        //Menu Bar
        menuBar = new JMenuBar();
        menuFunctionalities = new JMenu("Functionalities");
        menuBar.add(menuFunctionalities);

        menuItemDriverOrder = new JMenuItem("Sort driver by experience");
        menuFunctionalities.add(menuItemDriverOrder);
        menuFunctionalities.add(new JSeparator());

        menuItemTrucksOrder = new JMenuItem("Sort trucks");
        menuFunctionalities.add(menuItemTrucksOrder);
        menuFunctionalities.add(new JSeparator());

        menuItemFilterTravel = new JMenuItem("Filter Travels");
        menuFunctionalities.add(menuItemFilterTravel);


        // Configurations menu
        menuConfigurations = new JMenu("Settings");
        menuItemConfigurations = new JMenuItem("Change Name and State of the Warehouse");
        menuConfigurations.add(menuItemConfigurations);
        menuBar.add(menuConfigurations);

        setJMenuBar(menuBar);

        //First tab
        tabMain = new JTabbedPane();
        labelWarehouseName = new JLabel("Warehouse:");
        labelTimeAndDate = new JLabel("Date and Time:");

        JPanel panelUpperSupport = new JPanel(new GridLayout(2, 1));
        panelUpperSupport.add(labelWarehouseName);
        panelUpperSupport.add(labelTimeAndDate);
        JPanel panelUpper = new JPanel(new BorderLayout());
        panelUpper.add(panelUpperSupport, BorderLayout.CENTER);
        JPanel panelSaveButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSaveButton.add(saveDataButton);
        panelUpper.add(panelSaveButton, BorderLayout.SOUTH);


        ////table panel

        travelsTable = new JTable();
        travelsTable.setModel(model);
        JScrollPane panelTable = new JScrollPane(travelsTable);

        ////First tab panel
        JPanel panelComplete = new JPanel(new BorderLayout());
        panelComplete.add(panelUpper, BorderLayout.NORTH);
        panelComplete.add(panelTable, BorderLayout.CENTER);
        tabMain.addTab("Upcoming Travels", panelComplete);


        ///Second Tab
        JPanel panelDriver = new JPanel(new BorderLayout());
        panelDriver.add(this.labelDrivers, BorderLayout.CENTER);
        JPanel driverSupport = new JPanel(new FlowLayout(FlowLayout.CENTER));
        driverSupport.add(this.buttonDrivers);
        panelDriver.add(driverSupport, BorderLayout.SOUTH);

        JPanel panelTruck = new JPanel(new BorderLayout());
        panelTruck.add(this.labelTruck, BorderLayout.CENTER);
        JPanel truckSupport = new JPanel(new FlowLayout(FlowLayout.CENTER));
        truckSupport.add(this.buttonTrucks);
        panelTruck.add(truckSupport, BorderLayout.SOUTH);

        JPanel panelInterstateTravel = new JPanel(new BorderLayout());
        panelInterstateTravel.add(this.labelInterstateTravels, BorderLayout.CENTER);
        JPanel supportInterstateTravel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        supportInterstateTravel.add(this.buttonInterstateTravels);
        panelInterstateTravel.add(supportInterstateTravel, BorderLayout.SOUTH);

        JPanel panelOutOfStateTravel = new JPanel(new BorderLayout());
        panelOutOfStateTravel.add(this.labelOutOfStateTravel, BorderLayout.CENTER);
        JPanel supportOutOfStateTravel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        supportOutOfStateTravel.add(this.buttonOutOfStateTravels);
        panelOutOfStateTravel.add(supportOutOfStateTravel, BorderLayout.SOUTH);

        JPanel support = new JPanel(new GridLayout(2, 2));
        support.add(panelDriver);
        support.add(panelTruck);
        support.add(panelInterstateTravel);
        support.add(panelOutOfStateTravel);

        JPanel management = new JPanel(new BorderLayout());
        management.add(support, BorderLayout.CENTER);

        labelStatusTab = new JLabel("Status: Remember to sabe the data before exit");
        labelStatusTab.setOpaque(true);
        labelStatusTab.setBackground(Color.RED);
        this.add(labelStatusTab, BorderLayout.SOUTH);
        this.add(tabMain);

        tabMain.setBackground(Color.white);
        this.tabMain.addTab("Resources Management", management);


        this.buttonDrivers.addActionListener(new ListenerActionEvent());
        this.buttonTrucks.addActionListener(new ListenerActionEvent());
        this.buttonInterstateTravels.addActionListener(new ListenerActionEvent());
        this.buttonOutOfStateTravels.addActionListener(new ListenerActionEvent());
        this.saveDataButton.addActionListener(new ListenerActionEvent());
        this.menuItemDriverOrder.addActionListener(new ListenerActionEvent());
        this.menuItemTrucksOrder.addActionListener(new ListenerActionEvent());
        this.menuItemFilterTravel.addActionListener(new ListenerActionEvent());
        this.menuItemConfigurations.addActionListener(new ListenerActionEvent());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(getRootPane(),"Are you sure you want to exit? Did you already save the data?\nSelect \"Accept \"to exit,or \"Cancel\" or \"No\" to return.");
                if (option == 0 ){
                    System.exit(0);
                }
            }
        });
        setBounds(400,200,600,500);
        setMinimumSize(new Dimension(600,500));
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    }
    public void setDateAndTime(String value){
        this.labelTimeAndDate.setText("Date and Time: " + value);
    }
    public void setNameWarehouse(String value){
        this.labelWarehouseName.setText("Warehouse: "+value);
    }
    public void setStatusBar(String value){
        labelStatusTab.setText("Status: Remember to save data before close. -"+value);
    }

    public void addElement (String[] travelData){
        this.model.addRow(travelData);
        this.travelsTable.setModel(this.model);

    }
    public void emptyTable(){
        while (this.model.getRowCount()>0){
            model.removeRow(0);
        }
        this.travelsTable.setModel(this.model);
    }
    public void showMessage (String message){
        JOptionPane.showMessageDialog(this,message,"ERROR",JOptionPane.INFORMATION_MESSAGE);

    }

            private class ListenerActionEvent implements ActionListener{
                public ListenerActionEvent(){

                }

                @Override
                public void actionPerformed(ActionEvent e) {
                   if (e.getSource()==buttonDrivers)
                       presenter.openManagementDrivers();
                   else if (e.getSource() == buttonTrucks)
                       presenter.openManagementTruck();
                   else if (e.getSource() == buttonInterstateTravels)
                       presenter.openManagementInterstate();
                   else if (e.getSource() == buttonOutOfStateTravels)
                       presenter.openManagementOutOfState();
                   else if (e.getSource() == menuItemDriverOrder)
                       presenter.openDriverSort();
                   else if (e.getSource() == menuItemFilterTravel)
                       presenter.openTravelSort();
                   else if (e.getSource() == menuItemConfigurations)
                       presenter.openConfigurations();
                   else if (e.getSource()==saveDataButton)
                       presenter.saveData();


                }
            }






}


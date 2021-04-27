package View;

import Presenter.PresenterDataHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JDialogDataHandler extends JDialog {

    //___________________________________________________________________________________//

    private final String[] STATES = {"Alabama", "Alaska", "American Samoa", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "District of Columbia", "Florida", "Georgia", "Guam", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Minor Outlying Islands", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Northern Mariana Islands", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Puerto Rico", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "U.S. Virgin Islands", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
    //************************************************************************************//

    private PresenterDataHandler presenterDataHandler ;

    private JLabel labelWarehouseName ;
    private JTextField txtFieldWarehouseName ;

    private JLabel labelState ;
    private JComboBox comboBoxState ;

    private JButton buttonAccept ;



    public JDialogDataHandler(PresenterDataHandler presenterDataHandler){
        this.presenterDataHandler = presenterDataHandler ;
        initComponents();
    }
    private void initComponents(){
        ///Upper Panel
        JPanel panelData = new JPanel(new GridLayout(7,2));
        labelWarehouseName = new JLabel("Warehouse Name") ;
        txtFieldWarehouseName = new JTextField() ;
        panelData.add(labelWarehouseName) ;
        panelData.add(txtFieldWarehouseName);

        labelState = new JLabel("States");
        comboBoxState = new JComboBox(STATES) ;
        panelData.add(labelState) ;
        panelData.add(comboBoxState);

        //Lower Pane;
        JPanel panelLower = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonAccept  = new JButton("Accept") ;
        panelLower.add(buttonAccept) ;

        setLayout(new BorderLayout());
        add(panelData,BorderLayout.CENTER);
        add(panelLower,BorderLayout.SOUTH);

        buttonAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenterDataHandler.changeSettings();}
        });

        setBounds(300,300,300,300);
        setVisible(true);
    }
    public String getName(){
        return txtFieldWarehouseName.getText().trim();
    }
    public void setName (String name){
        txtFieldWarehouseName.setText(name);
    }
    public String getState(){
        return (String) comboBoxState.getSelectedItem();
    }
    public void setStates(String states){
        comboBoxState.setSelectedItem(states);
    }
    public void showMessage (String message){
        JOptionPane.showMessageDialog(this,message,"ERROR",JOptionPane.INFORMATION_MESSAGE);

    }
   /* public void validateFields()throws Exception{
        if (!Util.ValidateControls.validateNameAddress(getName()))
            throw new Exception("Error in \"NAME\" field") ;
            }
    */
}

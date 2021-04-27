package View;

import Presenter.PresenterTruckNotAvailable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JDialogTruckNotAvailable extends JDialog {

    private JLabel labelShow ;
    private PresenterTruckNotAvailable  presenterTruckNotAvailable ;

    public JDialogTruckNotAvailable() {
    }

    public JDialogTruckNotAvailable(PresenterTruckNotAvailable presenterTruckNotAvailable) {
        super();
        this.presenterTruckNotAvailable = presenterTruckNotAvailable;
        this.initComponents() ;
    }

    private void initComponents(){
        setTitle("Amount of trucks not available and in bad shape ");
        setLayout(new FlowLayout(FlowLayout.CENTER));

        labelShow  = new JLabel("Amount of trucks not available and in bad shape.");
        add(labelShow);


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                presenterTruckNotAvailable.enableMainWindow();
            }
        });
        setBounds(300,300,400,100);
        setResizable(false);
        setVisible(true);
    }
    public void setAmountTrucks(int amount){
        labelShow.setText("Amount of trucks not available and in bad shape:" + amount);
    }
}

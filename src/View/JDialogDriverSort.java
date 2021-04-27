package View;

import Presenter.MainPresenter;
import Presenter.PresenterDriverSort;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JDialogDriverSort extends JDialog {

    //____________________________________________________________________________________//
    private final String[] COLUMNS_TITLE = {"Name" , "Driver License","Sex" ,"Available","Address","Raring","Years of experience"};
    //************************************************************************************//


    private PresenterDriverSort presenterDriverSort;
    private JTable tableDrivers ;
    private DefaultTableModel model ;



    public JDialogDriverSort() {

    }
    public JDialogDriverSort(PresenterDriverSort presenterDriverSort) {
        super();
        this.presenterDriverSort = presenterDriverSort;
        initComponents() ;

    }

    private void initComponents(){
        setTitle("Drivers sort by year of experience");
        tableDrivers = new JTable() ;
        model = new DefaultTableModel(new Object[][]{},COLUMNS_TITLE){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false ;
            }
        };
        tableDrivers.setModel(model);
        JScrollPane panelTable = new JScrollPane(tableDrivers) ;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(panelTable);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                presenterDriverSort.enableMainWindow();
            }
        });
        setBounds(300,300,500,500);
        setResizable(false);
        setVisible(true);
    }

    public void emptyTable(){
        while (this.model.getRowCount()>0){
            model.removeRow(0);
        }
        this.tableDrivers.setModel(this.model);
    }
    public void addElement(String [] dataDriver){
        this.model.addRow(dataDriver);
        this.tableDrivers.setModel(this.model);
    }
}

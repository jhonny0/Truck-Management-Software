import Presenter.MainPresenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Launcher extends JFrame {
    private JSpinner spinner ;
    public JTable table ;

    public Launcher() {
        super("AAAA");
        //Setting up times
        GregorianCalendar start =   new GregorianCalendar() ;
        start.add(Calendar.HOUR,8);
        GregorianCalendar actualCalendar = (GregorianCalendar) start.clone();
        actualCalendar.add(GregorianCalendar.MONTH,11);
        Date actualDate = actualCalendar.getTime();

        GregorianCalendar end  = new GregorianCalendar();
        end.add(GregorianCalendar.YEAR,2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.spinner= new JSpinner(new SpinnerDateModel(actualCalendar.getTime(),start.getTime(), end.getTime(),GregorianCalendar.DAY_OF_WEEK_IN_MONTH ));
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("AAAAAAAAAA");
        this.add(spinner);
        setSize(300,300);
        setVisible(true);
    }



    public static void main(String [] args) {
        MainPresenter mainPresenter = new MainPresenter();
        mainPresenter.init();

        ActionListener c = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(new GregorianCalendar());
            }
        };
    }


}

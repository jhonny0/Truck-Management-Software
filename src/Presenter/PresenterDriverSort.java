package Presenter;

import Modules.Driver;
import Modules.Handler;
import View.JDialogDriverSort;

import javax.swing.*;
import java.util.ArrayList;

public class PresenterDriverSort {

    private MainPresenter mainPresenter ;
    private JDialogDriverSort view ;
    private Handler handler ;

    public PresenterDriverSort() {
    }

    public PresenterDriverSort(Handler handler) {
        this.handler = handler;
        view = new JDialogDriverSort(this);
    }
    public void init( MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter ;
        this.mainPresenter.setViewEnable(false);
        loadData() ;
    }
    public void enableMainWindow(){
        mainPresenter.setViewEnable(true);
    }
    public void loadData(){
        view.emptyTable();
        ArrayList<Driver> driverList = handler.getDriverOrderDecreasing();
        for (Driver driver: driverList){
            this.view.addElement(driver.toTable());
        }
    }
}

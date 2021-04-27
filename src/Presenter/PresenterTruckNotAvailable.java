package Presenter;

import Modules.Handler;
import View.JDialogTruckNotAvailable;

import javax.swing.*;

public class PresenterTruckNotAvailable {

    private MainPresenter mainPresenter ;
    private Handler handler;
    private JDialogTruckNotAvailable view ;

    public PresenterTruckNotAvailable(){

    }

    public PresenterTruckNotAvailable(Handler handler) {
        this.handler = handler;
        view = new JDialogTruckNotAvailable(this);
    }
    public void init(MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter ;
        this.mainPresenter.setViewEnable(false);
        view.setAmountTrucks(handler.truckNotAvailableBadConditions()) ;
    }
    public void enableMainWindow(){
        mainPresenter.setViewEnable(true);
    }
    public void loadData(){
        view.setAmountTrucks(handler.truckNotAvailableBadConditions());
    }
}

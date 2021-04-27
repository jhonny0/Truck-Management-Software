package Presenter;

import Modules.Handler;
import View.JDialogSortTravel;

public class PresenterTravelSort {
    private Handler handler;
    private MainPresenter mainPresenter ;
    private JDialogSortTravel view ;


    public PresenterTravelSort(){

    }
    public PresenterTravelSort(Handler handler) {
        this.handler = handler;
        this.view = new JDialogSortTravel(this) ;

    }

    public void init(MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter ;
        this.mainPresenter.setViewEnable(false);
        loadData();

    }
    public void enableMainWindow(){
        mainPresenter.setViewEnable(true);
    }
    private void loadData(){
        this.view.loadDrivers(this.handler.getDriverList().toArray());
        this.view.loadTruck(this.handler.getTruckList().toArray());

    }

    public void applyFilter(){
        try {
            view.emptyTable();
            String travelType = view.getTravelType();
        }catch (Exception e){
            view.showMessage(e.getMessage());
            loadData();
            e.printStackTrace();
        }
    }
}

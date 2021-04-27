package Presenter;

import Modules.*;
import View.JDialogManagementInterstate;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class PresenterManagementInterstate {

    private Handler handler ;
    private JDialogManagementInterstate view ;
    private MainPresenter mainPresenter ;

    public PresenterManagementInterstate(Handler handler) {
        this.handler = handler;
    }

    public void init (MainPresenter presenter){
        this.view = new JDialogManagementInterstate(this);
        this.loadData() ;
        this.mainPresenter = presenter;
        this.mainPresenter.setViewEnable(false);
    }

    public void enableMainWindow(){
        mainPresenter.setViewEnable(true);
    }
    public void loadData(){
        this.view.loadDrivers(this.handler.getDriverList().toArray());
        this.view.loadTrucks(this.handler.getTruckList().toArray());

        try {
            this.view.emptyTable() ;
            ArrayList<Travel> listTravel = this.handler.getTravelList();

            for (int travel = 0 ; travel < listTravel.size(); travel++){
                Travel actualTravel = (Travel) listTravel.get(travel);
                if (actualTravel instanceof InterstateTravel)
                    this.view.addElement(actualTravel.linkForTable());

            }

        }catch (Exception e ){
            System.out.println("ERROR LOADING DATA");
            e.printStackTrace();
        }
    }
    public void addTravel(){
        try {
            waitWarehouseBusy();
            //this.view.validateFields();
            String codeID = this.view.getCodeID() ;
            short milesToTravel = this.view.getMilesToTravel();
            Driver driver = this.handler.indexToDriver(this.view.getDriver());
            Trucks trucks = this.handler.indexPerTrucks(this.view.getTruck());
            GregorianCalendar dateAndTime = this.view.getDateAndTime();
            short duration = this.view.getDuration() ;
            String destination = this.view.getDestination() ;
            InterstateTravel travel = new InterstateTravel(codeID,milesToTravel,trucks,driver,dateAndTime,duration,destination);
            this.handler.addTravel(travel,this.handler.lastIndexTravel());
            this.view.restoreFields();

        }catch (Exception e){
            this.view.showMessage(e.getMessage());
            e.printStackTrace();
        }
        finally {
            this.view.emptyTable();
            loadData();
            handler.setWarehouseInUse(false);
        }
    }

    public void deleteTravel(String idTravel){
        try {
            waitWarehouseBusy() ;
            int index = this.handler.indexPerTravelCode(idTravel);
            this.handler.deleteTravel(index);
            this.view.restoreFields() ;
        }catch (Exception e ){
            this.view.showMessage(e.getMessage());
        }finally {
            this.view.emptyTable();
            loadData();
            handler.setWarehouseInUse(false);
        }

    }
    public void fillFields(String idTravel){
        try {
            InterstateTravel travel = (InterstateTravel) this.handler.travelPerIDTravel(idTravel);
            this.view.setCodeID(travel.getCodeID());
            this.view.setMilesToTravel(travel.getMilesToTravel());
            this.view.setDriver(this.handler.indexPerID(travel.getDriver().getDriverLicense()));
            this.view.setTruck(this.handler.indexPerPlateTrucks(travel.getTrucks().getLicensePlate()));
            this.view.setDateAndTime(travel.getDateAndTime());
            this.view.setDuration(travel.getDuration());
            this.view.setDestination(travel.getDestination());

        }catch (Exception e){
            e.printStackTrace();
            this.view.showMessage(e.getMessage());
        }
    }

    public void updateTravel(String idTravel){
        try {
            waitWarehouseBusy();
            //this.view.validateFields();
            int index = this.handler.indexPerTravelCode(idTravel);
            String codeID = this.view.getCodeID() ;
            short milesToTravel  = this.view.getMilesToTravel() ;
            Driver driver = this.handler.indexToDriver(this.view.getDriver());
            Trucks trucks = this.handler.indexPerTrucks(this.view.getTruck());
            GregorianCalendar dateAndTime = this.view.getDateAndTime() ;
            short duration = this.view.getDuration();
            String destination = this.view.getDestination();
            InterstateTravel travelUpdated = new InterstateTravel(codeID, milesToTravel,trucks,driver,dateAndTime,duration,destination);
            this.handler.updateTravel(travelUpdated,index);
            this.view.restoreFields();

        }catch (Exception e ){
            e.printStackTrace();
            this.view.showMessage(e.getMessage());
        }finally {
            this.view.emptyTable() ;
            loadData();
            handler.setWarehouseInUse(false);
        }
    }
    private void waitWarehouseBusy(){
        if (handler.isWarehouseInUse()){
            handler.keepOperationBusy();

        }
        handler.setWarehouseInUse(true);
    }
}

package Presenter;

import Modules.Handler;
import Modules.InterstateTravel;
import Modules.OutStateTravel;
import Modules.Travel;
import View.JFrameWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.ArrayList;

public class MainPresenter {

    private Handler handler ;
    private JFrameWindow view ;
    private Timer timer = new Timer(5000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            simulateHandler();
        }
    });

    public MainPresenter(){
        this.handler = new Handler() ;
        handler.readNameState();
    }
    public void init (){
        view = new JFrameWindow(this);
        view.setNameWarehouse(handler.getName()+" - "+handler.getState());

        try {
            handler.readDrivers();
            handler.readTrucks();
            handler.readTravels();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        this.loadData();
        this.startTimer();
    }
    public void setViewEnable(boolean value){
        this.view.setEnabled(value);
    }
    public void openManagementTruck(){
        PresenterTruckManagement presenterTruckManagement = new PresenterTruckManagement(handler);
        presenterTruckManagement.init(this);
    }
    public void openManagementDrivers(){
        PresenterManagementDriver presenterManagementDriver = new PresenterManagementDriver(handler);
        presenterManagementDriver.init(this);
    }
    public void openManagementInterstate(){
        PresenterManagementInterstate presenterManagementInterstate = new PresenterManagementInterstate(handler);
        presenterManagementInterstate.init(this);
    }
    public void openManagementOutOfState(){
        PresenterManagementOutOfState presenterManagementOutOfState = new PresenterManagementOutOfState((handler));
        presenterManagementOutOfState.init(this);
    }
    public void openTrucksNotAvailable(){
        PresenterTruckNotAvailable presenterTruckNotAvailable = new PresenterTruckNotAvailable(handler);
        presenterTruckNotAvailable.init(this);
    }
    public void openDriverSort(){
        PresenterDriverSort presenterDriverSort = new PresenterDriverSort(handler) ;
        presenterDriverSort.init(this);
    }

    public void openTravelSort(){
        PresenterTravelSort presenterTravelSort = new PresenterTravelSort(handler);
        presenterTravelSort.init(this);

    }
    public void openConfigurations(){
        PresenterDataHandler presenterDataHandler = new PresenterDataHandler(handler);
        presenterDataHandler.init(this);
    }

    public void loadData(){
        try {
            this.handler.updateDateTime();
            this.view.setDateAndTime(this.handler.getDateAndTimetoGUI());
            ArrayList<Travel> travelList = this.handler.getTravelOrderByDateAndTime();
            String[] dataToTable = new String[6];
            Travel actualTravel;
            for (int indexTravel = 0 ; indexTravel <  travelList.size();indexTravel++){
                actualTravel = (Travel) travelList.get(indexTravel);
                dataToTable[0] = actualTravel.getCodeID();
                if (actualTravel instanceof InterstateTravel){
                    dataToTable[1] = InterstateTravel.TYPE_OF_TRAVEL ;
                }else {
                    dataToTable[1] = OutStateTravel.TYPE_OF_TRAVEL;
                }
                dataToTable[2] = actualTravel.getDestination();
                dataToTable[3] = actualTravel.dateAndTimeToTable();
                dataToTable[4] = actualTravel.getTrucks().getLicensePlate();
                dataToTable[5] = actualTravel.getStatus();
                this.view.addElement(dataToTable);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void simulateHandler(){
        try {
            if (!handler.isWarehouseInUse()){
                handler.setWarehouseInUse(true);;
                ArrayList<Travel> travelList = this.handler.getTravelOrderByDateAndTime();
                for (Travel actualTravel : travelList){
                    if (actualTravel.isLeaving()){
                        this.handler.travelLeaving(actualTravel.getCodeID());
                    }
                }
            }
        }catch (Exception e){
            this.view.showMessage(e.getMessage());
        }finally {
            this.view.emptyTable();
            this.loadData();
            handler.setWarehouseInUse(false);
        }
    }
    public void stopTimer(){
        this.timer.stop();
    }
    public void startTimer(){
        this.timer.start();
    }
    public void establishStateName (String nameState){
        view.setNameWarehouse(nameState);
        handler.writeNameState();
    }
    public void saveData(){
        try {
            while (handler.isWarehouseInUse()){

            }
            handler.setWarehouseInUse(true);
            view.setStatusBar("Saving Data , wait...");
            handler.writeDrivers();
            handler.writerTrucks();
            handler.writeTravels();

        }catch (Exception e){
            e.printStackTrace();
            view.showMessage(e.getMessage());
        }finally {
            view.setStatusBar("Data Saved Successfully.(" + this.handler.getDateAndTimetoGUI()+ ")");
            handler.setWarehouseInUse(false);
        }
    }
}

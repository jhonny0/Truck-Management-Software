package Presenter;

import Modules.Handler;
import Modules.Travel;
import Modules.Trucks;
import View.JDialogManagementTruck;

import javax.crypto.ExemptionMechanismException;
import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class PresenterTruckManagement {

    private Handler handler;
    private JDialogManagementTruck view ;
    private MainPresenter mainPresenter ;

    public PresenterTruckManagement(Handler handler){
        this.handler = handler ;
    }
    public void init (MainPresenter presenter){
        view = new JDialogManagementTruck(this);
        this.loadData();
        this.mainPresenter = presenter;
        this.mainPresenter.setViewEnable(false);
    }
    public void enableWindowMain(){
        this.mainPresenter.setViewEnable(true);
    }

    public void loadData(){
        try {
            this.view.emptyTable();
            ArrayList<Trucks> truckList = this.handler.getTruckList();

            for (int truck = 0 ; truck < truckList.size();truck++){
                Trucks actualTruck = (Trucks) truckList.get(truck);
                this.view.addElement(actualTruck.toTable());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addTruck(){
        try {
            waitWarehouseBusy();
            //this.view.validateFields();
            String plate = this.view.getLicensePlate();
            String code = this.view.getCode();
            String brand = this.view.getBrand();
            String model = this.view.getModel();
            short fabricationYear = this.view.getFabricationYear();
            short mileage = this.view.getMileage();
            //short milesToTravel = this.view.getMilesTotravel();
            String status = this.view.getTruckHealth();
            String availability ;

            availability = Trucks.AVAILABLE ;

            Trucks trucks = new Trucks(plate,code,brand,model,fabricationYear,mileage,status,availability);
            this.handler.addTruck(trucks,this.handler.lastIndexTrucks());
            this.view.restoreFields();

        }catch (Exception e){
            this.view.showMessage(e.getMessage());
        }
        finally {
            this.view.emptyTable();
            loadData();
            handler.setWarehouseInUse(false);
        }

    }

    public void fillFields(int index){
        Trucks trucks = this.handler.indexPerTrucks(index);
        this.view.setLicensePlate(trucks.getLicensePlate());
        this.view.setCode(trucks.getCode());
        this.view.setModel(trucks.getModel());
        this.view.setFabricationYear(trucks.getFabrication_year());
        this.view.setMileage(trucks.getMileage());
        this.view.setBrand(trucks.getBrand());
        //this.view.setMilesToTravel(trucks.getMileageToTravel());
        this.view.setTruckHealth(trucks.getTruckHealth());
        this.view.setAvailable(trucks.getAvailable());


    }

    public void deleteTruck(int index){
        try {
            waitWarehouseBusy();
            this.handler.eliminateTruck(index);
            this.view.restoreFields();

        }catch (Exception e){
            this.view.showMessage(e.getMessage());

        }
        finally {
           this.view.emptyTable();
           loadData();
           handler.setWarehouseInUse(false);
        }
    }
    public void updateTruck(int index){
        try {
            waitWarehouseBusy();
            Trucks trucks = this.handler.indexPerTrucks(index);
            if (this.handler.ableToUpdateTruck(index, view.getLicensePlate())){
                //this.view.validateFields();
                trucks.setLicensePlate(this.view.getLicensePlate());
                trucks.setCode(this.view.getCode());
                trucks.setModel(this.view.getModel());
                trucks.setFabrication_year(this.view.getFabricationYear());
                trucks.setMileage(this.view.getMileage());
                trucks.setTruckHealth(this.view.getTruckHealth());
                trucks.setAvailable(this.view.getAvailable());
                handler.eliminateTruck(index);
                handler.addTruck(trucks,index);
                this.view.restoreFields();
            }
        }catch (Exception e){
            this.view.showMessage(e.getMessage());
        }finally {
            this.view.emptyTable();
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

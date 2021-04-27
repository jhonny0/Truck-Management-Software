package Presenter;

import Modules.Address;
import Modules.Driver;
import Modules.Handler;
import View.JDialogManagementDriver;

import javax.net.ssl.SNIHostName;
import javax.swing.*;
import java.util.ArrayList;

public class PresenterManagementDriver {

    private Handler handler ;
    private JDialogManagementDriver view ;
    private MainPresenter mainPresenter ;

    public PresenterManagementDriver(Handler handler) {
        this.handler = handler;
    }

    public void init (MainPresenter mainPresenter){
        this.view = new JDialogManagementDriver(this) ;
        this.loadData() ;
        this.mainPresenter = mainPresenter ;
        this.mainPresenter.setViewEnable(false);
    }
    public void enableMainWindow(){
        this.mainPresenter.setViewEnable(true);
    }
    public void loadData(){
        try {
            this.view.emptyTable() ;
            ArrayList<Driver> drivers = this.handler.getDriverList();

            for (int driver = 0 ; driver< drivers.size();driver++){
                Driver actualDriver = (Driver) drivers.get(driver);
                this.view.addElement(actualDriver.toTable()) ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addDriver(){
        try {
            waitWarehouseBusy() ;
            //this.view.validateFields() ;
            String name = this.view.getName() ;
            char sex = this.view.getSex() ;
            String driverLicense = this.view.getDriverLicense() ;
            boolean availability = this.view.getAvailability();

            String street = this.view.getStreet() ;
            String city = this.view.getCity() ;
            String state = this.view.getState() ;
            Address address = new Address(street,city,state) ;

            char rating = this.view.getRating() ;
            short yearsOfExperience = this.view.getYearsOfExperience() ;
            Driver driver = new Driver(driverLicense,name,sex,address,rating,yearsOfExperience,availability);
            this.handler.addDriver(driver,this.handler.lastDriverIndex());
            this.view.restoreFields() ;
        }catch (Exception e){
            this.view.showMessage(e.getMessage()) ;

        }finally {
            this.view.emptyTable() ;
            loadData();
            handler.setWarehouseInUse(false);
        }
    }
    public void fillFields(int index){
        Driver driver = this.handler.indexToDriver(index);
        this.view.setName(driver.getDriverName());
        this.view.setSex(driver.getSex());
        this.view.setDriverLicense(driver.getDriverLicense()) ;
        this.view.setAvailability(driver.getAvailability());
        Address address = driver.getAddress();
        this.view.setStreet(address.getStreet());
        this.view.setCity(address.getCity());
        this.view.setState(address.getState());
        this.view.setRating(driver.getRatings());
        this.view.setYearsOfExperience(driver.getYearsOfExperiences());
    }

    public void deleteDriver(int index){
        try {
            waitWarehouseBusy();
            this.handler.deleteDriver(index);
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

    public void updateDriver(int index){
        try {
            waitWarehouseBusy() ;
            Driver driver = this.handler.indexToDriver(index) ;
            if (this.handler.ableToUpdateDriver(index, this.view.getDriverLicense())){
                driver.setDriverName(this.view.getName());
                driver.setSex(this.view.getSex());
                driver.setDriverLicense(this.view.getDriverLicense());
                driver.setAvailability(this.view.getAvailability());
                Address address = new Address(this.view.getStreet() ,this.view.getCity(),this.view.getState());
                driver.setAddress(address);
                driver.setRatings(this.view.getRating());
                driver.setYearsOfExperiences(this.view.getYearsOfExperience());
                this.view.restoreFields() ;


            }
        }catch (Exception e){
            this.view.showMessage(e.getMessage()) ;

        }finally {
            this.view.emptyTable() ;
            loadData();
            handler.setWarehouseInUse(false);
        }
    }
    private void waitWarehouseBusy(){
        if (handler.isWarehouseInUse()){
            handler.keepOperationBusy() ;
        }
        handler.setWarehouseInUse(true);
    }
}

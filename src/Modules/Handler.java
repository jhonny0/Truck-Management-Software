package Modules;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.concurrent.CancellationException;

public class Handler {

    private String name;
    private String state;
    private GregorianCalendar dateAndTime = new GregorianCalendar();
    private boolean warehouseInUse = false;
    private ArrayList<Trucks> truckList;
    private ArrayList<Driver> driverList;
    private ArrayList<Travel> travelList;


    public boolean isWarehouseInUse() {
        return warehouseInUse;
    }

    public void setWarehouseInUse(boolean value) {
        this.warehouseInUse = value;
    }

    public void keepOperationBusy(){
        while (warehouseInUse){

        }
    }

    public Handler() {
        this.truckList = new ArrayList<>();
        this.driverList = new ArrayList<>();
        this.travelList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private File createAndOpenDataBase(String dataBaseName){
        File file = new File("DataBase" + File.separator + dataBaseName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public void readNameState() {
        File file = createAndOpenDataBase("Warehouse.raj");

        try {
            Scanner read = new Scanner(file);
            if(read.hasNextLine()) {
                setName(read.nextLine());
            }
            if(read.hasNextLine()){
                setState(read.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeNameState() {
        FileWriter writer;
        File file = createAndOpenDataBase("Warehouse.raj");

        try {
            writer = new FileWriter(file);
            writer.write(name + "\n");
            writer.write(state + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Trucks> getTruckList() {
        return this.truckList;
    }

    public ArrayList<Driver> getDriverList() {
        return this.driverList;
    }

    public ArrayList<Travel> getTravelList() {
        return this.travelList;
    }

    public void updateDateTime() {
        this.dateAndTime = new GregorianCalendar();

    }

    public String getDateAndTimetoGUI() {
        return dateAndTime.get(GregorianCalendar.MONTH) + "/" + dateAndTime.get(GregorianCalendar.DATE) + "/"
                + dateAndTime.get(GregorianCalendar.YEAR) + "-" + dateAndTime.get(GregorianCalendar.HOUR_OF_DAY) +
                ":" + dateAndTime.get(GregorianCalendar.MINUTE);
    }


    //**********Drivers*********//

    public boolean writeDrivers() {
        FileWriter writer;
        File file = createAndOpenDataBase("Drivers.raj");

        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            return false;
        }
        for (Driver driver : this.driverList) {
            driver = (Driver) driver;

            try {
                writer.write(driver.toString());
            } catch (IOException e) {
                return false;
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void readDrivers() throws FileNotFoundException {
        this.driverList.clear();
        try {
            File file = createAndOpenDataBase("Drivers.raj");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String a = reader.nextLine();
                String[] reading = a.split("/");
                Address address = new Address(reading[Driver.STREET], reading[Driver.CITY], reading[Driver.STATE]);
                Driver driver = new Driver(reading[Driver.DRIVER_LICENSE], reading[Driver.NAME], reading[Driver.SEX].charAt(0), address, reading[Driver.RATING].charAt(0)
                        , Short.valueOf(reading[Driver.YEARS_EXPERIENCE]), Boolean.valueOf(reading[Driver.AVAILABLE]));
                this.driverList.add(driver);

            }
            reader.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("DataBase \"Drivers.raj\" not found.");
        }

    }

    private boolean presentDriverID(String driverLicense) {
        for (Object driver : this.driverList) {
            Driver actual = (Driver) driver;
            if (actual.isDriverLicense(driverLicense)) {
                return true;
            }
        }
        return false;
    }


    public int lastDriverIndex() {
        return this.driverList.size();
    }


    public void addDriver(Driver driver, int index) throws Exception {
        if (this.presentDriverID(driver.getDriverLicense())) {
            throw new Exception("Driver already exists. Try using different a driver license.");
        }
        this.driverList.add(index, driver);
    }


    public void deleteDriver(int index) throws Exception {
        Driver driver = (Driver) this.driverList.get(index);
        if (!driver.getAvailability()) {
            throw new Exception("Can not delete a driver that has been already assigned.\n" +
                    "Try deleting the travel that the driver was assigned for");
        }
        this.driverList.remove(index);
    }

    public boolean ableToUpdateDriver(int index, String newLicense) throws Exception {
        Driver driver = (Driver) this.driverList.get(index);

        if (!driver.getAvailability()) {
            throw new Exception("Can not update a driver that has been already assigned\n"
                    + "Try deleting the travel that the driver was assigned for.");
        }
        else if(!driver.isDriverLicense(newLicense)){
            for(int currentIndex = 0; currentIndex < driverList.size(); currentIndex++){
                if(index != currentIndex && driverList.get(currentIndex).isDriverLicense(newLicense)){
                    throw new Exception("That driver license is in use.");
                }
            }
        }
        return true;
    }

    public int indexPerID(String driverLicense) throws Exception {
        Driver driver;
        for (short index = 0; index < this.driverList.size(); index++) {
            driver = (Driver) driverList.get(index);
            if (driver.isDriverLicense(driverLicense)) {
                return index;
            }
        }
        throw new Exception("The driver " + driverLicense + " was not found.");
    }

    public Driver indexToDriver(int index) {
        return (Driver) this.driverList.get(index);
    }

    //*********************************//


    //**********************TRucks//////////////

    public boolean writerTrucks() {
        FileWriter writer;
        File file = createAndOpenDataBase("Trucks.raj");

        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            return false;
        }
        for (Object trucks : this.truckList) {
            trucks = (Trucks) trucks;
            try {
                writer.write(trucks.toString());

            } catch (IOException e) {
                return false;
            }
        }
        try {
            writer.close();

        }catch (IOException e){
            return false;
        }
        return true;
    }

    public void readTrucks()throws FileNotFoundException{
        this.truckList.clear();
        try {
            File file = createAndOpenDataBase("Trucks.raj");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()){
                String[] reading = reader.nextLine().split("/");
                Trucks trucks = new Trucks(reading[Trucks.LICENSE_PLATE],reading[Trucks.CODE],
                        reading[Trucks.BRAND],reading[Trucks.MODEL],
                        Short.valueOf(reading[Trucks.FABRICATION_YEAR]),Short.valueOf(reading[Trucks.MILEAGE])
                        ,reading[Trucks.TRUCK_HEALTH],reading[Trucks.AVAILABILITY]) ;
                this.truckList.add(trucks) ;
            }
            reader.close();
        }
        catch (FileNotFoundException e){
            throw  new FileNotFoundException("DataBase \" Trucks.raj\" was not fond .");

        }

    }

    public int indexPerPlateTrucks(String plate) throws Exception{
        Trucks trucks ;
        for (int index = 0 ; index <this.truckList.size();index++){
            trucks = (Trucks) truckList.get(index) ;
            if (trucks.isLicensePlate(plate)){
                return index ;
            }
        }
        throw new Exception("The truck with license plate :"+plate+" was not found") ;

    }
    /// look if the license plate is already on the data base
    private boolean isLicensePlateTruck(String plate){
        for (Object trucks: this.truckList){
            Trucks actual = (Trucks) trucks ;
            if (actual.isLicensePlate(plate))
                return true ;
        }
        return false ;
    }
    ///adding new trucks to data base
    public void addTruck( Trucks trucks, int index)throws Exception{
        if (isLicensePlateTruck(trucks.getLicensePlate()))
            throw new Exception(String.format("The truck already exist.\n License plate already exist.")) ;
        this.truckList.add(index,trucks) ;


    }

    /////Eleminating trucks from data base

    public void eliminateTruck(int index) throws Exception{
        Trucks trucks = (Trucks) this.truckList.get(index);
        if (trucks.truckInUse()){
            throw new Exception("The truck had been assigned therefore can not be deleted.\n " +
                    "Try unassigned the truck before deleting it.");
        }
        this.truckList.remove(index) ;
    }

    public boolean ableToUpdateTruck(int index, String newLicensePlate)throws Exception{
        Trucks trucks = (Trucks) this.truckList.get(index) ;
        if (trucks.truckInUse()){
            throw new Exception("Trucks that are in use can not been updated") ;
        }
        if(!trucks.isLicensePlate(newLicensePlate)){
            for(int currentIndex = 0; currentIndex < truckList.size(); currentIndex++){
                if(index != currentIndex && truckList.get(currentIndex).isLicensePlate(newLicensePlate)){
                    throw new Exception("This License Plate is in use.");
                }
            }
        }
        return true;
    }
    public Trucks indexPerTrucks(int index){
        return (Trucks) this.truckList.get(index) ;
    }
    public int lastIndexTrucks(){
        return this.truckList.size();
    }
    //////******************************************************************************//


    ///*****************TRAVEL OR LOADS***************************************************//

    public boolean writeTravels(){
        FileWriter writer = null ;
        File file = createAndOpenDataBase("Travels.raj");
        try {
            writer = new FileWriter(file) ;
        } catch (IOException e) {
            e.printStackTrace();
            return false ;
        }
        for (int travel = 0 ; travel < this.travelList.size();travel++){
            try {
                if (this.travelList.get(travel) instanceof InterstateTravel){
                    InterstateTravel actualTravel = (InterstateTravel) this.travelList.get(travel);
                    writer.write(actualTravel.toString());
                }else{
                    OutStateTravel actualTravel = (OutStateTravel) this.travelList.get(travel);
                    writer.write(actualTravel.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            return false ;
        }
        return true ;
    }

    public void readTravels()throws Exception{
        this.travelList.clear();
        try {
            File file = createAndOpenDataBase("Travels.raj") ;
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()){

                String[] reading = reader.nextLine().split("/") ;
                Trucks trucks = (Trucks) this.truckList.get(this.indexPerPlateTrucks(reading[Travel.TRUCK_ID]));
                Driver driver = (Driver) driverList.get(this.indexPerID(reading[Travel.DRIVER_ID]));

                GregorianCalendar dateAndTime = new GregorianCalendar() ;
                dateAndTime.set(Calendar.YEAR,Integer.valueOf(reading[Travel.YEAR]));
                dateAndTime.set(Calendar.MONTH,Integer.valueOf(reading[Travel.MONTH])-1);
                dateAndTime.set(Calendar.DATE,Integer.valueOf(reading[Travel.DAY]));

                dateAndTime.set(Calendar.HOUR_OF_DAY,Integer.valueOf(reading[Travel.HOUR]));
                dateAndTime.set(Calendar.MINUTE,Integer.valueOf(reading[Travel.MINUTE]));
                dateAndTime.set(Calendar.SECOND,Integer.valueOf(reading[Travel.SECOND]));

                if (reading[Travel.TYPE_OF_TRAVEL].equals(InterstateTravel.TYPE_OF_TRAVEL)){
                    InterstateTravel travel = new InterstateTravel() ;
                    travel.setTrucks(trucks);
                    travel.setDriver(driver);
                    travel.setCodeID(reading[Travel.CODE_ID]);
                    travel.setMilesToTravel(Short.valueOf(reading[Travel.MILES_TO_TRAVEL]));
                    travel.setDuration(Short.valueOf(reading[Travel.DURATION]));
                    travel.setDestination(reading[Travel.DESTINATION]);
                    travel.setDateAndTime(dateAndTime);
                    this.travelList.add(travel) ;
                }else {
                    OutStateTravel travel = new OutStateTravel();
                    travel.setTrucks(trucks);
                    travel.setDriver(driver);
                    travel.setCodeID(reading[Travel.CODE_ID]);
                    travel.setMilesToTravel(Short.valueOf(reading[Travel.MILES_TO_TRAVEL]));
                    travel.setDuration(Short.valueOf(reading[Travel.DURATION]));
                    travel.setDestination(reading[Travel.DESTINATION]);
                    travel.setDateAndTime(dateAndTime);
                    this.travelList.add(travel);
                }


            }
            reader.close();
        }
        catch (FileNotFoundException e){
            throw new FileNotFoundException("DataBase \" Trucks.raj\" not found.");
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public int indexPerTravelCode(String CodeId){
        Travel travel ;
        for (int index = 0 ; index < this.travelList.size();index++){
            travel = (Travel) this.travelList.get(index) ;
            if (travel.isCodeID(CodeId))
                return index ;
        }
        return -1 ;
    }

    private boolean isTravelID(String ID){
        for (Travel travel : this.travelList){
            Travel actual = (Travel) travel ;
            if (actual.isCodeID(ID))
                return true ;
        }
        return false;
    }

    public void addTravel( Travel travel, int index) throws Exception{
        Trucks trucks = travel.getTrucks() ;
        Driver driver = travel.getDriver();

        if (isTravelID(travel.getCodeID())){
            throw new Exception(String.format("The travel already exist.\n The Travel ID code is already assigned ")) ;
        }
        if (trucks.truckInUse()){
            throw new Exception("The truck you are trying to assigned is already in use.");

        }
        if (!driver.isAvailable()){
            throw new Exception("The driver selected is already assigned to other travel");
        }

        trucks.setAvailable(Trucks.IN_USE);
        driver.setAvailability(false);
        travel.setDriver(driver);
        travel.setTrucks(trucks);
        this.travelList.add(index,travel);
    }

    public void deleteTravel(int index) throws Exception{
        try{
            Travel travel = (Travel) this.travelList.get(index);
            Driver driver = travel.getDriver();
            Trucks trucks = travel.getTrucks();
            int indexDriver = this.indexPerID(driver.getDriverLicense());
            int indexTruck = this.indexPerPlateTrucks(trucks.getLicensePlate());
            driver.setAvailability(true);
            trucks.setAvailable("Yes");
            this.driverList.remove(indexDriver);
            this.truckList.remove(indexTruck);
            this.driverList.add(indexDriver,driver);
            this.truckList.add(indexTruck,trucks);
            this.travelList.remove(index);


        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void updateTravel( Travel updatedTravel , int travelIndex)throws Exception{


        Travel travelsNotUpdated = (Travel) this.travelList.get(travelIndex) ;

        if(!updatedTravel.getDriver().isDriverLicense(travelsNotUpdated.getDriver().getDriverLicense())){
            if(!updatedTravel.getDriver().getAvailability()){
                throw new Exception("The driver selected is in use.");
            }
        }
        if(!updatedTravel.getTrucks().isLicensePlate(travelsNotUpdated.getTrucks().getLicensePlate())){
            if(updatedTravel.getTrucks().getAvailable().equals(Trucks.IN_USE)){
                throw new Exception("The selected truck is in use");
            }
        }

        for(int currentIndex = 0; currentIndex < travelList.size(); currentIndex++){
            if(travelIndex != currentIndex && updatedTravel.isCodeID(travelList.get(currentIndex).getCodeID())){
                throw new Exception("The given ID code is in use");
            }
        }
        travelsNotUpdated.getDriver().setAvailability(true);
        travelsNotUpdated.getTrucks().setAvailable(Trucks.AVAILABLE);

        travelList.remove(travelIndex);
        updatedTravel.getDriver().setAvailability(false);
        updatedTravel.getTrucks().setAvailable(Trucks.IN_USE);
        travelList.add(travelIndex, updatedTravel);
    }

    public Travel indexPerTravel(int index){
        return (Travel) this.travelList.get(index) ;
    }
    public int lastIndexTravel(){
        return travelList.size();
    }
    public Travel travelPerIDTravel(String IDTravel)throws Exception{

        Travel actualTravel ;
        for (int travel= 0 ; travel <this.travelList.size();travel++){
            System.out.println("IDDD:" + IDTravel + "  " + travelList.get(travel).getCodeID());
            actualTravel = (Travel) this.travelList.get(travel);
            if (actualTravel.isCodeID(IDTravel)) {
                return actualTravel;
            }
        }
            throw new Exception("Travel "+IDTravel+" not found.");
    }



        public void travelLeaving(String IDTravel)throws Exception{
        try {
            int travelIndex = this.indexPerTravelCode(IDTravel);
            Travel travel = (Travel) (this.travelList.get(travelIndex));
            int indexDriver = this.indexPerID(travel.getDriver().getDriverLicense());
            int indexTruck = this.indexPerPlateTrucks(travel.getTrucks().getLicensePlate()) ;
            this.travelList.remove(travelIndex) ;
            this.driverList.remove(indexDriver);
            this.truckList.remove(indexTruck);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        }
        public ArrayList getTravelOrderByDateAndTime(){
        ArrayList<Travel> listTravel = (ArrayList<Travel>) this.travelList.clone() ;
        listTravel.sort(null);
        return listTravel ;
        }

        ///******************************************************************//
    public int truckNotAvailableBadConditions(){
        int counter = 0 ;
        for (Trucks trucks : this.truckList) {
            if (trucks.badHealth())
                counter++;
        }
            return counter;
        }

        //*****************************************************************//
    public ArrayList<Driver> getDriverOrderDecreasing(){
        ArrayList<Driver> listDriver = (ArrayList<Driver>) this.driverList.clone();
        listDriver.sort(null);
        return listDriver;
    }
    ///********************************************************************//

    public ArrayList<Travel> getTravelFilter(String typeOfTravel, String plateTruck, String idDriver , String destination, GregorianCalendar date ){
        ArrayList<Travel> listTravel = new ArrayList<>();
        if (typeOfTravel == null)
            return listTravel ;
        for (Travel actualTravel : travelList){
            if (!typeOfTravel.equals("") && !actualTravel.getTypeOfTravel().equals(typeOfTravel))
                continue;

            if (plateTruck !=null && !actualTravel.getTrucks().isLicensePlate(plateTruck))
                continue;
            if (idDriver !=null && !actualTravel.getDriver().isDriverLicense(idDriver))
                continue;
            if (destination !=null && !actualTravel.isDestination(destination))
            continue;
            if (date!=null && !actualTravel.sameDate(date))
                continue;
            listTravel.add(actualTravel);
        }
        return listTravel ;
    }


    }









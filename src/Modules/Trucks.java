package Modules;

public class Trucks implements Comparable<Trucks>{

   //CONSTANTS
    public static final int LICENSE_PLATE =0 ;
    public static final int CODE =1 ;
    public static final int BRAND = 2 ;
    public static final int MODEL =3 ;
    public static final int FABRICATION_YEAR = 4;
    public static final int MILEAGE = 5 ;
    //public static final int MILEAGE_TO_TRAVEL = 6;
    public static final int TRUCK_HEALTH =6 ;
    public static final int AVAILABILITY =7 ;

    public static final String AVAILABLE = "YES" ;
    public static final String IN_USE = "IN USE" ;
    public static final String NOT_AVAILABLE = " NOT AVAILABLE" ;

    //ATTRIBUTES
    private String licensePlate ;
    private String code ;
    private String brand ;
    private String model ;
    private short fabrication_year ;
    private short mileage ;
    private short mileageToTravel ;
    private String truckHealth ;
    private String available ;

    public Trucks(String licensePlate, String code, String brand, String model,
                  short fabrication_year, short mileage, String truckHealth, String available) {
        this.licensePlate = licensePlate;
        this.code = code;
        this.brand = brand;
        this.model = model;
        this.fabrication_year = fabrication_year;
        this.mileage = mileage;
        this.mileageToTravel = mileageToTravel;
        this.truckHealth = truckHealth;
        this.available = available;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String value) {
        this.licensePlate = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String value) {
        this.brand = value;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String value) {
        this.model = value;
    }

    public short getFabrication_year() {
        return fabrication_year;
    }

    public void setFabrication_year(short value) {
        this.fabrication_year = value;
    }

    public short getMileage() {
        return mileage;
    }

    public void setMileage(short value) {
        this.mileage = value;
    }

    public short getMileageToTravel() {
        return mileageToTravel;
    }

    public void setMileageToTravel(short value) {
        this.mileageToTravel = value;
    }

    public String getTruckHealth() {
        return truckHealth;
    }

    public void setTruckHealth(String value) {
        this.truckHealth = value;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String value) {
        this.available = value;
    }
    //

    public boolean goodHealth(){
        return this.truckHealth.equalsIgnoreCase("GOOD") ;
    }
    public boolean regularHealth(){
        return this.truckHealth.equalsIgnoreCase("REGULAR") ;
    }
    public boolean badHealth(){
        return this.truckHealth.equalsIgnoreCase("BAD") ;
    }

    public boolean truckInUse(){
        return this.getAvailable().equalsIgnoreCase(IN_USE);
    }

    public boolean equals(Trucks otherTrucks){
        return this.getLicensePlate().equalsIgnoreCase(otherTrucks.getLicensePlate()) ;
    }

    @Override
    public String toString() {
        return licensePlate + "/" +
                code + "/" +
                brand + "/" +
                model + "/" +
                fabrication_year + "/" +
                mileage + "/" +
                truckHealth + "/" +
                available + "\n";
    }
    public boolean isLicensePlate(String licensePlate){
        return this.licensePlate.equals(licensePlate) ;
    }

    public String [] toTable(){
        return new String[]{
                this.licensePlate,this.code,this.brand,this.model,
                Short.toString(this.fabrication_year),this.truckHealth,this.available,
                Short.toString(this.mileage)};
        }


    @Override
    public int compareTo(Trucks trucks) {
        return 0;
    }
}

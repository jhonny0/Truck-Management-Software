package Modules;

public class Driver implements Comparable<Driver> {

    //Constants
    public static final int DRIVER_LICENSE = 0 ;
    public static final int NAME = 1 ;
    public static final int SEX = 2;
    public static final int STREET = 3 ;
    public static final int CITY = 4 ;
    public static final int STATE = 5 ;
    public static final int RATING = 6 ;
    public static final int YEARS_EXPERIENCE = 7 ;
    public static final int AVAILABLE = 8 ;
    public static final Character[] DRIVER_RATINGS = {'A','B','C'} ;

    //ATTRIBUTES
    private String driverLicense ;
    private String driverName ;
    private Address address ;
    private char sex ;
    private char ratings ;
    private short yearsOfExperiences ;
    private boolean available ;


    public Driver(String driverLicense, String driverName, char sex, Address address, char ratings,
                  short yearsOfExperiences, boolean available) {


        this.driverLicense = driverLicense;
        this.driverName = driverName;
        this.sex = sex;
        this.ratings = ratings;
        this.yearsOfExperiences = yearsOfExperiences;
        this.address = address;
        this.available = available;
    }

    //getters and setters
    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String value) {
        this.driverLicense = value;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String value) {
        this.driverName = value;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char value) {
        this.sex = value;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address value) {
        this.address = value;
    }

    public char getRatings() {
        return ratings;
    }

    public void setRatings(char value) {
        this.ratings = value;
    }

    public short getYearsOfExperiences() {
        return yearsOfExperiences;
    }

    public void setYearsOfExperiences(short value) {
        this.yearsOfExperiences = value;
    }

    public boolean getAvailability() {
        return available;
    }

    public void setAvailability(boolean value) {
        this.available = value;
    }

    //

    public boolean isAvailable (){
        return this.getAvailability();
    }
    public boolean isDriverLicense(String value){
        return this.driverLicense.equals(value) ;
    }
    public boolean isRating (char value){
        return this.ratings == value ;
    }
    public boolean equals (Driver others){
        return this.driverLicense.equals(others.getDriverLicense()) ;
    }

    @Override
    public String toString() {
        return driverLicense + "/" +
                driverName +  "/" +
                sex + "/" +
                address.getStreet() + "/" +
                address.getCity() + "/" +
                address.getState() + "/" +
                ratings + "/" +
                yearsOfExperiences + "/" +
                available + "\n";
    }

    public String [] toTable (){
        String availability ;
        if (available) {
            availability = "Yes" ;

        }else {
            availability = "No";
        }
        return new String[]{ driverLicense,driverName,Character.toString(sex),
                availability, this.address.toTable(),Character.toString(this.ratings),Short.toString(this.yearsOfExperiences)};


    }

    @Override
    public int compareTo(Driver otherDriver) {
        if (this.yearsOfExperiences > otherDriver.getYearsOfExperiences()){
            return -1 ;
        }else if (this.yearsOfExperiences < otherDriver.getYearsOfExperiences()){
            return 1 ;
        }
        return 0;
    }
}

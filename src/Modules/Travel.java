package Modules;

import java.lang.annotation.Target;
import java.time.Year;
import java.util.GregorianCalendar;

public class Travel implements Comparable<Travel>{


    /////CONSTANTS
    public static final int TYPE_OF_TRAVEL = 0 ;
    public static final int CODE_ID = 1 ;
    public static final int MILES_TO_TRAVEL = 2 ;
    public static final int TRUCK_ID = 3 ;
    public static final int DRIVER_ID = 4 ;
    public static final int YEAR = 5 ;
    public static final int MONTH = 6;
    public static final int DAY = 7;
    public static final int HOUR = 8;
    public static final int MINUTE = 9 ;
    public static final int SECOND =10 ;
    public static final int DURATION = 11 ;
    public static final int DESTINATION = 12 ;

    /* DATE AND TIME ORDER :
    YEAR, MONTH,DAY , HOUR , MINUTE,SECOND*/

    //ATTRIBUTES

    private String codeID ;
    private short milesToTravel ;
    private Trucks trucks ;
    private Driver driver ;
    private GregorianCalendar dateAndTime ;
    private short duration ;
    private String destination ;

    public Travel(){

    }

    public Travel(String codeID, short milesToTravel, Trucks trucks,
                  Driver driver, GregorianCalendar dateAndTime, short duration, String destination) {
        this.codeID = codeID;
        this.milesToTravel = milesToTravel;
        this.trucks = trucks;
        this.driver = driver;
        this.dateAndTime = dateAndTime;
        this.duration = duration;
        this.destination = destination;
    }



    public String getCodeID() {
        return codeID;
    }

    public void setCodeID(String value) {
        this.codeID = value;
    }

    public short getMilesToTravel() {
        return milesToTravel;
    }

    public void setMilesToTravel(short value) {
        this.milesToTravel = value;
    }

    public Trucks getTrucks() {
        return trucks;
    }

    public void setTrucks(Trucks value) {
        this.trucks = value;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public GregorianCalendar getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(GregorianCalendar dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public short getDuration() {
        return duration;
    }

    public void setDuration(short duration) {
        this.duration = duration;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    public boolean isCodeID(String otherCodeID){
        return this.codeID.equalsIgnoreCase(otherCodeID);
    }

    public boolean equals(Travel other){
        return this.codeID.equalsIgnoreCase(other.getCodeID());
    }

    public GregorianCalendar[] getIntervals(int start , int end){
        GregorianCalendar actual = new GregorianCalendar();
        GregorianCalendar ini = (GregorianCalendar) actual.clone();
        GregorianCalendar ter = (GregorianCalendar) actual.clone();
        ini.add(GregorianCalendar.MINUTE,start);
        ter.add(GregorianCalendar.MINUTE,end);
        GregorianCalendar[] intervals = {ini,ter} ;
        return intervals ;
    }

    ///get the status of the travel
    public String getStatus(){

        GregorianCalendar[]intervals = getIntervals(50,60);
        if (dateAndTime.after(intervals[0])&& dateAndTime.before(intervals[1])){
            System.out.println("Arriving");
            return "Arriving" ;
        }
        intervals = getIntervals(35,49);
        if (dateAndTime.after(intervals[0])&& dateAndTime.before(intervals[1])){
            System.out.println("Parking");
            return "Parking";
        }

        intervals =getIntervals(6,34);
        if (dateAndTime.after(intervals[0])&&dateAndTime.before(intervals[1])){
            System.out.println("Unloading ");
            return "Unloading" ;
        }

        intervals = getIntervals(0,5);
        if (dateAndTime.after(intervals[0])&& dateAndTime.before(intervals[1])){
            System.out.println("Leaving");
            return "Leaving" ;
        }
        return "Waiting" ;


    }
    private String dateAndTimeToString(){
        return this.dateAndTime.get(GregorianCalendar.YEAR)+"/"+(this.dateAndTime.get(GregorianCalendar.MONTH)+1)+
                "/"+this.dateAndTime.get(GregorianCalendar.DATE)+"/"+this.dateAndTime.get(GregorianCalendar.HOUR_OF_DAY)+
                "/" +this.dateAndTime.get(GregorianCalendar.MINUTE)+"/"+this.dateAndTime.get(GregorianCalendar.SECOND);

    }

    public String dateAndTimeToTable(){
        return this.dateAndTime.get(GregorianCalendar.YEAR)+"/"+(this.dateAndTime.get(GregorianCalendar.MONTH)+1 +"/"+
                +this.dateAndTime.get(GregorianCalendar.DATE))+ " " +this.dateAndTime.get(GregorianCalendar.HOUR_OF_DAY)+":"+
                this.dateAndTime.get(GregorianCalendar.MINUTE);



    }

    @Override
    public String toString() {
        return codeID + "/" +
                milesToTravel + "/" +
                trucks.getLicensePlate() + "/" +
                driver.getDriverLicense() + "/" +
                this.dateAndTimeToString() + "/" +
                duration + "/" +
                destination + "\n";
    }
    public String[] linkForTable(){
        return new String[] {codeID, Short.toString(milesToTravel),
        this.dateAndTimeToTable(), Short.toString(duration), destination,
        trucks.getLicensePlate(), driver.getDriverLicense()};
    }
    public boolean isLeaving (){
        GregorianCalendar dateAndTimeActual = new GregorianCalendar() ;
        GregorianCalendar dateAndTimeTravel = getDateAndTime();
        return dateAndTimeTravel.before(dateAndTimeActual);
    }
    @Override
    public int compareTo(Travel otherTravel) {
        if (this.dateAndTime.before(otherTravel.getDateAndTime()))
            return -1 ;
        else if (this.dateAndTime.after(otherTravel.getDateAndTime()))
            return 1 ;
        return 0 ;
    }

    public boolean isDestination(String value){
        return this.destination.equals(value) ;
    }

    public String getTypeOfTravel(){
        return "" ;
    }

    public boolean sameDate(GregorianCalendar dateCompare){
        return dateAndTime.get(GregorianCalendar.MONTH) == dateCompare.get(GregorianCalendar.MONTH)&&
                dateAndTime.get(GregorianCalendar.DATE) == dateCompare.get(GregorianCalendar.DATE) &&
                dateAndTime.get(GregorianCalendar.YEAR) == dateCompare.get(GregorianCalendar.YEAR) ;



    }
    public String[]  dataToFilter(){
        //Here is the data to sort and filter by :
        return new String[]{this.codeID,Short.toString(milesToTravel),dateAndTimeToString(),Short.toString(duration),destination,
        trucks.getLicensePlate(),driver.getDriverLicense()};
    }

}

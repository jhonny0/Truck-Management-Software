package Modules;

import java.util.GregorianCalendar;

public class InterstateTravel extends Travel{

    ///CONSTANTS
    public final static String TYPE_OF_TRAVEL = "Interstate Travel" ;
    public InterstateTravel(){
        super();
    }

    public InterstateTravel(String codeID, short milesToTravel, Trucks trucks, Driver driver, GregorianCalendar dateAndTime, short duration, String destination) {
        super(codeID, milesToTravel, trucks, driver, dateAndTime, duration, destination);
    }
        public String typeOfTravel(){
        return "Interstate Travel" ;
    }

    public String toString (){
        String travel = this.TYPE_OF_TRAVEL + "/" + super.toString();
        return travel ;
    }

    public String[] linkToTable(){
        return new String[]{this.getCodeID(),Short.toString(this.getMilesToTravel()),this.dateAndTimeToTable(),
        Short.toString(this.getDuration()),this.getDestination(),this.getTrucks().getLicensePlate(),this.getDriver().getDriverLicense()};

    }


    public String getTypeOfTravel() {
        return TYPE_OF_TRAVEL;
    }
}

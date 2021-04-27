package Modules;

import java.util.GregorianCalendar;

public class OutStateTravel extends Travel {

    //CONSTANTS
    public static final String TYPE_OF_TRAVEL = "Out of State Travel" ;

    public OutStateTravel(){
        super();
    }
    public OutStateTravel(String codeID, short milesToTravel, Trucks trucks, Driver driver, GregorianCalendar dateAndTime, short duration, String destination) {
        super(codeID, milesToTravel, trucks, driver, dateAndTime, duration, destination);
    }

    public boolean isDestination(String destination){
        return this.getDestination().equalsIgnoreCase(destination);
    }

    @Override
    public String toString() {
        String travel = this.TYPE_OF_TRAVEL + "/"+super.toString();
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

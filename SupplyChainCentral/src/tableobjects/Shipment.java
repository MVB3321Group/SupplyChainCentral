/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Shipment
*/
package tableobjects;

import java.util.Date;

/**
 *
 * @author Benjamin
 */
public class Shipment {
    private int shipID;
    private String origin;
    private String destination;
    private int priority;
    private int scheduleID;
    private Date startTime;
    private Date endTime;
    private String currentLocation;
    
    public Shipment(int shipID, String origin, String destination, int priority) {
        setShipID(shipID);
        setOrigin(origin);
        setDestination(destination);
        setPriority(priority);
    }
    
    public int getShipID() {
        return shipID;
    }
    
    public void setShipID(int shipID) {
        this.shipID = shipID;
    }
    
    public String getOrigin() {
        return origin;
    }
    
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
}

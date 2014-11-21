/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Shipment
*/
package tableobjects;

import java.sql.Date;

/**
 *
 * @author Benjamin
 */
public class Shipment {
    private int shipID;
    private int originatorID;
    private String origin;
    private String destination;
    private int priority;
    private int scheduleID;
    private Date startTime;
    private Date endTime;
    private String currentLocation;
    private Date ETA;
    
    public Shipment(int originatorID, String origin, String destination, int priority) {
        setOriginatorID(originatorID);
        setOrigin(origin);
        setDestination(destination);
        setPriority(priority);
    }
    
    public Shipment(int shipID, int originatorID, String origin,
            String destination, int priority,int scheduleID, Date startTime,
            Date endTime, String currentLocatio) {
        setShipID(shipID);
        setOriginatorID(originatorID);
        setOrigin(origin);
        setDestination(destination);
        setPriority(priority);
        setScheduleID(scheduleID);
        setStartTime(startTime);
        setEndTime(endTime);
        setCurrentLocation(currentLocation);
        //setETA(ETA);
    }
    
    public Shipment() {} // No-arg constructor
    
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

    public int getOriginatorID() {
        return originatorID;
    }

    public void setOriginatorID(int originatorID) {
        this.originatorID = originatorID;
    }
    
    public void setETA(Date ETA){
            this.ETA = ETA;
    }
    
    public Date getETA() {
        return ETA;
    }
}
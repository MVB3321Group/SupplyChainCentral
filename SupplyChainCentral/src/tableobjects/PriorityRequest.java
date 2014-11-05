/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    PriorityRequest
*/
package tableobjects;

/**
 *
 * @author Benjamin
 */
public class PriorityRequest {
    private int employeeID;
    private int shipID;
    private int priority;
    
    public PriorityRequest(int employeeID, int shipID, int priority) {
        setEmployeeID(employeeID);
        setShipID(shipID);
        setPriority(priority);
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getShipID() {
        return shipID;
    }

    public void setShipID(int shipID) {
        this.shipID = shipID;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    
}

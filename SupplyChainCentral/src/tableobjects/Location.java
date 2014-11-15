/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Location
*/
package tableobjects;

import javafx.geometry.Point2D;

/**
 *
 * @author Benjamin
 */
public class Location {
    private String locationCode;
    private int locationType;
    private String address;
    private int zip;
    private String city;
    private String state;
    private Point2D GPScoords;
    
    public Location(String locationCode, int locationType, String address,
                    int zip, String city, String state, Point2D GPScoords) {
        setLocationCode(locationCode);
        setLocationType(locationType);
        setAddress(address);
        setZip(zip);
        setCity(city);
        setState(state);
        setGPScoords(GPScoords);
    }
    
    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }    

    public Point2D getGPScoords() {
        return GPScoords;
    }

    public void setGPScoords(Point2D GPScoords) {
        this.GPScoords = GPScoords;
    }
}

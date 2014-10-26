/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Location
*/
package tableobjects;

/**
 *
 * @author Benjamin
 */
public class Location {
    private String locationCode;
    private String locationType;
    private String address;
    private int zip;
    private String city;
    private String state;
    
    public Location(String locationCode, String locationType, String address,
                    int zip, String city, String state) {
        setLocationCode(locationCode);
        setLocationType(locationType);
        setAddress(address);
        setZip(zip);
        setCity(city);
        setState(state);
    }
    
    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
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
}

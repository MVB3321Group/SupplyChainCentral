/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Inventory
*/
package tableobjects;

/**
 *
 * @author Benjamin
 */
public class Inventory {
    private String locationCode;
    private String pName;
    private int productID;
    private int quantity;
    
    public Inventory(String locationCode, int productID, int quantity) {
        setLocationCode(locationCode);
        setProductID(productID);
        setQuantity(quantity);
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

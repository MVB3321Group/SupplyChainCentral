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
    private int productID;
    private int amount;
    
    public Inventory(String locationCode, int productID, int amount) {
        setLocationCode(locationCode);
        setProductID(productID);
        setAmount(amount);
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

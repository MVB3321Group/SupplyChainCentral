/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    Product
*/
package tableobjects;

/**
 *
 * @author Benjamin
 */
public class Product {
    private String pName;
    private int productID;
    private double height;
    private double length;
    private double width;
    private double weight;
    
    public Product(String pName, int productID, double height, double length,
            double width, double weight) {
        setPName(pName);
        setProductID(productID);
        setHeight(height);
        setLength(length);
        setWidth(width);
        setWeight(weight);
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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}

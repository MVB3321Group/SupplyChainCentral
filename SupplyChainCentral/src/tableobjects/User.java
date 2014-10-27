/*  Supply Chain Central Application
    Michael Bernard, Benjamin Chopson, Vasily Kushakov
    CSCI 3321
    User
*/
package tableobjects;

/**
 *
 * @author Benjamin
 */
public class User {
    private String fName;
    private String lName;
    private int employeeID;
    private int managerID;
    private int roleID;
    private String locationCode;
    private String password;
    
    public User(String fName, String lName, int employeeID, int managerID,
                int roleID, String locationCode, String password) {
        setfName(fName);
        setlName(lName);
        setEmployeeID(employeeID);
        setManagerID(managerID);
        setRoleID(roleID);
        setLocationCode(locationCode);
        setPassword(password);
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

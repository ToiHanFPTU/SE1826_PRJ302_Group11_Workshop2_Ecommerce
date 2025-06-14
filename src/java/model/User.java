package model;

public class User {

    private String userID;
    private String fullName;
    private String roleID;
    private String password;
    private String phone;

    // Constructor
    public User(String userID, String fullName, String roleID, String password, String phone) {
        this.userID = userID;
        this.fullName = fullName;
        this.roleID = roleID;
        this.password = password;
        this.phone = phone;
    }

    // Getter and Setter
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Optional: toString for debugging
    @Override
    public String toString() {
        return "User{"
                + "userID='" + userID + '\''
                + ", fullName='" + fullName + '\''
                + ", roleID='" + roleID + '\''
                + ", password='" + password + '\''
                + ", phone='" + phone + '\''
                + '}';
    }
}

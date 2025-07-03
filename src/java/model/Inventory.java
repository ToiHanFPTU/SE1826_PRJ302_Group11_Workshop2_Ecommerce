package model;

public class Inventory {

    private int warehouseID;
    private int productID;
    private String productName;
    private int stockQuantity;
    private int reorderThreshold;

    public Inventory() {
    }

    
    
    public Inventory(int warehouseID, int productID, String productName, int stockQuantity, int reorderThreshold) {
        this.warehouseID = warehouseID;
        this.productID = productID;
        this.productName = productName;
        this.stockQuantity = stockQuantity;
        this.reorderThreshold = reorderThreshold;
    }

    public int getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(int warehouseID) {
        this.warehouseID = warehouseID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getReorderThreshold() {
        return reorderThreshold;
    }

    public void setReorderThreshold(int reorderThreshold) {
        this.reorderThreshold = reorderThreshold;
    }

}

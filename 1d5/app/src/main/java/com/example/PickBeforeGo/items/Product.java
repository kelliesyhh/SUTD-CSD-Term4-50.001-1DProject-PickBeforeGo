package com.example.PickBeforeGo.items;

public class Product {

    private String productName;
    private String brandName;
    private String weightName;
    private int imageId;

    public Product(String productName, String weightName, String brandName, int imageId){
        this.productName = productName;
        this.brandName = brandName;
        this.weightName = weightName;
        this.imageId = imageId;
    }

    public String getProductName() {
        return productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getWeightName() {
        return weightName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setWeightName(String weightName) {
        this.weightName = weightName;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

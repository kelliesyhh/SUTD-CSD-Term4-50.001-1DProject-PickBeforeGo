package com.example.PickBeforeGo.components;

public class Product {
    private String category = null;
    private String date = null;
    private String description = null;
    private String imageURL = null;
    private String productID = null;
    private String productName = null;
    private String price = null;
    private String time = null;
    private String weight = null;
    private String nextRestockTime = null;
    private Double discountPercent = null;
    private Boolean isPromo = false;
    private Boolean isFavourite = false;
    private Boolean inStock = false;

    public Product(){}

    private Product(String category, String date, String description, String imageURL, String productID, String productName, String price,
                    String time, String weight, String nextRestockTime, Double discountPercent, Boolean isPromo, Boolean isFavourite, Boolean inStock){
        this.category = category;
        this.date = date;
        this.description = description;
        this.imageURL = imageURL;
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.time = time;
        this.weight= weight;
        this.nextRestockTime = nextRestockTime;
        this.discountPercent = discountPercent;
        this.isPromo = isPromo;
        this.isFavourite = isFavourite;
        this.inStock = inStock;
    }

    public String getCategory(){
        return this.category;
    }

    public String getDate(){
        return this.date;
    }

    public String getDescription(){
        return this.description;
    }

    public String getImageURL(){
        return this.imageURL;
    }

    public String getProductID(){
        return this.productID;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getPrice(){
        return this.price;
    }

    public String getTime(){
        return this.time;
    }

    public String getWeight() {return this.weight;}

    public String getNextRestockTime(){return this.nextRestockTime;}

    public Double getDiscountPercent(){return this.discountPercent;}

    public Boolean getIsPromo() {return this.isPromo;}

    public Boolean getIsFavourite() {return this.isFavourite;}

    public Boolean getInStock() {return this.inStock;}

    public void setCategory(String category) {this.category = category;}

    public void setDate(String date) {this.date = date;}

    public void setDescription(String description) {this.description = description;}

    public void setImageURL(String imageURL) {this.imageURL = imageURL;}

    public void setProductID(String productID) {this.productID = productID;}

    public void setProductName(String productName) {this.productName = productName;}

    public void setPrice(String price) {this.price = price;}

    public void setTime(String time) {this.time = time;}

    public void setWeight(String weight) {this.weight = weight;}

    public void setNextRestockTime(String nextRestockTime) {this.nextRestockTime = nextRestockTime;}

    public void setDiscountPercent(Double discountPercent) {this.discountPercent = discountPercent;}

    public void setIsPromo(Boolean isPromo) {this.isPromo = isPromo;}

    public void setIsFavourite(Boolean isFavourite) {this.isFavourite = isFavourite;}

    public void setInStock(Boolean inStock) {this.inStock = inStock;}

}

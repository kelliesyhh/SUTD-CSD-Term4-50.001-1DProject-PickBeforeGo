package com.example.PickBeforeGo1.components;

//TODO need justify why the use for builder class here LOL
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

    private Product(ProductBuilder productBuilder){
        this.category = productBuilder.category;
        this.date = productBuilder.date;
        this.description = productBuilder.description;
        this.imageURL = productBuilder.imageURL;
        this.productID = productBuilder.productID;
        this.productName = productBuilder.productName;
        this.price = productBuilder.price;
        this.time = productBuilder.time;
        this.weight= productBuilder.weight;
        this.nextRestockTime = productBuilder.nextRestockTime;
        this.discountPercent = productBuilder.discountPercent;
        this.isPromo = productBuilder.isPromo;
        this.isFavourite = productBuilder.isFavourite;
        this.inStock = productBuilder.inStock;
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

    public Boolean getIsFavorite() {return this.isFavourite;}

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


    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public static class ProductBuilder {
        private String category;
        private String date;
        private String description;
        private String imageURL;
        private String productID;
        private String productName;
        private String price;
        private String time;
        private String weight;
        private String nextRestockTime;
        private Double discountPercent;
        private Boolean isPromo;
        private Boolean isFavourite;
        private Boolean inStock;

        public ProductBuilder(){}

        public ProductBuilder setCategory(String category){
            this.category = category;
            return this;
        }

        public ProductBuilder setDate(String date){
            this.date = date;
            return this;
        }

        public ProductBuilder setDescription(String description){
            this.description = description;
            return this;
        }

        public ProductBuilder setImageURL(String imageURL){
            this.imageURL = imageURL;
            return this;
        }

        public ProductBuilder setProductID(String productID){
            this.productID = productID;
            return this;
        }

        public ProductBuilder setProductName(String productName){
            this.productName = productName;
            return this;
        }

        public ProductBuilder setPrice(String price){
            this.price = price;
            return this;
        }

        //TODO get system time and input as time attribute
        public ProductBuilder setTime(String time){
            this.time = time;
            return this;
        }

        public ProductBuilder setWeight(String weight){
            this.weight = weight;
            return this;
        }

        public ProductBuilder setNextRestockTime(String nextRestockTime){
            this.nextRestockTime = nextRestockTime;
            return this;
        }

        public ProductBuilder setDiscountPercent(Double discountPercent){
            this.discountPercent = discountPercent;
            return this;
        }

        public ProductBuilder setIsPromo(Boolean isPromo){
            this.isPromo = isPromo;
            return this;
        }

        public ProductBuilder setIsFavourite(Boolean isFavourite){
            this.isFavourite = isFavourite;
            return this;
        }

        public ProductBuilder setInStock(Boolean inStock){
            this.inStock = inStock;
            return this;
        }

        public Product build(){
            Product product = new Product(this);
            //validate the product
            return product;
        }

        }

}

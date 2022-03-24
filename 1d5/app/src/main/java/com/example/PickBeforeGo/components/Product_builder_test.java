package com.example.PickBeforeGo.components;
//making builder class for product

//TODO make this the default class for product
public class Product_builder_test {

    private String category = null;
    private String date = null;
    private String description = null;
    private String imageURL = null;
    private String pid = null;
    private String productName = null;
    private String price = null;
    private String time = null;

    private Product_builder_test(ProductBuilder productBuilder){
        this.category = productBuilder.category;
        this.date = productBuilder.date;
        this.description = productBuilder.description;
        this.imageURL = productBuilder.imageURL;
        this.pid = productBuilder.pid;
        this.productName = productBuilder.productName;
        this.price = productBuilder.price;
        this.time = productBuilder.time;
    }

    static class ProductBuilder {
        private String category;
        private String date;
        private String description;
        private String imageURL;
        private String pid;
        private String productName;
        private String price;
        private String time;

        ProductBuilder(){}

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

        public ProductBuilder setPid(String pid){
            this.pid = pid;
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

    public String getPid(){
        return this.pid;
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


}

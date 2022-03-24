package com.example.PickBeforeGo.components;

//TODO need justify why the use for builder class here LOL
public class Product {

    private String category = null;
    private String date = null;
    private String description = null;
    private String imageURL = null;
    private String pid = null;
    private String productName = null;
    private String price = null;
    private String time = null;
    private String weight = null;

    private Product(ProductBuilder productBuilder){
        this.category = productBuilder.category;
        this.date = productBuilder.date;
        this.description = productBuilder.description;
        this.imageURL = productBuilder.imageURL;
        this.pid = productBuilder.pid;
        this.productName = productBuilder.productName;
        this.price = productBuilder.price;
        this.time = productBuilder.time;
        this.weight= productBuilder.weight;
    }

    //all getter methods
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

    public String getWeight() {return this.weight;}

    public static class ProductBuilder {
        private String category;
        private String date;
        private String description;
        private String imageURL;
        private String pid;
        private String productName;
        private String price;
        private String time;
        private String weight;

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

        public ProductBuilder setWeight(String weight){
            this.weight = weight;
            return this;
        }

        public Product build(){
            Product product = new Product(this);
            //validate the product
            return product;
        }

        }

}

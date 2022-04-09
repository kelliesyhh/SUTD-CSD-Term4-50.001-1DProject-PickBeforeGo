package com.example.PickBeforeGo.helper;


import java.util.ArrayList;

public class UserHelperClass {

    String username;
    String email;
    String password;
    /*
        ArrayList<ProductHelperClass> favourites;
    */
    boolean is_admin;

    //empty constructor
    UserHelperClass(){
    }

    //constructor
    public UserHelperClass(String username, String email, String password, boolean is_admin) {
        this.username = username;
        this.email = email;
        this.password = password;
/*
        this.favourites = new ArrayList<ProductHelperClass>();
*/
        this.is_admin = is_admin;
    }

    public Boolean getisadmin(){ return is_admin; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*public ArrayList<ProductHelperClass> getFavourites() {
        return favourites;
    }

    public void setFavourites(ArrayList<ProductHelperClass> favourites) {
        this.favourites = favourites;
    }*/


    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }
}
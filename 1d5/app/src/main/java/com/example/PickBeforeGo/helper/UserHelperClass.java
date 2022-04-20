package com.example.PickBeforeGo.helper;

public class UserHelperClass {
    String username;
    String email;
    String password;
    boolean isAdmin;

    //constructors
    public UserHelperClass() {
    }
    public UserHelperClass(String username, String email, String password, boolean isAdmin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

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

    public boolean getIsAdmin() { return isAdmin; }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}


    /*
        ArrayList<ProductHelperClass> favourites;


    /*public ArrayList<ProductHelperClass> getFavourites() {
        return favourites;
    }

    public void setFavourites(ArrayList<ProductHelperClass> favourites) {
        this.favourites = favourites;
    }
    */
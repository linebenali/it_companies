package com.example.itcompanies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class Company {
    private int id;
    private String name;
    private byte[] image; // Image sous forme de blob
    private ArrayList<String> services;
    private String phone;
    private String website;
    private String mail;
    private String location;

    // Constructeur avec tous les champs
    public Company(int id, String name, byte[] image, ArrayList<String> services, String phone, String website, String mail, String location) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.services = services;
        this.phone = phone;
        this.website = website;
        this.mail = mail;
        this.location = location;
    }

    // Constructeur sans ID
    public Company(String name, byte[] image, ArrayList<String> services, String phone, String website, String mail, String location) {
        this.name = name;
        this.image = image;
        this.services = services;
        this.phone = phone;
        this.website = website;
        this.mail = mail;
        this.location = location;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public ArrayList<String> getServices() {
        return services;
    }

    public void setServices(ArrayList<String> services) {
        this.services = services;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}

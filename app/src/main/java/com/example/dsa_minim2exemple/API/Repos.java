package com.example.dsa_minim2exemple.API;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Repos implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("language")
    @Expose
    private String language;

    public Repos(String name, String language) {
        this.name = name;
        this.language = language;
    }

    public Repos() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

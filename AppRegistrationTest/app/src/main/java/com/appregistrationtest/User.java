package com.appregistrationtest;

import java.io.Serializable;

public class User implements Serializable{

    public static final int STUDENT = 0;
    public static final int TEACHER = 1;
    public static final String USER = "USER";

    public int identity;
    public String phoneNumber;
    public String name;
    public String schoolName;
    public String schoolClass;

    public User() {

    }

    public User(int identity){
        this.identity = identity;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPersonalInfo (String name, String schoolName, String schoolClass){
        this.name = name;
        this.schoolName = schoolName;
        this.schoolClass = schoolClass;
    }
}

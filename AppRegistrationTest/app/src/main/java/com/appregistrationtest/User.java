package com.appregistrationtest;

import java.io.Serializable;

/**
 * A user class to store their information
 * and pack the information for uploading to firebase storage
 */
public class User implements Serializable{

    public static final int STUDENT = 0;
    public static final int TEACHER = 1;
    public static final String USER = "USER";

    //Store the identity (STUDENT of TEACHER)
    public int identity;
    public String phoneNumber;
    public String name;
    public String schoolName;
    public String schoolClass;
    public String[] electives;

    /**
     *  Create a new user with empty constructor
     */
    public User() {

    }

    /**
     * Create a new user will his/her identity
     * @param identity  the identity of the user
     */
    public User(int identity){
        this.identity = identity;
    }

    /**
     * Set the phone number of the user
     * @param phoneNumber the phone number of the user
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *  Set the information gathered from reg_3
     * @param name the real name of the user
     * @param schoolName the school name of the user entered
     * @param schoolClass the class of the user in school
     */
    public void setPersonalInfo (String name, String schoolName, String schoolClass){
        this.name = name;
        this.schoolName = schoolName;
        this.schoolClass = schoolClass;
    }

    /**
     * Set the electives of the user chosen
     * @param electives the electives packed in a String[]
     */
    public void setElectives (String[] electives){
        this.electives = electives;
    }
}

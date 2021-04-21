package com.abdulrehman.i170357_i170015;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    //String firstName, String lastName, String date,String gender, String bio

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setLoginStatus(String status ){ //1 for Register , 2 for Login
        prefs.edit().putString("status", status).commit();
    }

    public String getLoginStatus() {
        String status = prefs.getString("status","");
        return status;
    }


    public void setId(String id) {
        prefs.edit().putString("id", id).commit();
    }

    public String getId() {
        String id = prefs.getString("id","");
        return id;
    }

    public void setDate(String date) {
        prefs.edit().putString("date", date).commit();
    }

    public String getDate() {
        String date = prefs.getString("date","");
        return date;
    }

    public void setGender(String gender) {
        prefs.edit().putString("gender", gender).commit();
    }

    public String getGender() {
        String gender = prefs.getString("gender","");
        return gender;
    }

    public void setBio(String bio) {
        prefs.edit().putString("bio", bio).commit();
    }

    public String getBio() {
        String bio = prefs.getString("bio","");
        return bio;
    }

    public void setFirstName(String firstName) {
        prefs.edit().putString("firstName", firstName).commit();
    }

    public String getFirstName() {
        String firstname = prefs.getString("firstName","");
        return firstname;
    }

    public void setLastName(String lastName) {
        prefs.edit().putString("lastName", lastName).commit();
    }

    public String getLastName() {
        String lastName = prefs.getString("lastName","");
        return lastName;
    }
}

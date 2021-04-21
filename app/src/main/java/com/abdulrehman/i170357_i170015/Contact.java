package com.abdulrehman.i170357_i170015;

public class Contact {

    private String contactID,firstName, lastName, date , bio, gender;
    private String image;

    public Contact(String firstName, String lastName, String date,String gender, String bio) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.gender = gender;
        this.bio = bio;
        this.image = null;
    }

    public Contact(String contactID, String firstName, String lastName, String date, String bio, String gender) {
        this.contactID = contactID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.bio = bio;
        this.gender = gender;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public String getBio() { return bio; }

    public void setBio(String bio) { this.bio = bio; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }
}

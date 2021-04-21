package com.abdulrehman.i170357_i170015;

public class User {
    private String id,email,password;

    public User(String id,String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
    public User() {
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String password) {
        this.password = password;
    }
}

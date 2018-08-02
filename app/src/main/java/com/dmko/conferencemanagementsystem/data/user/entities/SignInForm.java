package com.dmko.conferencemanagementsystem.data.user.entities;


public class SignInForm {

    private String username;
    private String password;

    public SignInForm(String email, String password) {
        this.username = email;
        this.password = password;
    }

    public String getEmail() {
        return username;
    }

    public void setEmail(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignInForm{" +
                "email='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

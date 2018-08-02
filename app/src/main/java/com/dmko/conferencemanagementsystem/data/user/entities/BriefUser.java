package com.dmko.conferencemanagementsystem.data.user.entities;

public class BriefUser {

    private long id;
    private String email;
    private String firstname;
    private String lastname;
    private String username;
    private boolean isAdmin = true;

    public BriefUser() {
    }

    public BriefUser(long id, String email, String firstName, String lastName, String username, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.firstname = firstName;
        this.lastname = lastName;
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "BriefUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}

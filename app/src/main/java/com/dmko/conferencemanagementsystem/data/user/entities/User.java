package com.dmko.conferencemanagementsystem.data.user.entities;


public class User extends BriefUser {

    private boolean enabled;

    public User() {
    }

    public User(long id, String email, String firstName, String lastName, String username, boolean enabled, boolean isAdmin) {
        super(id, email, firstName, lastName, username, isAdmin);
        this.enabled = enabled;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

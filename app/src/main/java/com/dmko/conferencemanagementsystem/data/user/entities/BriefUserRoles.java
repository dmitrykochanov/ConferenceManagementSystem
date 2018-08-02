package com.dmko.conferencemanagementsystem.data.user.entities;

import java.util.List;

public class BriefUserRoles extends BriefUser {

    public interface Roles {
        long SUBMITTER = 0;
        long REVIEWER = 1;
        long CREATOR = 2;
        long ADMIN = 3;
    }

    private List<Long> roles;

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return super.toString() +
                "roles=" + roles +
                '}';
    }
}

package com.dmko.conferencemanagementsystem.data.user.local;


import com.dmko.conferencemanagementsystem.data.user.entities.User;
import com.dmko.conferencemanagementsystem.data.user.entities.UserWithToken;

public interface LocalUserSource {

    void saveUserWithToken(UserWithToken userWithToken);

    User getUser();

    String getToken();

    void deleteUserWithToken();
}

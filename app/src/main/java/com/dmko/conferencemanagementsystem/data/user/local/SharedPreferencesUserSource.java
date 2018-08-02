package com.dmko.conferencemanagementsystem.data.user.local;


import android.content.SharedPreferences;

import com.dmko.conferencemanagementsystem.data.user.entities.User;
import com.dmko.conferencemanagementsystem.data.user.entities.UserWithToken;

public class SharedPreferencesUserSource implements LocalUserSource {

    private static final String KEY_USER_TOKEN = "token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_FIRST_NAME = "user_first_name";
    private static final String KEY_USER_LAST_NAME = "user_last_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_ENABLED = "user_enabled";
    private static final String KEY_USER_IS_ADMIN = "user_is_admin";

    private SharedPreferences prefs;

    public SharedPreferencesUserSource(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public void saveUserWithToken(UserWithToken userWithToken) {
        User user = userWithToken.getUser();
        prefs.edit()
                .putString(KEY_USER_TOKEN, userWithToken.getToken())
                .putLong(KEY_USER_ID, user.getId())
                .putString(KEY_USER_EMAIL, user.getEmail())
                .putString(KEY_USER_FIRST_NAME, user.getFirstName())
                .putString(KEY_USER_LAST_NAME, user.getLastName())
                .putString(KEY_USERNAME, user.getUsername())
                .putBoolean(KEY_USER_ENABLED, user.isEnabled())
                .putBoolean(KEY_USER_IS_ADMIN, user.isAdmin())
                .apply();
    }

    @Override
    public User getUser() {
        long id = prefs.getLong(KEY_USER_ID, -1);
        if (id == -1) {
            return null;
        }

        String email = prefs.getString(KEY_USER_EMAIL, null);
        String firstName = prefs.getString(KEY_USER_FIRST_NAME, null);
        String lastName = prefs.getString(KEY_USER_LAST_NAME, null);
        String username = prefs.getString(KEY_USERNAME, null);
        boolean isEnabled = prefs.getBoolean(KEY_USER_ENABLED, true);
        boolean isAdmin = prefs.getBoolean(KEY_USER_IS_ADMIN, false);

        return new User(id, email, firstName, lastName, username, isEnabled, isAdmin);
    }

    @Override
    public String getToken() {
        return prefs.getString(KEY_USER_TOKEN, null);
    }

    @Override
    public void deleteUserWithToken() {
        prefs.edit()
                .remove(KEY_USER_TOKEN)
                .remove(KEY_USER_ID)
                .remove(KEY_USER_TOKEN)
                .remove(KEY_USER_EMAIL)
                .remove(KEY_USER_FIRST_NAME)
                .remove(KEY_USER_LAST_NAME)
                .remove(KEY_USERNAME)
                .remove(KEY_USER_ENABLED)
                .apply();
    }
}

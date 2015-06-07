package com.pinch_in.pinch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by kdeal on 6/6/15.
 */
public class Login {

    private static final String LOGIN_TOKIN_KEY = "login_tokin";
    private static final int MIN_PASSWORD_LEN = 8;

    public static boolean loggedIn(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Utils.PREFS_FILE, Context.MODE_PRIVATE);
        return pref.contains(LOGIN_TOKIN_KEY);
    }

    public static void logout(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Utils.PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.remove(LOGIN_TOKIN_KEY);
        edit.commit();
    }

    public static boolean login(String email, String password, Context context) {
        if (!Utils.validEmail(email)) {
            Utils.shortToast(R.string.error_email_invalid, context);
            return false;
        }
        if (password.length() < MIN_PASSWORD_LEN) {
            Utils.shortToast(R.string.error_password_length, context);
            return false;
        }

        SharedPreferences pref = context.getSharedPreferences(Utils.PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(LOGIN_TOKIN_KEY, "sotuhsoehtu");
        edit.commit();
        return true;
    }

    public static void launchLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}

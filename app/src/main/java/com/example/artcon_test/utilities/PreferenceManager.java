package com.example.artcon_test.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PreferenceManager {
    private final SharedPreferences sharedPreferences;
    public PreferenceManager(Context context){
        sharedPreferences = context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE);

    }

    public void putBoolean(String key, Boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public Boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key,false);
    }

    // Example methods in PreferenceManager
    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void clear(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

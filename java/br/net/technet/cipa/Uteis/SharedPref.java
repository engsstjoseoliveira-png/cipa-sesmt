package br.net.technet.cipa.Uteis;

import android.content.SharedPreferences;

import android.content.Context;

public class SharedPref {

    private SharedPreferences sharedPreferences;

    public SharedPref(Context context) {

        sharedPreferences = context.getSharedPreferences("filename", Context.MODE_PRIVATE);
    }

    //Save theme preference
    public void setNightModeState(Boolean state) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("NightMode", state);
        editor.apply();
    }

    //Get theme preference
    public Boolean loadNightModeState() {

        return sharedPreferences.getBoolean("NightMode", false);
    }
}

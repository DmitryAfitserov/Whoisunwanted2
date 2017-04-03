package com.example.dmitry.whoisunwanted;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dmitry on 02.04.2017.
 */

public class MySharedPreferences {

    Context context;
    private final String KEY_DAY = "fdfdf";
    private final String KEY_MONTH = "fgbgvn";
    private final String KEY_LIVES = "key_lives";

    public MySharedPreferences(Context context){
        this.context = context;
    }

    public void putInt(int value, String key){
        SharedPreferences sp = context.getSharedPreferences("prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putIntForLives(int value){
        SharedPreferences sp = context.getSharedPreferences("prefs", Activity.MODE_PRIVATE);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(KEY_LIVES, value);
        editor.putInt(KEY_DAY, day);
        editor.putInt(KEY_MONTH, month);
        editor.commit();
    }

    public void putFloat(float value, String key){
        SharedPreferences sp = context.getSharedPreferences("prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public int getInt(String key){
        SharedPreferences sp = context.getSharedPreferences("prefs", Activity.MODE_PRIVATE);
        int value = sp.getInt(key, 0);
        return value;
    }

    public int getIntForLives(){
        SharedPreferences sp = context.getSharedPreferences("prefs", Activity.MODE_PRIVATE);
        int value = sp.getInt(KEY_LIVES, 0);
        int day_last = sp.getInt(KEY_DAY, 0);
        int month_last = sp.getInt(KEY_MONTH, 1);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);

        if(day_last != day && month_last != month){
            value += 10;
            Log.d("EEE", "dfd " + day_last + " " + month_last  + " " + day + " " + month);
        }
        return value;
    }

    public float getFloat(String key){
        SharedPreferences sp = context.getSharedPreferences("prefs", Activity.MODE_PRIVATE);
        float value = sp.getFloat(key, 0f);
        return value;
    }




}

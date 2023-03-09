package org.rmj.appdriver.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppConfig {
    private static final String TAG = AppConfig.class.getSimpleName();

    private final SharedPreferences pref;

    private final SharedPreferences.Editor editor;

    private static final String CONFIG_NAME = "Sms_Receiver_Config";
    private static final String PRODUCT_ID = "sProdctID";
    private static final String SERVER_ADDRESS = "sDbAddrss";

    private static AppConfig appConfig;

    private AppConfig(Context context){
        int priv_Mode = 0;
        pref = context.getSharedPreferences(CONFIG_NAME, priv_Mode);
        editor = pref.edit();
    }

    public static AppConfig getInstance(Context context){
        if(appConfig == null){
            appConfig = new AppConfig(context);
        }
        return appConfig;
    }

    public void setServerAddress(String fsVal){
        editor.putString(SERVER_ADDRESS, fsVal);
        editor.commit();
    }

    public String getServerAddress(){
        return pref.getString(SERVER_ADDRESS, "");
    }
}

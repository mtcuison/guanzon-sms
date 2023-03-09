package org.rmj.appdriver.Api;

import android.content.Context;

import org.rmj.appdriver.Preferences.AppConfig;

public class ApiAddress {

    private final AppConfig poConfig;

    public static final String SMS_UPLOAD = "telemarketing/SMSClassify.php";

    private static ApiAddress apiAddress;

    private ApiAddress(Context context) {
        this.poConfig = AppConfig.getInstance(context);
    }

    public static ApiAddress getInstance(Context context){
        if(apiAddress == null){
            apiAddress = new ApiAddress(context);
        }
        return apiAddress;
    }

    public String getSMSUploadAPI(){
        String lsAddress = poConfig.getServerAddress();
        return lsAddress + SMS_UPLOAD;
    }
}

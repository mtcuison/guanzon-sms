package org.guanzongroup.smsAppDriver;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Constants {

    public static String APPROVAL_CODE_EMPTY(String fsMessage) throws Exception{
        JSONObject loJson = new JSONObject();
        JSONObject loError = new JSONObject();
        loJson.put("result", "error");
        loError.put("code", "404");
        loError.put("message", fsMessage);
        loJson.put("error", loError);
        return loJson.toString();
    }

    public static String APPROVAL_CODE_GENERATED(String fsMessage) throws Exception{
        JSONObject loJson = new JSONObject();
        loJson.put("result", "success");
        loJson.put("code", fsMessage);
        return loJson.toString();
    }

    public String DATE_MODIFIED = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime());
}

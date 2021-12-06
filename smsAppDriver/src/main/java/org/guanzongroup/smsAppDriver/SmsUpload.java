package org.guanzongroup.smsAppDriver;

import android.content.Context;

import org.guanzongroup.smsAppDriver.Database.ESmsIncoming;
import org.guanzongroup.smsAppDriver.Http.WebClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class SmsUpload {
    private static final String TAG = SmsUpload.class.getSimpleName();

    private final Context mContext;
    private String psRmServer = "https://restgk.guanzongroup.com.ph/security/request_android_object.php";

    public interface OnUploadCallback{
        void OnUpload(String args);
        void OnFailed(String message);
    }

    public static final String LIVE_SERVER = "https://restgk.guanzongroup.com.ph/security/request_android_object.php";
    public static final String LOCAL_SERVER = "http://192.168.10.140/security/request_android_object.php";

    public SmsUpload(Context context) {
        this.mContext = context;
    }

    public void setRemoteServer(String address){
        psRmServer = address;
    }

    public void UploadSmsIncoming(List<ESmsIncoming> smsIncomings, OnUploadCallback callback){
        try{
            if(smsIncomings == null){
                callback.OnFailed("No sms incoming to be upload.");
            } else if(smsIncomings.size() == 0){
                callback.OnFailed("No sms incoming to be upload.");
            } else {
                JSONObject params = new JSONObject();
                JSONArray smsDetail = new JSONArray();
                for (int x = 0; x < smsIncomings.size(); x++) {
                    JSONObject joSms = new JSONObject();
                    ESmsIncoming loSms = smsIncomings.get(x);
                    joSms.put("dTransact", loSms.getTransact());
                    joSms.put("sSourceCd", loSms.getSourceCd());
                    joSms.put("sMessagex", loSms.getMessagex());
                    joSms.put("sMobileNo", loSms.getMobileNo());
                    joSms.put("cSubscrbr", loSms.getSubscrbr());
                    joSms.put("dFollowUp", loSms.getFollowUp());
                    smsDetail.put(joSms);
                }
                params.put("incoming", smsDetail);
//            String lsResponse = WebClient.sendRequest(psRmServer, params.toString(), null);
//            String lsResponse = Constants.APPROVAL_CODE_EMPTY("sample");
                String lsResponse = Constants.APPROVAL_CODE_GENERATED("sample");

                if (lsResponse == null) {
                    callback.OnFailed("Server unresponsive");
                } else {
                    JSONObject joResponse = new JSONObject(lsResponse);
                    String result = joResponse.getString("result");
                    if (result.equalsIgnoreCase("success")) {
                        callback.OnUpload("Sms incoming uploaded successfully.");
                    } else {
                        JSONObject joError = joResponse.getJSONObject("error");
                        String message = joError.getString("message");
                        callback.OnFailed(message);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }
}

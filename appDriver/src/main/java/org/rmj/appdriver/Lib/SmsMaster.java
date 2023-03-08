package org.rmj.appdriver.Lib;

import static org.rmj.appdriver.Etc.Constants.getSubs;
import static org.rmj.appdriver.Etc.Constants.parseMobileNo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.Database.DataAccessObject.DSmsIncoming;
import org.rmj.appdriver.Database.Entity.ESmsIncoming;
import org.rmj.appdriver.Database.GGC_SysDB;
import org.rmj.appdriver.Etc.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SmsMaster {
    private static final String TAG = SmsMaster.class.getSimpleName();

    private final DSmsIncoming poDao;

    private String message;

    private static final String PDU_TYPE = "pdus";
    private static final String DEFAULT_MESSAGE = "Good day! This is Guanzon Group of Companies. How may we help you?";

    public String getMessage(){
        return message;
    }

    public SmsMaster(Context instance) {
        this.poDao = GGC_SysDB.getInstance(instance).smsIncoming();
    }

    public boolean SaveSmsIncoming(Intent intent){
        try{
            SmsManager poSmsMngr = SmsManager.getDefault();

            // Get the SMS message.
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs;
            String strMessage = "";
            String format = bundle.getString("format");

            // Retrieve the SMS message received.
            Object[] pdus = (Object[]) bundle.get(PDU_TYPE);

            if (pdus != null) {

                // Check the Android version.

                // Fill the msgs array.
                msgs = new SmsMessage[pdus.length];

                for (int i = 0; i < msgs.length; i++) {
                    // Check Android version and use appropriate createFromPdu.
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                }

                StringBuilder content = new StringBuilder();
                if (msgs.length > 0) {
                    for (int x = 0; x < msgs.length; x++) {
                        content.append(msgs[x].getMessageBody());
                    }
                }
                String mySmsText = content.toString();

                // Auto reply message to sender
                String lsSender = msgs[0].getOriginatingAddress();
                poSmsMngr.sendTextMessage(lsSender, null, DEFAULT_MESSAGE, null, null);

                // Insert received SMS to database
                ESmsIncoming loSmsinfo = new ESmsIncoming();
                String lsMobileN = parseMobileNo(lsSender);
                loSmsinfo.setSourceCd("HL");
                loSmsinfo.setMobileNo(lsMobileN);
                loSmsinfo.setSubscrbr(getSubs(lsMobileN));
                loSmsinfo.setMessagex(mySmsText);
                loSmsinfo.setSendStat("0");
                loSmsinfo.setSendDate(new Constants().DATE_MODIFIED);
                poDao.SaveSmsInfo(loSmsinfo);
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean UploadSms(int id){
        try{
            JSONObject params = new JSONObject();
            JSONArray smsDetail = new JSONArray();

            ESmsIncoming loDetail = poDao.getSMSIncomingInfo(id);

            if(loDetail == null){
                message = "Unable to find record.";
                return false;
            }

            JSONObject joSms = new JSONObject();
            joSms.put("dTransact", loDetail.getTransact());
            joSms.put("sSourceCd", loDetail.getSourceCd());
            joSms.put("sMessagex", loDetail.getMessagex());
            joSms.put("sMobileNo", loDetail.getMobileNo());
            joSms.put("cSubscrbr", loDetail.getSubscrbr());
            joSms.put("dFollowUp", loDetail.getFollowUp());
            smsDetail.put(joSms);

            params.put("incoming", smsDetail);
//            String lsResponse = WebClient.sendRequest(serverAddress, params.toString(), null);
//            String lsResponse = Constants.APPROVAL_CODE_EMPTY("sample");
            String lsResponse = Constants.APPROVAL_CODE_GENERATED("sample");

            if (lsResponse == null) {
                message = "Server unresponsive";
                return false;
            }

            JSONObject joResponse = new JSONObject(lsResponse);
            String result = joResponse.getString("result");

            if (result.equalsIgnoreCase("error")) {
                JSONObject joError = joResponse.getJSONObject("error");
                String lsErrorCd = joError.getString("code");
                if(lsErrorCd.equalsIgnoreCase("x")){
                    loDetail.setSendStat("3");
                    loDetail.setSendDate(new Constants().DATE_MODIFIED);
                    poDao.UpdateSms(loDetail);
                    Log.d(TAG, "Sms incoming from " + loDetail.getMobileNo() + " has been tagged as spam.");
                }
                message = joError.getString("message");
                return false;
            }

            loDetail.setSendStat("1");
            loDetail.setSendDate(new Constants().DATE_MODIFIED);
            poDao.UpdateSms(loDetail);
            Log.d(TAG, "Sms incoming from " + loDetail.getMobileNo() + " has been uploaded successfully.");
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean UploadSmsData(){
        try{
            List<ESmsIncoming> loDetails = poDao.getSmsIncomingForUpload();

            if(loDetails == null){
                message = "No sms incoming to upload.";
                return false;
            }

            if(loDetails.size() == 0){
                message = "No sms incoming to upload.";
                return false;
            }

            for (int x = 0; x < loDetails.size(); x++) {
                JSONObject joSms = new JSONObject();
                ESmsIncoming loSms = loDetails.get(x);
                joSms.put("dTransact", loSms.getTransact());
                joSms.put("sSourceCd", loSms.getSourceCd());
                joSms.put("sMessagex", loSms.getMessagex());
                joSms.put("sMobileNo", loSms.getMobileNo());
                joSms.put("cSubscrbr", loSms.getSubscrbr());
                joSms.put("dFollowUp", loSms.getFollowUp());


                String lsResponse = Constants.APPROVAL_CODE_GENERATED("sample");

                if (lsResponse == null) {
                    message = "Server unresponsive";
                    return false;
                }

                JSONObject joResponse = new JSONObject(lsResponse);
                String result = joResponse.getString("result");

                if (result.equalsIgnoreCase("error")) {
                    JSONObject joError = joResponse.getJSONObject("error");
                    String lsErrorCd = joError.getString("code");
                    if(lsErrorCd.equalsIgnoreCase("x")){
                        loSms.setSendStat("3");
                        loSms.setSendDate(new Constants().DATE_MODIFIED);
                        poDao.UpdateSms(loSms);
                        Log.d(TAG, "Sms incoming from " + loSms.getMobileNo() + " has been tagged as spam.");
                    }
                    message = joError.getString("message");
                    return false;
                }

                loSms.setSendStat("1");
                loSms.setSendDate(new Constants().DATE_MODIFIED);
                poDao.UpdateSms(loSms);
                Log.d(TAG, "Sms incoming from " + loSms.getMobileNo() + " has been uploaded successfully.");

                Thread.sleep(1000);
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}

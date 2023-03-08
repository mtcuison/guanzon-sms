package org.guanzonsms.receiver.BroadcastReceiver;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import org.guanzongroup.smsAppDriver.Constants;
import org.guanzongroup.smsAppDriver.Database.DSmsIncoming;
import org.guanzongroup.smsAppDriver.Database.ESmsIncoming;
import org.guanzongroup.smsAppDriver.Database.GGC_SysDB;
import org.guanzonsms.receiver.Callback.UpdateSmsServerCallback;
import org.guanzonsms.receiver.Object.AppConstants;
import org.guanzonsms.receiver.Object.SmsUploadManager;

import java.util.List;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();
    public static final String PDU_TYPE = "pdus";
    public static final String DEFAULT_MESSAGE = "Good day! This is Guanzon Group of Companies. How may we help you?";
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
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
            boolean isVersionM = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);

            // Fill the msgs array.
            msgs = new SmsMessage[pdus.length];

            for (int i = 0; i < msgs.length; i++) {
                // Check Android version and use appropriate createFromPdu.
                if (isVersionM) {
                    // If Android version M or newer:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    // If Android version L or older:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
            }

            StringBuffer content = new StringBuffer();
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
            String lsMobileN = org.guanzongroup.smsAppDriver.SmsManager.parseMobileNo(lsSender);
            loSmsinfo.setSourceCd("HL");
            loSmsinfo.setMobileNo(lsMobileN);
            loSmsinfo.setSubscrbr(org.guanzongroup.smsAppDriver.SmsManager.getSubs(lsMobileN));
            loSmsinfo.setMessagex(mySmsText);
            loSmsinfo.setSendStat("0");
            loSmsinfo.setSendDate(new Constants().DATE_MODIFIED);
            insertSms(context, loSmsinfo);
        }

    }

    private void insertSms(Context context, ESmsIncoming foSmsinfo) {
        new InsertSmsAsync(context).execute(foSmsinfo);
    }

    private static class InsertSmsAsync extends AsyncTask<ESmsIncoming, Void, String> {
        private static final String TAG = InsertSmsAsync.class.getSimpleName();
        private final org.guanzongroup.smsAppDriver.SmsManager poSmsMngr;
        private final SmsUploadManager poUploadx;

        InsertSmsAsync(Context context) {
            this.poSmsMngr = new org.guanzongroup.smsAppDriver.SmsManager(context);
            this.poUploadx = new SmsUploadManager(context);
        }

        @Override
        protected String doInBackground(ESmsIncoming... eSmsInfos) {
            String lsResult = "";
            try {

                poSmsMngr.saveSmsIncoming(eSmsInfos[0]);
                lsResult = "Received SMS saved to local database.";

                List<ESmsIncoming> loSmsList = poSmsMngr.getSmsIncomingForUpload();
                poUploadx.uploadSms(loSmsList, new UpdateSmsServerCallback() {
                    @Override
                    public void OnUpdateSuccess(String message) {
                        Log.e(TAG, "Incoming SMS Uploaded -> " + message);
                    }

                    @Override
                    public void OnUpdateFailed(String message) {
                        Log.e(TAG, "Incoming SMS Upload Failed -> " + message);
                    }
                });

                return lsResult;

            } catch(Exception e) {
                lsResult = e.getMessage();
                return lsResult;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e(TAG, s);
        }
    }

}
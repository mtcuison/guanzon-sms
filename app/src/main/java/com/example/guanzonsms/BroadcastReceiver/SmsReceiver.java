package com.example.guanzonsms.BroadcastReceiver;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.guanzonsms.DAO.DSmsInfo;
import com.example.guanzonsms.Entity.ESmsInfo;
import com.example.guanzonsms.RoomDatabase.GSMS_DB;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();
    public static final String PDU_TYPE = "pdus";

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        GSMS_DB loDatabse = GSMS_DB.getInstance(context);
        DSmsInfo loDao = loDatabse.smsDao();

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

                // Insert received SMS to database
                ESmsInfo loSmsinfo = new ESmsInfo();
                loSmsinfo.setReceiverNumber("Me");
                loSmsinfo.setSenderNumber(msgs[i].getOriginatingAddress());
                loSmsinfo.setTextMessage(msgs[i].getMessageBody());
                loSmsinfo.setTimeStamp(String.valueOf(msgs[i].getTimestampMillis()));
                insertSms(loDao, loSmsinfo);

                // Build the message to show.
                strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                strMessage += " :" + msgs[i].getMessageBody() + "\n";
                // Log and display the SMS message.
                Log.e(TAG, "onReceive: " + strMessage);
                Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();
            }

        }

    }

    private void insertSms(DSmsInfo foDao, ESmsInfo foSmsinfo) {
        new InsertSmsAsync(foDao).execute(foSmsinfo);
    }

    private static class InsertSmsAsync extends AsyncTask<ESmsInfo, Void, String> {
        private static final String TAG = InsertSmsAsync.class.getSimpleName();
        private final DSmsInfo loDao;

        InsertSmsAsync(DSmsInfo foDao) {
            this.loDao = foDao;
        }

        @Override
        protected String doInBackground(ESmsInfo... eSmsInfos) {
            String lsResult = "";
            try {
                loDao.insert(eSmsInfos[0]);
                lsResult = "Insertion Success";
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
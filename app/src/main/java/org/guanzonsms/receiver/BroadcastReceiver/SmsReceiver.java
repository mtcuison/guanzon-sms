package org.guanzonsms.receiver.BroadcastReceiver;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import org.guanzongroup.smsAppDriver.Database.DSmsIncoming;
import org.guanzongroup.smsAppDriver.Database.ESmsIncoming;
import org.guanzongroup.smsAppDriver.Database.GGC_SysDB;
import org.guanzonsms.receiver.Object.Timestamp;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();
    public static final String PDU_TYPE = "pdus";
    public static final String DEFAULT_MESSAGE = "Good day! This is Guanzon Group of Companies. How may we help you?";
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        GGC_SysDB loDatabse = GGC_SysDB.getInstance(context);
        DSmsIncoming loDao = loDatabse.smsIncoming();
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

                // Auto reply message to sender
                poSmsMngr.sendTextMessage(msgs[i].getOriginatingAddress(), null, DEFAULT_MESSAGE, null, null);

                // Insert received SMS to database
                ESmsIncoming loSmsinfo = new ESmsIncoming();
                loSmsinfo.setMobileNo(msgs[i].getOriginatingAddress());
                loSmsinfo.setMessagex(msgs[i].getMessageBody());
                loSmsinfo.setSendDate(Timestamp.get());
                insertSms(loDao, loSmsinfo);

                // Build the message to show.
//                strMessage += "SMS from " + msgs[i].getOriginatingAddress();
//                strMessage += " :" + msgs[i].getMessageBody() + "\n";
//                // Log and display the SMS message.
//                Log.e(TAG, "onReceive: " + strMessage);
//                Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();
            }

        }

    }

    private void insertSms(DSmsIncoming foDao, ESmsIncoming foSmsinfo) {
        new InsertSmsAsync(foDao).execute(foSmsinfo);
    }

    private static class InsertSmsAsync extends AsyncTask<ESmsIncoming, Void, String> {
        private static final String TAG = InsertSmsAsync.class.getSimpleName();
        private final DSmsIncoming loDao;

        InsertSmsAsync(DSmsIncoming foDao) {
            this.loDao = foDao;
        }

        @Override
        protected String doInBackground(ESmsIncoming... eSmsInfos) {
            String lsResult = "";
            try {
                loDao.SaveSmsInfo(eSmsInfos[0]);
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
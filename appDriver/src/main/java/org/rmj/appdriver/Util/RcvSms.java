package org.rmj.appdriver.Util;

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

import org.guanzongroup.smsAppDriver.Constants;
import org.guanzongroup.smsAppDriver.Database.ESmsIncoming;
import org.guanzonsms.receiver.Callback.UpdateSmsServerCallback;
import org.guanzonsms.receiver.Object.SmsUploadManager;
import org.rmj.appdriver.Lib.SmsMaster;

import java.util.List;

public class RcvSms extends BroadcastReceiver {
    private static final String TAG = RcvSms.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMaster loSys = new SmsMaster(context);

        if(!loSys.SaveSmsIncoming(intent)){
            Log.e(TAG, loSys.getMessage());
        }

        
    }
}
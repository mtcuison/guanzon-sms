package org.rmj.appdriver.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.rmj.appdriver.Lib.SmsMaster;
import org.rmj.appdriver.Server.ConnectionUtil;

public class RcvSms extends BroadcastReceiver {
    private static final String TAG = RcvSms.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMaster loSys = new SmsMaster(context);
        ConnectionUtil loConn = new ConnectionUtil(context);

        if(!loSys.SaveSmsIncoming(intent)){
            Log.e(TAG, loSys.getMessage());
        }

        if(!loConn.hasActiveServer()){
            Log.e(TAG, loConn.getMessage());
            return;
        }

        if(!loSys.UploadSmsData()){
            Log.e(TAG, loSys.getMessage());
        }
    }
}
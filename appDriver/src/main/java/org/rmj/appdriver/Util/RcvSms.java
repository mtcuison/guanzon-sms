package org.rmj.appdriver.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.rmj.appdriver.Lib.SmsMaster;
import org.rmj.appdriver.Server.ConnectionUtil;

public class RcvSms extends BroadcastReceiver {
    private static final String TAG = RcvSms.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        new UploadSmsTask(context).execute(intent);
    }

    private class UploadSmsTask extends AsyncTask<Intent, Void, Boolean>{

        private final SmsMaster poSys;
        private final ConnectionUtil poConn;
        private String message;

        public UploadSmsTask(Context context) {
            this.poSys = new SmsMaster(context);
            this.poConn = new ConnectionUtil(context);
        }

        @Override
        protected Boolean doInBackground(Intent... intents) {
            Intent intent = intents[0];
            if(!poSys.SaveSmsIncoming(intent)){
                Log.e(TAG, poSys.getMessage());
                message = poSys.getMessage();
                return false;
            }

            if(!poConn.hasActiveServer()){
                Log.e(TAG, poConn.getMessage());
                message = poConn.getMessage();
                return false;
            }

            if(!poSys.UploadSmsData()){
                Log.e(TAG, poSys.getMessage());
                message = poSys.getMessage();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                Log.e(TAG, message);
            } else {
                Log.d(TAG, "Sms uploading has finished successfully");
            }
        }
    }
}
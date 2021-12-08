package org.guanzonsms.receiver.Object;

import android.content.Context;
import android.util.Log;

import org.guanzongroup.smsAppDriver.Database.ESmsIncoming;
import org.guanzongroup.smsAppDriver.Http.ConnectionCheck;
import org.guanzongroup.smsAppDriver.SmsManager;
import org.guanzongroup.smsAppDriver.SmsUpload;
import org.guanzonsms.receiver.Callback.UpdateSmsServerCallback;

import java.util.List;

public class SmsUploadManager {
    private static final String TAG = SmsUploadManager.class.getSimpleName();
    private final ConnectionCheck poConnect;
    private final SmsManager poSmsMngr;
    private final SmsUpload poUploadx;

    // Call this object inside AsyncTask
    public SmsUploadManager(Context context) {
        Log.e(TAG, "Initialized.");
        this.poConnect = new ConnectionCheck(context);
        this.poSmsMngr = new SmsManager(context);
        this.poUploadx = new SmsUpload(context);
    }

    public void uploadSms(List<ESmsIncoming> foSmsList, UpdateSmsServerCallback callback) {
        try {
            poConnect.checkActiverServer((isDeviceConnected, serverAddress) -> {
                if(isDeviceConnected) {
                    poUploadx.UploadSmsIncoming(foSmsList, serverAddress, new SmsUpload.OnUploadCallback() {
                        @Override
                        public void OnUpload(String args) {
                            if(updateLocalDatabase(foSmsList)) {
                                Log.e(TAG, "Local database updated.");
                            }
                            callback.OnUpdateSuccess(args);
                        }

                        @Override
                        public void OnFailed(String message) {
                            callback.OnUpdateFailed(message);
                        }
                    });

                } else {
                    callback.OnUpdateFailed("No Data Connection: Server update failed.");
                }
            });
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private boolean updateLocalDatabase(List<ESmsIncoming> foSmsInfos) {
        if(foSmsInfos.size() < 1) {
            return false;
        } else {
            for(int x = 0; x < foSmsInfos.size(); x++) {
                poSmsMngr.updateUploadedSms(foSmsInfos.get(x).getTransnox());
            }
            return true;
        }
    }

}

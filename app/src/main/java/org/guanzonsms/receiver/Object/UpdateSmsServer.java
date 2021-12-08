package org.guanzonsms.receiver.Object;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.guanzongroup.smsAppDriver.Database.ESmsIncoming;
import org.guanzongroup.smsAppDriver.Database.SmsRepository;
import org.guanzongroup.smsAppDriver.Http.ConnectionCheck;
import org.guanzongroup.smsAppDriver.SmsManager;
import org.guanzongroup.smsAppDriver.SmsUpload;
import org.guanzonsms.receiver.Callback.UpdateInstance;
import org.guanzonsms.receiver.Callback.UpdateSmsServerCallback;
import java.util.Arrays;
import java.util.List;

public class UpdateSmsServer implements UpdateInstance {
    private static final String TAG = UpdateSmsServer.class.getSimpleName();
    private final Application poApp;
    private final SmsRepository poSmsInfo;
    private final ConnectionCheck poConn;

    public UpdateSmsServer(Application application) {
        this.poApp = application;
        this.poSmsInfo = new SmsRepository(application);
        this.poConn = new ConnectionCheck(application);
    }

    @Override
    public void onUpdateServer(UpdateSmsServerCallback callback) {
        try{
            new UpdateSmsServerTask(poApp, poSmsInfo, poConn, callback).execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class UpdateSmsServerTask extends AsyncTask<Void, Void, Void> {
        private final UpdateSmsServerCallback callback;
        private final SmsManager poSmsMngr;
        private final SmsRepository poSmsInfo;
        private final SmsUpload poUpload;
        private final ConnectionCheck loConn;

        public UpdateSmsServerTask(Application foApp, SmsRepository foSmsInfo, ConnectionCheck loConn, UpdateSmsServerCallback callback) {
            this.callback = callback;
            this.poSmsMngr = new SmsManager(foApp);
            this.poSmsInfo = foSmsInfo;
            this.poUpload = new SmsUpload(foApp);
            this.loConn = loConn;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                List<ESmsIncoming> loSmsInfos = poSmsInfo.getSmsIncomingForUpload();

                loConn.checkActiverServer((isDeviceConnected, serverAddress) -> {
                    if(isDeviceConnected) {

                        poUpload.UploadSmsIncoming(loSmsInfos, serverAddress, new SmsUpload.OnUploadCallback() {
                            @Override
                            public void OnUpload(String args) {
                                if(updateLocalDatabase(loSmsInfos)) {
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

            } catch (Exception e) {
                Log.e(TAG, Arrays.toString(e.getStackTrace()));
                e.printStackTrace();
            }

            return null;
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

}

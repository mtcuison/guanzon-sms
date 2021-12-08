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

    public UpdateSmsServer(Application application) {
        this.poApp = application;
    }

    @Override
    public void onUpdateServer(UpdateSmsServerCallback callback) {
        try{
            new UpdateSmsServerTask(poApp, callback).execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class UpdateSmsServerTask extends AsyncTask<Void, Void, Void> {
        private final UpdateSmsServerCallback callback;
        private final SmsUploadManager poUploadx;
        private final SmsRepository poSmsInfo;

        public UpdateSmsServerTask(Application foApp, UpdateSmsServerCallback callback) {
            this.callback = callback;
            this.poUploadx = new SmsUploadManager(foApp);
            this.poSmsInfo = new SmsRepository(foApp);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                List<ESmsIncoming> loSmsInfos = poSmsInfo.getSmsIncomingForUpload();
                poUploadx.uploadSms(loSmsInfos, callback);
            } catch (Exception e) {
                Log.e(TAG, Arrays.toString(e.getStackTrace()));
                e.printStackTrace();
            }

            return null;
        }

    }

}

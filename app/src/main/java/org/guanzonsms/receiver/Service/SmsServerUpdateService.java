package org.guanzonsms.receiver.Service;

import static org.guanzonsms.receiver.Object.SmsNotifBuilder.APP_SYNC_DATA;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.guanzonsms.receiver.Callback.UpdateInstance;
import org.guanzonsms.receiver.Callback.UpdateSmsServerCallback;
import org.guanzonsms.receiver.Object.SmsNotifBuilder;
import org.guanzonsms.receiver.Object.UpdateSmsServer;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SmsServerUpdateService extends JobService {
    private static final String TAG = SmsServerUpdateService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters params) {
        try{
            SmsNotifBuilder.createNotification(getApplication(), "SMS Receiver", "SMS Server updating..",APP_SYNC_DATA).show();
            doBackgroundTask(params);
        } catch (Exception e){
            e.printStackTrace();
            jobFinished(params, false);
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e(TAG, "Incoming SMS update to server has stopped.");
        SmsNotifBuilder.createNotification(getApplication(), "SMS Receiver", "SMS Server updating suddenly stopped.",APP_SYNC_DATA).show();
        return true;
    }

    private void doBackgroundTask(JobParameters params) {
        UpdateInstance poUpdate = new UpdateSmsServer(getApplication());
        new Thread(() -> {
                poUpdate.onUpdateServer(new UpdateSmsServerCallback() {
                    @Override
                    public void OnUpdateSuccess(String message) {
                        Log.e(TAG, poUpdate.getClass().getSimpleName() + " update success." + message);
                        SmsNotifBuilder.createNotification(getApplication(), "SMS Receiver", message,APP_SYNC_DATA).show();
                    }

                    @Override
                    public void OnUpdateFailed(String message) {
                        Log.e(TAG, poUpdate.getClass().getSimpleName() + " update failed. " + message);
                        SmsNotifBuilder.createNotification(getApplication(), "SMS Receiver", message, APP_SYNC_DATA).show();
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            jobFinished(params, false);
        }).start();
    }

}

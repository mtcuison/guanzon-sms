package org.guanzonsms.receiver.Service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.guanzonsms.receiver.Callback.UpdateInstance;
import org.guanzonsms.receiver.Callback.UpdateSmsServerCallback;
import org.guanzonsms.receiver.Object.UpdateSmsServer;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SmsServerUpdateService extends JobService {
    private static final String TAG = SmsServerUpdateService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters params) {
        try{
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
        return true;
    }

    private void doBackgroundTask(JobParameters params) {
        UpdateInstance poUpdate = new UpdateSmsServer(getApplication());
        new Thread(() -> {
                poUpdate.onUpdateServer(new UpdateSmsServerCallback() {
                    @Override
                    public void OnUpdateSuccess(String message) {
                        Log.e(TAG, poUpdate.getClass().getSimpleName() + " update success." + message);
                    }

                    @Override
                    public void OnUpdateFailed(String message) {
                        Log.e(TAG, poUpdate.getClass().getSimpleName() + " update failed. " + message);
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

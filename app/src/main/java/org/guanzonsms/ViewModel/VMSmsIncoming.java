package org.guanzonsms.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.appdriver.Database.Entity.ESmsIncoming;
import org.rmj.appdriver.Lib.SmsMaster;
import org.rmj.appdriver.Server.ConnectionUtil;

import java.util.List;

public class VMSmsIncoming extends AndroidViewModel {
    private static final String TAG = VMSmsIncoming.class.getSimpleName();
    private final SmsMaster poSys;
    private final ConnectionUtil poConn;

    public VMSmsIncoming(@NonNull Application application) {
        super(application);
        this.poSys = new SmsMaster(application);
        this.poConn = new ConnectionUtil(application);
    }

    public interface OnUploadSmsCallback{
        void OnUpload();
        void OnSuccess();
        void OnFailed(String message);
    }

    public LiveData<List<ESmsIncoming>> getSmsIncomingListForViewing() {
        return poSys.getSmsIncomingListForViewing();
    }


    public void UploadSms(OnUploadSmsCallback callback){
        new UploadTask(callback).execute();
    }


    private class UploadTask extends AsyncTask<Void, Void, Boolean>{

        private final OnUploadSmsCallback callback;

        private String message;

        public UploadTask(OnUploadSmsCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnUpload();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

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
                callback.OnFailed(message);
            } else {
                callback.OnSuccess();
            }
        }
    }
}

package org.guanzonsms.receiver.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.guanzonsms.receiver.DAO.DSmsInfo;
import org.guanzonsms.receiver.Entity.ESmsInfo;
import org.guanzonsms.receiver.RoomDatabase.GSMS_DB;

import java.util.List;

public class RSmsInfo {
    private DSmsInfo smsDao;
    private LiveData<List<ESmsInfo>> smsList;

    public RSmsInfo(Application application) {
        GSMS_DB database =  GSMS_DB.getInstance(application);
        smsDao = database.smsDao();
        smsList = smsDao.getSmsList();
    }

    public void insert(ESmsInfo foSmsInfo) {
        new InsertSmsAsync(smsDao).execute(foSmsInfo);
    }

    public void delete(ESmsInfo foSmsInfo) {
        new DeleteSmsAsync(smsDao).execute(foSmsInfo);
    }

    public void deleteAll() {
        new DeleteAllSmsAsync(smsDao).execute();
    }

    public LiveData<List<ESmsInfo>> getSmsList() {
        return smsList;
    }

    private static class InsertSmsAsync extends AsyncTask<ESmsInfo, Void, Void> {

        private final DSmsInfo smsDao;

        private InsertSmsAsync(DSmsInfo foSmsDao) {
            this.smsDao = foSmsDao;
        }

        @Override
        protected Void doInBackground(ESmsInfo... eSmsInfos) {
            smsDao.insert(eSmsInfos[0]);
            return null;
        }

    }

    private static class DeleteSmsAsync extends AsyncTask<ESmsInfo, Void, Void> {

        private final DSmsInfo smsDao;

        private DeleteSmsAsync(DSmsInfo foSmsDao) {
            this.smsDao = foSmsDao;
        }


        @Override
        protected Void doInBackground(ESmsInfo... eSmsInfos) {
            smsDao.delete(eSmsInfos[0]);
            return null;
        }
    }

    private static class DeleteAllSmsAsync extends AsyncTask<ESmsInfo, Void, Void> {

        private final DSmsInfo smsDao;

        private DeleteAllSmsAsync(DSmsInfo foSmsDao) {
            this.smsDao = foSmsDao;
        }

        @Override
        protected Void doInBackground(ESmsInfo... eSmsInfos) {
            smsDao.deleteAll();
            return null;
        }
    }



}

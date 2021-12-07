package org.guanzongroup.smsAppDriver.Database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SmsRepository implements DSmsIncoming{
    private static final String TAG = SmsRepository.class.getSimpleName();

    private final Context instance;
    private final DSmsIncoming poDao;

    public SmsRepository(Context instance) {
        this.instance = instance;
        this.poDao = GGC_SysDB.getInstance(instance).smsIncoming();
    }

    @Override
    public void SaveSmsInfo(ESmsIncoming smsIncoming) {
        poDao.SaveSmsInfo(smsIncoming);
    }

    @Override
    public void UpdateSmsServerUploaded(int TransNox) {
        poDao.UpdateSmsServerUploaded(TransNox);
    }

    @Override
    public List<ESmsIncoming> getSMSIncoming(int TransNox) {
        return poDao.getSMSIncoming(TransNox);
    }

    @Override
    public List<ESmsIncoming> getSmsIncomingList() {
        return poDao.getSmsIncomingList();
    }

    @Override
    public LiveData<List<ESmsIncoming>> getSmsIncomingListForViewing() {
        return poDao.getSmsIncomingListForViewing();
    }

    @Override
    public ESmsIncoming getSMSIncomingInfo(int TransNox) {
        return poDao.getSMSIncomingInfo(TransNox);
    }

    @Override
    public List<ESmsIncoming> getSmsIncomingForUpload() {
        return poDao.getSmsIncomingForUpload();
    }

    @Override
    public LiveData<String> getNumberOfUploadedSms() {
        return null;
    }
}

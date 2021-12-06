package org.guanzonsms.receiver.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.guanzonsms.receiver.Entity.ESmsInfo;
import org.guanzonsms.receiver.Repository.RSmsInfo;

import java.util.List;

public class VMSmsInfo extends AndroidViewModel {
    private static final String TAG = VMSmsInfo.class.getSimpleName();
    private final RSmsInfo poSms;

    public VMSmsInfo(@NonNull Application application) {
        super(application);
        this.poSms = new RSmsInfo(application);
    }

    public void insert(ESmsInfo foSmsInfo) {
        poSms.insert(foSmsInfo);
    }

    public void delete(ESmsInfo foSmsInfo) {
        poSms.delete(foSmsInfo);
    }

    public void deleteAll() {
        poSms.deleteAll();
    }

    public LiveData<List<ESmsInfo>> getSmsList() {
        return poSms.getSmsList();
    }

}

package com.example.guanzonsms.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.guanzonsms.Entity.ESmsInfo;
import com.example.guanzonsms.R;
import com.example.guanzonsms.Repository.RSmsInfo;

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

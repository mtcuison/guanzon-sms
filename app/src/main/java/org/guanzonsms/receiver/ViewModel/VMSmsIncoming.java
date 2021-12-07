package org.guanzonsms.receiver.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.guanzongroup.smsAppDriver.Database.ESmsIncoming;
import org.guanzongroup.smsAppDriver.Database.SmsRepository;

import java.util.ArrayList;
import java.util.List;

public class VMSmsIncoming extends AndroidViewModel {
    private static final String TAG = VMSmsIncoming.class.getSimpleName();
    private final SmsRepository poSmsRepo;

    private final MutableLiveData<List<ESmsIncoming>> poSmsList = new MutableLiveData<>();

    public VMSmsIncoming(@NonNull Application application) {
        super(application);
        this.poSmsRepo = new SmsRepository(application);
    }

    public void SaveSmsInfo(ESmsIncoming smsIncoming) {
        poSmsRepo.SaveSmsInfo(smsIncoming);
    }

    public List<ESmsIncoming> getSmsIncomingList() {
        return poSmsRepo.getSmsIncomingList();
    }

}

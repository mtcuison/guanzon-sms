package org.guanzonsms.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.appdriver.Database.Entity.ESmsIncoming;
import org.rmj.appdriver.Lib.SmsMaster;

import java.util.List;

public class VMSmsIncoming extends AndroidViewModel {
    private static final String TAG = VMSmsIncoming.class.getSimpleName();
    private final SmsMaster poSys;

    public VMSmsIncoming(@NonNull Application application) {
        super(application);
        this.poSys = new SmsMaster(application);
    }

    public LiveData<List<ESmsIncoming>> getSmsIncomingListForViewing() {
        return poSys.getSmsIncomingListForViewing();
    }

}

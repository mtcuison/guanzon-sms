package org.guanzongroup.smsAppDriver;

import android.content.Context;

import org.guanzongroup.smsAppDriver.Database.ESmsIncoming;
import org.guanzongroup.smsAppDriver.Database.SmsRepository;

public class SmsManager extends SmsRepository {
    private static final String TAG = SmsManager.class.getSimpleName();

    private final Context instance;

    public interface SmsManagerCallBack{
        void OnActionFinished(String args);
    }

    public SmsManager(Context context) {
        super(context);
        this.instance = context;
    }

    public void saveSmsIncoming(ESmsIncoming smsIncoming){
        SaveSmsInfo(smsIncoming);
    }

    public void updateUploadedSms(String TransNox){
        UpdateSmsServerUploaded(TransNox);
    }

    public static String getSubs(String mobileNo){
        switch (mobileNo){
            case "+63813":
            case "+63907":
            case "+63908":
            case "+63909":
            case "+63910":
            case "+63912":
            case "+63918":
            case "+63919":
            case "+63920":
            case "+63921":
            case "+63928":
            case "+63929":
            case "+63930":
            case "+63938":
            case "+63939":
            case "+63946":
            case "+63947":
            case "+63948":
            case "+63949":
            case "+63989":
            case "+63998":
            case "+63999":
            case "+63950":
            case "+63913":
            case "+63970":
            case "+63914":
            case "+63981":
            case "+63911":
            case "+63961":
            case "+63922":
            case "+63923":
            case "+63932":
            case "+63933":
            case "+63934":
            case "+63925":
            case "+63942":
            case "+63943":
            case "+63931":
            case "+63941":
            case "+63924":
            case "+63944":
                return "1";
            case "+63906":
            case "+63915":
            case "+63916":
            case "+63917":
            case "+63926":
            case "+63927":
            case "+63935":
            case "+63994":
            case "+63996":
            case "+63997":
            case "+63817":
            case "+63904":
            case "+63956":
            case "+63975":
            case "+63979":
            case "+63936":
            case "+63965":
            case "+63976":
            case "+63937":
            case "+63966":
            case "+63977":
            case "+63995":
            case "+63945":
            case "+63967":
            case "+63978":
                return "0";
            default:
                return "4";
        }
    }
}

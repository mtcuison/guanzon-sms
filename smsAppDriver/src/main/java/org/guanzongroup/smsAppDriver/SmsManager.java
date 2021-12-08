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

    public void updateUploadedSms(int TransNox){
        UpdateSmsServerUploaded(TransNox, new Constants().DATE_MODIFIED);
    }

    /**
     *
     * @param MobileNo pass the mobile of recieve sms for reformatting into "09"
     * @return formatted value of mobile no. if requires.
     */
    public static String parseMobileNo(String MobileNo){
        String lsResult;

        if (MobileNo.substring(0, 3).equalsIgnoreCase("+63")) {
            lsResult = MobileNo.replace("+63", "0");
        } else if (MobileNo.substring(0, 1).equalsIgnoreCase("9")) {
            lsResult = "0" + MobileNo;
        } else if (MobileNo.substring(0, 2).equalsIgnoreCase("63")) {
            lsResult = MobileNo.replace("63", "0");
        } else if (MobileNo.substring(0, 2).equalsIgnoreCase("09")) {
            lsResult = MobileNo;
        } else {
            MobileNo = MobileNo.replace("-", "");
            MobileNo = MobileNo.replace("+", "");
            String lsTarget = MobileNo.substring(0, 2);
            lsResult = MobileNo.replace(lsTarget, "0");
        }

        return lsResult;
    }


    /**
     *
     * @param mobileNo pass formatted mobile no to identify what network subscriber
     * @return 0 = Globe
     *         1 = Smart
     *         4 = Unknown
     */
    public static String getSubs(String mobileNo){
        String preMob = mobileNo.substring(0, 4);

        switch (preMob){
            case "0813":
            case "0907":
            case "0908":
            case "0909":
            case "0910":
            case "0912":
            case "0918":
            case "0919":
            case "0920":
            case "0921":
            case "0928":
            case "0929":
            case "0930":
            case "0938":
            case "0939":
            case "0946":
            case "0947":
            case "0948":
            case "0949":
            case "0989":
            case "0998":
            case "0999":
            case "0950":
            case "0913":
            case "0970":
            case "0914":
            case "0981":
            case "0911":
            case "0961":
            case "0922":
            case "0923":
            case "0932":
            case "0933":
            case "0934":
            case "0925":
            case "0942":
            case "0943":
            case "0931":
            case "0941":
            case "0924":
            case "0944":
                return "1";
            case "0906":
            case "0915":
            case "0916":
            case "0917":
            case "0926":
            case "0927":
            case "0935":
            case "0994":
            case "0996":
            case "0997":
            case "0817":
            case "0904":
            case "0956":
            case "0975":
            case "0979":
            case "0936":
            case "0965":
            case "0976":
            case "0937":
            case "0966":
            case "0977":
            case "0995":
            case "0945":
            case "0967":
            case "0978":
                return "0";
            default:
                return "4";
        }
    }
}

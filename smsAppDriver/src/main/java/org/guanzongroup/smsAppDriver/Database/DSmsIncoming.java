package org.guanzongroup.smsAppDriver.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DSmsIncoming {

    @Insert
    void SaveSmsInfo(ESmsIncoming smsIncoming);

    @Query("UPDATE SMS_Incoming SET dSendDate=:DateSent, cSendStat = '1' WHERE sTransnox=:TransNox")
    void UpdateSmsServerUploaded(int TransNox, String DateSent);

    @Query("SELECT * FROM SMS_Incoming WHERE sTransnox =:TransNox")
    List<ESmsIncoming> getSMSIncoming(int TransNox);

    @Query("SELECT * FROM SMS_Incoming ORDER BY dTransact DESC")
    List<ESmsIncoming> getSmsIncomingList();

    @Query("SELECT * FROM SMS_Incoming ORDER BY dTransact DESC")
    LiveData<List<ESmsIncoming>> getSmsIncomingListForViewing();

    @Query("SELECT * FROM SMS_Incoming WHERE sTransnox =:TransNox")
    ESmsIncoming getSMSIncomingInfo(int TransNox);

    @Query("SELECT * FROM SMS_Incoming WHERE cSendStat <> '1'")
    List<ESmsIncoming> getSmsIncomingForUpload();

//    @Query("SELECT (SELECT COUNT(*) FROM SMS_Incoming WHERE cSendStat <> '1') AS SentOverReceive")

    @Query("SELECT (SELECT COUNT(*) FROM SMS_Incoming " +
            "WHERE cSendStat <> '1')" +
            " || '/' || " +
            "(SELECT COUNT(*) FROM SMS_Incoming " +
            "WHERE cSendStat == '1') AS Current_Inventory")
    LiveData<String> getNumberOfUploadedSms();
}

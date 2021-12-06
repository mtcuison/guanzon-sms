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

    @Query("UPDATE SMS_Incoming SET cSendStat = '1' WHERE sTransnox=:TransNox")
    void UpdateSmsServerUploaded(String TransNox);

    @Query("SELECT * FROM SMS_Incoming WHERE sTransnox =:TransNox")
    List<ESmsIncoming> getSMSIncoming(String TransNox);

    @Query("SELECT * FROM SMS_Incoming ORDER BY dTransact DESC")
    List<ESmsIncoming> getSmsIncomingList();

    @Query("SELECT * FROM SMS_Incoming WHERE cSendStat <> '1'")
    List<ESmsIncoming> getSmsIncomingForUpload();

    @Query("SELECT (SELECT COUNT(*) FROM SMS_Incoming WHERE cSendStat <> '1') AS SentOverReceive")
    LiveData<String> getNumberOfUploadedSms();
}

package org.rmj.appdriver.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.appdriver.Database.Entity.ESmsIncoming;

import java.util.List;

@Dao
public interface DSmsIncoming {

    @Insert
    void SaveSmsInfo(ESmsIncoming smsIncoming);

    @Update
    void UpdateSms(ESmsIncoming foVal);

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

    @Query("SELECT * FROM SMS_Incoming WHERE cSendStat == '0'")
    List<ESmsIncoming> getSmsIncomingForUpload();

    @Query("SELECT (SELECT COUNT(*) FROM SMS_Incoming " +
            "WHERE cSendStat == '0')" +
            " || '/' || " +
            "(SELECT COUNT(*) FROM SMS_Incoming " +
            "WHERE cSendStat == '1') AS Current_Inventory")
    LiveData<String> getNumberOfUploadedSms();
}

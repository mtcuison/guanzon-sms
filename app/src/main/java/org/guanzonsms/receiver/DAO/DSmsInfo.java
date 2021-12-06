package org.guanzonsms.receiver.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import org.guanzonsms.receiver.Entity.ESmsInfo;

import java.util.List;

@Dao
public interface DSmsInfo {
    @Insert
    void insert(ESmsInfo foSmsInfo);

    @Delete
    void delete(ESmsInfo eSmsInfo);

    @Query("DELETE FROM smsInfo")
    void deleteAll();

    @Query("SELECT * FROM smsInfo")
    LiveData<List<ESmsInfo>> getSmsList();
}

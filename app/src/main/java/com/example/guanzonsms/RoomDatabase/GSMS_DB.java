package com.example.guanzonsms.RoomDatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.guanzonsms.DAO.DSmsInfo;
import com.example.guanzonsms.Entity.ESmsInfo;

@Database(entities = {ESmsInfo.class}, version = 1)
public abstract class GSMS_DB extends RoomDatabase {
    private static GSMS_DB instance;
    public abstract DSmsInfo smsDao();

    public static synchronized GSMS_DB getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GSMS_DB.class, "GSMS_DB")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}

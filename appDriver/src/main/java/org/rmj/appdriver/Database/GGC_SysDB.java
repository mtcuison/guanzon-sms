package org.rmj.appdriver.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.rmj.appdriver.Database.DataAccessObject.DSmsIncoming;
import org.rmj.appdriver.Database.Entity.ESmsIncoming;

@Database(entities = {ESmsIncoming.class}, version = 1, exportSchema = false)
public abstract class GGC_SysDB extends RoomDatabase {
    private static final String TAG = GGC_SysDB.class.getSimpleName();
    private static GGC_SysDB instance;

    public abstract DSmsIncoming smsIncoming();

    public static synchronized GGC_SysDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
            GGC_SysDB.class, "GGC_ISysDBF.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.e(TAG, "Local database has been created.");
        }
    };
}

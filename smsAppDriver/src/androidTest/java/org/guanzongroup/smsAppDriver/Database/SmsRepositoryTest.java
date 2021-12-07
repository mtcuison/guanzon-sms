package org.guanzongroup.smsAppDriver.Database;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class SmsRepositoryTest {

    private DSmsIncoming poDao;
    private GGC_SysDB db;

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, GGC_SysDB.class).build();
        poDao = db.smsIncoming();
    }

    @After
    public void finis() throws IOException{
        db.close();
    }

    @Test
    public void saveSmsInfo() {
        ESmsIncoming loSms = new ESmsIncoming();
        loSms.setMessagex("Unit test entry");
        loSms.setTransact("2021-");
        loSms.setSourceCd("");
        loSms.setMobileNo("");
        loSms.setSubscrbr("");
        loSms.setFollowUp("");
        loSms.setNoRetryx("");
        loSms.setReadxxxx("");
        loSms.setDateRead("");
        loSms.setRepliedx("");
        loSms.setDateRpld("");
        loSms.setPostedxx("");
        loSms.setTranStat("");
        loSms.setSendStat("");
        loSms.setSendDate("");
        poDao.SaveSmsInfo(loSms);
        List<ESmsIncoming> loResult = poDao.getSMSIncoming(1);
        ESmsIncoming result = loResult.get(0);
        assertEquals(1, result.getTransnox());
    }

    @Test
    public void getSmsIncomingList() throws Exception {
        for(int x = 0; x < 10; x++){
            ESmsIncoming loSms = new ESmsIncoming();
            loSms.setMessagex("Unit test entry");
            loSms.setTransact("2021-");
            loSms.setSourceCd("");
            loSms.setMobileNo("");
            loSms.setSubscrbr("2");
            loSms.setFollowUp("");
            loSms.setNoRetryx("");
            loSms.setReadxxxx("");
            loSms.setDateRead("");
            loSms.setRepliedx("");
            loSms.setDateRpld("");
            loSms.setPostedxx("");
            loSms.setTranStat("");
            loSms.setSendStat("");
            loSms.setSendDate("");
            poDao.SaveSmsInfo(loSms);
        }
        List<ESmsIncoming> loIncm = poDao.getSmsIncomingList();
        assertEquals(10, loIncm.size());
    }

    @Test
    public void testSpecialCharacters() throws Exception{
        ESmsIncoming loSms = new ESmsIncoming();
        loSms.setMessagex("Unit test entry ñ @ Å Î");
        loSms.setTransact("2021-");
        loSms.setSourceCd("");
        loSms.setMobileNo("");
        loSms.setSubscrbr("2");
        loSms.setFollowUp("");
        loSms.setNoRetryx("");
        loSms.setReadxxxx("");
        loSms.setDateRead("");
        loSms.setRepliedx("");
        loSms.setDateRpld("");
        loSms.setPostedxx("");
        loSms.setTranStat("");
        loSms.setSendStat("");
        loSms.setSendDate("");
        poDao.SaveSmsInfo(loSms);

        ESmsIncoming loResult = poDao.getSMSIncomingInfo(1);
        String lsMessage = loResult.getMessagex();
        assertEquals("Unit test entry ñ @ Å Î", lsMessage);
    }
}
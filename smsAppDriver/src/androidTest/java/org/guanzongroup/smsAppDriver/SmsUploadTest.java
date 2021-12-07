package org.guanzongroup.smsAppDriver;

import static org.junit.Assert.*;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ApplicationLifecycleMonitorRegistry;

import org.guanzongroup.smsAppDriver.Database.ESmsIncoming;
import org.guanzongroup.smsAppDriver.Database.SmsRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class SmsUploadTest {
    private static final String TAG = SmsUploadTest.class.getSimpleName();

    private SmsUpload poUpload;
    private SmsRepository poDB;

    private SmsUpload.OnUploadCallback smsCallback;

    private boolean isSuccess = false;

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        poDB = new SmsRepository(context);
        poUpload = new SmsUpload(context);

//        for(int x = 0; x < 10; x++){
//            ESmsIncoming loSms = new ESmsIncoming();
//            loSms.setMessagex("Unit test entry ñ");
//            loSms.setTransact("2021-");
//            loSms.setSourceCd("");
//            loSms.setMobileNo("");
//            loSms.setSubscrbr("2");
//            loSms.setFollowUp("");
//            loSms.setNoRetryx("");
//            loSms.setReadxxxx("");
//            loSms.setDateRead("");
//            loSms.setRepliedx("");
//            loSms.setDateRpld("");
//            loSms.setPostedxx("");
//            loSms.setTranStat("");
//            loSms.setSendStat("");
//            loSms.setSendDate("");
//            poDB.SaveSmsInfo(loSms);
//        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSpecialCharacters() throws Exception{
        ESmsIncoming loSms = new ESmsIncoming();
        loSms.setMessagex("Unit test entry ñ");
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
        poDB.SaveSmsInfo(loSms);

        ESmsIncoming loResult = poDB.getSMSIncomingInfo(0);
        String lsMessage = loResult.getMessagex();
        assertEquals("Unit test entry ñ", lsMessage);
    }

    @Test
    public void testUploadSmsIncomingNoValue() {
        poUpload.setRemoteServer(SmsUpload.LOCAL_SERVER);
        List<ESmsIncoming> loSms = null;
        poUpload.UploadSmsIncoming(loSms, SmsUpload.LOCAL_SERVER, new SmsUpload.OnUploadCallback() {
            @Override
            public void OnUpload(String args) {
                Log.d(TAG, args);
                isSuccess = true;
            }

            @Override
            public void OnFailed(String message) {
                Log.d(TAG, message);
                isSuccess = false;
            }
        });
        assertFalse(isSuccess);
    }

    @Test
    public void testUploadSmsIncomingZeroIndex() {
        poUpload.setRemoteServer(SmsUpload.LOCAL_SERVER);
        List<ESmsIncoming> loSms = new ArrayList<>();
        poUpload.UploadSmsIncoming(loSms, SmsUpload.LOCAL_SERVER, new SmsUpload.OnUploadCallback() {
            @Override
            public void OnUpload(String args) {
                Log.d(TAG, args);
                isSuccess = true;
            }

            @Override
            public void OnFailed(String message) {
                Log.d(TAG, message);
                isSuccess = false;
            }
        });
        assertFalse(isSuccess);
    }

    @Test
    public void testUploadSmsIncomingLive() {
        poUpload.setRemoteServer(SmsUpload.LIVE_SERVER);
        List<ESmsIncoming> loSms = poDB.getSmsIncomingForUpload();
        poUpload.UploadSmsIncoming(loSms, SmsUpload.LOCAL_SERVER, new SmsUpload.OnUploadCallback() {
            @Override
            public void OnUpload(String args) {
                Log.d(TAG, args);
                isSuccess = true;
            }

            @Override
            public void OnFailed(String message) {
                Log.d(TAG, message);
                isSuccess = false;
            }
        });
        assertTrue(isSuccess);
    }

    @Test
    public void testUploadSmsIncomingLocal() {
        poUpload.setRemoteServer(SmsUpload.LOCAL_SERVER);
        List<ESmsIncoming> loSms = poDB.getSmsIncomingForUpload();
        poUpload.UploadSmsIncoming(loSms, SmsUpload.LOCAL_SERVER, new SmsUpload.OnUploadCallback() {
            @Override
            public void OnUpload(String args) {
                Log.d(TAG, args);
                isSuccess = true;
            }

            @Override
            public void OnFailed(String message) {
                Log.d(TAG, message);
                isSuccess = false;
            }
        });
        assertTrue(isSuccess);
    }
}
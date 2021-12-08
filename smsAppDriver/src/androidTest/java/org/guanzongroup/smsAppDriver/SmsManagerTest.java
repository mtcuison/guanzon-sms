package org.guanzongroup.smsAppDriver;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SmsManagerTest {

    private SmsManager poSms;

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        poSms = new SmsManager(context);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSubstringMobileNo() {
        String MobileNo = "09171870011";
        String lsResult = MobileNo.substring(0, 4);
        assertEquals("0917", lsResult);
    }

    @Test
    public void testSubstringMobileNo1() {
        String MobileNo = "+639171870011";
        String lsResult = MobileNo.substring(0, 6);
        assertEquals("+63917", lsResult);
    }

    @Test
    public void testGetSubscriberGlobe() throws Exception {
        String MobileNo = "09171870011";
        String lsResult = SmsManager.getSubs(MobileNo);
        assertEquals("0", lsResult);
    }

    @Test
    public void testGetSubscriberSmart() throws Exception {
        String MobileNo = "09183403969";
        String lsResult = SmsManager.getSubs(MobileNo);
        assertEquals("1", lsResult);
    }

    @Test
    public void testGetSubscriberUnknown() throws Exception {
        String MobileNo = "09921234568";
        String lsResult = SmsManager.getSubs(MobileNo);
        assertEquals("4", lsResult);
    }

    @Test
    public void testParseMobileNo() throws Exception{
        String MobileNo = "+639270359402";
        String lsResult = SmsManager.parseMobileNo(MobileNo);
        assertEquals("09270359402", lsResult);
    }

    @Test
    public void testParseMobileNo1() throws Exception{
        String MobileNo = "9171870011";
        String lsResult = SmsManager.parseMobileNo(MobileNo);
        assertEquals("09171870011", lsResult);
    }

    @Test
    public void testParseMobileNo2() throws Exception{
        String MobileNo = "+659171870011";
        String lsResult = SmsManager.parseMobileNo(MobileNo);
        assertEquals("09171870011", lsResult);
    }
}
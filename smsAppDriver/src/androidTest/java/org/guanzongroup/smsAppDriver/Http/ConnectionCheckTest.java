package org.guanzongroup.smsAppDriver.Http;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConnectionCheckTest {

    private ConnectionCheck poConn;
    private boolean isConnected = false;
    private String address = "";

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        poConn = new ConnectionCheck(context);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void checkActiverServer() {
        poConn.checkActiverServer((isDeviceConnected, serverAddress) -> {
            isConnected = isDeviceConnected;
            address = serverAddress;
        });
        assertTrue(isConnected);
        assertEquals("", address);
    }
}
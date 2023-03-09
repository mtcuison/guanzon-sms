package org.rmj.appdriver.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RcvBootDevice extends BroadcastReceiver {

    public RcvBootDevice() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            try {
                Intent serviceIntent = new Intent(context, Class.forName("org.guanzonsms.Activity.Activity_Main"));
                context.startService(serviceIntent);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
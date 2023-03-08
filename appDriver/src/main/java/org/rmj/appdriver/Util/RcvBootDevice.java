package org.rmj.appdriver.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RcvBootDevice extends BroadcastReceiver {

    private final Class<?> activity;

    public RcvBootDevice(Class<?> activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, activity);
            context.startService(serviceIntent);
        }
    }
}
package org.guanzonsms.receiver.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.guanzonsms.receiver.Activity.Activity_Main;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, Activity_Main.class);
            context.startService(serviceIntent);
        }
    }
}
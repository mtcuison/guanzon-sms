package org.rmj.appdriver.Util;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.rmj.appdriver.Etc.ITelephony;

import java.lang.reflect.Method;

public class RcvIncomingCall extends BroadcastReceiver {

    private static final String TAG = RcvIncomingCall.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction()) &&
                intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            rejectCall(context, null);
        }
    }

    @SuppressLint("MissingPermission")
    protected void rejectCall(@NonNull Context context, Number number) {
        boolean failed = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);

            try {
                telecomManager.endCall();
                Log.d(TAG, "Invoked 'endCall' on TelecomManager");
            } catch (Exception e) {
                Log.e(TAG, "Couldn't end call with TelecomManager", e);
                failed = true;
            }
        } else {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            try {
                Method m = tm.getClass().getDeclaredMethod("getITelephony");
                m.setAccessible(true);

                ITelephony telephony = (ITelephony) m.invoke(tm);

                telephony.endCall();
            } catch (Exception e) {
                Log.e(TAG, "Couldn't end call with TelephonyManager", e);
                failed = true;
            }
        }
        if (failed) {
            Toast.makeText(context, "OS does not support call blocking.", Toast.LENGTH_LONG).show();
        }

    }
}
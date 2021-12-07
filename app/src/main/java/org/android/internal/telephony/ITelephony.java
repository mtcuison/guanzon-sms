package org.android.internal.telephony;

public interface ITelephony {
    boolean endCall();
    void answerRingingCall();
    void silenceRinger();
}

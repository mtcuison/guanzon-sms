package org.guanzonsms.receiver.Callback;

public interface UpdateSmsServerCallback {
    void OnUpdateSuccess(String message);
    void OnUpdateFailed(String message);
}

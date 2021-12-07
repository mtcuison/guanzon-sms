package org.guanzonsms.receiver.Callback;

public interface UpdateSmsServerCallback {
    void OnUpdateSuccess();
    void OnUpdateFailed(String message);
}

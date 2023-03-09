package org.guanzonsms.Callback;

public interface UpdateSmsServerCallback {
    void OnUpdateSuccess(String message);
    void OnUpdateFailed(String message);
}

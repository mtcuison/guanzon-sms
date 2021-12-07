package org.guanzongroup.smsAppDriver.Http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ConnectionCheck {
    private static final String TAG = ConnectionCheck.class.getSimpleName();

    private final Context context;

    private static final String LIVE_SERVER = "https://restgk.guanzongroup.com.ph/security/request_android_object.php";
    private static final String LOCAL_SERVER = "http://192.168.10.140/security/request_android_object.php";

    public interface OnCheckConnectionCallback{
        void OnCheck(boolean isDeviceConnected, String serverAddress);
    }

    public ConnectionCheck(Context context) {
        this.context = context;
    }

    /**
     *
     * use this method inside asyncTask to avoid Application Not Responding
     * @param callback OnCheckConnectionCallback returns server
     *                 address (Live/Local) to be pass on webclient
     *                 for uploading sms incoming
     */
    public void checkActiverServer(OnCheckConnectionCallback callback){
        if(isDeviceConnected(LIVE_SERVER)){
            callback.OnCheck(true, LIVE_SERVER);
        } else if(isDeviceConnected(LOCAL_SERVER)){
            callback.OnCheck(true, LOCAL_SERVER);
        } else {
            callback.OnCheck(false, "");
        }
    }


    private boolean isDeviceConnected(String address){
        return deviceConnected() && isReachable(address);
    }

    private boolean deviceConnected(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private boolean isReachable(String Address)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        trustAllCertificates();

        try
        {
            HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(
                    Address).openConnection();
            httpUrlConnection.setRequestProperty("Connection", "close");
            httpUrlConnection.setRequestMethod("HEAD");
            httpUrlConnection.setConnectTimeout(5000);
            int responseCode = httpUrlConnection.getResponseCode();

            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException | NetworkOnMainThreadException noInternetConnection){
            noInternetConnection.printStackTrace();
            return false;
        }
    }

    private void trustAllCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }

    private boolean isWifiConnected(){
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }
}

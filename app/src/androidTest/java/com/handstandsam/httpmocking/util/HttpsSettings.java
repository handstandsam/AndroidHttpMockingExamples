package com.handstandsam.httpmocking.util;

import com.squareup.okhttp.OkHttpClient;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by agupt13 on 6/21/16.
 */
public class HttpsSettings {

    public static OkHttpClient allowAllCertificates() {
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };


            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sc.getSocketFactory();


            okHttpClient.setSslSocketFactory(sslSocketFactory);
            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            okHttpClient.setHostnameVerifier(allHostsValid);
            return okHttpClient;
        } catch (Exception ex) {
            System.out.println(" Exception thrown  : " + ex.getMessage());
        }
        return okHttpClient;
    }
}

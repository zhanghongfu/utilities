package dolf.zhang.utilities.http;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author dolf
 * @description
 * @date 16/9/26
 */
public class DefaultTrustManager implements X509TrustManager {
    public DefaultTrustManager() {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }
}

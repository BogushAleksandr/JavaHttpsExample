package com.ua.in.nova.https.util;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.security.cert.*;
import java.util.Base64;
import java.security.cert.Certificate;

/**
 * @author Bogush Aleksandr
 * @version 1.0
 * @since 15-11-2020
 */

public class JavaHttpsExample {
    public static void main(String[] args) throws IOException, CertificateEncodingException {
        String httpsURL = "https://google.com/";
        getCertificateFromUrlAndSaveToFile(httpsURL);
    }
    private static void getCertificateFromUrlAndSaveToFile(String aURL) throws IOException, CertificateEncodingException {
        URL destinationURL = new URL(aURL);
        HttpsURLConnection conn = (HttpsURLConnection) destinationURL.openConnection();
        conn.connect();
        Certificate[] certs = conn.getServerCertificates();
        int i = 0;
        for (Certificate cert : certs) {
            if (cert instanceof X509Certificate) {
                X509Certificate x = (X509Certificate) cert;
                String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";
                String END_CERT = "-----END CERTIFICATE-----";
                String LINE_SEPARATOR = System.getProperty("line.separator");
                final Base64.Encoder encoder = Base64.getMimeEncoder(64, LINE_SEPARATOR.getBytes());
                Certificate certificate = x;
                byte[] rawCrtText = certificate.getEncoded();
                String encodedCertText = new String(encoder.encode(rawCrtText));
                String prettified_cert = BEGIN_CERT + LINE_SEPARATOR + encodedCertText + LINE_SEPARATOR + END_CERT;
                System.out.println(prettified_cert);
                OutputStream os = null;
                try {
                    os = new FileOutputStream(new File("/home/.../.../.../MyCert" + i + ".cer"));
                    os.write(prettified_cert.getBytes(), 0, prettified_cert.length());
                    os.close();
                    i++;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}


package br.nauber.flickrbrowser;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by naubergois on 5/21/16.
 */
public class WebViewSSLNoError extends WebViewClient {

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();

    }
}

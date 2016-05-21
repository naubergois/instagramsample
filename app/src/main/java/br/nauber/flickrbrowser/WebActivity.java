package br.nauber.flickrbrowser;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

public class WebActivity extends BaseVideoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Toolbar toolbar = activateToolBar();


        setUpDrawer();

        WebView view=(WebView) findViewById(R.id.webview);

        view.setWebViewClient(new WebViewSSLNoError());
        view.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");

        view.loadUrl("https://m.omelete.uol.com.br");



    }

}

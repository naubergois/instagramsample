package br.nauber.flickrbrowser;

import android.os.Bundle;
import android.webkit.WebView;

public class VideoWebActivity extends BaseVideoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_web);
//        Toolbar toolbar = activateToolBar();


        Video video = (Video) getIntent().getSerializableExtra("VIDEO_TRANSFER");

        setUpDrawer();

        WebView view=(WebView) findViewById(R.id.webview);

        view.setWebViewClient(new WebViewSSLNoError());
        //view.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");
        view.getSettings().setJavaScriptEnabled(true);

        view.loadUrl("https://www.youtube.com/watch?v="+video.getId());



    }

}

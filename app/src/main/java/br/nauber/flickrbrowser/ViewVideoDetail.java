package br.nauber.flickrbrowser;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class ViewVideoDetail extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_video_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);

        playerView = (YouTubePlayerView) findViewById(R.id.player_view);
        playerView.initialize(YouTubeConnector.KEY, this);


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        if (!b) {
            Video video = (Video) getIntent().getSerializableExtra("VIDEO_TRANSFER");
            youTubePlayer.cueVideo(video.getId());
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

//    @Override
//    public void onInitializationSuccess(Provider provider, YouTubePlayer player,
//                                        boolean restored) {
//        if(!restored){
//            player.cueVideo(getIntent().getStringExtra("VIDEO_ID"));
//        }
//    }

}

package br.nauber.flickrbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by naubergois on 5/22/16.
 */
public class YouTubeConnectorToday extends YouTubeConnector {

    private String LOG_TAG = YouTubeConnectorToday.class.getSimpleName();

    public YouTubeConnectorToday(String searchCriteria, boolean matchAll) {
        super(searchCriteria, matchAll);
    }

    private String getYesterdayDateString() {
        Locale brasil = new Locale("pt", "BR");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", brasil);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }


    @Override
    public boolean createAndUpdateURI(String searchCriteria, boolean matchAll) {
        final String YOUTUBE_API_BASE_URL = "https://www.googleapis.com/youtube/v3/search";
        final String PART_PARAM = "part";
        final String CHANNELID_PARAM = "channelId";
        final String MAXRESULTS_PARAM = "maxResults";
        final String PUBLISHED_AFTER = "publishedAfter";
        final String ORDER_PARAM = "order";
        final String KEY_PARAM = "key";



        Uri myDestinationURI = Uri.parse(YOUTUBE_API_BASE_URL).buildUpon()
                .appendQueryParameter(PART_PARAM, "snippet")
                .appendQueryParameter(CHANNELID_PARAM, "UCaSAM5kna2KyX-uVLSGr8PQ")
                .appendQueryParameter(MAXRESULTS_PARAM, "30")
                .appendQueryParameter(ORDER_PARAM, "date")
                .appendQueryParameter(PUBLISHED_AFTER, getYesterdayDateString())
                .appendQueryParameter(KEY_PARAM, "AIzaSyCTzmsfTOdFbGRgmTcde4YvHxsuk-iUghY")
                .build();


        setMyDestinationURI(myDestinationURI);
        this.setmRawURL(myDestinationURI.getPath());

        return false;
    }


    public class DownloadJsonData extends DownloadRawData {

        public DownloadJsonData() {
        }

        @Override
        protected void onPostExecute(String s) {
            Log.e(LOG_TAG, "Video onPostExecute ");
            super.onPostExecute(s);
            processResult();

        }


        @Override
        protected String doInBackground(String... params) {
            String[] param = {getMyDestinationURI().toString()};
            return super.doInBackground(param);
        }


    }

    public void processResult() {
        if (getmDownloadStatus() != DownLoadStatus.OK) {
            Log.e(LOG_TAG, "Error downloading Raw File ");
            return;
        }

        final String YOUTUBE_ITEMS = "items";
        final String YOUTUBE_SNIPPET = "snippet";
        final String YOUTUBE_THUMBNAILS = "thumbnails";
        final String YOUTUBE_HIGH = "high";
        final String YOUTUBE_TITLE = "title";
        final String YOUTUBE_URL = "url";
        final String YOUTUBE_ID = "id";
        final String YOUTUBE_VIDEOID = "videoId";


        try {
            JSONObject jsonObject = new JSONObject(getmData());
            JSONArray itemsArray = jsonObject.getJSONArray(YOUTUBE_ITEMS);
            if (itemsArray.length() > 0) {
                VideoTemp.setVideosNotification(new ArrayList<Video>());
            }
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jsonVideo = itemsArray.getJSONObject(i);

                String snippet = jsonVideo.getString(YOUTUBE_SNIPPET);

                String id = null;
                JSONObject jsonId = jsonVideo.getJSONObject(YOUTUBE_ID);
                if (jsonId.has(YOUTUBE_VIDEOID)) {
                    id = jsonId.getString(YOUTUBE_VIDEOID);
                }

                JSONObject jsonSnippet = jsonVideo.getJSONObject(YOUTUBE_SNIPPET);


                String title = jsonSnippet.getString(YOUTUBE_TITLE);

                JSONObject jsonTHUMBNAILS = jsonSnippet.getJSONObject(YOUTUBE_THUMBNAILS);
                JSONObject jsonHigh = jsonTHUMBNAILS.getJSONObject(YOUTUBE_HIGH);


                String url = jsonHigh.getString(YOUTUBE_URL);

                if (id != null) {

                    Video video = new Video();
                    video.setTitle(title);
                    video.setUrl(url);
                    video.setId(id);


                    VideoTemp.getVideosNotification().add(video);
                }


            }

            for (Video singlePhoto : VideoTemp.getVideosNotification()) {
                Log.v(LOG_TAG, singlePhoto.toString());

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error in json", e);
        }


    }

    @Override
    public List<Video> getVideos() {
        return VideoTemp.getVideosNotification();
    }
}
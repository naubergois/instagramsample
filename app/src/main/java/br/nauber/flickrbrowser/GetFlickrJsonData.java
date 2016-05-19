package br.nauber.flickrbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naubergois on 5/15/16.
 */
public class GetFlickrJsonData extends GetRawData {

    private String LOG_TAG = GetFlickrJsonData.class.getSimpleName();
    private List<Photo> mPhotos;
    private Uri myDestinationURI;

    public GetFlickrJsonData(String searchCriteria, boolean matchAll) {
        super(null);
        Log.d(LOG_TAG, "Running process 5");
        createAndUpdateURI(searchCriteria, matchAll);
        mPhotos=new ArrayList<Photo>();
    }

    @Override
    public void execute() {
        Log.d(LOG_TAG, "Running process 4");
        super.setmRawURL(myDestinationURI.toString());
        DownloadJsonData downloadJsonData=new DownloadJsonData();
        Log.v(LOG_TAG, "Build uri= "+myDestinationURI.toString());
        downloadJsonData.execute(myDestinationURI.toString());
    }

    private boolean createAndUpdateURI(String searchCriteria, boolean matchAll) {
        Log.d(LOG_TAG, "Running process 6");
        final String FLICKR_API_BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAGS_PARAM = "tags";
        final String TAGMODE_PARAM = "tagmode";
        final String FORMAT_PARAM = "format";
        final String NO_JSON_CALLBACK_PARAM = "nojsoncallback";
        myDestinationURI = Uri.parse(FLICKR_API_BASE_URL).buildUpon()
                .appendQueryParameter(TAGS_PARAM, searchCriteria)
                .appendQueryParameter(TAGMODE_PARAM, "All")
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(NO_JSON_CALLBACK_PARAM, "1")
                .build();

        return false;
    }


    public class DownloadJsonData extends DownloadRawData {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            processResult();
        }


        @Override
        protected String doInBackground(String... params) {
            String[] param={myDestinationURI.toString()};
            return super.doInBackground(param);
        }


    }

    public void processResult() {
        if (getmDownloadStatus() != DownLoadStatus.OK) {
            Log.e(LOG_TAG, "Error downloading Raw File ");
            return;
        }

        final String FLICKR_ITEMS = "items";
        final String FLICKR_TITLES = "title";
        final String FLICKR_MEDIA = "media";
        final String FLICKR_PHOTO_URL = "m";
        final String FLICKR_AUTHOR = "author";
        final String FLICKR_AUTHOR_ID = "author_id";
        final String FLICKR_LINK = "link";
        final String FLICKR_TAGS = "tags";

        try {
            JSONObject jsonObject = new JSONObject(getmData());
            JSONArray itemsArray = jsonObject.getJSONArray(FLICKR_ITEMS);
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                String title = jsonPhoto.getString(FLICKR_TITLES);
                String author = jsonPhoto.getString(FLICKR_AUTHOR);
                String authorID = jsonPhoto.getString(FLICKR_AUTHOR_ID);
                String link = jsonPhoto.getString(FLICKR_LINK);
                String tag = jsonPhoto.getString(FLICKR_TAGS);

                JSONObject jsonMedia = jsonPhoto.getJSONObject(FLICKR_MEDIA);
                String photoUrl = jsonMedia.getString(FLICKR_PHOTO_URL);

                Photo photo = new Photo(title, author, authorID, link,tag, photoUrl);
                this.mPhotos.add(photo);

            }

            for (Photo singlePhoto : this.mPhotos) {
                Log.v(LOG_TAG, singlePhoto.toString());

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error in json", e);
        }


    }


    public List<Photo> getmPhotos() {
        return mPhotos;
    }
}

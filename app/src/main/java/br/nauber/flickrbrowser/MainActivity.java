package br.nauber.flickrbrowser;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseListPhotoActivity {

    private final String LOG_TAG = "Main Activity";
    private List<Photo> mPhotoList = new ArrayList<Photo>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = activateToolBar();

        setUp();
        setUpDrawer();

        ProcessProcessPhotos processProcessPhotos = new ProcessProcessPhotos("marvel", true);
        processProcessPhotos.execute();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (getmFlickrRecyclerViewAdapter() != null) {

            String query = getSavedPreferenceData(FLICKR_QUERY);
            if (query.length() > 0) {
                ProcessProcessPhotos processProcessPhotos = new ProcessProcessPhotos(query, true);
                processProcessPhotos.execute();
            }
        }
    }





}

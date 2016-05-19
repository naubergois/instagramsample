package br.nauber.flickrbrowser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private final String LOG_TAG = "Main Activity";
    private List<Photo> mPhotoList = new ArrayList<Photo>();
    private RecyclerView mRecyclerView;
    private FlickrRecyclerViewAdapter mFlickrRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = activateToolBar();
        activateToolbarWithHomeEnabled();


        Log.d(LOG_TAG, "Running process 1");

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFlickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(new ArrayList<Photo>(), MainActivity.this);
        mRecyclerView.setAdapter(mFlickrRecyclerViewAdapter);

        mRecyclerView.addOnItemTouchListener(new RcycleItemClickListener(this, mRecyclerView, new RcycleItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Normal Tap", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onItemLongClick(View view, int position) {

                Intent intent = new Intent(MainActivity.this, ViewPhotoDetailsActivity.class);
                Photo photo = mFlickrRecyclerViewAdapter.getPhoto(position);
                if (photo == null) {
                    Toast.makeText(MainActivity.this, "Photo null", Toast.LENGTH_LONG).show();
                }
                intent.putExtra(PHOTO_TRANSFER, photo);
                startActivity(intent);


            }
        }));

        Log.d(LOG_TAG, "Running process");
        ProcessProcessPhotos processProcessPhotos = new ProcessProcessPhotos("marvel", true);
        processProcessPhotos.execute();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mFlickrRecyclerViewAdapter != null) {

            String query = getSavedPreferenceData(FLICKR_QUERY);
            if (query.length() > 0) {
                ProcessProcessPhotos processProcessPhotos = new ProcessProcessPhotos(query, true);
                processProcessPhotos.execute();
            }
        }
    }

    private String getSavedPreferenceData(String flickr_query) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        return sharedPreferences.getString(flickr_query, "");
    }


    public class ProcessProcessPhotos extends GetFlickrJsonData {
        public ProcessProcessPhotos(String searchCriteria, boolean matchAll) {
            super(searchCriteria, matchAll);
        }


        @Override
        public void execute() {

            ProcessData processData = new ProcessData();
            processData.execute();


        }

        public class ProcessData extends DownloadJsonData {


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mFlickrRecyclerViewAdapter.loadNewData(getmPhotos());
            }
        }
    }

}

package br.nauber.flickrbrowser;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**
 * Created by naubergois on 5/22/16.
 */
public class OmeleteSchedulingService extends IntentService {
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    public static final int NOTIFICATION_ID = 1;
    private final String LOG_TAG = "Service Receiver";

    public OmeleteSchedulingService() {
        super("Omelete Scheduling");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG, "Service start");

        OmeleteSchedulingService.ProcessProcessVideos processProcessVideos = new ProcessProcessVideos("", true);
        processProcessVideos.execute();
    }





    public class ProcessProcessVideos extends YouTubeConnectorToday {
        public ProcessProcessVideos(String search, boolean matchAll) {
            super(search, true);
        }


        public void execute() {

            ProcessData processData = new ProcessData();
            processData.execute();


        }

        public class ProcessData extends DownloadJsonData {

            Bitmap fig;

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                List<Video> videos = getVideos();
                for (Video video : videos
                        ) {
                    fig=getBitmapFromURL(video.getUrl());
                    sendNotification("Novo vídeo do Omelete " + video.getTitle(), video);
                }


            }

            @Override
            protected String doInBackground(String... params) {

                return super.doInBackground(params);
            }

            private void sendNotification(String msg, Video video) {
                mNotificationManager = (NotificationManager)
                        getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                        new Intent(getApplicationContext(), YouTubeListActivity.class), 0);

                NotificationCompat.Builder mBuilder;
                if (fig!=null) {

                    mBuilder =
                            new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(R.drawable.logo)
                                    .setContentTitle(getString(R.string.novovideo))
                                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(fig))
                                    .setContentText(msg);
                }else{
                    mBuilder =
                            new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(R.drawable.logo)
                                    .setContentTitle(getString(R.string.novovideo))
                                    .setStyle(new NotificationCompat.BigTextStyle())
                                    .setContentText(msg);

                }

                mBuilder.setContentIntent(contentIntent);
                mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
            }

            public Bitmap getBitmapFromURL(String src) {
                try {
                    URL url = new URL(src);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    return myBitmap;
                } catch (IOException e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
                return null;
            }

        }
    }
}

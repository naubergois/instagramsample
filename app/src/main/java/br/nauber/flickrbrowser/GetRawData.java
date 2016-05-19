package br.nauber.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by naubergois on 5/15/16.
 */
enum DownLoadStatus{IDLE,PROCESSING,NOT_INITIALIZED,FAILED_OR_EMPTY,OK}
public class GetRawData {

    private String LOG_TAG=GetRawData.class.getSimpleName();
    private String mRawURL;

    public void setmRawURL(String mRawURL) {
        this.mRawURL = mRawURL;
    }

    private String mData;
    private DownLoadStatus mDownloadStatus;

    public GetRawData(String mRawURL) {
        this.mRawURL = mRawURL;
        this.mDownloadStatus=DownLoadStatus.IDLE;
    }

    public void reset(){
        this.mRawURL=null;
        this.mDownloadStatus=DownLoadStatus.IDLE;
        this.mData=null;

    }

    public String getmData() {
        return mData;
    }

    public DownLoadStatus getmDownloadStatus() {
        return mDownloadStatus;
    }

    public void execute(){
        this.mDownloadStatus=DownLoadStatus.PROCESSING;
        DownloadRawData downloadRawData=new DownloadRawData();
        downloadRawData.execute(mRawURL);

    }

    public class DownloadRawData extends AsyncTask<String,Void,String>{

        @Override
        protected void onPostExecute(String s) {
            mData=s;
            Log.v(LOG_TAG,"Data Returned was: "+mData);

            if (mData==null){
                if (mRawURL==null){
                    mDownloadStatus=DownLoadStatus.NOT_INITIALIZED;
                }
                else{
                    mDownloadStatus=DownLoadStatus.FAILED_OR_EMPTY;
                }

            }else{
                mDownloadStatus=DownLoadStatus.OK;
            }
        }



        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection=null;
            BufferedReader reader=null;
            if (params==null){
                return null;
            }

            try {
                URL url=new URL(params[0]);
                urlConnection=(HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream i=urlConnection.getInputStream();
                if (i==null){
                    return null;
                }

                StringBuffer buffer=new StringBuffer();
                reader=new BufferedReader(new InputStreamReader(i));

                String line;
                while ((line=reader.readLine())!=null){
                    buffer.append(line+"\n");
                }

                return buffer.toString();

            }catch (IOException e){
                Log.e(LOG_TAG,"Error",e);


            }finally {
                if (urlConnection!=null){
                    urlConnection.disconnect();
                }
                if (reader!=null){
                    try{
                    reader.close();
                    }catch (final IOException e){
                        Log.e(LOG_TAG,"Error closing stream",e);
                    }

                }
            }

            return null;
        }
    }
}

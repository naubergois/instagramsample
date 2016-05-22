package br.nauber.flickrbrowser;

import java.util.List;

/**
 * Created by naubergois on 5/21/16.
 */
public class VideoTemp {

    private static List<Video> videos;

    public static List<Video> getVideos() {
        return videos;
    }

    public static void setVideos(List<Video> videos) {
        VideoTemp.videos = videos;
    }
}

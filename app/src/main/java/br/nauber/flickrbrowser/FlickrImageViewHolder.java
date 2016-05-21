package br.nauber.flickrbrowser;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by naubergois on 5/15/16.
 */
public class FlickrImageViewHolder extends RecyclerView.ViewHolder {

    protected SimpleDraweeView thumbnail;
    protected TextView title;
    protected TextView tags;

    public FlickrImageViewHolder(View view) {
        super(view);
        this.thumbnail = (SimpleDraweeView) view.findViewById(R.id.thumbnail);
        //this.title = (TextView) view.findViewById(R.id.title);
//        this.tags=(TextView) view.findViewById(R.id.tags);

    }
}

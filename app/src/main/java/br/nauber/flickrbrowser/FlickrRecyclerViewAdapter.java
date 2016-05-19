package br.nauber.flickrbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by naubergois on 5/15/16.
 */
public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrImageViewHolder> {
    private List<Photo> photoList;
    private Context mContext;

    public FlickrRecyclerViewAdapter(List<Photo> photoList, Context mContext) {
        this.photoList = photoList;
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(FlickrImageViewHolder holder, int position) {
        Photo item = photoList.get(position);
        Picasso.with(mContext).load(item.getmImage()).error(R.drawable.placeholder).into(holder.thumbnail);


        holder.title.setText(item.getmTitle());
        holder.title.setBackground(holder.thumbnail.getDrawable());
//        holder.tags.setText(item.getmTags());

    }

    @Override
    public int getItemCount() {
        return (null != photoList ? photoList.size() : 0);
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);
        FlickrImageViewHolder flickrImageViewHolder = new FlickrImageViewHolder(view);

        return flickrImageViewHolder;
    }


    public void loadNewData(List<Photo> photos) {
        this.photoList = photos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position) {
        return (photoList != null ? photoList.get(position) : null);
    }
}

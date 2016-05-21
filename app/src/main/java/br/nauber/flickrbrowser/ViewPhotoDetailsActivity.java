package br.nauber.flickrbrowser;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;


public class ViewPhotoDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo_details);

        Toolbar toolbar = activateToolBar();
        setUpDrawer();



        Photo photo = (Photo) getIntent().getSerializableExtra(PHOTO_TRANSFER);

        if (photo != null) {

            TextView photoTitle = (TextView) findViewById(R.id.photo_title);
            photoTitle.setText(photo.getmTitle());

            TextView photoTags = (TextView) findViewById(R.id.photo_tags);
            photoTags.setText(photo.getmTags());


            TextView photoAuthor = (TextView) findViewById(R.id.photo_autor);
            photoAuthor.setText(photo.getmAuthor());




            Uri uri = Uri.parse(photo.getmImage());
            SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.photo_image);
            draweeView.setImageURI(uri);

        }


    }

}

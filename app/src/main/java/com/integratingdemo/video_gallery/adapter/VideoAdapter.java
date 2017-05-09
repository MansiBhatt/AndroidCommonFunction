package com.integratingdemo.video_gallery.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.integratingdemo.R;
import com.integratingdemo.video_gallery.VideoFullScreenActivity;

import java.util.ArrayList;

/**
 * Created by Janki on 23-01-2017.
 */

public class VideoAdapter extends BaseAdapter {
    private Context mcontext;
    private ArrayList<String> list;
    EasyVideoPlayer player;

    public VideoAdapter(Context context, ArrayList<String> videolist) {
        this.mcontext = context;
        this.list = videolist;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private static class Holder {
        private ImageView ivthumbnail;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Holder viewHolder;
        try {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.gridrow_video, null);
                viewHolder = new Holder();
                viewHolder.ivthumbnail = (ImageView) view.findViewById(R.id.iv_thumbnail);
                view.setTag(viewHolder);
            } else {

                viewHolder = (Holder) view.getTag();


            }

            Bitmap bmThumbnail;
            bmThumbnail = ThumbnailUtils.createVideoThumbnail(list.get(i), MediaStore.Video.Thumbnails.MICRO_KIND);
            viewHolder.ivthumbnail.setImageBitmap(getResizedBitmap(bmThumbnail, 200, 200));
            viewHolder.ivthumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, VideoFullScreenActivity.class);
                    intent.putExtra("path", list.get(i));
                    mcontext.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }
        return view;
    }

    private Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        // GET CURRENT SIZE
        int width = bm.getWidth();
        int height = bm.getHeight();
        // GET SCALE SIZE
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }


}

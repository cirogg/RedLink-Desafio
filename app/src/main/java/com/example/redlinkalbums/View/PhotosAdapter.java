package com.example.redlinkalbums.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.redlinkalbums.Model.PhotoAlbum;
import com.example.redlinkalbums.R;

import java.util.ArrayList;
import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<PhotoAlbum> photosArray;


    public PhotosAdapter(Context context) {
        this.context = context;
        this.photosArray = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cell = inflater.inflate(R.layout.cell_album_photos,parent,false);
        ViewHolderPhoto viewHolderPhoto = new ViewHolderPhoto(cell);

        return viewHolderPhoto;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PhotoAlbum photoAlbum = photosArray.get(position);
        ViewHolderPhoto viewHolderAlbum = (ViewHolderPhoto) holder;
        viewHolderAlbum.setDataPhoto(photoAlbum);
    }

    @Override
    public int getItemCount() {
        return photosArray.size();
    }

    private class ViewHolderPhoto extends RecyclerView.ViewHolder{

        TextView albumID;
        TextView albumTitle;
        ImageView imageViewPhoto;

        public ViewHolderPhoto(@NonNull View itemView) {
            super(itemView);

            albumID = itemView.findViewById(R.id.textViewIdAlbum);
            albumTitle = itemView.findViewById(R.id.textViewTitleAlbum);
            imageViewPhoto = itemView.findViewById(R.id.imageViewPhotoAlbum);

        }

        public void setDataPhoto(PhotoAlbum photoAlbum){
            albumID.setText(String.valueOf(photoAlbum.getId()));
            albumTitle.setText(photoAlbum.getTitle());

            Glide.with(context).load(photoAlbum.getThumbnailUrl()).into(imageViewPhoto);

        }
    }

    public void setPhotosArray(List<PhotoAlbum> list){
        this.photosArray = list;
    }
}

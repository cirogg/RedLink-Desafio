package com.example.redlinkalbums.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redlinkalbums.Model.Album;
import com.example.redlinkalbums.R;

import java.util.ArrayList;
import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter implements Filterable {

    private Context context;
    private List<Album> albumsArray;
    private List<Album> albumsArrayForFilter;

    private AlbumAdapterComunicator albumAdapterComunicator;

    public AlbumsAdapter(Context context, AlbumAdapterComunicator albumAdapterComunicator) {
        this.context = context;
        this.albumsArray = new ArrayList<>();
        this.albumAdapterComunicator = albumAdapterComunicator;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cell = inflater.inflate(R.layout.cell_album,parent,false);
        ViewHolderAlbum viewHolderAlbum = new ViewHolderAlbum(cell);

        return viewHolderAlbum;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Album album = albumsArray.get(position);
        ViewHolderAlbum viewHolderAlbum = (ViewHolderAlbum) holder;
        viewHolderAlbum.setDataAlbums(album);
    }

    @Override
    public int getItemCount() {
        return albumsArray.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        //Por cada letra que se agregue al searchview se va a ejecutar este metodo, utilizando el charsequense para compararse con el name cada pet del array.
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Album> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(albumsArrayForFilter);
            }else{
                String filterString = charSequence.toString().toLowerCase().trim();

                for (Album album : albumsArrayForFilter) {
                    if (album.getTitle().toLowerCase().contains(filterString)){
                        filteredList.add(album);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            albumsArray.clear();
            albumsArray.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
        };

        private class ViewHolderAlbum extends RecyclerView.ViewHolder{

        TextView albumID;
        TextView albumTitle;

        public ViewHolderAlbum(@NonNull View itemView) {
            super(itemView);
            albumID = itemView.findViewById(R.id.textViewIdAlbum);
            albumTitle = itemView.findViewById(R.id.textViewTitleAlbum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    albumAdapterComunicator.albumClicked(albumsArray.get(getAdapterPosition()));
                }
            });
        }

        private void setDataAlbums(Album album){
            albumID.setText(String.valueOf(album.getId()));
            albumTitle.setText(album.getTitle());
        }
    }

    public void setAlbumList(List<Album> list){
        if (list != null){
            this.albumsArray.clear();
            this.albumsArray = list;
            notifyDataSetChanged();
        }
    }

    public void setAlbumListForFilter(List<Album> list){
            this.albumsArrayForFilter = list;
    }

    public interface AlbumAdapterComunicator{
            void albumClicked(Album album);
    }
}

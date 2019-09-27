package com.example.redlinkalbums.View;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.redlinkalbums.Controller.AlbumController;
import com.example.redlinkalbums.Model.PhotoAlbum;
import com.example.redlinkalbums.R;
import com.example.redlinkalbums.Utils.ResultListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotosAlbumFragment extends Fragment {

    public static String ALBUM_ID = "id";

    private RecyclerView recyclerViewPhotos;
    private PhotosAdapter photosAdapter;

    private List<PhotoAlbum> arrayPhotos;

    private FrameLayout frameLayoutProgressbar;

    public PhotosAlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photos_album, container, false);

        Bundle bundle = getArguments();
        Integer id = bundle.getInt(ALBUM_ID);

        frameLayoutProgressbar = view.findViewById(R.id.frameLayoutProgress);

        createRecycler(view);

        AlbumController albumController = new AlbumController(getContext());
        ResultListener<List<PhotoAlbum>> viewListener = new ResultListener<List<PhotoAlbum>>() {
            @Override
            public void finish(List<PhotoAlbum> result) {
                if (result.size() != 0){
                    arrayPhotos = result;
                    photosAdapter.setPhotosArray(arrayPhotos);
                    photosAdapter.notifyDataSetChanged();
                    frameLayoutProgressbar.setVisibility(View.GONE);
                    frameLayoutProgressbar.setClickable(false);
                }
            }
        };

        albumController.getPhotosAlbum(viewListener,id);


        return view;
    }

    private void createRecycler(View view){
        recyclerViewPhotos = view.findViewById(R.id.recyclerViewPhotos);
        photosAdapter = new PhotosAdapter(getContext());
        recyclerViewPhotos.setAdapter(photosAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewPhotos.setLayoutManager(layoutManager);
    }

}

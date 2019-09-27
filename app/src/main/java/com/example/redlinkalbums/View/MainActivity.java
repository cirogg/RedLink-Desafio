package com.example.redlinkalbums.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.redlinkalbums.Controller.AlbumController;
import com.example.redlinkalbums.Model.Album;
import com.example.redlinkalbums.R;
import com.example.redlinkalbums.Utils.ResultListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AlbumsAdapter.AlbumAdapterComunicator {

    public static String ALBUM_ID = "id";

    private RecyclerView rwAlbums;
    private AlbumsAdapter albumsAdapter;
    private List<Album> arrayAlbums;
    private SearchView searchView;

    private FrameLayout frameLayoutContainerPhotos;
    private FrameLayout frameLayoutAlpha;

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayoutContainerPhotos = findViewById(R.id.frameLayoutContainerPhotos);
        frameLayoutAlpha = findViewById(R.id.frameLayoutAlpha);

        frameLayoutAlpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameLayoutAlpha.setVisibility(View.GONE);
                frameLayoutContainerPhotos.setVisibility(View.GONE);
            }
        });

        arrayAlbums = new ArrayList<>();

        createRecycler();
        loadAlbums();
    }

    private void createRecycler(){
        rwAlbums = findViewById(R.id.recyclerViewAlbums);
        albumsAdapter = new AlbumsAdapter(this,this);
        rwAlbums.setAdapter(albumsAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rwAlbums.setLayoutManager(layoutManager);
    }

    private void loadAlbums(){
        AlbumController albumController = new AlbumController(this);
        ResultListener<List<Album>> viewListener = new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> result) {
                if (result.size() != 0){
                    arrayAlbums = result;
                    albumsAdapter.setAlbumList(arrayAlbums);
                    albumsAdapter.setAlbumListForFilter(new ArrayList<Album>(arrayAlbums));
                }
            }
        };
        albumController.getAllAlbums(viewListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);
        searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                albumsAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void albumClicked(Album album) {
        PhotosAlbumFragment photosAlbumFragment = new PhotosAlbumFragment();
        loadPhotoFragment(photosAlbumFragment,album.getId());
    }

    private void loadPhotoFragment(Fragment fragment, Integer id){
        frameLayoutContainerPhotos.setVisibility(View.VISIBLE);
        frameLayoutAlpha.setVisibility(View.VISIBLE);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutContainerPhotos,fragment);
        Bundle bundle = new Bundle();
        bundle.putInt(ALBUM_ID,id);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

        if (frameLayoutAlpha.getVisibility() == View.VISIBLE){
            frameLayoutAlpha.setVisibility(View.GONE);
            frameLayoutContainerPhotos.setVisibility(View.GONE);
        }else{
            super.onBackPressed();
        }

    }
}

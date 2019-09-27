package com.example.redlinkalbums.Controller;

import android.content.Context;

import com.example.redlinkalbums.Model.Album;
import com.example.redlinkalbums.Model.DAOAlbum;
import com.example.redlinkalbums.Model.PhotoAlbum;
import com.example.redlinkalbums.Utils.ResultListener;

import java.util.List;

public class AlbumController {

    private Context context;

    public AlbumController(Context context) {
        this.context = context;
    }


    //Metodo para traer todos los albums
    public void getAllAlbums(final ResultListener<List<Album>> viewListener){

        ResultListener<List<Album>> controllerListener = new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> result) {
                viewListener.finish(result);
            }
        };

        DAOAlbum daoAlbum = new DAOAlbum();
        daoAlbum.getAllAlbums(controllerListener);

    }

    //Metodo para traer fotos segun el album id
    public void getPhotosAlbum(final ResultListener<List<PhotoAlbum>> viewListener,Integer id){

        ResultListener<List<PhotoAlbum>> controllerListener = new ResultListener<List<PhotoAlbum>>() {
            @Override
            public void finish(List<PhotoAlbum> result) {
                viewListener.finish(result);
            }
        };

        DAOAlbum daoAlbum = new DAOAlbum();
        daoAlbum.getAlbumPhotos(controllerListener,id);

    }
}

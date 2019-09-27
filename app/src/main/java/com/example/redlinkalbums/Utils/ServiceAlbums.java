package com.example.redlinkalbums.Utils;

import com.example.redlinkalbums.Model.Album;
import com.example.redlinkalbums.Model.PhotoAlbum;

import java.math.BigInteger;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceAlbums {

    // obtengo por la URL lista de mascotas
    @GET("albums")
    Call<List<Album>> getAllAlbums();

    // obtengo por la URL el id de la mascota
    @GET("photos?")
    Call<List<PhotoAlbum>> getPhotosAlbum(@Query("albumId") Integer id);

}

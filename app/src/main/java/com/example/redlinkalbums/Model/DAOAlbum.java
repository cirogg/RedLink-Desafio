package com.example.redlinkalbums.Model;

import com.example.redlinkalbums.Utils.ResultListener;
import com.example.redlinkalbums.Utils.ServiceAlbums;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DAOAlbum {

    private Retrofit retrofit;
    private ServiceAlbums serviceAlbums;
    private static final String BASEURL = "https://jsonplaceholder.typicode.com/";

    public DAOAlbum(){
        OkHttpClient.Builder httpCLient = new OkHttpClient.Builder();

        //Creo retrofit
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.client(httpCLient.build()).build();
        serviceAlbums = retrofit.create(ServiceAlbums.class);
    }

    //Metodo para traer todos los Albums
    public void getAllAlbums(final ResultListener<List<Album>> controllerListener){
        Call<List<Album>> retrofitCall = serviceAlbums.getAllAlbums();
        retrofitCall.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                List<Album> albumList = response.body();
                controllerListener.finish(albumList);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                controllerListener.finish(new ArrayList<Album>());
            }
        });
    }

    //Metodo para traer fotos segun el album id
    public void getAlbumPhotos(final ResultListener<List<PhotoAlbum>> controllerListener, Integer id){
        Call<List<PhotoAlbum>> retrofitcall = serviceAlbums.getPhotosAlbum(id);
        retrofitcall.enqueue(new Callback<List<PhotoAlbum>>() {
            @Override
            public void onResponse(Call<List<PhotoAlbum>> call, Response<List<PhotoAlbum>> response) {
                List<PhotoAlbum> photoAlbumsList = response.body();
                controllerListener.finish(photoAlbumsList);
            }

            @Override
            public void onFailure(Call<List<PhotoAlbum>> call, Throwable t) {
                controllerListener.finish(new ArrayList<PhotoAlbum>());
            }
        });
    }

}

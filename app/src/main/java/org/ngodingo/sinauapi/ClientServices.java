package org.ngodingo.sinauapi;

import org.ngodingo.sinauapi.model.BeritaIndonesiaModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClientServices {

    @GET("top-headlines")
    Call<BeritaIndonesiaModel> getBerita(@Query("country") String negara,
                                         @Query("apiKey") String api);

}

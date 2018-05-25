package org.ngodingo.sinauapi.model;

import com.google.gson.annotations.SerializedName;

public class BeritaModel {

    @SerializedName("title")
    public String judul;

    @SerializedName("description")
    public String deskripsi;

    @SerializedName("url")
    private String urlBerita;

    @SerializedName("urlToImage")
    private String urlGambar;

    public int idBerita;

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getUrlBerita() {
        return urlBerita;
    }

    public String getUrlGambar() {
        return urlGambar;
    }


}

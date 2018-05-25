package org.ngodingo.sinauapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeritaIndonesiaModel {

    @SerializedName("status")
    private String status;

    @SerializedName("totalResults")
    private int totalHasil;

    @SerializedName("articles")
    private List<BeritaModel> semuaArtikel;

    public List<BeritaModel> getSemuaArtikel() {
        return semuaArtikel;
    }

    public String getStatus() {
        return status;
    }
}

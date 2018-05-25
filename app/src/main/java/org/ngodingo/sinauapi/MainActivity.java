package org.ngodingo.sinauapi;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.ngodingo.sinauapi.adapter.BeritaAdapter;
import org.ngodingo.sinauapi.model.BeritaIndonesiaModel;
import org.ngodingo.sinauapi.model.BeritaModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    List<BeritaModel> beritaList = new ArrayList<>();
    RecyclerView recyclerView;

    ClientServices services;

    BeritaAdapter adapter;
    SQLiteBerita sqLiteBerita;
    Boolean statusdata;

    private static final String TAG_PREF = "status_data";

    SharedPreferences pref;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.rvList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        //membuat objek retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        //mencetak layanan atau service
        services = retrofit.create(ClientServices.class);

        pref = getSharedPreferences( TAG_PREF , MODE_PRIVATE );
        statusdata = pref.getBoolean(TAG_PREF, false);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        getBeritaIndonesia();


    }

    private void getBeritaIndonesia() {
        //membuat objek request untuk menge-get apa yang diinginkan
        Call<BeritaIndonesiaModel> request = services.getBerita("id", "a90246cda57645019081622a8a37800b");

        //mengirimkan request
        request.enqueue(new Callback<BeritaIndonesiaModel>() {
            @Override
            public void onResponse(Call<BeritaIndonesiaModel> call, Response<BeritaIndonesiaModel> response) {

                String success = response.body().getStatus();
                Toast.makeText(MainActivity.this, success, Toast.LENGTH_SHORT).show();

                beritaList = response.body().getSemuaArtikel();
                sqLiteBerita = new SQLiteBerita(MainActivity.this);

                addToSql(sqLiteBerita);
                //buat Adapter yang nantinya akan jadi Adapter RecyclerView nya
                adapter = new BeritaAdapter(MainActivity.this, sqLiteBerita.getData());
//                adapter.notifyDataSetChanged();

                recyclerView.setAdapter(adapter);

                progressBar.setVisibility(View.GONE);

                SharedPreferences sp = getSharedPreferences( TAG_PREF , MODE_PRIVATE );
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean( TAG_PREF, true);
                editor.commit();

                statusdata = true;
            }

            @Override
            public void onFailure(Call<BeritaIndonesiaModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
                if (statusdata) {
                    sqLiteBerita = new SQLiteBerita(MainActivity.this);
                    adapter = new BeritaAdapter(MainActivity.this, sqLiteBerita.getData());
                    adapter.notifyDataSetChanged();

                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                }
            }

        });

    }

    private void addToSql(SQLiteBerita sqLiteBerita) {
        for (int posisi = 0; posisi < beritaList.size(); posisi++) {
            String judul = beritaList.get(posisi).getJudul();
            String deskripsi = beritaList.get(posisi).getDeskripsi();

            sqLiteBerita.addData(judul, deskripsi);
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.menu_item, menu);
//        return super.onCreateOptionsMenu(menu);
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if (item.getItemId() == R.id.navigation_provinsi) {
//            //request provinsi
////            getBeritaIndonesia();
//
//        } else {
//            //request instansi
//        }
//
//        return super.onOptionsItemSelected(item);
//
//
//    }
}

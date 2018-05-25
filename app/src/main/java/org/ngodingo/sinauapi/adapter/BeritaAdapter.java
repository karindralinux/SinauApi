package org.ngodingo.sinauapi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.ngodingo.sinauapi.R;
import org.ngodingo.sinauapi.model.BeritaModel;

import java.util.List;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.BeritaViewHolder> {

    private Context context;
    private List<BeritaModel> beritaList;

    public BeritaAdapter(Context context, List<BeritaModel> beritaList) {
        this.context = context;
        this.beritaList = beritaList;
    }

    @Override
    public BeritaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.berita_item, parent, false);
        return new BeritaViewHolder(view);

    }

    @Override
    public void onBindViewHolder(BeritaViewHolder holder, int position) {

        holder.judulBerita.setText(beritaList.get(position).getJudul());

        holder.deskripsiBerita.setText(beritaList.get(position).getDeskripsi());

        Glide.with(context)
                .load(beritaList.get(position).getUrlGambar())
                .into(holder.imgBerita);


    }

    @Override
    public int getItemCount() {
        return beritaList.size();
    }

    public class BeritaViewHolder extends RecyclerView.ViewHolder {

        TextView judulBerita;
        TextView deskripsiBerita;
        ImageView imgBerita;

        public BeritaViewHolder(View itemView) {
            super(itemView);
            judulBerita = (TextView) itemView.findViewById(R.id.textview_judul_berita);
            imgBerita = (ImageView) itemView.findViewById(R.id.imageview_berita);
            deskripsiBerita = (TextView) itemView.findViewById(R.id.textview_deskripsi_berita);
        }


    }
//
//    public void addToSql() {
//        for (int posisi = 0; posisi <= beritaList.size(); posisi++) {
//            String judul = beritaList.get(posisi).getJudul();
//            String deskripsi = beritaList.get(posisi).getDeskripsi();
//        }
//    }


}

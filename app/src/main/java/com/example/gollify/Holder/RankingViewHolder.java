package com.example.gollify.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.gollify.R;

public class RankingViewHolder extends RecyclerView.ViewHolder {

    public TextView nome, pontos, id;
    public ImageView deleteContact;
    public ImageView editContact;

    public RankingViewHolder(View view) {
        super(view);
        id = view.findViewById(R.id.txtId);
        nome = view.findViewById(R.id.ranking_name);
        pontos = view.findViewById(R.id.ranking_point);
        //deleteContact = view.findViewById(R.id.delete_contact);
        editContact = view.findViewById(R.id.edit_contact);
    }
}

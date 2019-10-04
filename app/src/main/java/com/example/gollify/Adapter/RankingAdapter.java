package com.example.gollify.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.gollify.DataBase.DataBaseHelper;
import com.example.gollify.Holder.RankingViewHolder;
import com.example.gollify.Pojo.Ranking;
import com.example.gollify.R;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Ranking> listRanking;
    private ArrayList<Ranking> mArrayList;
    private DataBaseHelper mDatabase;

    public RankingAdapter(Context context, ArrayList<Ranking> listRanking) {
        this.context = context;
        this.listRanking = listRanking;
        this.mArrayList=listRanking;
        mDatabase = new DataBaseHelper(context);
    }

    @Override
    public RankingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_list_layout, parent, false);
        return new RankingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RankingViewHolder holder, int position) {
        final Ranking rankings = listRanking.get(position);

       // holder.id.setText(Integer.toString(rankings.getId()));
        holder.id.setText(Integer.toString(position+1));
        holder.nome.setText(rankings.getNome());
        holder.pontos.setText(rankings.getPontos());


        /*
        holder.deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database


                AQUI EU ALTEREI 30/07/2019

                mDatabase.deleteContact(rankings.getId());


                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
        */


    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    listRanking = mArrayList;
                } else {

                    ArrayList<Ranking> filteredList = new ArrayList<>();

                    for (Ranking ranking : mArrayList) {

                        if (ranking.getNome().toLowerCase().contains(charString)) {

                            filteredList.add(ranking);
                        }
                    }

                    listRanking = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listRanking;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listRanking = (ArrayList<Ranking>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return listRanking.size();
    }





}
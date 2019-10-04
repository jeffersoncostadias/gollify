package com.example.gollify.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gollify.Adapter.RankingAdapter;
import com.example.gollify.DataBase.DataBaseHelper;
import com.example.gollify.Pojo.Ranking;
import com.example.gollify.R;

import java.util.ArrayList;


public class RankingActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    DataBaseHelper mDatabase;
    private ArrayList<Ranking> allUsuarios =new ArrayList<>();
    private RankingAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tela Ranking");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CoordinatorLayout fLayout =  findViewById(R.id.activity_to_do);

        RecyclerView rankingView = findViewById(R.id.ranking_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rankingView.setLayoutManager(linearLayoutManager);
        rankingView.setHasFixedSize(true);
        mDatabase = new DataBaseHelper(this);
        allUsuarios = mDatabase.listUsuarios();

        if(allUsuarios.size() > 0){
            rankingView.setVisibility(View.VISIBLE);
            mAdapter = new RankingAdapter(this, allUsuarios);
            rankingView.setAdapter(mAdapter);

        }else {
            rankingView.setVisibility(View.GONE);
            Toast.makeText(this, "There is no contact in the database. Start adding now", Toast.LENGTH_LONG).show();
        }


        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskDialog();
            }
        });
        */

    } // FIM ONCREATE


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_ranking, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Id correspondente ao botão Up/Home da actionbar
             case android.R.id.home:
                 Intent intent = new Intent();
                 intent.putExtra("msg", "Lição Finalizada");
                 setResult(1, intent);
                 finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mAdapter!=null)
                    mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }


}


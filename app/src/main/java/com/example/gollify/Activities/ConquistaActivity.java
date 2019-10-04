package com.example.gollify.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gollify.DataBase.DataBaseHelper;
import com.example.gollify.R;

public class ConquistaActivity extends AppCompatActivity {

    LinearLayout layExtra1, layExtra2, layExtra3;
    DataBaseHelper myDatabase;
    String nome, id;
    TextView txtMoedas, txtPontosConq, txtNomeConquista;
    ProgressBar progBarAtiv;
    RatingBar ratingBarNivel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conquista);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tela Conquistas");
        setSupportActionBar(toolbar);
        myDatabase = new DataBaseHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        obterValoresDeMainActivity();

        layExtra1 = findViewById(R.id.layExtra1);
        layExtra2 = findViewById(R.id.layExtra2);
        layExtra3 = findViewById(R.id.layExtra3);
        txtNomeConquista =  findViewById(R.id.txtNomeConquista);
        progBarAtiv =  findViewById(R.id.progBarAtiv);
        ratingBarNivel = findViewById(R.id.ratingBarNivel);
        txtMoedas = findViewById(R.id.txtMoedas);
        txtPontosConq = findViewById(R.id.txtPontosConq);

       // ratingBarNivel.setRating(0);
        txtNomeConquista.setText(obterNomeUsuario());
        mostrar();
        ratingBarNivel.setRating(Float.parseFloat(obterNivelUsuario()));
        progBarAtiv.setProgress(Integer.parseInt(obterProgressoUsuario()));
        txtMoedas.setText(obterMoedasUsuario());
        txtPontosConq.setText(obterPontosUsuario());


    }// FIM ON CREATE

   public void mostrar(){
        int op = Integer.parseInt(consultarConquista());
       switch (op) {
           case 0:
               layExtra1.setVisibility(View.INVISIBLE);
               layExtra2.setVisibility(View.INVISIBLE);
               layExtra3.setVisibility(View.INVISIBLE);

               break;

           case 1:
               layExtra1.setVisibility(View.VISIBLE);
               layExtra2.setVisibility(View.INVISIBLE);
               layExtra3.setVisibility(View.INVISIBLE);
               break;

           case 2:
               layExtra1.setVisibility(View.VISIBLE);
               layExtra2.setVisibility(View.VISIBLE);
               layExtra3.setVisibility(View.INVISIBLE);
               break;

           case 3:
               layExtra1.setVisibility(View.VISIBLE);
               layExtra2.setVisibility(View.VISIBLE);
               layExtra3.setVisibility(View.VISIBLE);
               break;

       }
   }

    public void obterValoresDeMainActivity(){
        //OBTER DA MAIN ACTIVITY OS PARAMETROS ID USUÁRIO
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        nome = intent.getStringExtra("NOME");
        Log.i("Tela Amizade ID: ", id);
        Log.i("Tela Amizade NOME: ", nome);
    } // FIM


    public  String obterIdUsuario(){
        // String id = "1";
        Cursor res = myDatabase.obterDadosByNome(nome);
        Log.i("Usuario por DB NOME", nome);
        String data = null;
        if (res.moveToFirst()) {
            data = res.getString(0);
            //txtPontos.setText(data);
        }
        Log.i("Usuario por DB ID", data);
        return data;
    }



    public  String obterNomeUsuario(){
        // String id = "1";
        Cursor res = myDatabase.obterDadosByNome(nome);
        // Log.i("Metodo obter usuario", nome);
        String data = null;
        if (res.moveToFirst()) {
            data = res.getString(1);
            //txtPontos.setText(data);
        }
        //Log.i("Tela MainActivity ID", data);
        return data.toUpperCase();
    }


    public  String obterPontosUsuario(){
        // String id = "1";
        Cursor res = myDatabase.obterDadosByNome(nome);
        // Log.i("Metodo obter usuario", nome);
        String data = null;
        if (res.moveToFirst()) {
            data = res.getString(3);
            //txtPontos.setText(data);
        }
        //Log.i("Tela MainActivity ID", data);
        return data;
    }


    public  String obterMoedasUsuario(){
        // String id = "1";
        Cursor res = myDatabase.obterDadosByNome(nome);
        // Log.i("Metodo obter usuario", nome);
        String data = null;
        if (res.moveToFirst()) {
            data = res.getString(4);
            //txtPontos.setText(data);
        }
        //Log.i("Tela MainActivity ID", data);
        return data;
    }


    public  String obterNivelUsuario(){
        // String id = "1";
        Cursor res = myDatabase.obterDadosByNome(nome);
        // Log.i("Metodo obter usuario", nome);
        String data = null;
        if (res.moveToFirst()) {
            data = res.getString(5);
            //txtPontos.setText(data);
        }
        Log.i("Tela Conquista Nivel","Aqui"+ data);
        return data;
    }


    public  String obterProgressoUsuario(){
        // String id = "1";
        Cursor res = myDatabase.obterDadosByNome(nome);
        // Log.i("Metodo obter usuario", nome);
        String data = null;
        if (res.moveToFirst()) {
            data = res.getString(6);
            //txtPontos.setText(data);
        }
        //Log.i("Tela MainActivity ID", data);
        return data;
    }


    public  String consultarConquista(){
        // String id = "1";
        Cursor res = myDatabase.obterDadosByNome(nome);
        // Log.i("Metodo obter usuario", nome);
        String data = null;
        if (res.moveToFirst()) {
            data = res.getString(7);
            //txtPontos.setText(data);
        }
        //Log.i("Tela MainActivity ID", data);
        return data;
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

}

package com.example.gollify.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gollify.DataBase.DataBaseHelper;
import com.example.gollify.R;

import java.util.ArrayList;
import java.util.Collections;

public class Extra1Activity extends AppCompatActivity {
    private static final Integer MAX_BOTOES = 6;
    private View btn[] = new View[MAX_BOTOES];
    Context context;
    DataBaseHelper myDatabase;
    private Integer numerosAcertos;
    ArrayList<Integer> quantidadeElementos;

    private String nome, senha,id;
   // private ConstraintLayout scrollView;
    private ScrollView scrollView;

    private ProgressBar barraProgresso;
    private TextView txtBarra;

    private TextView acertou1;
    private TextView acertou2;
    private Button btnConcluir;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra1);
        myDatabase = new DataBaseHelper(this);
        scrollView = findViewById(R.id.main);

        quantidadeElementos = new ArrayList<>();
        context = getBaseContext();
        barraProgresso =  findViewById(R.id.progressBar);
        txtBarra = findViewById(R.id.progressBarView);

        acertou1 = findViewById(R.id.textViewPara1);
        acertou2 = findViewById(R.id.textViewPara2);
        btnConcluir = findViewById(R.id.btnConcluirExtra1);

        for (Integer i = 0; i < MAX_BOTOES; i++){
            btn[i] = findViewById(getResources().getIdentifier("button" + String.valueOf(i+1), "id", context.getPackageName()));
        }
        obterValoresDeMainActivity();
        reiniciarJogo();
    } // FIM

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void mudarView(View view){
        if(view.getId() != btn[quantidadeElementos.get(numerosAcertos)].getId())
            apagarBotoes();
        else {
            view.setVisibility(View.INVISIBLE);
            try {
                scrollView.setBackgroundColor(view.getBackgroundTintList().getDefaultColor());
            }catch (NullPointerException e){
                scrollView.setBackgroundColor(Color.WHITE);
            }
            numerosAcertos++;
            atualizarViewBar(numerosAcertos);
        }
    }


    public void apagarBotoes(){
        for(Integer i=0; i < MAX_BOTOES; i++)
            btn[i].setVisibility(View.VISIBLE);
        numerosAcertos = 0;
        atualizarViewBar(numerosAcertos);
        scrollView.setBackgroundColor(Color.WHITE);
    }

    public void atualizarViewBar(Integer acertos){
        barraProgresso.setProgress(acertos);
        txtBarra.setText(acertos+"/"+MAX_BOTOES);

        if(acertos.equals(MAX_BOTOES))
            concluirJogo();
    }

    public void concluirJogo(){
        txtBarra.setVisibility(View.INVISIBLE);
        barraProgresso.setVisibility(View.INVISIBLE);
        acertou1.setVisibility(View.VISIBLE);
        acertou2.setVisibility(View.VISIBLE);
        //acertou2.setVisibility(View.VISIBLE);
        btnConcluir.setVisibility(View.VISIBLE);
    }

    public void sortear(){
        quantidadeElementos.clear();
        for(Integer i=0; i < MAX_BOTOES; i++)
            quantidadeElementos.add(i);
        Collections.shuffle(quantidadeElementos);

        for(Integer i=0; i < MAX_BOTOES; i++)
            System.out.println(quantidadeElementos.get(i));
    }

    public void reiniciarJogo(){
        txtBarra.setVisibility(View.VISIBLE);
        barraProgresso.setVisibility(View.VISIBLE);
        acertou1.setVisibility(View.INVISIBLE);
        acertou2.setVisibility(View.INVISIBLE);
        btnConcluir.setVisibility(View.INVISIBLE);
        sortear();
        apagarBotoes();
    }

    public void restart(View view){
        reiniciarJogo();
    }

    // MÉTODO QUE GRAVA O PROGRESSO NO BANCO DE DADOS
    public void gravarProgresso() {
        Cursor cur = myDatabase.existemDados();
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {

                // Tabela esta vazia, preencha com seus dados iniciais
                boolean isInserted = myDatabase.inserirDadosProgesso("40");
                if (isInserted == true) {
                    Toast.makeText(this, "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarProgresso(id, "40");
                if (isUpdate == true) {
                    //Toast.makeText(this, "Dados atualizados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser atualizados", Toast.LENGTH_LONG).show();
                }
            }
        }

    } // FIM



    public void obterValoresDeMainActivity(){
        //OBTER DA MAIN ACTIVITY OS PARAMETROS ID USUÁRIO
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        nome = intent.getStringExtra("NOME");
        // Log.i("Tela Cardinal ID: ", id);
        //Log.i("Tela Cardinal NOME: ", nome);
        //Log.i("NOME SHAREDPREFERENCE:", nome.concat("_act3"));
        //Log.i("ValorSharedPreferences:", lerArquivoPontos());

    }// FIM

    public void concluir(View view){
        Intent intent = new Intent();
        intent.putExtra("msg", "Lição Finalizada");
        setResult(1, intent);
        gravarProgresso();
        finish();
    }


}


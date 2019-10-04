package com.example.gollify.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gollify.DataBase.DataBaseHelper;
import com.example.gollify.R;

public class ObjetosActivity extends AppCompatActivity implements View.OnClickListener{
    DataBaseHelper myDatabase;
    private String nome, senha,id;
    public  String existeSPrefer = "DefaultPontos";
    private boolean foiClicado[] = new boolean[6];
    private Button bt3Concluir;
    private ImageButton btnBoot, btnComb, btnCoat, btnCrib, btnHanger, btnHat;
    public int pontos;
    private TextView txtPontos, txtBoot, txtComb, txtCoat, txtCrib, txtHanger, txtHat;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetos);
        myDatabase = new DataBaseHelper(this);
        txtPontos = findViewById(R.id.txtPontos);

        btnBoot = findViewById(R.id.btnBoot);
        btnComb = findViewById(R.id.btnComb);
        btnCoat = findViewById(R.id.btnCoat);
        btnCrib = findViewById(R.id.btnCrib);
        btnHanger = findViewById(R.id.btnHanger);
        btnHat = findViewById(R.id.btnHat);

        bt3Concluir = findViewById(R.id.bt3);
        txtBoot = findViewById(R.id.txtBoot);
        txtComb = findViewById(R.id.txtComb);
        txtCoat = findViewById(R.id.txtCoat);
        txtCrib = findViewById(R.id.txtCrib);
        txtHanger = findViewById(R.id.txtHanger);
        txtHat = findViewById(R.id.txtHat);

        btnBoot.setOnClickListener(this);
        btnComb.setOnClickListener(this);
        btnCoat.setOnClickListener(this);
        btnCrib.setOnClickListener(this);
        btnHanger.setOnClickListener(this);
        btnHat.setOnClickListener(this);

        inicializaVetores();

        obterValoresDeMainActivity();

        verificaSPPontos();
        verificaSPBotao();

        mostrarDadosTxtPontos();
        checarBotãoConcluir();
        mostrarInformacao();

    } // FIM MÉTODO ON CREATE

    public void mostrarInformacao(){

        View toastView = getLayoutInflater().inflate(R.layout.toast_custom_a, null);
        Toast toast = new Toast(getApplicationContext());
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0,180);
        toast.show();

    }

    public void checarBotãoConcluir(){
        String opA = "0";
        String opB = lerArquivoBotaoConcluir();
        Log.i("Checar Botoes Inicio: ", String.valueOf(checarTodosBotões()));
        if (opA.equals(opB)) {
            bt3Concluir.setVisibility( View.INVISIBLE);
            txtBoot.setVisibility(View.INVISIBLE);
            txtComb.setVisibility(View.INVISIBLE);
            txtCoat.setVisibility(View.INVISIBLE);
            txtCrib.setVisibility(View.INVISIBLE);
            txtHanger.setVisibility(View.INVISIBLE);
            txtHat.setVisibility(View.INVISIBLE);
        }else{
            bt3Concluir.setVisibility( View.VISIBLE);
            txtBoot.setVisibility(View.VISIBLE);
            txtComb.setVisibility(View.VISIBLE);
            txtCoat.setVisibility(View.VISIBLE);
            txtCrib.setVisibility(View.VISIBLE);
            txtHanger.setVisibility(View.VISIBLE);
            txtHat.setVisibility(View.VISIBLE);
        }
    }

    public void verificaSPBotao(){
        if(lerArquivoBotaoConcluir().equals(existeSPrefer)){
            salvarArquivoBotaoConcluir("0");
            Log.i("VERIFICA BOTAO CONCLUIR", "é igual"+lerArquivoBotaoConcluir() );

        }else {
            Log.i("NÃO É VERDADE BOTÃO", "Não é igual "+lerArquivoBotaoConcluir() );
        }
    }

    // MÉTODO CRIAR SHARED PREFERENCES PRA SALVAR O STATUS DE BOTÃO CONCLUIR
    public void salvarArquivoBotaoConcluir(String statusBotao) {
        String statusStringBotao = statusBotao;
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_bt3"), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("statusBooleanPoint", statusBooleanPoint);
        editor.putString("statusStringPontos", statusStringBotao);
        editor.commit();
    }  // FIM

    // MÉTODO CRIAR SHARED PREFERENCES PRA LER O STATUS DE BOTÃO CONCLUIR
    public String lerArquivoBotaoConcluir() {
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_bt3"), Context.MODE_PRIVATE);
        String defaultPontos = "DefaultPontos";
        String statusFinal = sharedPreferences.getString("statusStringPontos", defaultPontos);
        Log.i("Ler Arquivos: ", statusFinal);
        return statusFinal;
    }  // FIM




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

    public void inicializaVetores(){
        // INICIALIZAR O  ARRAY COM O VALOR FALSE
        for (int cont = 0; cont < 6; cont++) {
            foiClicado[cont] = false;
            Log.i("Foi clicado: " + cont + " |", String.valueOf(foiClicado[cont]));
        } // FIM
    }


    public void verificaSPPontos(){
        if(lerArquivoPontos().equals(existeSPrefer)){
            salvarArquivoPontos("0");
            Log.i("VERDADE", "é igual" );
        }else {
            Log.i("NÃO É VERDADE", "Não é igual" );
        }
    }



    // MÉTODO CRIAR SHARED PREFERENCES PRA SALVAR O STATUS DE PONTOS
    public void salvarArquivoPontos(String pontos) {
        String statusStringPontos = pontos;
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_act3"), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("statusBooleanPoint", statusBooleanPoint);
        editor.putString("statusStringPontos", statusStringPontos);
        editor.commit();
    }  // FIM


    // MÉTODO CRIAR SHARED PREFERENCES PRA LER O STATUS DE PONTOS
    public String lerArquivoPontos() {
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_act3"), Context.MODE_PRIVATE);
        String defaultPontos = "DefaultPontos";
        String statusFinal = sharedPreferences.getString("statusStringPontos", defaultPontos);
        Log.i("Ler Arquivos: ", statusFinal);
        return statusFinal;
    }  // FIM



    // METODO MOSTRAR OS DADOS OBTIDOS PELO ID DO USUÁRIO EM UMA DIALOG
    public  void mostrarDadosEmDialog(){
        // String id = "1";
        Cursor res = myDatabase.obterDados(id);
        String data = null;
        if (res.moveToFirst()) {
            data = "Id:"+res.getString(0)+"\n"+
                    "Pontos :"+ res.getString(3)+"\n\n";
        }
        showMessage("Pontos", data);
    }// FIM


    // MÉTODO QUE MOSTRA NO TXT OS PONTOS OBTIDOS PELO USUÁRIO
    public  void mostrarDadosTxtPontos(){
        // String id = "1";
        Cursor res = myDatabase.obterDados(id);
        String data = null;
        if (res.moveToFirst()) {
            data = res.getString(3);
            txtPontos.setText(data);
        }
    } // FIM


    // MÉTODO QUE GRAVA OS PONTOS NO BANCO DE DADOS
    public  void gravarPontos(){
        Cursor cur = myDatabase.existemDados();
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt (0) == 0) {

                // Tabela esta vazia, preencha com seus dados iniciais
                boolean isInserted = myDatabase.inserirDadosPontos(txtPontos.getText().toString());
                if(isInserted == true){
                    Toast.makeText(this,"Dados inseridos",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this,"Dados não podem ser inseridos",Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate= myDatabase.atualizarPontos(id, txtPontos.getText().toString());
                if(isUpdate == true){
                    //Toast.makeText(this,"Dados atualizados",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "Dados não podem ser atualizados", Toast.LENGTH_LONG).show();
                }
            }
        }

    }


    // MÉTODO QUE GRAVA AS MOEDAS NO BANCO DE DADOS
    public void gravarMoedas() {
        Cursor cur = myDatabase.existemDados();
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {

                // Tabela esta vazia, preencha com seus dados iniciais
                boolean isInserted = myDatabase.inserirDadosMoedas("900");
                if (isInserted == true) {
                    Toast.makeText(this, "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarMoedas(id, "900");
                if (isUpdate == true) {
                    //Toast.makeText(this, "Dados atualizados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser atualizados", Toast.LENGTH_LONG).show();
                }
            }
        }

    } // FIM

    // MÉTODO QUE GRAVA O PROGRESSO NO BANCO DE DADOS
    public void gravarProgresso() {
        Cursor cur = myDatabase.existemDados();
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {

                // Tabela esta vazia, preencha com seus dados iniciais
                boolean isInserted = myDatabase.inserirDadosProgesso("30");
                if (isInserted == true) {
                    Toast.makeText(this, "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarProgresso(id, "30");
                if (isUpdate == true) {
                    //Toast.makeText(this, "Dados atualizados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser atualizados", Toast.LENGTH_LONG).show();
                }
            }
        }

    } // FIM

    // MÉTODO QUE GRAVA O NÍVEL NO BANCO DE DADOS
    public void gravarNivel() {
        Cursor cur = myDatabase.existemDados();
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {

                // Tabela esta vazia, preencha com seus dados iniciais
                boolean isInserted = myDatabase.inserirDadosNivel("2");
                if (isInserted == true) {
                    Toast.makeText(this, "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarNivel(id, "2");
                if (isUpdate == true) {
                    //Toast.makeText(this, "Dados atualizados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser atualizados", Toast.LENGTH_LONG).show();
                }
            }
        }

    } // FIM

    // MÉTODO QUE GRAVA O STATUS CARDVIEWS NO BANCO DE DADOS
    public void gravarStatusCardViews() {
        Cursor cur = myDatabase.existemDados();
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {

                // Tabela esta vazia, preencha com seus dados iniciais
                boolean isInserted = myDatabase.inserirDadosCardViews("5");
                if (isInserted == true) {
                    // Toast.makeText(this, "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarCardViews(id, "5");
                if (isUpdate == true) {
                    //Toast.makeText(this, "Dados atualizados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser atualizados", Toast.LENGTH_LONG).show();
                }
            }
        }

    } // FIM





    // MÉTODO PARA SOMAR 100 PONTOS
    public void somarpontosMaximo() {
        int soma = Integer.parseInt(txtPontos.getText().toString());
        pontos = soma;
        pontos = pontos + 100;
        txtPontos.setText(String.valueOf(pontos));
    }// FIM


    // MÉTODO PARA SOMAR 10 PONTOS
    public void somarpontosMinimo() {
        int soma = Integer.parseInt(txtPontos.getText().toString());
        pontos = soma;
        pontos = pontos + 10;
        txtPontos.setText(String.valueOf(pontos));
    }//FIM


    // MÉTODO PARA MOSTRAR POR PARAMETRO DADOS NUMA DIALOG
    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }// FIM


    @SuppressLint("LongLogTag")
    @Override
    public void onClick(View v) {
        // salvarArquivoPontos("0");
        String opA = "0";
        String opB = lerArquivoPontos();
        Log.i("Checar Botoes Inicio: "+v.getId()+"|", String.valueOf(checarTodosBotões()));
        if (opA.equals(opB)) {
            switch (v.getId()) {

                case R.id.btnBoot:
                    foiClicado[0] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.boot);
                    ouvirSomObjetos();
                    somarpontosMaximo();
                    gravarPontos();
                    //  mostrarDadosEmDialog();
                    break;

                case R.id.btnComb:
                    foiClicado[1] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.comb);
                    ouvirSomObjetos();
                    somarpontosMaximo();
                    gravarPontos();
                    //  mostrarDadosEmDialog();
                    break;

                case R.id.btnCoat:
                    foiClicado[2] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.coat);
                    ouvirSomObjetos();
                    somarpontosMaximo();
                    gravarPontos();
                    //  mostrarDadosEmDialog();
                    break;
                case R.id.btnCrib:
                    foiClicado[3] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.crib);
                    ouvirSomObjetos();
                    somarpontosMaximo();
                    gravarPontos();
                    //  mostrarDadosEmDialog();
                    break;

                case R.id.btnHanger:
                    foiClicado[4] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.hanger);
                    ouvirSomObjetos();
                    somarpontosMaximo();
                    gravarPontos();
                    // mostrarDadosEmDialog();
                    break;

                case R.id.btnHat:
                    foiClicado[5] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.hat);
                    ouvirSomObjetos();
                    somarpontosMaximo();
                    gravarPontos();
                    break;
            }

        } else {
            switch (v.getId()) {
                case R.id.btnBoot:
                    mediaPlayer = MediaPlayer.create(this, R.raw.boot);
                    ouvirSomObjetos();
                    somarpontosMinimo();
                    gravarPontos();
                   // mostrarDadosEmDialog();
                    break;

                case R.id.btnComb:
                    mediaPlayer = MediaPlayer.create(this, R.raw.comb);
                    ouvirSomObjetos();
                    somarpontosMinimo();
                    gravarPontos();
                    //mostrarDadosEmDialog();
                    break;

                case R.id.btnCoat:
                    mediaPlayer = MediaPlayer.create(this, R.raw.coat);
                    ouvirSomObjetos();
                    somarpontosMinimo();
                    gravarPontos();
                   // mostrarDadosEmDialog();
                    break;
                case R.id.btnCrib:
                    mediaPlayer = MediaPlayer.create(this, R.raw.crib);
                    ouvirSomObjetos();
                    somarpontosMinimo();
                    gravarPontos();
                  //  mostrarDadosEmDialog();
                    break;

                case R.id.btnHanger:
                    mediaPlayer = MediaPlayer.create(this, R.raw.hanger);
                    ouvirSomObjetos();
                    somarpontosMinimo();
                    gravarPontos();
                   // mostrarDadosEmDialog();
                    break;

                case R.id.btnHat:
                    mediaPlayer = MediaPlayer.create(this, R.raw.hat);
                    ouvirSomObjetos();
                    somarpontosMinimo();
                    gravarPontos();

                   // mostrarDadosEmDialog();
                    break;

            }
         }
        if(checarTodosBotões()){
            salvarArquivoPontos("1");
            salvarArquivoBotaoConcluir("1");
            gravarMoedas();
            gravarProgresso();
            gravarNivel();
            gravarStatusCardViews();
            bt3Concluir.setVisibility( View.VISIBLE);
            txtBoot.setVisibility(View.VISIBLE);
            txtComb.setVisibility(View.VISIBLE);
            txtCoat.setVisibility(View.VISIBLE);
            txtCrib.setVisibility(View.VISIBLE);
            txtHanger.setVisibility(View.VISIBLE);
            txtHat.setVisibility(View.VISIBLE);
        }
        Log.i("Checar Botoes Fim"+"ObjetosActivity:"+v.getId()+"|", String.valueOf(checarTodosBotões()));
    }



    // MÉTODO PARA CHECAR SE TODOS OS BOTÕES FORAM CLICADOS, SE SIM OS PONTOS PASSAM A VALER 10
    public Boolean checarTodosBotões(){
        boolean checado = false;
        for (int cont = 0; cont < 6; cont++) {
            if (foiClicado[cont] == true){
                checado = true;
            } else{
                checado = false;
            }
            // Log.i("Foi clicado: " + cont + " |", String.valueOf(foiClicado[cont]));
        } //FOR

        return checado;
    } // FIM



    public void ouvirSomObjetos(){
        if(mediaPlayer != null){
            mediaPlayer.start();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.release();
                }
            });
        }

    }


    public void concluir(View view){
        Intent intent = new Intent();
        intent.putExtra("msg", "Lição Finalizada");
        setResult(1, intent);
        finish();
    }

}

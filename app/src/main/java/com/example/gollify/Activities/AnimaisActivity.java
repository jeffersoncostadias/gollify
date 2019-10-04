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

public class AnimaisActivity extends AppCompatActivity implements View.OnClickListener {
    DataBaseHelper myDatabase;
    private String nome, senha, id;
    public  String existeSPrefer = "DefaultPontos";
    private boolean foiClicado[] = new boolean[6];
    private Button bt1Concluir;
    private ImageButton btnCat, btnCow, btnDog, btnLion, btnMonkey, btnSheep;
    public int pontos;
    private TextView txtPontos, txtCat, txtDog, txtCow, txtLion, txtMonkey, txtSheep;
    private MediaPlayer mediaPlayer, mediaPlayerPontos;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animais);
        myDatabase = new DataBaseHelper(this);
        btnCat = findViewById(R.id.btnCat);
        btnCow = findViewById(R.id.btnCow);
        btnDog = findViewById(R.id.btnDog);
        btnLion = findViewById(R.id.btnLion);
        btnMonkey = findViewById(R.id.btnMonkey);
        btnSheep = findViewById(R.id.btnSheep);
        bt1Concluir = findViewById(R.id.bt1);
        txtPontos = findViewById(R.id.txtPontos);
        txtCat = findViewById(R.id.txtCat);
        txtDog = findViewById(R.id.txtDog);
        txtCow = findViewById(R.id.txtCow);
        txtLion = findViewById(R.id.txtLion);
        txtMonkey = findViewById(R.id.txtMonkey);
        txtSheep = findViewById(R.id.txtSheep);

        btnCat.setOnClickListener(this);
        btnCow.setOnClickListener(this);
        btnDog.setOnClickListener(this);
        btnLion.setOnClickListener(this);
        btnMonkey.setOnClickListener(this);
        btnSheep.setOnClickListener(this);

        inicializaVetores();

        obterValoresDeMainActivity();

        verificaSPPontos();
        verificaSPBotao();

        mostrarDadosTxtPontos();
        checarBotãoConcluir();
        mostrarInformacao();

    } //  FIM MÉTODO ON CREATE


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
            bt1Concluir.setVisibility( View.INVISIBLE);
            txtCat.setVisibility(View.INVISIBLE);
            txtCow.setVisibility(View.INVISIBLE);
            txtDog.setVisibility(View.INVISIBLE);
            txtLion.setVisibility(View.INVISIBLE);
            txtMonkey.setVisibility(View.INVISIBLE);
            txtSheep.setVisibility(View.INVISIBLE);
        }else{
            bt1Concluir.setVisibility( View.VISIBLE);
            txtCat.setVisibility(View.VISIBLE);
            txtCow.setVisibility(View.VISIBLE);
            txtDog.setVisibility(View.VISIBLE);
            txtLion.setVisibility(View.VISIBLE);
            txtMonkey.setVisibility(View.VISIBLE);
            txtSheep.setVisibility(View.VISIBLE);
        }
    }


    public void verificaSPPontos(){
        if(lerArquivoPontoMinimo().equals(existeSPrefer)){
            salvarArquivoPontos("0");
            Log.i("VERDADE", "é igual" );
        }else {
            Log.i("NÃO É VERDADE", "Não é igual" );
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


    // MÉTODO CRIAR SHARED PREFERENCES PRA SALVAR O STATUS DE PONTOS
    public void salvarArquivoPontos(String pontos) {
        String statusStringPontos = pontos;
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_act1"), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("statusBooleanPoint", statusBooleanPoint);
        editor.putString("statusStringPontos", statusStringPontos);
        editor.commit();
    }  // FIM


    // MÉTODO CRIAR SHARED PREFERENCES PRA LER O STATUS DE PONTOS
    public String lerArquivoPontoMinimo() {
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_act1"), Context.MODE_PRIVATE);
        String defaultPontos = "DefaultPontos";
        String statusFinal = sharedPreferences.getString("statusStringPontos", defaultPontos);
        Log.i("Ler Arquivos: ", statusFinal);
        return statusFinal;
    }  // FIM


    // MÉTODO CRIAR SHARED PREFERENCES PRA SALVAR O STATUS DE BOTÃO CONCLUIR
    public void salvarArquivoBotaoConcluir(String statusBotao) {
        String statusStringBotao = statusBotao;
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_bt1"), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("statusBooleanPoint", statusBooleanPoint);
        editor.putString("statusStringPontos", statusStringBotao);
        editor.commit();
    }  // FIM

    // MÉTODO CRIAR SHARED PREFERENCES PRA LER O STATUS DE BOTÃO CONCLUIR
    public String lerArquivoBotaoConcluir() {
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_bt1"), Context.MODE_PRIVATE);
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
        Log.i("Tela Animais ID: ", id);
        Log.i("Tela Animais NOME: ", nome);
        Log.i("NOME SHAREDPREFERENCE:", nome.concat("_act1"));
        Log.i("ValorSharedPreferences:", lerArquivoPontoMinimo());
        // FIM
    }

    public void inicializaVetores(){
        // INICIALIZAR O  ARRAY COM O VALOR FALSE
        for (int cont = 0; cont < 6; cont++) {
            foiClicado[cont] = false;
            Log.i("Foi clicado: " + cont + " |", String.valueOf(foiClicado[cont]));
        } // FIM
    }

    // METODO MOSTRAR OS DADOS OBTIDOS PELO ID DO USUÁRIO EM UMA DIALOG
    public void mostrarDadosEmDialog() {
        // String id = "1";
        Cursor res = myDatabase.obterDados(id);
        String data = null;
        if (res.moveToFirst()) {
            data = "Id:" + res.getString(0) + "\n" +
                    "Pontos :" + res.getString(3) + "\n\n";
        }
        showMessage("Pontos", data);
    } // FIM



   // MÉTODO PARA MOSTRAR NO TXT OS PONTOS OBTIDOS EM CADA ACERTO PELO USUÁRIO
    public void mostrarDadosTxtPontos() {
        // String id = "1";
        Cursor res = myDatabase.obterDados(id);
        String data = null;
        if (res.moveToFirst()) {
            data = res.getString(3);
            txtPontos.setText(data);
        }

    } // FIM


    // MÉTODO QUE GRAVA OS PONTOS NO BANCO DE DADOS
    public void gravarPontos() {
        Cursor cur = myDatabase.existemDados();
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {

                // Tabela esta vazia, preencha com seus dados iniciais
                boolean isInserted = myDatabase.inserirDadosPontos(txtPontos.getText().toString());
                if (isInserted == true) {
                    Toast.makeText(this, "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarPontos(id, txtPontos.getText().toString());
                if (isUpdate == true) {
                    //Toast.makeText(this, "Dados atualizados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser atualizados", Toast.LENGTH_LONG).show();
                }
            }
        }

    } // FIM


    // MÉTODO QUE GRAVA AS MOEDAS NO BANCO DE DADOS
    public void gravarMoedas() {
        Cursor cur = myDatabase.existemDados();
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {

                // Tabela esta vazia, preencha com seus dados iniciais
                boolean isInserted = myDatabase.inserirDadosMoedas("300");
                if (isInserted == true) {
                    Toast.makeText(this, "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarMoedas(id, "300");
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
                boolean isInserted = myDatabase.inserirDadosProgesso("10");
                if (isInserted == true) {
                    Toast.makeText(this, "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarProgresso(id, "10");
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
                boolean isInserted = myDatabase.inserirDadosCardViews("2");
                if (isInserted == true) {
                   // Toast.makeText(this, "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarCardViews(id, "2");
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


    // MÉTODO PARA FINALIZAR A ACTIVITY E RETORNAR UM RESULT
    public void concluir(View view) {
        Intent intent = new Intent();
        intent.putExtra("msg", "Lição Finalizada");
        setResult(1, intent);
        finish();

    }// FIM





    @SuppressLint("LongLogTag")
    @Override
    public void onClick(View v) {
       // salvarArquivoPontos("0");
        String opA = "0";
        String opB = lerArquivoPontoMinimo();
        Log.i("Checar Botoes Inicio: ", String.valueOf(checarTodosBotões()));
        if (opA.equals(opB)) {
            switch (v.getId()) {
                case R.id.btnCat:
                    foiClicado[0] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.cat);
                    ouvirSomAninal();
                    somarpontosMaximo();
                    gravarPontos();
                    //mostrarDadosEmDialog();
                    break;

                case R.id.btnCow:
                    foiClicado[1] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.cow);
                    ouvirSomAninal();
                    somarpontosMaximo();
                    gravarPontos();
                    //mostrarDadosEmDialog();
                    break;

                case R.id.btnDog:
                    foiClicado[2] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.dog);
                    ouvirSomAninal();
                    somarpontosMaximo();
                    gravarPontos();
                    //mostrarDadosEmDialog();
                    break;
                case R.id.btnLion:
                    foiClicado[3] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.lion);
                    ouvirSomAninal();
                    somarpontosMaximo();
                    gravarPontos();
                    //mostrarDadosEmDialog();
                    break;

                case R.id.btnMonkey:
                    foiClicado[4] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.monkey);
                    ouvirSomAninal();
                    somarpontosMaximo();
                    gravarPontos();
                    //mostrarDadosEmDialog();
                    //playMusicPontos();
                    break;

                case R.id.btnSheep:
                    foiClicado[5] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.sheep);
                    ouvirSomAninal();
                    somarpontosMaximo();
                    gravarPontos();
                    break;
            }

        } else {
            switch (v.getId()) {
                case R.id.btnCat:
                    mediaPlayer = MediaPlayer.create(this, R.raw.cat);
                    ouvirSomAninal();
                    somarpontosMinimo();
                    gravarPontos();
                   // mostrarDadosEmDialog();
                    break;

                case R.id.btnCow:
                    mediaPlayer = MediaPlayer.create(this, R.raw.cow);
                    ouvirSomAninal();
                    somarpontosMinimo();
                    gravarPontos();
                   // mostrarDadosEmDialog();
                    break;

                case R.id.btnDog:
                    mediaPlayer = MediaPlayer.create(this, R.raw.dog);
                    ouvirSomAninal();
                    somarpontosMinimo();
                    gravarPontos();
                  //  mostrarDadosEmDialog();
                    break;
                case R.id.btnLion:
                    mediaPlayer = MediaPlayer.create(this, R.raw.lion);
                    ouvirSomAninal();
                    somarpontosMinimo();
                    gravarPontos();
                   // mostrarDadosEmDialog();
                    break;

                case R.id.btnMonkey:
                    mediaPlayer = MediaPlayer.create(this, R.raw.monkey);
                    ouvirSomAninal();
                    somarpontosMinimo();
                    gravarPontos();
                  //  mostrarDadosEmDialog();
                    break;

                case R.id.btnSheep:
                    mediaPlayer = MediaPlayer.create(this, R.raw.sheep);
                    ouvirSomAninal();
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
            bt1Concluir.setVisibility( View.VISIBLE);
            txtCat.setVisibility(View.VISIBLE);
            txtCow.setVisibility(View.VISIBLE);
            txtDog.setVisibility(View.VISIBLE);
            txtLion.setVisibility(View.VISIBLE);
            txtMonkey.setVisibility(View.VISIBLE);
            txtSheep.setVisibility(View.VISIBLE);
            gravarStatusCardViews();

        }
       Log.i("Checar Botoes Fim: AnimaisActivity", String.valueOf(checarTodosBotões()));

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



    public void ouvirSomAninal() {
        if (mediaPlayer != null) {
            mediaPlayer.start();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.release();
                }
            });
        }

    }


}

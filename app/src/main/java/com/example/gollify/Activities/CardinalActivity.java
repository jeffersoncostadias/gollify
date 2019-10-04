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

public class CardinalActivity extends AppCompatActivity implements View.OnClickListener {
    DataBaseHelper myDatabase;
    private String nome, senha,id;
    public  String existeSPrefer = "DefaultPontos";
    private boolean foiClicado[] = new boolean[6];
    private Button bt2Concluir;
    private ImageButton btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix;
    public int pontos;
    private TextView txtPontos, txtOne, txtTwo, txtThree, txtFour, txtFive, txtSix;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardinal);
        myDatabase = new DataBaseHelper(this);
        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);
        btnThree = findViewById(R.id.btnThree);
        btnFour = findViewById(R.id.btnFour);
        btnFive = findViewById(R.id.btnFive);
        btnSix = findViewById(R.id.btnSix);
        bt2Concluir = findViewById(R.id.bt2);
        txtPontos = findViewById(R.id.txtPontos);
        txtOne = findViewById(R.id.txtOne);
        txtTwo = findViewById(R.id.txtTwo);
        txtThree = findViewById(R.id.txtThree);
        txtFour = findViewById(R.id.txtFour);
        txtFive = findViewById(R.id.txtFive);
        txtSix = findViewById(R.id.txtSix);

        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);

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
            bt2Concluir.setVisibility( View.INVISIBLE);
            txtOne.setVisibility(View.INVISIBLE);
            txtTwo.setVisibility(View.INVISIBLE);
            txtThree.setVisibility(View.INVISIBLE);
            txtFour.setVisibility(View.INVISIBLE);
            txtFive.setVisibility(View.INVISIBLE);
            txtSix.setVisibility(View.INVISIBLE);
        }else{
            bt2Concluir.setVisibility( View.VISIBLE);
            txtOne.setVisibility(View.VISIBLE);
            txtTwo.setVisibility(View.VISIBLE);
            txtThree.setVisibility(View.VISIBLE);
            txtFour.setVisibility(View.VISIBLE);
            txtFive.setVisibility(View.VISIBLE);
            txtSix.setVisibility(View.VISIBLE);
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
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_bt2"), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("statusBooleanPoint", statusBooleanPoint);
        editor.putString("statusStringPontos", statusStringBotao);
        editor.commit();
    }  // FIM

    // MÉTODO CRIAR SHARED PREFERENCES PRA LER O STATUS DE BOTÃO CONCLUIR
    public String lerArquivoBotaoConcluir() {
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_bt2"), Context.MODE_PRIVATE);
        String defaultPontos = "DefaultPontos";
        String statusFinal = sharedPreferences.getString("statusStringPontos", defaultPontos);
        Log.i("Ler Arquivos: ", statusFinal);
        return statusFinal;
    }  // FIM


    // MÉTODO QUE GRAVA AS MOEDAS NO BANCO DE DADOS
    public void gravarMoedas() {
        Cursor cur = myDatabase.existemDados();
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {

                // Tabela esta vazia, preencha com seus dados iniciais
                boolean isInserted = myDatabase.inserirDadosMoedas("600");
                if (isInserted == true) {
                    Toast.makeText(this, "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarMoedas(id, "600");
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
                boolean isInserted = myDatabase.inserirDadosProgesso("20");
                if (isInserted == true) {
                    Toast.makeText(this, "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarProgresso(id, "20");
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
                boolean isInserted = myDatabase.inserirDadosCardViews("3");
                if (isInserted == true) {
                    // Toast.makeText(this, "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarCardViews(id, "3");
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
        Log.i("Tela Cardinal ID: ", id);
        Log.i("Tela Cardinal NOME: ", nome);
        Log.i("NOME SHAREDPREFERENCE:", nome.concat("_act2"));
        Log.i("ValorSharedPreferences:", lerArquivo());
        // FIM
    }

    public void inicializaVetores(){
        // INICIALIZAR O  ARRAY COM O VALOR FALSE
        for (int cont = 0; cont < 6; cont++) {
            foiClicado[cont] = false;
            Log.i("Foi clicado: " + cont + " |", String.valueOf(foiClicado[cont]));
        } // FIM
    }


    public void verificaSPPontos(){
        if(lerArquivo().equals(existeSPrefer)){
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
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_act2"), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("statusBooleanPoint", statusBooleanPoint);
        editor.putString("statusStringPontos", statusStringPontos);
        editor.commit();
    }  // FIM


    // MÉTODO CRIAR SHARED PREFERENCES PRA LER O STATUS DE PONTOS
    public String lerArquivo() {
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_act2"), Context.MODE_PRIVATE);
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
    } // FIM


    // MÉTODO QUE MOSTRA NO TXT OS PONTOS OBTIDOS PELO USUÁRIO
    public  void mostrarDadosTxtPontos(){
        // String id = "1";
        Cursor res = myDatabase.obterDados(id);
        String data = null;
        if (res.moveToFirst()) {
            data = res.getString(3);
            txtPontos.setText(data);
        }

    }// FIM


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
                   // Toast.makeText(this,"Dados atualizados",Toast.LENGTH_LONG).show();
                }
                else {
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
        String opB = lerArquivo();
        Log.i("Checar Botoes Inicio: ", String.valueOf(checarTodosBotões()));
        if (opA.equals(opB)) {
            switch (v.getId()) {
                case R.id.btnOne:
                    foiClicado[0] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.one);
                    ouvirSomNumero();
                    somarpontosMaximo();
                    gravarPontos();
                    //  mostrarDadosEmDialog();
                    //salvarArquivoPontos("0");
                    lerArquivo();
                    break;

                case R.id.btnTwo:
                    foiClicado[1] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.two);
                    ouvirSomNumero();
                    somarpontosMaximo();
                    gravarPontos();
                    //  mostrarDadosEmDialog();
                    break;

                case R.id.btnThree:
                    foiClicado[2] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.three);
                    ouvirSomNumero();
                    somarpontosMaximo();
                    gravarPontos();
                    //  mostrarDadosEmDialog();
                    break;
                case R.id.btnFour:
                    foiClicado[3] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.four);
                    ouvirSomNumero();
                    somarpontosMaximo();
                    gravarPontos();
                    //  mostrarDadosEmDialog();
                    break;

                case R.id.btnFive:
                    foiClicado[4] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.five);
                    ouvirSomNumero();
                    somarpontosMaximo();
                    gravarPontos();
                    //   mostrarDadosEmDialog();
                    //playMusicPontos();
                    break;

                case R.id.btnSix:
                    foiClicado[5] = true;
                    mediaPlayer = MediaPlayer.create(this, R.raw.six);
                    ouvirSomNumero();
                    somarpontosMaximo();
                    gravarPontos();

                    break;
            }

        } else {
            switch (v.getId()) {
                case R.id.btnOne:
                    mediaPlayer = MediaPlayer.create(this, R.raw.one);
                    ouvirSomNumero();
                    somarpontosMinimo();
                    gravarPontos();
                   // mostrarDadosEmDialog();
                    break;

                case R.id.btnTwo:
                    mediaPlayer = MediaPlayer.create(this, R.raw.two);
                    ouvirSomNumero();
                    somarpontosMinimo();
                    gravarPontos();
                    //mostrarDadosEmDialog();
                    break;

                case R.id.btnThree:
                    mediaPlayer = MediaPlayer.create(this, R.raw.three);
                    ouvirSomNumero();
                    somarpontosMinimo();
                    gravarPontos();
                    //mostrarDadosEmDialog();
                    break;
                case R.id.btnFour:
                    mediaPlayer = MediaPlayer.create(this, R.raw.four);
                    ouvirSomNumero();
                    somarpontosMinimo();
                    gravarPontos();
                   // mostrarDadosEmDialog();
                    break;

                case R.id.btnFive:
                    mediaPlayer = MediaPlayer.create(this, R.raw.five);
                    ouvirSomNumero();
                    somarpontosMinimo();
                    gravarPontos();
                   // mostrarDadosEmDialog();
                    break;

                case R.id.btnSix:
                    mediaPlayer = MediaPlayer.create(this, R.raw.six);
                    ouvirSomNumero();
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
            gravarStatusCardViews();
            bt2Concluir.setVisibility( View.VISIBLE);
            txtOne.setVisibility(View.VISIBLE);
            txtTwo.setVisibility(View.VISIBLE);
            txtThree.setVisibility(View.VISIBLE);
            txtFour.setVisibility(View.VISIBLE);
            txtFive.setVisibility(View.VISIBLE);
            txtSix.setVisibility(View.VISIBLE);
        }
        Log.i("Checar Botoes Fim: Cardinal Activity", String.valueOf(checarTodosBotões()));
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

    public void ouvirSomNumero(){
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


     // MÉTODO PARA FINALIZAR A ACTIVITY E RETORNAR UM RESULT
    public void concluir(View view) {
        Intent intent = new Intent();
        intent.putExtra("msg", "Lição Finalizada");
        setResult(1, intent);
        finish();
    }// FIM


}

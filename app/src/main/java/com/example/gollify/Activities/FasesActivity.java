package com.example.gollify.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gollify.DataBase.DataBaseHelper;
import com.example.gollify.R;

public class FasesActivity extends AppCompatActivity {

    CardView adquirirFase1, adquirirFase2, adquirirFase3;
    DataBaseHelper myDatabase;
    private String nome, senha, id;
    private ImageView imvCubo, imvPote, imvBau;
    private int idButton = 0;
    TextView txtMoedas;
    public  String existeSPrefer = "DefaultPontos";
    private boolean foiClicado[] = new boolean[1];
    public int pontos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fases);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tela Adquirir Fases");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myDatabase = new DataBaseHelper(this);
        adquirirFase1 =  findViewById(R.id.fase1);
        adquirirFase2 =  findViewById(R.id.fase2);
        adquirirFase3 =  findViewById(R.id.fase3);
        txtMoedas = findViewById(R.id.txtMoedas);
        imvCubo = findViewById(R.id.imvCubo);
        imvPote = findViewById(R.id.imvPote);
        imvBau = findViewById(R.id.imvBau);

        obterValoresDeMainActivity();
        statusCardViews();
        mostrarDadosTxtMoedas();
        verificaSPCardView1();
        verificaSPCardView2();
        verificaSPCardView3();

        //gravarMoedas("10000");

        adquirirFase1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idButton = 1;
                int valorAtualMoedas = mostrarValorAtualMoedas();
                if(valorAtualMoedas >= 900){
                    addTaskDialog("Fase Extra 1");
                }else{

                    Toast.makeText(getApplicationContext(),"Você não tem moedas suficientes!", Toast.LENGTH_SHORT).show();

                }

            }
        });

        adquirirFase2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idButton = 2;
                int valorAtualMoedas = mostrarValorAtualMoedas();
                if(valorAtualMoedas >= 1300){
                    addTaskDialog("Fase Extra 2");
                }else{
                    Toast.makeText(getApplicationContext(),"Você não tem moedas suficientes!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        adquirirFase3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idButton = 3;
                int valorAtualMoedas = mostrarValorAtualMoedas();
                if(valorAtualMoedas >= 2000){
                    addTaskDialog("Fase Extra 3");
                }else{
                    Toast.makeText(getApplicationContext(),"Você não tem moedas suficientes!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    } // FIM ON CREATE


    private void addTaskDialog( String titulo){
        //LayoutInflater inflater = LayoutInflater.from(this);
       // View view = inflater.inflate(R.layout.add_contact_layout, null);
       // final EditText nameField = view.findViewById(R.id.enter_name);
        //final EditText noField = view.findViewById(R.id.enter_phno);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage("Tem certeza que deseja adquirir essa fase?");
      //  builder.setView(view);
        builder.create();

        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gerarAquisicao();
            }
        });

        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        builder.show();
    }

   public void gerarAquisicao(){
       int resultado = 0, num1 = 0, num2 = 0;
       num1 = mostrarValorAtualMoedas();

       iniciarTask();
        if(idButton == 1){
           resultado = num1 - 900;
           gravarMoedas(String.valueOf(resultado));
           cadastrarAquisicao("1");
           Toast.makeText(getApplicationContext(),"Fase liberada com sucesso", Toast.LENGTH_SHORT).show();

       }else if(idButton == 2){
           resultado = num1 - 1300;
           gravarMoedas(String.valueOf(resultado));
           cadastrarAquisicao("2");
           Toast.makeText(getApplicationContext(),"Fase liberada com sucesso", Toast.LENGTH_SHORT).show();
       }else if(idButton == 3){
           resultado = num1 - 2000;
           gravarMoedas(String.valueOf(resultado));
           cadastrarAquisicao("3");
           Toast.makeText(getApplicationContext(),"Fase liberada com sucesso", Toast.LENGTH_SHORT).show();

       }

   }

    private void iniciarTask() {
        new Task().execute();
    }
    private class Task extends AsyncTask<Void, Void, Void> {
        //  private String text;
        //String usuarioLogado = obterNomeUsuario();
       // String nivelusuarioLogado = obterNivelUsuario();

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("test","doInBackground");
           // this.text = obterNomeUsuario();
            return null;
        }

        protected void onPostExecute(Void result) {
            Log.d("test","onPostExecute");
            mostrarDadosTxtMoedas();
            statusCardViews();
            //txtNavNome = navViewHead.findViewById(R.id.txtNavNome);
           // txtNavNome.setText("Usuário: "+usuarioLogado);

           // txtNavNível = navViewHead.findViewById(R.id.txtNavNivel);

            //  Log.i("ON POST EXECUTE", "Aqui: "+ usuarioLogado);
        }

    }


    // MÉTODO PARA MOSTRAR NO TXT AS MOEDAS OBTIDOS EM CADA ACERTO PELO USUÁRIO
    public void mostrarDadosTxtMoedas() {
        // String id = "1";
        Cursor res = myDatabase.obterDados(id);
        String data = null;
        if (res.moveToFirst()) {
            data = res.getString(4);
            txtMoedas.setText(data);
        }

    } // FIM

    // MÉTODO PARA MOSTRAR AS MOEDAS OBTIDAS DO BANCO DE DADPS
    public int mostrarValorAtualMoedas() {
        // String id = "1";
        Cursor res = myDatabase.obterDados(id);
        int data = 0;
        if (res.moveToFirst()) {
            data = res.getInt(4);
        }

        return data;
    } // FIM


    public void statusCardViews(){
        int num = mostrarValorFaseAtual();
        Log.i("StatusCardvires", "Texto "+num);
        switch (mostrarValorFaseAtual()){

            case 0 :
                adquirirFase1.setEnabled(true);
                adquirirFase2.setEnabled(true);
                adquirirFase3.setEnabled(true);
                break;

            case 1 :
                adquirirFase1.setEnabled(false);
                imvCubo.setImageResource(R.drawable.icone_cubo);
                imvCubo.setBackgroundResource(R.drawable.circulo_branco);
                adquirirFase2.setEnabled(true);
                adquirirFase3.setEnabled(true);
                break;

            case 2 :
                adquirirFase1.setEnabled(false);
                imvCubo.setImageResource(R.drawable.icone_cubo);
                imvCubo.setBackgroundResource(R.drawable.circulo_branco);
                adquirirFase2.setEnabled(false);
                imvPote.setImageResource(R.drawable.icone_pote_ouro);
                imvPote.setBackgroundResource(R.drawable.circulo_vermelho);
                adquirirFase3.setEnabled(true);
                break;

            case 3 :
                adquirirFase1.setEnabled(false);
                imvCubo.setImageResource(R.drawable.icone_cubo);
                imvCubo.setBackgroundResource(R.drawable.circulo_branco);
                adquirirFase2.setEnabled(false);
                imvPote.setImageResource(R.drawable.icone_pote_ouro);
                imvPote.setBackgroundResource(R.drawable.circulo_vermelho);
                adquirirFase3.setEnabled(false);
                imvBau.setImageResource(R.drawable.icone_bau);
                imvBau.setBackgroundResource(R.drawable.circulo_verde);


                break;


        }




    }


    // MÉTODO PARA MOSTRAR AS FASES OBTIDAS DO BANCO DE DADPS
    public int mostrarValorFaseAtual() {
        // String id = "1";
        Cursor res = myDatabase.obterDados(id);
        int data = 0;
        if (res.moveToFirst()) {
            data = res.getInt(7);
        }
        return data;
    } // FIM



    // MÉTODO QUE GRAVA AS MOEDAS NO BANCO DE DADOS
    public void gravarMoedas( String pMoedas) {
        Cursor cur = myDatabase.existemDados();
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {

                // Tabela esta vazia, preencha com seus dados iniciais
                boolean isInserted = myDatabase.inserirDadosMoedas(pMoedas);
                if (isInserted == true) {
                    Toast.makeText(this, "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarMoedas(id, pMoedas);
                if (isUpdate == true) {
                    //Toast.makeText(this, "Dados atualizados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser atualizados", Toast.LENGTH_LONG).show();
                }
            }
        }

    } // FIM


    // MÉTODO QUE GRAVA AS FASES NO BANCO DE DADOS
    public void cadastrarAquisicao(String pFase) {
        Cursor cur = myDatabase.existemDados();
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {

                // Tabela esta vazia, preencha com seus dados iniciais
                boolean isInserted = myDatabase.inserirDadosFase(pFase);
                if (isInserted == true) {
                    Toast.makeText(this, "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarFase(id, pFase);
                if (isUpdate == true) {
                    //Toast.makeText(this, "Dados atualizados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Dados não podem ser atualizados", Toast.LENGTH_LONG).show();
                }
            }
        }

    } // FIM


    public void verificaSPCardView1(){
        if(lerArquivoStatusBotões1().equals(existeSPrefer)){
            salvarArquivoCardView1("0");
            Log.i("VERIFICA BOTAO CONCLUIR", "é igual"+ lerArquivoStatusBotões1() );

        }else {
            Log.i("NÃO É VERDADE BOTÃO", "Não é igual "+ lerArquivoStatusBotões1() );
        }
    }


    public void verificaSPCardView2(){
        if(lerArquivoStatusBotões2().equals(existeSPrefer)){
            salvarArquivoCardView2("0");
            Log.i("VERIFICA BOTAO CONCLUIR", "é igual"+ lerArquivoStatusBotões2() );

        }else {
            Log.i("NÃO É VERDADE BOTÃO", "Não é igual "+ lerArquivoStatusBotões2() );
        }
    }

    public void verificaSPCardView3(){
        if(lerArquivoStatusBotões3().equals(existeSPrefer)){
            salvarArquivoCardView3("0");
            Log.i("VERIFICA BOTAO CONCLUIR", "é igual"+ lerArquivoStatusBotões3() );

        }else {
            Log.i("NÃO É VERDADE BOTÃO", "Não é igual "+ lerArquivoStatusBotões3() );
        }
    }

    // MÉTODO CRIAR SHARED PREFERENCES PRA SALVAR O STATUS DE CardView1
    public void salvarArquivoCardView1(String statusBotao) {
        String statusStringBotao = statusBotao;
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_fase1"), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("statusBooleanPoint", statusBooleanPoint);
        editor.putString("statusStringPontos", statusStringBotao);
        editor.commit();
    }  // FIM

    // MÉTODO CRIAR SHARED PREFERENCES PRA SALVAR O STATUS DE CardView2
    public void salvarArquivoCardView2(String statusBotao) {
        String statusStringBotao = statusBotao;
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_fase2"), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("statusBooleanPoint", statusBooleanPoint);
        editor.putString("statusStringPontos", statusStringBotao);
        editor.commit();
    }  // FIM

    // MÉTODO CRIAR SHARED PREFERENCES PRA SALVAR O STATUS DE CardView3
    public void salvarArquivoCardView3(String statusBotao) {
        String statusStringBotao = statusBotao;
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_fase3"), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("statusBooleanPoint", statusBooleanPoint);
        editor.putString("statusStringPontos", statusStringBotao);
        editor.commit();
    }  // FIM



    // MÉTODO CRIAR SHARED PREFERENCES PRA LER O STATUS DE BOTÃO FASE 1
    public String lerArquivoStatusBotões1() {
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_fase1"), Context.MODE_PRIVATE);
        String defaultPontos = "DefaultPontos";
        String statusFinal = sharedPreferences.getString("statusStringPontos", defaultPontos);
        Log.i("Ler Arquivos FASE 1: ", statusFinal);
        return statusFinal;
    }  // FIM

    // MÉTODO CRIAR SHARED PREFERENCES PRA LER O STATUS DE BOTÃO FASE 1
    public String lerArquivoStatusBotões2() {
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_fase2"), Context.MODE_PRIVATE);
        String defaultPontos = "DefaultPontos";
        String statusFinal = sharedPreferences.getString("statusStringPontos", defaultPontos);
        Log.i("Ler Arquivos FASE 2: ", statusFinal);
        return statusFinal;
    }  // FIM


    // MÉTODO CRIAR SHARED PREFERENCES PRA LER O STATUS DE BOTÃO FASE 1
    public String lerArquivoStatusBotões3() {
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getSharedPreferences(nome.concat("_fase3"), Context.MODE_PRIVATE);
        String defaultPontos = "DefaultPontos";
        String statusFinal = sharedPreferences.getString("statusStringPontos", defaultPontos);
        Log.i("Ler Arquivos FASE 3: ", statusFinal);
        return statusFinal;
    }  // FIM

    public void obterValoresDeMainActivity(){
        //OBTER DA MAIN ACTIVITY OS PARAMETROS ID USUÁRIO
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        nome = intent.getStringExtra("NOME");
        Log.i("Tela Animais ID: ", id);
        Log.i("Tela Animais NOME: ", nome);
        // FIM
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

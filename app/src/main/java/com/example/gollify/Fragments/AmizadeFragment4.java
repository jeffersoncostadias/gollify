package com.example.gollify.Fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gollify.DataBase.DataBaseHelper;
import com.example.gollify.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmizadeFragment4 extends Fragment {
    DataBaseHelper myDatabase;

    private String consultarPronuncia, compareB, nome, id;
    public  String existeSPrefer = "DefaultPontos";
    private boolean foiClicado[] = new boolean[1];
    private TextView txvResult, questions;
    private ImageView btnSpeak;
    private ImageButton btnLister;
    public int pontos;
    private TextView txtPontos;
    private MediaPlayer mediaPlayer;

    public AmizadeFragment4() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("ID");
            nome = bundle.getString("NOME");
            Log.i("Tela Fragment1: ", id);
            Log.i("Tela Fragment1: ", nome);
        }

        Log.i("Primeiro: ", id);

        inicializaVetores();

        verificaSharedPreference();

        mostrarDadosTxtPontos();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myDatabase = new DataBaseHelper(getContext());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.amizade_fragment4, container, false);

        txvResult = view.findViewById(R.id.txvResult);
        questions = view.findViewById(R.id.questions);
        btnSpeak = view.findViewById(R.id.btnSpeak);
        btnLister = view.findViewById(R.id.btnLister);
        txtPontos = view.findViewById(R.id.txtPontos);
        txvResult.setVisibility(View.INVISIBLE);
        final int trt = txtPontos.getText().length();
        Log.i("Segundo: ", "é segundo");


        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             ouvirPronuncia();
            }
        });

        btnLister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //somarpontosMaximo();
                //  Toast.makeText(getContext(), "Funcionou", Toast.LENGTH_SHORT).show();
                mediaPlayer = MediaPlayer.create(getContext(), R.raw.bill_gentle);
                playMusic();
            }
        });

        return view;

    }// FIM ON CREATEVIEW

    public void ouvirPronuncia(){

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Fale e acerte na atividade 01!");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(getContext(), "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }

    }

    public void resultadoPronuncia(){

        View toastView = getLayoutInflater().inflate(R.layout.toast_custom_c, null);
        Toast toast = new Toast(getContext());
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0,400);
        toast.show();

    }

    public void verificaSharedPreference(){
        if(lerArquivo().equals(existeSPrefer)){
            salvarArquivo("0");
            Log.i("VERDADE", "é igual" );
        }else {
            Log.i("NÃO É VERDADE", "Não é igual" );
        }
    }


    public void inicializaVetores(){
        // INICIALIZAR O  ARRAY COM O VALOR FALSE
        for (int cont = 0; cont < 1; cont++) {
            foiClicado[cont] = false;
            Log.i("Foi clicado Fragment1: " + cont + " |", String.valueOf(foiClicado[cont]));
            Log.i("Checar Botoes: ", String.valueOf(checarTodosBotões()));
        }
    }// FIM


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case 10:
                if (resultCode == getActivity().RESULT_OK && intent != null) {
                    ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //txvResult.setVisibility(View.INVISIBLE);
                    txvResult.setText(result.get(0));
                    consultarPronuncia = questions.getText().toString().toLowerCase();
                    compareB = txvResult.getText().toString().toLowerCase();

                    if (consultarPronuncia.equals(compareB)) {
                        somarPontos();
                        gravarPontos();
                       // mostrarDadosEmDialog();
                        resultadoPronuncia();

                       // Toast.makeText(getContext(), "Falou Certo!", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(getContext(), "Errou, mas tente novamente!", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
        }
    }



    // METODO MOSTRAR OS DADOS OBTIDOS PELO ID DO USUÁRIO EM UMA DIALOG
    public void mostrarDadosEmDialog() {
        Cursor res = myDatabase.obterDados(id);
        String data = null;
        if (res.moveToFirst()) {
            data = "Id:" + res.getString(0) + "\n" +
                    "Pontos :" + res.getString(3) + "\n\n";
        }
        showMessage("Pontos", data);
    }// FIM


    // MÉTODO QUE MOSTRA NO TXT OS PONTOS OBTIDOS PELO USUÁRIO
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
                    Toast.makeText(getContext(), "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarPontos(id, txtPontos.getText().toString());
                if (isUpdate == true) {
                    //Toast.makeText(getContext(), "Dados atualizados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Dados não podem ser atualizados", Toast.LENGTH_LONG).show();
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
                boolean isInserted = myDatabase.inserirDadosMoedas("1700");
                if (isInserted == true) {
                    Toast.makeText(getContext(), "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarMoedas(id, "1700");
                if (isUpdate == true) {
                    //Toast.makeText(this, "Dados atualizados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Dados não podem ser atualizados", Toast.LENGTH_LONG).show();
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
                boolean isInserted = myDatabase.inserirDadosProgesso("60");
                if (isInserted == true) {
                    Toast.makeText(getContext(), "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarProgresso(id, "60");
                if (isUpdate == true) {
                    //Toast.makeText(this, "Dados atualizados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Dados não podem ser atualizados", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

        // MÉTODO QUE GRAVA O NÍVEL NO BANCO DE DADOS
    public void gravarNivel() {
        Cursor cur = myDatabase.existemDados();
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt(0) == 0) {

                // Tabela esta vazia, preencha com seus dados iniciais
                boolean isInserted = myDatabase.inserirDadosNivel("3");
                if (isInserted == true) {
                    Toast.makeText(getContext(), "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarNivel(id, "3");
                if (isUpdate == true) {
                    //Toast.makeText(this, "Dados atualizados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Dados não podem ser atualizados", Toast.LENGTH_LONG).show();
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
                boolean isInserted = myDatabase.inserirDadosCardViews("8");
                if (isInserted == true) {
                    // Toast.makeText(this, "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                  //  Toast.makeText(this, "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarCardViews(id, "8");
                if (isUpdate == true) {
                    //Toast.makeText(this, "Dados atualizados", Toast.LENGTH_LONG).show();
                } else {
                  //  Toast.makeText(this, "Dados não podem ser atualizados", Toast.LENGTH_LONG).show();
                }
            }
        }

    } // FIM

    public void somarpontosAntigos() {
        String opA = "0";
        String opB = lerArquivo();
        Log.i("Checar Botoes: ", String.valueOf(checarTodosBotões()));
        if (opA.equals(opB)) {
            somarpontosMaximo();
            foiClicado[0] = true;
        } else {
            somarpontosMinimo();
            salvarArquivo("1");
        }
    }


    public void somarPontos(){
        // salvarArquivoPontos("0");
        String opA = "0";
        String opB = lerArquivo();
        Log.i("Checar Botoes ", String.valueOf(checarTodosBotões()));
        if (opA.equals(opB)) {
            switch (Integer.parseInt(opB)) {
                case 0:
                    foiClicado[0] = true;
                    somarpontosMaximo();
                    break;
                }

        } else {
            switch (Integer.parseInt(opB)) {
                case 1:
                    somarpontosMinimo();
                    break;
            }
        }
        if(checarTodosBotões()){
            salvarArquivo("1");
            gravarMoedas();
            gravarNivel();
            gravarProgresso();
            gravarStatusCardViews();
        }
      //  Log.i("ObjetosActivity:", String.valueOf(checarTodosBotões()));
    }



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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    } // FIM



    public void playMusic() {
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

    // MÉTODO CRIAR SHARED PREFERENCES PRA SALVAR O STATUS DE PONTOS
    public void salvarArquivo(String pontos) {
        String statusStringPontos = pontos;
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(nome.concat("_act6D"), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("statusBooleanPoint", statusBooleanPoint);
        editor.putString("statusStringPontos", statusStringPontos);
        editor.commit();
    }  // FIM


    // MÉTODO CRIAR SHARED PREFERENCES PRA LER O STATUS DE PONTOS
    public String lerArquivo() {
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(nome.concat("_act6D"), Context.MODE_PRIVATE);
        String defaultPontos = "DefaultPontos";
        String statusFinal = sharedPreferences.getString("statusStringPontos", defaultPontos);
        Log.i("Ler Arquivos: ", statusFinal);
        return statusFinal;
    }  // FIM


    // MÉTODO PARA CHECAR SE TODOS OS BOTÕES FORAM CLICADOS, SE SIM OS PONTOS PASSAM A VALER 10
    public Boolean checarTodosBotões() {
        boolean checado = false;
        for (int cont = 0; cont < 1; cont++) {
            if (foiClicado[cont] == true) {
                checado = true;
            } else {
                checado = false;
            }
            // Log.i("Foi clicado: " + cont + " |", String.valueOf(foiClicado[cont]));
        } //FOR
        return checado;
    } // FIM

}

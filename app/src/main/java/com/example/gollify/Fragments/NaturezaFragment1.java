package com.example.gollify.Fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gollify.DataBase.DataBaseHelper;
import com.example.gollify.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NaturezaFragment1 extends Fragment {
    DataBaseHelper myDatabase;

    private String compareA, compareB, nome, id;
    public  String existeSPrefer = "DefaultPontos";
    private boolean foiClicado[] = new boolean[1];
    private TextView txvResult, questions;
    private RadioGroup responderQuestao;
    private RadioButton rbAlternativa1,rbAlternativa2,rbAlternativa3,rbAlternativa4;
    public int pontos;
    private TextView txtPontos;
    private MediaPlayer mediaPlayer;

    public NaturezaFragment1() {
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
        View view = inflater.inflate(R.layout.natureza_fragment1, container, false);

        txvResult = view.findViewById(R.id.txvResult);
        txtPontos = view.findViewById(R.id.txtPontos);
        questions = view.findViewById(R.id.questions);
        responderQuestao = view.findViewById(R.id.rgQuestoes);
        rbAlternativa1 = view.findViewById(R.id.rbAlternativa1);
        rbAlternativa2 = view.findViewById(R.id.rbAlternativa2);
        rbAlternativa3 = view.findViewById(R.id.rbAlternativa3);
        rbAlternativa4 = view.findViewById(R.id.rbAlternativa4);

        Log.i("Segundo: ", "é segundo");

        responderQuestao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (responderQuestao.getCheckedRadioButtonId()) {
                    case R.id.rbAlternativa1:
                        somarPontos();
                        gravarPontos();
                        validarResposta();
                        playMusic();
                        Toast.makeText(getContext(), "Resposta Correta!", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.rbAlternativa2:
                        validarResposta();
                        Toast.makeText(getContext(), "Resposta errada!", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.rbAlternativa3:
                        validarResposta();
                        Toast.makeText(getContext(), "Resposta errada!", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.rbAlternativa4:
                        validarResposta();
                        Toast.makeText(getContext(), "Resposta errada!", Toast.LENGTH_SHORT).show();
                        break;
                }



              //  Toast.makeText(getContext(), responderQuestao.getCheckedRadioButtonId()+" | "+ R.id.rbAlternativa1, Toast.LENGTH_LONG).show();

            }
        });


        return view;

    }// FIM ON CREATEVIEW



    public void validarResposta(){
      //  rbAlternativa1.setBackgroundResource(R.drawable.retangle_button);
        Drawable acertou = getResources().getDrawable(R.drawable.icone_correto);
        Drawable errou = getResources().getDrawable(R.drawable.icone_error);
        acertou.setBounds(0, 0, acertou.getIntrinsicWidth(), acertou.getIntrinsicHeight());
        errou.setBounds(0, 0, errou.getIntrinsicWidth(), errou.getIntrinsicHeight());
        rbAlternativa1.setCompoundDrawables(null,null,acertou,null);
        rbAlternativa2.setCompoundDrawables(null,null,errou,null);
        rbAlternativa3.setCompoundDrawables(null,null,errou,null);
        rbAlternativa4.setCompoundDrawables(null,null,errou,null);

        rbAlternativa1.setEnabled(false);
        rbAlternativa1.setTextColor(Color.BLACK);
        rbAlternativa2.setEnabled(false);
        rbAlternativa2.setTextColor(Color.BLACK);
        rbAlternativa3.setEnabled(false);
        rbAlternativa3.setTextColor(Color.BLACK);
        rbAlternativa4.setEnabled(false);
        rbAlternativa4.setTextColor(Color.BLACK);
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
                boolean isInserted = myDatabase.inserirDadosMoedas("1800");
                if (isInserted == true) {
                    Toast.makeText(getContext(), "Dados inseridos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Dados não podem ser inseridos", Toast.LENGTH_LONG).show();
                }
            } else {
                // Tabela ja contem dados.
                boolean isUpdate = myDatabase.atualizarMoedas(id, "1800");
                if (isUpdate == true) {
                    //Toast.makeText(this, "Dados atualizados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Dados não podem ser atualizados", Toast.LENGTH_LONG).show();
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
        }
      //  Log.i("ObjetosActivity:", String.valueOf(checarTodosBotões()));
    }



    // MÉTODO PARA SOMAR 100 PONTOS
    public void somarpontosMaximo() {
        int soma = Integer.parseInt(txtPontos.getText().toString());
        pontos = soma;
        pontos = pontos + 100;
        txtPontos.setText(String.valueOf(pontos));
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.beep_acertou2);
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(nome.concat("_act8A"), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("statusBooleanPoint", statusBooleanPoint);
        editor.putString("statusStringPontos", statusStringPontos);
        editor.commit();
    }  // FIM


    // MÉTODO CRIAR SHARED PREFERENCES PRA LER O STATUS DE PONTOS
    public String lerArquivo() {
        boolean statusBooleanPoint = false;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(nome.concat("_act8A"), Context.MODE_PRIVATE);
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

package com.example.gollify.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gollify.DataBase.DataBaseHelper;
import com.example.gollify.R;
import com.example.gollify.Utils.ValidarCampos;

public class TabFragment2 extends Fragment {
    DataBaseHelper myDatabase;
    private EditText edtNome;
    private EditText edtSenha;
    private Button btnOK;
    private DadosDialogListenerCadastro dadosDialogListener;
    private CancelarDialogListenerCadastro cancelarDialogListener;
    private String mText = "";
    public static TabFragment2 createInstance(String txt)
    {
        TabFragment2 fragment = new TabFragment2();
        fragment.mText = txt;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myDatabase = new DataBaseHelper(getContext());
        View v = inflater.inflate(R.layout.aba_fragment2,container,false);
        ((TextView) v.findViewById(R.id.txtNavNivel)).setText(mText);
        edtNome = v.findViewById(R.id.edtNome);
        edtSenha = v.findViewById(R.id.edtSenha);


        btnOK = v.findViewById(R.id.btnCadastrar);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bNome = true, bSenha= false;
                bNome = ValidarCampos.validarCamposVazios(edtNome,"Preencha o campo usuário");
                bSenha = ValidarCampos.validarCamposVazios(edtSenha,"Preencha o campo senha");

                if(bNome && bSenha){
                    if (!edtNome.getText().toString().equals(obterNomeUsuarioLogin())){
                        boolean cancelarDialogTab = true;
                        inserirUsuarios();
                        String nome = edtNome.getText().toString();
                        String senha = edtSenha.getText().toString();
                        dadosDialogListener.obterDadosDialogCadastro(nome, senha);
                        cancelarDialogListener.cancelarDadosDialogCadastro(cancelarDialogTab);
                    } else {
                        Toast.makeText(getContext(),"Usuário já está cadastrado! "+ obterNomeUsuarioLogin(), Toast.LENGTH_SHORT).show();

                    }

                }



            }
        });


        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            dadosDialogListener = (DadosDialogListenerCadastro) context;
            cancelarDialogListener = (CancelarDialogListenerCadastro) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DadosDialogListenerLogin");
        }
    }

    public interface DadosDialogListenerCadastro {
        void obterDadosDialogCadastro(String nome, String senha);
    }

    public interface CancelarDialogListenerCadastro {
        void cancelarDadosDialogCadastro(boolean cancelar);
    }


    public void inserirUsuarios(){
        myDatabase.inserirDadosCadastro(edtNome.getText().toString(), edtSenha.getText().toString(),"000", "0", "1","0", "0", "1");
    }


    public  String obterNomeUsuarioLogin(){
        String nome = edtNome.getText().toString().trim();
        Cursor res = myDatabase.obterDadosByNome(nome);
        // Log.i("Metodo obter usuario", nome);
        String data = null;
        if (res.moveToFirst()) {
            data = res.getString(1);
            //txtPontos.setText(data);
        }
        //Log.i("Tela MainActivity ID", data);
        return data;

    }
}

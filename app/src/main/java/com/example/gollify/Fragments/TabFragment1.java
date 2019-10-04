package com.example.gollify.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
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

public class TabFragment1 extends AppCompatDialogFragment {
    DataBaseHelper myDatabase;
    private EditText edtNome;
    private EditText edtSenha;
    private Button btnOK;
    private DadosDialogListenerLogin dadosDialogListener;
    private CancelarDialogListenerLogin cancelarDialogListener;

    private String mText = "";

    public static TabFragment1 createInstance(String txt) {
        TabFragment1 fragment = new TabFragment1();
        fragment.mText = txt;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myDatabase = new DataBaseHelper(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = inflater.inflate(R.layout.aba_fragment1, container, false);
        ((TextView) v.findViewById(R.id.txtNavNivel)).setText(mText);
        edtNome = v.findViewById(R.id.edtNome);
        edtSenha = v.findViewById(R.id.edtSenha);

        btnOK = v.findViewById(R.id.btnAcessar);


        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 boolean bNome = true, bSenha= false;
                 bNome = ValidarCampos.validarCamposVazios(edtNome,"Preencha o campo usuário");
                 bSenha = ValidarCampos.validarCamposVazios(edtSenha,"Preencha o campo senha");

                 if(bNome && bSenha){
                     if (edtNome.getText().toString().equals(obterNomeUsuarioLogin())){
                         boolean cancelarDialogTab = true;
                         String nome = edtNome.getText().toString();
                         String senha = edtSenha.getText().toString();
                         dadosDialogListener.obterDadosDialogLogin(nome, senha);
                         cancelarDialogListener.cancelarDadosDialogLogin(cancelarDialogTab);
                     } else {
                         Toast.makeText(getContext(),"Usuário não existe! "+ obterNomeUsuarioLogin(), Toast.LENGTH_SHORT).show();

                     }

                 }


            }
        });



        return v;
    } // FIM  ON CREATE



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            dadosDialogListener = (DadosDialogListenerLogin) context;
            cancelarDialogListener = (CancelarDialogListenerLogin) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DadosDialogListenerLogin");
        }
    }

    public interface DadosDialogListenerLogin {
        void obterDadosDialogLogin(String nome, String senha);
    }

    public interface CancelarDialogListenerLogin {
        void cancelarDadosDialogLogin(boolean cancelar);
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

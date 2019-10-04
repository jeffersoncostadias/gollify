package com.example.gollify.Fragments;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.gollify.DataBase.DataBaseHelper;
import com.example.gollify.R;

public class TelaLoginDialog extends AppCompatDialogFragment {
    DataBaseHelper myDatabase;

    private EditText edtNome;
    private EditText edtSenha;
    private DadosDialogListener dadosDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        myDatabase = new DataBaseHelper(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        setCancelable(false);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);


        builder.setView(view)
                .setTitle("Login ou Cadastro")
                .setMessage("Escolha fazer login ou cadastrar um novo usuário!")
                .setCancelable(false)
                .setNeutralButton("Cadastrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        inserirUsuarios();
                        String nome = edtNome.getText().toString();
                        String senha = edtSenha.getText().toString();
                        dadosDialogListener.obterDadosDialog(nome, senha);
                    }
                })
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nome = edtNome.getText().toString();
                        String senha = edtSenha.getText().toString();
                        dadosDialogListener.obterDadosDialog(nome, senha);
                    }
                });

        edtNome = view.findViewById(R.id.edtNome);
        edtSenha = view.findViewById(R.id.edtSenha);

        return builder.create();
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            dadosDialogListener = (DadosDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DadosDialogListenerLogin");
        }
    }

    public interface DadosDialogListener {
        void obterDadosDialog(String nome, String senha);
    }


    public void inserirUsuarios(){
        myDatabase.inserirDadosCadastro(edtNome.getText().toString(), edtSenha.getText().toString(),
                "000", "0", "1", "0", "0","1");
    }
}
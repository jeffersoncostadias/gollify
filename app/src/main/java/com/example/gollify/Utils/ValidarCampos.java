package com.example.gollify.Utils;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class ValidarCampos {
    public static boolean validarCamposVazios(View view, String pMensagem) {
        if (view instanceof EditText) {
            EditText edText = (EditText) view;
            Editable text = edText.getText();
            if (text != null) {
                String strText = text.toString();
                if (!TextUtils.isEmpty(strText)) {
                    return true;
                }
            }
            // em qualquer outra condição é gerado um erro
            edText.setError(pMensagem);
            edText.setFocusable(true);
            edText.requestFocus();
            return false;
        }
        return false;
    }
}

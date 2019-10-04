package com.example.gollify.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gollify.DataBase.DataBaseHelper;
import com.example.gollify.Fragments.CotidianoFragment1;
import com.example.gollify.Fragments.CotidianoFragment2;
import com.example.gollify.Fragments.CotidianoFragment3;
import com.example.gollify.Fragments.CotidianoFragment4;
import com.example.gollify.R;

public class CotidianoActivity extends FragmentActivity {
    DataBaseHelper myDatabase;
    private boolean foiClicado[] = new boolean[6];
    String nome, id;
    FragmentManager fm = getSupportFragmentManager();
    int cont = 1;
    Button btnProximo, btnAnterior, btnConcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotidiano);

        myDatabase = new DataBaseHelper(this);
        btnProximo = findViewById(R.id.btnProximo);
        btnAnterior = findViewById(R.id.btnAnterior);
        btnConcluir = findViewById(R.id.btnConcluir);
        btnConcluir.setVisibility(View.INVISIBLE);

        //inicializaVetores();

        obterValoresDeMainActivity();
        mostrarInformacao();

        if(savedInstanceState == null){
            CotidianoFragment1 fragment1 = new CotidianoFragment1();
            FragmentTransaction ft = fm.beginTransaction();

            // OBTER  DESTA ACTIVITY OS PARAMETROS ID USUÁRIO E PASSAR PARA O FRAGMENT 1
            Bundle bundle = new Bundle();
            bundle.putString("ID", id);
            bundle.putString("NOME", nome);
            fragment1.setArguments(bundle);
            // FIM
            ft.add(R.id.contentSpeech, fragment1, "fragment1");
            ft.commit();
        }


        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cont++;

                if(cont > 4){
                    cont = 4;
                } else if (cont == 2){
                    Toast.makeText(getApplication(), " CONT: "+cont, Toast.LENGTH_SHORT).show();
                    FragmentTransaction ft = fm.beginTransaction();
                    CotidianoFragment2 fragment2 = (CotidianoFragment2) fm.findFragmentByTag("fragment2");
                    if(fragment2 == null) {
                        fragment2 = new CotidianoFragment2();
                        // OBTER DA DESTA ACTIVITY OS PARAMETROS ID USUÁRIO E PASSAR PARA O FRAGMENT 2
                        Bundle bundle = new Bundle();
                        bundle.putString("ID", id);
                        bundle.putString("NOME", nome);
                        fragment2.setArguments(bundle);
                        // FIM
                    }
                    ft.replace(R.id.contentSpeech, fragment2, "fragment2");
                    ft.commit();
                } else if (cont == 3) {
                    Toast.makeText(getApplication(), " CONT: " + cont, Toast.LENGTH_SHORT).show();
                    FragmentTransaction ft = fm.beginTransaction();
                    CotidianoFragment3 fragment3 = (CotidianoFragment3) fm.findFragmentByTag("fragment3");
                    if (fragment3 == null) {
                        fragment3 = new CotidianoFragment3();
                        // OBTER DA DESTA ACTIVITY OS PARAMETROS ID USUÁRIO E PASSAR PARA O FRAGMENT 3
                        Bundle bundle = new Bundle();
                        bundle.putString("ID", id);
                        bundle.putString("NOME", nome);
                        fragment3.setArguments(bundle);
                        // FIM
                    }
                    ft.replace(R.id.contentSpeech, fragment3, "fragment3");
                    ft.commit();

                }else if (cont == 4) {
                    Toast.makeText(getApplication(), " CONT: " + cont, Toast.LENGTH_SHORT).show();
                    FragmentTransaction ft = fm.beginTransaction();
                    CotidianoFragment4 fragment4 = (CotidianoFragment4) fm.findFragmentByTag("fragment4");
                    if (fragment4 == null) {
                        fragment4 = new CotidianoFragment4();
                        // OBTER DA DESTA ACTIVITY OS PARAMETROS ID USUÁRIO E PASSAR PARA O FRAGMENT 4
                        Bundle bundle = new Bundle();
                        bundle.putString("ID", id);
                        bundle.putString("NOME", nome);
                        fragment4.setArguments(bundle);
                        // FIM
                    }
                    ft.replace(R.id.contentSpeech, fragment4, "fragment4");
                    ft.commit();
                    btnProximo.setVisibility(View.INVISIBLE);
                    btnConcluir.setVisibility(View.VISIBLE);

                }

            }


        });


        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  cont--;
               if(cont < 0){
                   cont = 1;
               } else if (cont == 1){
                    cont = 1;
                    Toast.makeText(getApplication(), " CONT: "+cont, Toast.LENGTH_SHORT).show();
                    FragmentTransaction ft = fm.beginTransaction();
                    CotidianoFragment1 fragment1 = (CotidianoFragment1) fm.findFragmentByTag("fragment1");
                    if(fragment1 == null) {
                        fragment1 = new CotidianoFragment1();
                        // OBTER DA DESTA ACTIVITY OS PARAMETROS ID USUÁRIO E PASSAR PARA O FRAGMENT 1
                        Bundle bundle = new Bundle();
                        bundle.putString("ID", id);
                        bundle.putString("NOME", nome);
                        fragment1.setArguments(bundle);
                        // FIM

                    }

                    ft.replace(R.id.contentSpeech, fragment1, "fragment1");
                    ft.commit();
                    cont = 1;
                } else if (cont == 2){
                   FragmentTransaction ft = fm.beginTransaction();
                   CotidianoFragment2 fragment2 = new CotidianoFragment2();
                   fragment2 = new CotidianoFragment2();
                   // OBTER DA DESTA ACTIVITY OS PARAMETROS ID USUÁRIO E PASSAR PARA O FRAGMENT 2
                   Bundle bundle = new Bundle();
                   bundle.putString("ID", id);
                   bundle.putString("NOME", nome);
                   fragment2.setArguments(bundle);
                   // FIM
                   ft.replace(R.id.contentSpeech, fragment2, "fragment2");
                   ft.commit();

               } else if (cont == 3) {
                    Toast.makeText(getApplication(), " CONT: " + cont, Toast.LENGTH_SHORT).show();
                    FragmentTransaction ft = fm.beginTransaction();
                    CotidianoFragment3 fragment3 = (CotidianoFragment3) fm.findFragmentByTag("fragment3");
                    if (fragment3 == null) {
                        fragment3 = new CotidianoFragment3();
                        // OBTER DA DESTA ACTIVITY OS PARAMETROS ID USUÁRIO E PASSAR PARA O FRAGMENT 3
                        Bundle bundle = new Bundle();
                        bundle.putString("ID", id);
                        bundle.putString("NOME", nome);
                        fragment3.setArguments(bundle);
                        // FIM
                    }
                    ft.replace(R.id.contentSpeech, fragment3, "fragment3");
                    ft.commit();
                   btnProximo.setVisibility(View.VISIBLE);
                   btnConcluir.setVisibility(View.INVISIBLE);

                }

            }
        });


        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("msg", "Lição Finalizada");
                setResult(1, intent);
                finish();
            }
        });


    }  // FIM MÉTODO ON CREATE



    public void mostrarInformacao(){

        View toastView = getLayoutInflater().inflate(R.layout.toast_custom_b, null);
        Toast toast = new Toast(getApplicationContext());
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0,0);
        toast.show();

    }



    public void obterValoresDeMainActivity(){
        //OBTER DA MAIN ACTIVITY OS PARAMETROS ID USUÁRIO
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        nome = intent.getStringExtra("NOME");
        Log.i("Tela Speech ID: ", id);
        Log.i("Tela Speech NOME: ", nome);
    } // FIM

    public void inicializaVetores(){
        // INICIALIZAR O  ARRAY COM O VALOR FALSE
        for (int cont = 0; cont < 6; cont++) {
            foiClicado[cont] = false;
            Log.i("Foi clicado: " + cont + " |", String.valueOf(foiClicado[cont]));
        }
    }// FIM





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {


        }catch (Exception exc) {

        }
     }


     // MÉTODO PARA FINALIZAR A ACTIVITY E DAR UM RESULT
    public void concluir(View view){
        Intent intent = new Intent();
        intent.putExtra("msg", "Lição Finalizada");
        setResult(1, intent);
        finish();
    }// FIM

}



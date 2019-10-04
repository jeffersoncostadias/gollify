package com.example.gollify.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gollify.DataBase.DataBaseHelper;
import com.example.gollify.Fragments.TabFragment1;
import com.example.gollify.Fragments.TabFragment2;
import com.example.gollify.Fragments.TelaLoginDialog;
import com.example.gollify.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabFragment1.DadosDialogListenerLogin,
        TabFragment1.CancelarDialogListenerLogin, TabFragment2.DadosDialogListenerCadastro, TabFragment2.CancelarDialogListenerCadastro, TelaLoginDialog.DadosDialogListener {

    DataBaseHelper myDatabase;
    private String nome, senha, id;
    private TextView txtNavNome, txtNavNível;
    public int pontos;
    TabbedDialog telaDialogTab;
    View navViewHead;

    public static final int CONSTANTE_TELA_1 = 1;
    public static final int CONSTANTE_TELA_2 = 2;
    public static final int CONSTANTE_TELA_3 = 3;
    public static final int CONSTANTE_TELA_4 = 4;
    public static final int CONSTANTE_TELA_5 = 5;
    public static final int CONSTANTE_TELA_6 = 6;
    public static final int CONSTANTE_TELA_7 = 7;
    public static final int CONSTANTE_TELA_8 = 8;
    public static final int CONSTANTE_TELA_9 = 9;
    public static final int CONSTANTE_TELA_10 = 10;
    public static final int CONSTANTE_TELA_11 = 11;
    public static final int CONSTANTE_TELA_12 = 12;
    public static final int CONSTANTE_TELA_13 = 13;
    public static final int CONSTANTE_TELA_14 = 14;

    int msgResult;
    ImageView imvNumeros, imvObjetos, imvExtra1, imvExtra2, imvExtra3,imvCotidiano,imvAmizade,imvNatureza, imvTecnologia;
    CardView cardView1,cardView2, cardView3, cardview4,cardview5,cardview6,cardview7,cardView8,cardView9,cardView10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tela Principal");
        setSupportActionBar(toolbar);
         myDatabase = new DataBaseHelper(this);
         telaDialogTab = new TabbedDialog();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navViewHead =  navigationView.inflateHeaderView(R.layout.nav_header_main);
        navigationView.setNavigationItemSelectedListener(this);

        //chamarDialog();

        txtNavNome = findViewById(R.id.txtNavNome);
        txtNavNível = findViewById(R.id.txtNavNivel);

        imvNumeros = findViewById(R.id.imvNumeros);
        imvObjetos = findViewById(R.id.imvObjetos);
        imvCotidiano = findViewById(R.id.imvCotidiano);
        imvAmizade = findViewById(R.id.imvAmizade);
        imvNatureza = findViewById(R.id.imvNatureza);
        imvTecnologia = findViewById(R.id.imvTecnologia);

        imvExtra1 = findViewById(R.id.imvExtra1);
        imvExtra2 = findViewById(R.id.imvExtra2);
        imvExtra3 = findViewById(R.id.imvExtra3);

        cardView1 =  findViewById(R.id.groupAnimal);
        cardView2 =  findViewById(R.id.groupNumeros);
        cardView3 =  findViewById(R.id.groupObjetosLar);
        cardview4 =  findViewById(R.id.extra1);
        cardview5 =  findViewById(R.id.falarSobreObjetos);
        cardview6 =  findViewById(R.id.falarSobreAmizade);
        cardview7 =  findViewById(R.id.extra2);
        cardView8 =  findViewById(R.id.escreverNatureza);
        cardView9 =  findViewById(R.id.escreverTecnologia);
        cardView10 =  findViewById(R.id.extra3);

        cardView2.setEnabled(false);
        cardView3.setEnabled(false);
        cardview5.setEnabled(false);
        cardview6.setEnabled(false);
        cardView8.setEnabled(false);
        cardView9.setEnabled(false);
        statusCardViewsFasesExtras();
        //statusCardViews();
        chamarDialogTab();
     //   startTask();

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AnimaisActivity.class);
                intent.putExtra("ID", obterIdUsuario() );
                intent.putExtra("NOME", obterNomeUsuario() );
                startActivityForResult(intent, CONSTANTE_TELA_1);
                //startActivity(intent);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), CardinalActivity.class);
                intent.putExtra("ID", obterIdUsuario() );
                intent.putExtra("NOME", obterNomeUsuario() );
                startActivityForResult(intent, CONSTANTE_TELA_2);
                //startActivity(intent);
            }
        });


        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), ObjetosActivity.class);
                intent.putExtra("ID", obterIdUsuario() );
                intent.putExtra("NOME", obterNomeUsuario() );
                startActivityForResult(intent, CONSTANTE_TELA_3);
                // startActivity(intent);
            }
        });

        cardview4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(getApplicationContext(),"teste4",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(), Extra1Activity.class);
                intent.putExtra("ID", obterIdUsuario() );
                intent.putExtra("NOME", obterNomeUsuario() );
                startActivityForResult(intent, CONSTANTE_TELA_4);
            }
        });


        cardview5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"teste5",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(), CotidianoActivity.class);
                intent.putExtra("ID", obterIdUsuario() );
                intent.putExtra("NOME", obterNomeUsuario() );
                startActivityForResult(intent, CONSTANTE_TELA_5);
            }
        });

        cardview6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"teste5",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(), AmizadeActivity.class);
                intent.putExtra("ID", obterIdUsuario() );
                intent.putExtra("NOME", obterNomeUsuario() );
                startActivityForResult(intent, CONSTANTE_TELA_6);
            }
        });

        cardview7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"teste5",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(), Extra2Activity.class);
                intent.putExtra("ID", obterIdUsuario() );
                intent.putExtra("NOME", obterNomeUsuario() );
                startActivityForResult(intent, CONSTANTE_TELA_7);
            }
        });

        cardView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"teste5",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(), NaturezaActivity.class);
                intent.putExtra("ID", obterIdUsuario() );
                intent.putExtra("NOME", obterNomeUsuario() );
                startActivityForResult(intent, CONSTANTE_TELA_8);
            }
        });

        cardView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"teste5",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(), TecnologiaActivity.class);
                intent.putExtra("ID", obterIdUsuario() );
                intent.putExtra("NOME", obterNomeUsuario() );
                startActivityForResult(intent, CONSTANTE_TELA_9);
            }
        });

        cardView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"teste5",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(), Extra3Activity.class);
                intent.putExtra("ID", obterIdUsuario() );
                intent.putExtra("NOME", obterNomeUsuario() );
                startActivityForResult(intent, CONSTANTE_TELA_10);
            }
        });





    } // FIM METODO ON CREATE



    public void statusCardViewsFasesExtras(){
        int num = mostrarValorFaseAtual();
       // Log.i("StatusCard Main", "Texto "+num);
        switch (mostrarValorFaseAtual()){

            case 0 :
                cardview4.setEnabled(false);
                cardview7.setEnabled(false);
                cardView10.setEnabled(false);
                break;

            case 1 :
                cardview4.setEnabled(true);
                imvExtra1.setImageResource(R.drawable.icone_cubo);
                imvExtra1.setBackgroundResource(R.drawable.color_cardview4);
                cardview7.setEnabled(false);
                cardView10.setEnabled(false);
                break;

            case 2 :
                cardview4.setEnabled(true);
                imvExtra1.setImageResource(R.drawable.icone_cubo);
                imvExtra1.setBackgroundResource(R.drawable.circulo_branco);
                cardview7.setEnabled(true);
                imvExtra2.setImageResource(R.drawable.icone_pote_ouro);
                imvExtra2.setBackgroundResource(R.drawable.color_cardview7);
                cardView10.setEnabled(false);
                break;

            case 3 :
                cardview4.setEnabled(true);
                imvExtra1.setImageResource(R.drawable.icone_cubo);
                imvExtra1.setBackgroundResource(R.drawable.color_cardview4);
                cardview7.setEnabled(true);
                imvExtra2.setImageResource(R.drawable.icone_pote_ouro);
                imvExtra2.setBackgroundResource(R.drawable.color_cardview7);
                cardView10.setEnabled(true);
                imvExtra3.setImageResource(R.drawable.icone_bau);
                imvExtra3.setBackgroundResource(R.drawable.color_cardview10);
                break;
        }

    }


    public void statusCardViews(){
        int num = mostrarValorCardViews();
        Log.i("StatusCard Main", "Texto "+num);
        switch (mostrarValorCardViews()){

            case 1 :
                cardView2.setEnabled(false);
                cardView3.setEnabled(false);
                cardview5.setEnabled(false);
                cardview6.setEnabled(false);
                cardView8.setEnabled(false);
                cardView9.setEnabled(false);

                break;

            case 2 :
                cardView2.setEnabled(true);
                imvNumeros.setImageResource(R.drawable.icone_numeros);
                imvNumeros.setBackgroundResource(R.drawable.color_cardview2);
                cardView3.setEnabled(false);
                cardview5.setEnabled(false);
                cardview6.setEnabled(false);
                cardView8.setEnabled(false);
                cardView9.setEnabled(false);
                break;

            case 3 :
                cardView2.setEnabled(true);
                imvNumeros.setImageResource(R.drawable.icone_numeros);
                imvNumeros.setBackgroundResource(R.drawable.color_cardview2);
                cardView3.setEnabled(true);
                imvObjetos.setImageResource(R.drawable.icone_objetolar);
                imvObjetos.setBackgroundResource(R.drawable.color_cardview3);
                cardview5.setEnabled(false);
                cardview6.setEnabled(false);
                cardView8.setEnabled(false);
                cardView9.setEnabled(false);
                break;

            case 5 :
                cardView2.setEnabled(true);
                imvNumeros.setImageResource(R.drawable.icone_numeros);
                imvNumeros.setBackgroundResource(R.drawable.color_cardview2);
                cardView3.setEnabled(true);
                imvObjetos.setImageResource(R.drawable.icone_objetolar);
                imvObjetos.setBackgroundResource(R.drawable.color_cardview3);
                cardview5.setEnabled(true);
                imvCotidiano.setImageResource(R.drawable.icone_falar_coisas);
                imvCotidiano.setBackgroundResource(R.drawable.color_cardview5);
                cardview6.setEnabled(false);
                cardView8.setEnabled(false);
                cardView9.setEnabled(false);
                break;

            case 6 :
                cardView2.setEnabled(true);
                imvNumeros.setImageResource(R.drawable.icone_numeros);
                imvNumeros.setBackgroundResource(R.drawable.color_cardview2);
                cardView3.setEnabled(true);
                imvObjetos.setImageResource(R.drawable.icone_objetolar);
                imvObjetos.setBackgroundResource(R.drawable.color_cardview3);
                cardview5.setEnabled(true);
                imvCotidiano.setImageResource(R.drawable.icone_falar_coisas);
                imvCotidiano.setBackgroundResource(R.drawable.color_cardview5);
                cardview6.setEnabled(true);
                imvAmizade.setImageResource(R.drawable.icone_falar_amizade);
                imvAmizade.setBackgroundResource(R.drawable.color_cardview6);
                cardView8.setEnabled(false);
                cardView9.setEnabled(false);
                break;

            case 8 :
                cardView2.setEnabled(true);
                imvNumeros.setImageResource(R.drawable.icone_numeros);
                imvNumeros.setBackgroundResource(R.drawable.color_cardview2);
                cardView3.setEnabled(true);
                imvObjetos.setImageResource(R.drawable.icone_objetolar);
                imvObjetos.setBackgroundResource(R.drawable.color_cardview3);
                cardview5.setEnabled(true);
                imvCotidiano.setImageResource(R.drawable.icone_falar_coisas);
                imvCotidiano.setBackgroundResource(R.drawable.color_cardview5);
                cardview6.setEnabled(true);
                imvAmizade.setImageResource(R.drawable.icone_falar_amizade);
                imvAmizade.setBackgroundResource(R.drawable.color_cardview6);
                cardView8.setEnabled(true);
                imvNatureza.setImageResource(R.drawable.icone_natureza);
                imvNatureza.setBackgroundResource(R.drawable.color_cardview8);
                cardView9.setEnabled(false);
                break;

            case 9:
                cardView2.setEnabled(true);
                imvNumeros.setImageResource(R.drawable.icone_numeros);
                imvNumeros.setBackgroundResource(R.drawable.color_cardview2);
                cardView3.setEnabled(true);
                imvObjetos.setImageResource(R.drawable.icone_objetolar);
                imvObjetos.setBackgroundResource(R.drawable.color_cardview3);
                cardview5.setEnabled(true);
                imvCotidiano.setImageResource(R.drawable.icone_falar_coisas);
                imvCotidiano.setBackgroundResource(R.drawable.color_cardview5);
                cardview6.setEnabled(true);
                imvAmizade.setImageResource(R.drawable.icone_falar_amizade);
                imvAmizade.setBackgroundResource(R.drawable.color_cardview6);
                cardView8.setEnabled(true);
                imvNatureza.setImageResource(R.drawable.icone_natureza);
                imvNatureza.setBackgroundResource(R.drawable.color_cardview8);
                cardView9.setEnabled(true);
                imvTecnologia.setImageResource(R.drawable.icone_tecnologia);
                imvTecnologia.setBackgroundResource(R.drawable.color_cardview9);
                break;

        }

    }


    // MÉTODO PARA MOSTRAR AS FASES OBTIDAS DO BANCO DE DADPS
    public int mostrarValorFaseAtual() {
        // String id = "1";
        Cursor res = myDatabase.obterDadosByNome(nome);
        int data = 0;
        if (res.moveToFirst()) {
            data = res.getInt(7);
        }
        return data;
    } // FIM



    // MÉTODO PARA MOSTRAR OS CARDVIEWS OBTIDAS DO BANCO DE DADPS
    public int mostrarValorCardViews() {
        // String id = "1";
        Cursor res = myDatabase.obterDadosByNome(nome);
        int data = 0;
        if (res.moveToFirst()) {
            data = res.getInt(8);
        }
        return data;
    } // FIM


    private void startTask() {
        new Task().execute();
    }



    private class Task extends AsyncTask<Void, Void, Void> {
        private String text;
        String usuarioLogado = obterNomeUsuario();
        String nivelusuarioLogado = obterNivelUsuario();

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("test","doInBackground");
            this.text = obterNomeUsuario();
            return null;
        }

        protected void onPostExecute(Void result) {
            Log.d("test","onPostExecute");
            txtNavNome = navViewHead.findViewById(R.id.txtNavNome);
            txtNavNome.setText("Usuário: "+usuarioLogado);

            txtNavNível = navViewHead.findViewById(R.id.txtNavNivel);
            txtNavNível.setText("Nível: "+nivelusuarioLogado);
            statusCardViewsFasesExtras();
            statusCardViews();
            //  Log.i("ON POST EXECUTE", "Aqui: "+ usuarioLogado);
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_sair) {
               finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
       // txtNavNome.setText(nome.toString());
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            // Handle the camera action
            onRestart();
           // onRestart();
        } else if (id == R.id.nav_ranking) {
            Intent intent = new Intent(getApplicationContext(), RankingActivity.class);
            intent.putExtra("ID", obterIdUsuario() );
            intent.putExtra("NOME", obterNomeUsuario() );
            startActivityForResult(intent, CONSTANTE_TELA_11);

        } else if (id == R.id.nav_conquista) {
            Intent intent = new Intent(getApplicationContext(), ConquistaActivity.class);
            intent.putExtra("ID", obterIdUsuario() );
            intent.putExtra("NOME", obterNomeUsuario() );
            startActivityForResult(intent, CONSTANTE_TELA_12);
        } else if (id == R.id.nav_fases) {
            Intent intent = new Intent(getApplicationContext(), FasesActivity.class);
            intent.putExtra("ID", obterIdUsuario() );
            intent.putExtra("NOME", obterNomeUsuario() );
            startActivityForResult(intent, CONSTANTE_TELA_13);
        } else if (id == R.id.nav_sobre) {
            Intent intent = new Intent(getApplicationContext(), SobreActivity.class);
            intent.putExtra("ID", obterIdUsuario() );
            intent.putExtra("NOME", obterNomeUsuario() );
            startActivityForResult(intent, CONSTANTE_TELA_14);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    protected void onActivityResult(int codigoTela, int resultado, Intent intent){

        if(codigoTela == CONSTANTE_TELA_1){
            try {
                Bundle params = intent.getExtras();
                if(params != null){
                    String msg = params.getString("msg");
                    msgResult = codigoTela;
                    startTask();
                  //  statusCardViews();
                  //  Toast.makeText(this, "Tela 1 -> Resultado: "+resultado+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                    // Toast.makeText(this, "Tela 1 -> Resultado: "+msgResult+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                }
            }catch (Exception exc) {

            }

        } else if(codigoTela == CONSTANTE_TELA_2){
            try {
                Bundle params = intent.getExtras();
                if(params != null){
                    String msg = params.getString("msg");
                    msgResult = codigoTela;
                    startTask();

                    //  statusCardViews();
                  //  Toast.makeText(this, "Tela 2 -> Resultado: "+resultado+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                    // Toast.makeText(this, "Tela 1 -> Resultado: "+msgResult+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                }
            }catch (Exception exc) {

            }
        }else if(codigoTela == CONSTANTE_TELA_3){
            try {
                Bundle params = intent.getExtras();
                if(params != null){
                    String msg = params.getString("msg");
                    msgResult = codigoTela;
                    startTask();

                    // statusCardViews();
                   // Toast.makeText(this, "Tela 3 -> Resultado: "+resultado+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                    // Toast.makeText(this, "Tela 1 -> Resultado: "+msgResult+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                }
            }catch (Exception exc) {

            }
        }else if(codigoTela == CONSTANTE_TELA_4) {
            try {
                Bundle params = intent.getExtras();
                if (params != null) {
                    String msg = params.getString("msg");
                    msgResult = codigoTela;
                    startTask();

                  //  statusCardViews();
                  //  Toast.makeText(this, "Tela 4 -> Resultado: " + resultado + " | Msg: " + msg, Toast.LENGTH_LONG).show();
                    // Toast.makeText(this, "Tela 1 -> Resultado: "+msgResult+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                }
            } catch (Exception exc) {

            }

        }else if(codigoTela == CONSTANTE_TELA_5) {
            try {
                Bundle params = intent.getExtras();
                if (params != null) {
                    String msg = params.getString("msg");
                    msgResult = codigoTela;
                    startTask();

                   // statusCardViews();
                  //  Toast.makeText(this, "Tela 5 -> Resultado: " + resultado + " | Msg: " + msg, Toast.LENGTH_LONG).show();
                    // Toast.makeText(this, "Tela 1 -> Resultado: "+msgResult+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                }
            } catch (Exception exc) {

            }

        }else if(codigoTela == CONSTANTE_TELA_6) {
            try {
                Bundle params = intent.getExtras();
                if (params != null) {
                    String msg = params.getString("msg");
                    msgResult = codigoTela;
                    startTask();

                    //statusCardViews();
                  //  Toast.makeText(this, "Tela 6 -> Resultado: " + resultado + " | Msg: " + msg, Toast.LENGTH_LONG).show();
                    // Toast.makeText(this, "Tela 1 -> Resultado: "+msgResult+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                }
            } catch (Exception exc) {

            }

        }else if(codigoTela == CONSTANTE_TELA_7) {
            try {
                Bundle params = intent.getExtras();
                if (params != null) {
                    String msg = params.getString("msg");
                    msgResult = codigoTela;
                    startTask();

                    // statusCardViews();
                   // Toast.makeText(this, "Tela 7 -> Resultado: " + resultado + " | Msg: " + msg, Toast.LENGTH_LONG).show();
                    // Toast.makeText(this, "Tela 1 -> Resultado: "+msgResult+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                }
            } catch (Exception exc) {

            }

        } else if(codigoTela == CONSTANTE_TELA_8) {
            try {
                Bundle params = intent.getExtras();
                if (params != null) {
                    String msg = params.getString("msg");
                    msgResult = codigoTela;
                    startTask();

                    // statusCardViews();
                  //  Toast.makeText(this, "Tela 8 -> Resultado: " + resultado + " | Msg: " + msg, Toast.LENGTH_LONG).show();
                    // Toast.makeText(this, "Tela 1 -> Resultado: "+msgResult+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                }
            } catch (Exception exc) {

            }

        }else if(codigoTela == CONSTANTE_TELA_9) {
            try {
                Bundle params = intent.getExtras();
                if (params != null) {
                    String msg = params.getString("msg");
                    msgResult = codigoTela;
                    startTask();

                    // statusCardViews();
                  //  Toast.makeText(this, "Tela 9 -> Resultado: " + resultado + " | Msg: " + msg, Toast.LENGTH_LONG).show();
                    // Toast.makeText(this, "Tela 1 -> Resultado: "+msgResult+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                }
            } catch (Exception exc) {

            }

        }else if(codigoTela == CONSTANTE_TELA_10) {
            try {
                Bundle params = intent.getExtras();
                if (params != null) {
                    String msg = params.getString("msg");
                    msgResult = codigoTela;
                    startTask();

                   // statusCardViews();
                   // Toast.makeText(this, "Tela 10 -> Resultado: " + resultado + " | Msg: " + msg, Toast.LENGTH_LONG).show();
                    // Toast.makeText(this, "Tela 1 -> Resultado: "+msgResult+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                }
            } catch (Exception exc) {

            }

        } else if(codigoTela == CONSTANTE_TELA_11) {
            try {
                Bundle params = intent.getExtras();
                if (params != null) {
                    String msg = params.getString("msg");
                    msgResult = codigoTela;
                    startTask();

                   // statusCardViews();
                    //Toast.makeText(this, "Tela 11 -> Resultado: " + resultado + " | Msg: " + msg, Toast.LENGTH_LONG).show();
                    // Toast.makeText(this, "Tela 1 -> Resultado: "+msgResult+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                }
            } catch (Exception exc) {

            }

        }else if(codigoTela == CONSTANTE_TELA_12) {
            try {
                Bundle params = intent.getExtras();
                if (params != null) {
                    String msg = params.getString("msg");
                    msgResult = codigoTela;
                    startTask();

                    // statusCardViews();
                   // Toast.makeText(this, "Tela 12 -> Resultado: " + resultado + " | Msg: " + msg, Toast.LENGTH_LONG).show();
                    // Toast.makeText(this, "Tela 1 -> Resultado: "+msgResult+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                }
            } catch (Exception exc) {

            }
        }else if(codigoTela == CONSTANTE_TELA_13) {
            try {
                Bundle params = intent.getExtras();
                if (params != null) {
                    String msg = params.getString("msg");
                    msgResult = codigoTela;
                    startTask();
                 //   Toast.makeText(this, "Tela 13 -> Resultado: " + resultado + " | Msg: " + msg, Toast.LENGTH_LONG).show();
                    // Toast.makeText(this, "Tela 1 -> Resultado: "+msgResult+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                }
            } catch (Exception exc) {

            }
        }else if(codigoTela == CONSTANTE_TELA_14) {
            try {
                Bundle params = intent.getExtras();
                if (params != null) {
                    String msg = params.getString("msg");
                    msgResult = codigoTela;
                    startTask();

                    //  statusCardViews();

                 //   Toast.makeText(this, "Tela 14 -> Resultado: " + resultado + " | Msg: " + msg, Toast.LENGTH_LONG).show();
                    // Toast.makeText(this, "Tela 1 -> Resultado: "+msgResult+" | Msg: "+msg, Toast.LENGTH_LONG).show();
                }
            } catch (Exception exc) {

            }
        }

    }


    public void onRestart(){
        super.onRestart();
        statusCardViews();
        //Log.i(CATEGORIA, getClassName()+".onRestart();");
        //Toast.makeText(this, " METODO RESTART"+msgResult, Toast.LENGTH_SHORT).show();
        if(msgResult == CONSTANTE_TELA_1 ){
            //  Toast.makeText(this, " Funcionou", Toast.LENGTH_SHORT).show();
         //   imvNumeros.setImageResource(R.drawable.icone_numeros);
          //  imvNumeros.setBackgroundResource(R.drawable.circulo_rosa);
         //   adquirirFase2.setEnabled(true);
        } else if(msgResult == CONSTANTE_TELA_2 ){
            //  Toast.makeText(this, " Funcionou", Toast.LENGTH_SHORT).show();
           // imvObjetos.setImageResource(R.drawable.icone_objetolar);
          //  imvObjetos.setBackgroundResource(R.drawable.circulo_verde);
          //  adquirirFase3.setEnabled(true);
        }

        else if(msgResult == CONSTANTE_TELA_3 ){
            //  Toast.makeText(this, " Funcionou", Toast.LENGTH_SHORT).show();
           // imvExtra1.setImageResource(R.drawable.icone_cubo);
           // imvExtra1.setBackgroundResource(R.drawable.circulo_branco);
           // cardview4.setEnabled(true);
        }
        else if(msgResult == CONSTANTE_TELA_4 ){
            //  Toast.makeText(this, " Funcionou", Toast.LENGTH_SHORT).show();
            //imvExtra.setImageResource(R.drawable.icone_cubo);
            // imvExtra.setBackgroundResource(R.drawable.circulo_branco);
            //cardview4.setEnabled(true);
        }
        else if(msgResult == CONSTANTE_TELA_5 ){
            //  Toast.makeText(this, " Funcionou", Toast.LENGTH_SHORT).show();
            //imvExtra.setImageResource(R.drawable.icone_cubo);
            // imvExtra.setBackgroundResource(R.drawable.circulo_branco);
            //cardview4.setEnabled(true);
        }
        else if(msgResult == CONSTANTE_TELA_6 ){
            //  Toast.makeText(this, " Funcionou", Toast.LENGTH_SHORT).show();
            //imvExtra.setImageResource(R.drawable.icone_cubo);
            // imvExtra.setBackgroundResource(R.drawable.circulo_branco);
            //cardview4.setEnabled(true);
        }
        else if(msgResult == CONSTANTE_TELA_7 ){
            //  Toast.makeText(this, " Funcionou", Toast.LENGTH_SHORT).show();
            //imvExtra.setImageResource(R.drawable.icone_cubo);
            // imvExtra.setBackgroundResource(R.drawable.circulo_branco);
            //cardview4.setEnabled(true);
        }

        else if(msgResult == CONSTANTE_TELA_8 ){
            //  Toast.makeText(this, " Funcionou", Toast.LENGTH_SHORT).show();
            //imvExtra.setImageResource(R.drawable.icone_cubo);
            // imvExtra.setBackgroundResource(R.drawable.circulo_branco);
            //cardview4.setEnabled(true);
        }

        else if(msgResult == CONSTANTE_TELA_9 ){
            //  Toast.makeText(this, " Funcionou", Toast.LENGTH_SHORT).show();
            //imvExtra.setImageResource(R.drawable.icone_cubo);
            // imvExtra.setBackgroundResource(R.drawable.circulo_branco);
            //cardview4.setEnabled(true);
        }

        else if(msgResult == CONSTANTE_TELA_10 ){
            //  Toast.makeText(this, " Funcionou", Toast.LENGTH_SHORT).show();
            //imvExtra.setImageResource(R.drawable.icone_cubo);
            // imvExtra.setBackgroundResource(R.drawable.circulo_branco);
            //cardview4.setEnabled(true);
        }


    }


    public void chamarDialog() {
        TelaLoginDialog telaDialog = new TelaLoginDialog();
        telaDialog.show(getSupportFragmentManager(), "tela dialog");
    }

    public void chamarDialogTab() {
        telaDialogTab.show(getSupportFragmentManager(), "tela dialog");
    }

    public void fecharDialogTab() {
        telaDialogTab.dismiss();

    }





    public  void mostrarDadosTxtPontos(){
        String id = "1";
        Cursor res = myDatabase.obterDados(id);
        String data = null;
        if (res.moveToFirst()) {
            data = res.getString(0);
            //  txtPontos.setText(data);
        }

    }

    public  String obterIdUsuario(){
        // String id = "1";
        Cursor res = myDatabase.obterDadosByNome(nome);
        Log.i("Usuario por DB NOME", nome);
        String data = null;
        if (res.moveToFirst()) {
            data = res.getString(0);
            //txtPontos.setText(data);
        }
        Log.i("Usuario por DB ID", data);
        return data;

    }

    public  String obterNomeUsuario(){
        // String id = "1";
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

    public  String obterNivelUsuario(){
        // String id = "1";
        Cursor res = myDatabase.obterDadosByNome(nome);
        // Log.i("Metodo obter usuario", nome);
        String data = null;
        if (res.moveToFirst()) {
            data = res.getString(5);
            //txtPontos.setText(data);
        }
        //Log.i("Tela MainActivity ID", data);
        return data;

    }


    public  void mostrarDadosEmDialog(String nome){
        String id = "1";
        Cursor res = myDatabase.obterDadosByNome(nome);
        //  Cursor res = myDatabase.obterDados(id);
        String data = null;
        if (res.moveToFirst()) {
            data = "Id:"+res.getString(0)+"\n\n"+
                    "Nome: "+ res.getString(1)+"\n\n"+
                    "Senha: "+ res.getString(2)+"\n\n"+
                    "Pontos: "+ res.getString(3)+"\n\n"+
                    "Moedas: "+ res.getString(4)+"\n\n"+
                    "Nivel: "+ res.getString(5)+"\n\n"+
                    "Progresso: "+ res.getString(6)+"\n\n"+
                    "Fases: "+ res.getString(7)+"\n\n"+
                    "Status CardViews: "+ res.getString(8)+"\n\n";
        }
        showMessage("Resultado", data);
    }



    private void showMessage(String title, String message) {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void obterDadosDialogLogin(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;

      //  mostrarDadosEmDialog(nome);


        startTask();

        Toast.makeText(this,"Seja bem Vindo!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelarDadosDialogLogin(boolean cancelar) {
      fecharDialogTab();
    }

    @Override
    public void obterDadosDialogCadastro(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;

        //mostrarDadosEmDialog(nome);

        Toast.makeText(this,"Usuário Cadastrado!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelarDadosDialogCadastro(boolean cancelar) {
        fecharDialogTab();
    }



    @Override
    public void obterDadosDialog(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
        Toast.makeText(this,"Seja bem Vindo!", Toast.LENGTH_SHORT).show();
    }
}

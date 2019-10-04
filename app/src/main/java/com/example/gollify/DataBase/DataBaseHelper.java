package com.example.gollify.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gollify.Pojo.Ranking;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
   public final static String DATABASE_NAME = "Gollify.db";
   public final static String TABLE_NAME = "usuario_table";
   public static final String COL_1 = "ID";
   public static final String COL_2 = "NOME";
   public static final String COL_3 = "SENHA";
   public static final String COL_4 = "PONTOS";
   public static final String COL_5 = "MOEDAS";
   public static final String COL_6 = "NIVEL";
   public static final String COL_7 = "PROGRESSO";
   public static final String COL_8 = "FASES";
   public static final String COL_9 = "CARDVIEWS";
   //public static final String COL_3 = "";


   public DataBaseHelper(Context context){
       super(context, DATABASE_NAME, null, 1);
   }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NOME TEXT,SENHA TEXT,PONTOS INTEGER,MOEDAS INTEGER,NIVEL INTEGER,PROGRESSO INTEGER, FASES INTEGER, CARDVIEWS INTERGER)");

      db.execSQL("INSERT INTO usuario_table (NOME, SENHA, PONTOS, MOEDAS, NIVEL, PROGRESSO, FASES, CARDVIEWS) VALUES('admin','123',0,0,1,0,0,1)");
      db.execSQL("INSERT INTO usuario_table (NOME, SENHA, PONTOS, MOEDAS, NIVEL, PROGRESSO, FASES, CARDVIEWS) VALUES('Pedro Silva','123',350,0,1,0,0,1)");
      db.execSQL("INSERT INTO usuario_table (NOME, SENHA, PONTOS, MOEDAS, NIVEL, PROGRESSO, FASES, CARDVIEWS) VALUES('Lucas Dias','123',260,0,1,0,0,1)");
      db.execSQL("INSERT INTO usuario_table (NOME, SENHA, PONTOS, MOEDAS, NIVEL, PROGRESSO, FASES, CARDVIEWS) VALUES('Mateus Castro','123',150,0,1,0,0,1)");
   }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
     onCreate(db);
    }

    public ArrayList<Ranking> listUsuarios(){
        //String sql = "select * from " + TABLE_NAME;
        String sql =  "SELECT * FROM "+TABLE_NAME+" ORDER BY PONTOS DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Ranking> storeRanking = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
               // int id = Integer.parseInt(cursor.getString(0));
                String nome = cursor.getString(1);
                String pontos = cursor.getString(3);
                //storeRanking.add(new Ranking(id, nome, pontos));
                storeRanking.add(new Ranking(nome, pontos));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeRanking;
    }



    public  boolean inserirDadosCadastro(String nome, String senha, String pontos, String moedas, String nivel, String progresso, String fase, String cardviews){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, nome);
        cv.put(COL_3, senha);
        cv.put(COL_4, pontos);
        cv.put(COL_5, moedas);
        cv.put(COL_6, nivel);
        cv.put(COL_7, progresso);
        cv.put(COL_8, fase);
        cv.put(COL_9, cardviews);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) return false;
        else return true;
    }

    public  boolean inserirTodosDados(String nome, String senha, String pontos, String moedas, String nivel, String progresso){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, nome);
        cv.put(COL_3, senha);
        cv.put(COL_4, pontos);
        cv.put(COL_5, moedas);
        cv.put(COL_6, nivel);
        cv.put(COL_7, progresso);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) return false;
        else return true;
    }


    public  boolean inserirDadosPontos(String pontos){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_4, pontos);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) return false;
        else return true;
    }

    public  boolean inserirDadosMoedas(String moedas){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_5, moedas);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) return false;
        else return true;
    }

    public  boolean inserirDadosNivel(String nivel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_6, nivel);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) return false;
        else return true;
    }

    public  boolean inserirDadosProgesso(String progresso){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_7, progresso);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) return false;
        else return true;
    }

    public  boolean inserirDadosFase(String fase){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_8, fase);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) return false;
        else return true;
    }

    public  boolean inserirDadosCardViews(String cardviews){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_9, cardviews);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) return false;
        else return true;
    }

    public Cursor obterDados(String id){
        SQLiteDatabase db = this.getWritableDatabase();
       String query = "SELECT * FROM "+TABLE_NAME+" WHERE ID='"+id+"'";
       Cursor cursor = db.rawQuery(query, null);

       return cursor;
    }


    public Cursor obterDadosByNome(String nome){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE NOME='"+nome+"'";
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public Cursor existemDados(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM "+TABLE_NAME, null);
        return cursor;
    }

    public boolean atualizarTodosDados(String id, String nome, String senha, String pontos){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
       contentValues.put(COL_1, id);
       contentValues.put(COL_2, nome);
       contentValues.put(COL_1, senha);
       contentValues.put(COL_4, pontos);
       db.update(TABLE_NAME, contentValues, "ID=?", new String[]{id});
       return true;
    }

    public boolean atualizarPontos(String id,  String pontos){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_4, pontos);
        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{id});
        return true;
    }

    public boolean atualizarMoedas(String id,  String moedas){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_5, moedas);
        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{id});
        return true;
    }


    public boolean atualizarNivel(String id,  String nivel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_6, nivel);
        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{id});
        return true;
    }

    public boolean atualizarProgresso(String id,  String progresso){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_7, progresso);
        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{id});
        return true;
    }


    public boolean atualizarFase(String id,  String fase){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_8, fase);
        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{id});
        return true;
    }

    public boolean atualizarCardViews(String id,  String cardviews){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_9, cardviews);
        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{id});
        return true;
    }



    public  Integer apagarDados(String id){
       SQLiteDatabase db = this.getWritableDatabase();
       return  db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }

    public  Cursor todosOsDados(){
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);

       return cursor;
    }

}

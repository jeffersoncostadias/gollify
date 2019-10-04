package com.example.gollify.Pojo;

public class Ranking {

    private int id;
    private String nome;
    private String pontos;

    public Ranking(int id, String nome, String pontos) {
        this.id = id;
        this.nome = nome;
        this.pontos = pontos;
    }

    public Ranking(String nome, String pontos) {
        this.nome = nome;
        this.pontos = pontos;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPontos() {
        return pontos;
    }

    public void setPontos(String pontos) {
        this.pontos = pontos;
    }
}

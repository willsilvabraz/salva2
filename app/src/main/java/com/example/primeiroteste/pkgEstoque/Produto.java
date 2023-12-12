package com.example.primeiroteste.pkgEstoque;

import com.google.firebase.database.DatabaseReference;

public class Produto {
    private String nome, id;
    private double valor;
    private int quantidade;

    private DatabaseReference ref;

    public Produto() {
    }

    public Produto(String nome, String id, double valor, int quantidade) {
        this.nome = nome;
        this.id = id;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                '}';
    }




}
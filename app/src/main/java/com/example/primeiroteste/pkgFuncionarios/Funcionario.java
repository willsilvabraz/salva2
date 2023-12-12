package com.example.primeiroteste.pkgFuncionarios;

import com.google.firebase.database.DatabaseReference;

public class Funcionario {
    private String nome, id;
    private double valor;
    private int idade;

    private DatabaseReference ref;

    public Funcionario() {
    }

    public Funcionario(String nome, String id, double valor, int idade) {
        this.nome = nome;
        this.id = id;
        this.valor = valor;
        this.idade = idade;
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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", valor=" + valor +
                ", quantidade=" + idade +
                '}';
    }




}
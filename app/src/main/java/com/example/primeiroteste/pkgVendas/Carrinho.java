package com.example.primeiroteste.pkgVendas;

import com.example.primeiroteste.pkgEstoque.Produto;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private double ValorCompra;

    private String id;
    private List<Produto> carrinho;

    public Carrinho(String id) {
        this.carrinho = this.carrinho = new ArrayList<>();;
    }

    public Carrinho() {
        this.carrinho = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Produto> getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(List<Produto> carrinho) {
        this.carrinho = carrinho;
    }

    @Override
    public String toString() {
        return "Carrinho{" +
                "carrinho=" + carrinho +
                '}';
    }

    public double getValorCompra() {
        return ValorCompra;
    }

    public void setValorCompra(double valorCompra) {
        ValorCompra = valorCompra;
    }
}

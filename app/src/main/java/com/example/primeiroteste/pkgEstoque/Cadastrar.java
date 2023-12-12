package com.example.primeiroteste.pkgEstoque;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.primeiroteste.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cadastrar extends Fragment {

    private EditText nome, valor, quantidade;
    private Button bt_cadastrar;

    private DatabaseReference referenciaProdutos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastrar_produto, container, false);
        inicializarComponentes(view);
        referenciaProdutos = FirebaseDatabase.getInstance().getReference("Produtos");
        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("resultado", "bt_cadastrar precionado ");
                cadastrarProduto();
            }
        });
        return view;
    }

    public void cadastrarProduto(){
        String novoId = referenciaProdutos.push().getKey();
        try{
            Produto temp = new Produto(
                    nome.getText().toString(),
                    novoId,
                    Double.parseDouble(valor.getText().toString()),
                    Integer.parseInt(quantidade.getText().toString())
            );
            DatabaseReference novoProduto = referenciaProdutos.child(novoId);
            novoProduto.setValue(temp);
        }catch (Exception e){
            Log.d("resultado", "Result" + e);
        }
    }

    public void inicializarComponentes(View view){
        nome = view.findViewById(R.id.nome);
        valor = view.findViewById(R.id.valor);
        quantidade = view.findViewById(R.id.quantidade);
        bt_cadastrar =view.findViewById(R.id.bt_cadastrar);
        Log.d("resultado", "inicializou componentes ");
    }
}
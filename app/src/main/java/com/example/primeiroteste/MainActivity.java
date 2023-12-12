package com.example.primeiroteste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.primeiroteste.pkgEstoque.Estoque;
import com.example.primeiroteste.pkgFuncionarios.RH;
import com.example.primeiroteste.pkgVendas.Carrinho;
import com.example.primeiroteste.pkgVendas.Venda;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button bt_pdv, bt_estoque, bt_rh, bt_teste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();
        Log.d("resultado", "inicializou");

        bt_estoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Estoque.class);
                startActivity(intent);
            }
        });

        bt_rh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RH.class);
                startActivity(intent);
            }
        });

        bt_pdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AbrirCaixa.class);
                startActivity(intent);
            }
        });

        bt_teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teste();
            }
        });

    }

    public void inicializarComponentes(){
        Log.d("resultado", "inicializou");
        bt_pdv = (Button) findViewById(R.id.bt_pdv);
        bt_estoque = (Button) findViewById(R.id.bt_estoque);
        bt_rh = (Button) findViewById(R.id.bt_rh);
        bt_teste = (Button)  findViewById(R.id.bt_teste);
    }

    public void teste (){
        DatabaseReference carrinhoRef = FirebaseDatabase.getInstance().getReference("teste");
        DatabaseReference ProdutoRef = FirebaseDatabase.getInstance().getReference("Produtos");
        DatabaseReference testeRef = FirebaseDatabase.getInstance().getReference("teste");

        Carrinho molde = new Carrinho();


        carrinhoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {

                    for (DataSnapshot snapshotObj: snapshot.getChildren()){
                        Carrinho carrinho = snapshotObj.getValue(Carrinho.class);
                        Carrinho mMold = new Carrinho();
                        testeRef.child("venda04").setValue(carrinho);
                    Log.d("teveoque","achei algo" + carrinho.getCarrinho());
                    }
                }catch (Exception e){
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
                    Log.d("teveoque","ondataChange " + molde.getCarrinho().toString());
    }
}


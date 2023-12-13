package com.example.primeiroteste.pkgVendas;

import static com.example.primeiroteste.R.layout.item_lista_estoque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.primeiroteste.AbrirCaixa;
import com.example.primeiroteste.FecharCaixa;
import com.example.primeiroteste.MainActivity;
import com.example.primeiroteste.R;
import com.example.primeiroteste.pkgEstoque.Produto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Venda extends AppCompatActivity {
    private ListView lista ;
    private List<Produto> produtoList = new ArrayList<>();
    private ArrayAdapter<Produto> adapterProduto;
    private TextView valorcarrinho;
    private Carrinho carrinho = new Carrinho();
    private Button verCarrinho, sair;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Produtos");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_venda);
            sair = findViewById(R.id.sair);
            lista = findViewById(R.id.outputVendaLista);
            verCarrinho = findViewById(R.id.verCarrinho);
            valorcarrinho = findViewById(R.id.outputValorCarrinho);
            listarProdutos();
            verCarrinho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Venda.this, FormCarrinho.class);
                    startActivity(intent);
                }
            });
            sair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Venda.this, FecharCaixa.class);
                    startActivity(intent);
                }
            });
        }catch(Exception e){
            Log.d("descobrir", ""+e);
        }
    }

    public void listarProdutos(){
        DatabaseReference preVenda = FirebaseDatabase.getInstance().getReference("pre_venda");
        DatabaseReference carrinhoReference = preVenda.child("itens");
        Log.d("resultado", "Chegoou em listar/ ativouListar ");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("resultado", "encontrou algo");
                produtoList.clear();
                try{
                    for(DataSnapshot snapshotObj: snapshot.getChildren()){
                        Produto produto = snapshotObj.getValue(Produto.class);

                        produtoList.add(produto);

                        adapterProduto = new ListAdapterVendas(Venda.this, item_lista_estoque, produtoList);
                        lista.setAdapter(adapterProduto);
                    }
                }catch (Exception e){
                    Log.d("Resultado", "resultado " + e);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Resultado", "resultado" + error);
            }
        });
    }
}
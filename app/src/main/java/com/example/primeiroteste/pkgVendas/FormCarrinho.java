package com.example.primeiroteste.pkgVendas;

import static com.example.primeiroteste.R.layout.activity_form_carrinho_itens;
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
import android.widget.Toast;

import com.example.primeiroteste.AbrirCaixa;
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

public class FormCarrinho extends AppCompatActivity {

    private ListView lista ;
    private List<Produto> produtoList = new ArrayList<>();
    private ArrayAdapter<Produto> adapterProduto;
    private EditText inputPesquisa ;
    private TextView valorcarrinho;
    private Carrinho carrinho = new Carrinho();
    private Button efetuarCompra;
    private DatabaseReference preVendaRef = FirebaseDatabase.getInstance().getReference("pre_venda");
    private DatabaseReference ref = preVendaRef.child("itens");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_carrinho);
        inputPesquisa = findViewById(R.id.carrinhoinputPesquisa);
        lista = findViewById(R.id.itemNoCarrinho);
        efetuarCompra = (Button) findViewById(R.id.comprar);

        efetuarCompra.setOnClickListener(new View.OnClickListener() {
            Carrinho carrinho = new Carrinho();
            @Override
            public void onClick(View v) {

                teste();

            }
        });

        listarProdutos();

    }

    public void listarProdutos(){
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
                        adapterProduto = new ListAdapterCarrinho(FormCarrinho.this, activity_form_carrinho_itens, produtoList);
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
    public void teste (){
        DatabaseReference vendaRef = FirebaseDatabase.getInstance().getReference("venda");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    double valorCarrinho = 0;
                    for (DataSnapshot snapshotObj: snapshot.getChildren()){
                        Produto produto = snapshotObj.getValue(Produto.class);

                        Log.d("teveoque","achei algo" + produto);
                        valorCarrinho += (produto.getValor() * produto.getQuantidade());
                        carrinho.getCarrinho().add(produto);

                    }

                    //   Log.d("teveoque","ondataChange " + carrinho.getCarrinho().toString());

                    carrinho.setValorCompra(valorCarrinho);
                    String novoId = preVendaRef.push().getKey();
                    carrinho.setId(novoId);

                    if(carrinho.getCarrinho().isEmpty()){
                        Toast.makeText(FormCarrinho.this, "Escolha pelomenos um item", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(FormCarrinho.this, Venda.class);
                        novoId = preVendaRef.push().getKey();
                        DatabaseReference carrinhoRef = vendaRef.child(novoId);
                        startActivity(intent);
                        carrinhoRef.setValue(carrinho);
                        preVendaRef.removeValue();
                        Toast.makeText(FormCarrinho.this, "Venda realizada", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
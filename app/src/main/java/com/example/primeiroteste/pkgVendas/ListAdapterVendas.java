package com.example.primeiroteste.pkgVendas;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.primeiroteste.R;
import com.example.primeiroteste.pkgEstoque.Produto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ListAdapterVendas extends ArrayAdapter<Produto> {
    private List<Produto> modeloProduto;
    private Context contexto;

    public ListAdapterVendas(@NonNull Context context, int resource, List<Produto> objects) {
        super(context, resource, objects);
        this.modeloProduto = objects;
        this.contexto = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(contexto).inflate(R.layout.item_lista_para_vendas, null);

        }
        Produto produto = modeloProduto.get(position);


        TextView textoNome = view.findViewById(R.id.textoNome);
        textoNome.setText(produto.getNome());

        TextView textoValorVenda = view.findViewById(R.id.textoValor);
        textoValorVenda.setText("Valor: " + String.valueOf(produto.getValor()) + " R$");

        TextView textoQuantidade = view.findViewById(R.id.textoquantidade);
        textoQuantidade.setText("Quantidade: " + String.valueOf(produto.getQuantidade()));

        Button buttonExcluir = view.findViewById(R.id.button);
        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAoCarrinho(produto);
            }
        });

        return view;
    }

    private void AddAoCarrinho(Produto produto) {
        try {
            DatabaseReference preVenda = FirebaseDatabase.getInstance().getReference("pre_venda");
            DatabaseReference carrinhoReference = preVenda.child("itens");
            carrinhoReference.orderByChild("nome").equalTo(produto.getNome()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Produto produtoNoCarrinho = snapshot.getValue(Produto.class);
                            int novaQuantidade = produtoNoCarrinho.getQuantidade() + 1;
                            snapshot.getRef().child("quantidade").setValue(novaQuantidade);
                            Log.d("Resultado", "Quantidade atualizada no carrinho");
                            Toast.makeText(contexto, "Produto adicionado ao carrinho", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        DatabaseReference novoProduto = carrinhoReference.child(produto.getId());
                        produto.setQuantidade(1);
                        novoProduto.setValue(produto);
                        Log.d("Resultado", "Produto adicionado ao carrinho0");
                        Toast.makeText(contexto, "Produto adicionado ao carrinho", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Resultado", "Erro ao verificar o carrinho: " + databaseError.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d("Resultado", "Erro ao Adicionar ao carrinho: " + e.getMessage());
        }
    }
}

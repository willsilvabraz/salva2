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

public class ListAdapterCarrinho extends ArrayAdapter<Produto> {
    private List<Produto> modeloProduto;
    private Context contexto0;
    public ListAdapterCarrinho(@NonNull Context context, int resource, List<Produto> objects) {
        super(context, resource, objects);
        this.modeloProduto = objects;
        this.contexto0 = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(contexto0).inflate(R.layout.activity_form_carrinho_itens,null);
        }

        Produto produto = modeloProduto.get(position);

        TextView nome = view.findViewById(R.id.nomeItemCarrinho);
        nome.setText(produto.getNome());

        TextView valorVenda = view.findViewById(R.id.valorItemCarrinho);
        valorVenda.setText("Valor Unitario: " + String.valueOf(produto.getValor()) + " R$");

        TextView quantidade = view.findViewById(R.id.quantidadeItemCarrinho);
        quantidade.setText("Quantidade: " + String.valueOf(produto.getQuantidade()));

        Button buttonExcluir = view.findViewById(R.id.removerDoCarrinho);
        buttonExcluir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                excluirProduto(produto);
            }
        });

        return view;
    }

    private void excluirProduto(Produto produto) {
        try {
            DatabaseReference preVenda = FirebaseDatabase.getInstance().getReference("pre_venda");
            DatabaseReference carrinhoReference = preVenda.child("itens");
            carrinhoReference.orderByChild("nome").equalTo(produto.getNome()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Produto produtoNoCarrinho = snapshot.getValue(Produto.class);
                            int novaQuantidade = produtoNoCarrinho.getQuantidade() - 1;

                            if(novaQuantidade < 1) {
                                snapshot.getRef().removeValue();
                                modeloProduto.remove(produto);
                            }else {
                                snapshot.getRef().child("quantidade").setValue(novaQuantidade);
                            }
                            Toast.makeText(getContext(), "Menos um item no carrinho", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("Resultado", "Produto adicionado ao carrinho0");
                        Toast.makeText(getContext(), "Produto adicionado ao carrinho", Toast.LENGTH_SHORT).show();
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

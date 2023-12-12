package com.example.primeiroteste.pkgEstoque;

import static com.example.primeiroteste.R.layout.item_lista_estoque;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.primeiroteste.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class Listar extends Fragment {
    private ListView lista;
    private List<Produto> produtoList = new ArrayList<>();
    private ArrayAdapter<Produto> adapterProduto;
    private DatabaseReference ref;
    private EditText inputPesquisa;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listar_produtos, container, false);
        inicializarComponentes(view);
        Log.d("resultado", "Chegoou em listar ");
        inputPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        listarProdutos();
                        Log.d("Resultado", "onTextChanged");
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Log.d("Resultado", "onChanged");

            }
            @Override
            public void afterTextChanged(Editable s) {

                        listarProdutos();
                        Log.d("Resultado", "afterTextChanged");
            }
        });
        return view;
    }
    public void inicializarComponentes(View view){
        ref = FirebaseDatabase.getInstance().getReference("Produtos");
        inputPesquisa = view.findViewById(R.id.inputPesquisa);
        lista = view.findViewById(R.id.lista);
        Log.d("resultado", "Chegoou em listar / inicializarComponentes");
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
                        Log.d("Resultado", "resultado" + snapshotObj.getKey());
                        Produto produto = snapshotObj.getValue(Produto.class);
                        if(produto.getNome().contains(inputPesquisa.getText().toString()) ||
                                inputPesquisa.getText().toString().equals("")
                        ){
                            produtoList.add(produto);
                            adapterProduto = new ListAdapter(getContext(), item_lista_estoque, produtoList);
                            adapterProduto = new ListAdapter(Listar.this.getContext(), item_lista_estoque, produtoList);
                            lista.setAdapter(adapterProduto);
                            Log.d("Resultado", "ID encontrado" + produto.getId());
                            Log.d("Resultado", "nome encontrado" + produto.getNome());
                        }
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
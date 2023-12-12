package com.example.primeiroteste.pkgFuncionarios;

import static com.example.primeiroteste.R.layout.item_lista_estoque;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
    private List<Funcionario> funcionarioList = new ArrayList<>();
    private ArrayAdapter<Funcionario> adapterProduto;
    private DatabaseReference ref;
    private EditText inputPesquisa;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listar_funcionarios, container, false);
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
        ref = FirebaseDatabase.getInstance().getReference("Funcionarios");
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
                funcionarioList.clear();
                try{

                for(DataSnapshot snapshotObj: snapshot.getChildren()){


                    Log.d("Resultado", "resultado" + snapshotObj.getKey());
                    Funcionario funcionario = snapshotObj.getValue(Funcionario.class);
                    if(funcionario.getNome().contains(inputPesquisa.getText().toString()) ||
                            inputPesquisa.getText().toString().equals("")
                    ){
                        funcionarioList.add(funcionario);

                        adapterProduto = new ListAdapter(getContext(), item_lista_estoque, funcionarioList);
                        adapterProduto = new ListAdapter(Listar.this.getContext(), item_lista_estoque, funcionarioList);

                        lista.setAdapter(adapterProduto);
                        Log.d("Resultado", "ID encontrado" + funcionario.getId());
                        Log.d("Resultado", "nome encontrado" + funcionario.getNome());
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
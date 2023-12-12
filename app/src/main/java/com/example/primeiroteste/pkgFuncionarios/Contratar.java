package com.example.primeiroteste.pkgFuncionarios;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.primeiroteste.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Contratar extends Fragment {

    private EditText nome, valor, quantidade;
    private Button bt_cadastrar;

    private DatabaseReference referenciaFuncionario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastrar_funcionario, container, false);
        inicializarComponentes(view);
        referenciaFuncionario = FirebaseDatabase.getInstance().getReference("Funcionarios");
        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("resultado", "bt_cadastrar precionado ");
                cadastrarFuncionario();
            }
        });
        return view;
    }

    public void cadastrarFuncionario(){
        String novoId = referenciaFuncionario.push().getKey();
        try{
            Funcionario temp = new Funcionario(
                    nome.getText().toString(),
                    novoId,
                    Double.parseDouble(valor.getText().toString()),
                    Integer.parseInt(quantidade.getText().toString())
            );
            DatabaseReference novoFuncionario = referenciaFuncionario.child(novoId);
            novoFuncionario.setValue(temp);
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
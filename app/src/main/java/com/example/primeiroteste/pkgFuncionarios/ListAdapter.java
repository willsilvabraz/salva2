package com.example.primeiroteste.pkgFuncionarios;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Funcionario> {
    private List<Funcionario> modeloFuncionario;
    private Context contexto;

    public ListAdapter(@NonNull Context context, int resource, List<Funcionario> objects) {
        super(context, resource, objects);
        this.modeloFuncionario = objects;
        this.contexto = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(contexto).inflate(R.layout.item_lista_estoque, null);

        }
        Funcionario funcionario = modeloFuncionario.get(position);


        TextView textoNome = view.findViewById(R.id.textoNome);
        textoNome.setText(funcionario.getNome());

        TextView textoValorVenda = view.findViewById(R.id.textoValor);
        textoValorVenda.setText("Salario: " + String.valueOf(funcionario.getValor()) + " R$");

        TextView textoQuantidade = view.findViewById(R.id.textoquantidade);
        textoQuantidade.setText("Idade: " + String.valueOf(funcionario.getIdade()));

        Button buttonExcluir = view.findViewById(R.id.button);
        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirProduto(funcionario);
            }
        });

        return view;
    }

    private void excluirProduto(Funcionario funcionario) {
        try {
            DatabaseReference funcionarioRef = FirebaseDatabase.getInstance().getReference("Produtos").child(funcionario.getId());
            funcionarioRef.removeValue();

            modeloFuncionario.remove(funcionario);

            notifyDataSetChanged();
            Toast.makeText(getContext().getApplicationContext(), "Produto excluído", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("Resultado", "Erro ao excluir Funcionario: " + e);
        }
    }
}

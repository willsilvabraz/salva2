package com.example.primeiroteste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.primeiroteste.pkgEstoque.Produto;
import com.example.primeiroteste.pkgVendas.Carrinho;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FecharCaixa extends AppCompatActivity {

    private DatabaseReference preVendaRef = FirebaseDatabase.getInstance().getReference("venda");
    private DatabaseReference ref = preVendaRef.child("itens");

    private Button fecharCaixa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fechar_caixa);
        fecharCaixa = (Button) findViewById(R.id.fecharCaixa);
        Log.d("fecharra", "inicializou");


        fecharCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Log.d("fecharra", "onClick");
                preVendaRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Carrinho carrinho = snapshot.getValue(Carrinho.class);

                                Log.d("fecharra", "onClick");

                                Log.d("fecharra", "fechar" + carrinho);

                            }
                        }catch (Exception e){
                            Log.d("fecharra", "fechar"+e);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

}
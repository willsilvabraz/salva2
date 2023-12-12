package com.example.primeiroteste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.primeiroteste.pkgVendas.Venda;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AbrirCaixa extends AppCompatActivity {


    private EditText valorInicial;
    private Button confirmar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abrir_caixa);

            confirmar =  (Button) findViewById(R.id.confirmar);
            valorInicial = findViewById(R.id.valorInicial);

        try {

            DatabaseReference bdCaixa = FirebaseDatabase.getInstance().getReference("Valor_inicial");

            confirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        bdCaixa.setValue(Double.parseDouble(valorInicial.getText().toString()));
                        Intent intent = new Intent(AbrirCaixa.this, Venda.class);
                        startActivity(intent);
                    }catch (Exception e){
                        Log.d("teveoque", "" + e);
                    }
                }
            });
        }catch (Exception e){
            Log.d("teveoque", "" + e);
        }
    }
}
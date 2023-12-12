package com.example.primeiroteste.pkgEstoque;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.primeiroteste.R;
import com.google.android.material.tabs.TabLayout;

public class Estoque extends AppCompatActivity {

    private TabLayout tabTelas;
    private ViewPager tela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_estoque);

    inicializarComponentes();
    }

    public void inicializarComponentes(){
        tela = findViewById(R.id.tela);
        tabTelas = findViewById(R.id.tabLayout);

        tabTelas.setupWithViewPager(tela);

        AdapterEstoque adaptadorEstoque = new AdapterEstoque(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adaptadorEstoque.addTela(new Cadastrar(), "Cadastrar");
        adaptadorEstoque.addTela(new Listar(), "Listar");
        tela.setAdapter(adaptadorEstoque);

    }
}
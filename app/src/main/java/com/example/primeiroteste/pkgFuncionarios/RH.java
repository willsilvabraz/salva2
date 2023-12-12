package com.example.primeiroteste.pkgFuncionarios;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.primeiroteste.R;
import com.google.android.material.tabs.TabLayout;

public class RH extends AppCompatActivity {

    private TabLayout tabTelas;
    private ViewPager tela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_rh);

    inicializarComponentes();
    }

    public void inicializarComponentes(){
        tela = findViewById(R.id.tela);
        tabTelas = findViewById(R.id.tabLayout);

        tabTelas.setupWithViewPager(tela);

        AdapterRH adaptadorEstoque = new AdapterRH(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adaptadorEstoque.addTela(new Contratar(), "Cadastrar");
        adaptadorEstoque.addTela(new Listar(), "Listar");
        tela.setAdapter(adaptadorEstoque);

    }
}
package com.example.primeiroteste.pkgEstoque;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class AdapterEstoque extends FragmentPagerAdapter {

    private final ArrayList<Fragment> telasEstoque = new ArrayList<>();
    private final ArrayList<String> titulosTelas = new ArrayList<>();

    public AdapterEstoque(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return telasEstoque.get(position);
    }

    @Override
    public int getCount() {
        return telasEstoque.size();
    }

    public void addTela(Fragment tela, String titulo){
        telasEstoque.add(tela);
        titulosTelas.add(titulo);
    }

    public CharSequence getTituloPagina(int position){
        return this.titulosTelas.get(position);
    }
}

package com.tododeportes.tododeportesapp.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tododeportes.tododeportesapp.R;
import com.tododeportes.tododeportesapp.comm.ListarCanchas;

public class ListarCachasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cachas);
        new ListarCanchas(this).execute();


    }
}

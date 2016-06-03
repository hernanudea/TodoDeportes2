package com.tododeportes.tododeportesapp.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tododeportes.tododeportesapp.comm.ListarPersonas;
import com.tododeportes.tododeportesapp.R;
import com.tododeportes.tododeportesapp.gui.adapter.ListarPersonasAdapter;
import com.tododeportes.tododeportesapp.pojo.Persona;

import java.util.ArrayList;

public class ListarPersonasActivity extends AppCompatActivity implements ListarPersonas.ListarPersonasListener {
    private RecyclerView rvPersonas;
    private ProgressBar pgbLoadingScenes;
    private RecyclerView.Adapter mPersonasAdapter;
    private RecyclerView.LayoutManager mPersonasLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_personas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListarPersonasActivity.this, RegistrarUsuarioActivity.class);
                startActivity(i);
            }
        });

        prepareUI();
        new ListarPersonas(this.getApplicationContext(), this).execute();

    }

    private void prepareUI() {
        rvPersonas = (RecyclerView) findViewById(R.id.rvPersonas);
        mPersonasLayoutManager = new LinearLayoutManager(this);
        rvPersonas.setLayoutManager(mPersonasLayoutManager);
        pgbLoadingScenes = (ProgressBar) findViewById(R.id.pgbLoadingFields);
    }

    @Override
    public void onListarPersonasFinish(ArrayList<Persona> listPersonas) {
        rvPersonas.setVisibility(View.VISIBLE);
        pgbLoadingScenes.setVisibility(View.GONE);
        mPersonasAdapter = new ListarPersonasAdapter(listPersonas);
        rvPersonas.setAdapter(mPersonasAdapter);
        mPersonasAdapter.notifyDataSetChanged();
        Log.d("ListaPersonasActivity", "Adapter OK");
    }
}

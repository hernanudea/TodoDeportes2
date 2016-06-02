package com.tododeportes.tododeportesapp.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.tododeportes.tododeportesapp.R;
import com.tododeportes.tododeportesapp.comm.ListarCanchas;
import com.tododeportes.tododeportesapp.gui.adapter.ListarCanchasAdapter;
import com.tododeportes.tododeportesapp.pojo.Cancha;

import java.util.ArrayList;

public class ListarCanchasActivity extends AppCompatActivity implements ListarCanchas.ListarCanchasListener {
    private RecyclerView rvCanchas;
    private RecyclerView.Adapter mCanchasAdapter;
    private RecyclerView.LayoutManager mCanchasLayoutManager;
    private ProgressBar pgbLoadingScenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_canchas);
        prepareUI();

        new ListarCanchas(this.getApplicationContext(), this).execute();


    }

    private void prepareUI() {
        rvCanchas = (RecyclerView) findViewById(R.id.rvCanchas);
        mCanchasLayoutManager = new LinearLayoutManager(this);
        rvCanchas.setLayoutManager(mCanchasLayoutManager);

        pgbLoadingScenes = (ProgressBar) findViewById(R.id.pgbLoadingFields);
    }

    @Override
    public void onListarCanchasFinish(ArrayList<Cancha> listCanchas) {
        rvCanchas.setVisibility(View.VISIBLE);
        pgbLoadingScenes.setVisibility(View.GONE);
        mCanchasAdapter = new ListarCanchasAdapter(listCanchas);
        rvCanchas.setAdapter(mCanchasAdapter);
        mCanchasAdapter.notifyDataSetChanged();
        Log.d("ListaCanchasActivity", "Adapter OK");
    }
}

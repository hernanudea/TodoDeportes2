package com.tododeportes.tododeportesapp.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tododeportes.tododeportesapp.R;
import com.tododeportes.tododeportesapp.comm.ListarCanchas;
import com.tododeportes.tododeportesapp.gui.adapter.ListarCanchasAdapter;
import com.tododeportes.tododeportesapp.pojo.Cancha;

import java.util.ArrayList;

public class ListarCachasActivity extends AppCompatActivity implements ListarCanchas.ListarCanchasListener {
    private RecyclerView rvCanchas;
    private RecyclerView.Adapter mCanchasAdapter;
    private RecyclerView.LayoutManager mCanchasLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cachas);
        prepareUI();

        new ListarCanchas(this.getApplicationContext(), this).execute();


    }

    private void prepareUI() {


    }

    @Override
    public void onListarCanchasFinish(ArrayList<Cancha> listCanchas) {
        rvCanchas = (RecyclerView) findViewById(R.id.rvCanchas);
        mCanchasLayoutManager = new LinearLayoutManager(this);
        rvCanchas.setLayoutManager(mCanchasLayoutManager);
        mCanchasAdapter = new ListarCanchasAdapter(listCanchas);
        rvCanchas.setAdapter(mCanchasAdapter);
        mCanchasAdapter.notifyDataSetChanged();
        Log.d("ListaCanchasActivity", "Adapter OK");
    }
}

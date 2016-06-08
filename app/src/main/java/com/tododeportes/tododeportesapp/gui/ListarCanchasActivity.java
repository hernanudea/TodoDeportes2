package com.tododeportes.tododeportesapp.gui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tododeportes.tododeportesapp.R;
import com.tododeportes.tododeportesapp.comm.ListarCanchas;
import com.tododeportes.tododeportesapp.gui.adapter.ListarCanchasAdapter;
import com.tododeportes.tododeportesapp.pojo.Cancha;

import java.util.ArrayList;

public class ListarCanchasActivity extends AppCompatActivity implements
        ListarCanchas.ListarCanchasListener,
        SearchView.OnQueryTextListener {

    private RecyclerView rvCanchas;
    private ListarCanchasAdapter mCanchasAdapter;
    private RecyclerView.LayoutManager mCanchasLayoutManager;
    private ProgressBar pgbLoadingScenes;
    private FloatingActionButton fabAddCancha;
    private ArrayList<Cancha> listCanchas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_canchas);
        prepareUI();

        downloadList();



        fabAddCancha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListarCanchasActivity.this, RegistrarCanchaActivity.class);
                startActivity(i);
            }
        });


    }

    private void prepareUI() {
        rvCanchas = (RecyclerView) findViewById(R.id.rvCanchas);
        mCanchasLayoutManager = new LinearLayoutManager(this);
        rvCanchas.setLayoutManager(mCanchasLayoutManager);
        pgbLoadingScenes = (ProgressBar) findViewById(R.id.pgbLoadingFields);
        fabAddCancha = (FloatingActionButton) findViewById(R.id.fab_add_field);
    }

    private void downloadList() {
        rvCanchas.setVisibility(View.GONE);
        pgbLoadingScenes.setVisibility(View.VISIBLE);
        new ListarCanchas(this.getApplicationContext(), this).execute();
    }

    @Override
    public void onListarCanchasFinish(ArrayList<Cancha> listCanchas) {
        this.listCanchas = listCanchas;
        rvCanchas.setVisibility(View.VISIBLE);
        pgbLoadingScenes.setVisibility(View.GONE);
        mCanchasAdapter = new ListarCanchasAdapter(listCanchas);
        rvCanchas.setAdapter(mCanchasAdapter);
        mCanchasAdapter.notifyDataSetChanged();
        Log.d("ListaCanchasActivity", "Adapter OK");
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            Toast.makeText(this, "onNewIntent", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listar_cancha, menu);
        final MenuItem item = menu.findItem(R.id.action_search_cancha);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_update_list) {
            downloadList();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final ArrayList<Cancha> filteredCanchaList = filter(listCanchas, newText);
        mCanchasAdapter.animateTo(filteredCanchaList);
        rvCanchas.scrollToPosition(0);
        return false;
    }

    private ArrayList<Cancha> filter(ArrayList<Cancha> list, String query) {
        query = query.toLowerCase();

        final ArrayList<Cancha> filteredCanchaList = new ArrayList<>();
        for (Cancha cancha : list) {
            final String text = cancha.getDescripcion().toLowerCase();
            if (text.contains(query)) {
                filteredCanchaList.add(cancha);
            }
        }
        return filteredCanchaList;
    }
}

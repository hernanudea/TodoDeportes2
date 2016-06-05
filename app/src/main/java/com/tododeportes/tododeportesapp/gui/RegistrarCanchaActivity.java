package com.tododeportes.tododeportesapp.gui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.tododeportes.tododeportesapp.R;
import com.tododeportes.tododeportesapp.comm.ListarTipoSubtipo;
import com.tododeportes.tododeportesapp.pojo.TipoDeporte;
import com.tododeportes.tododeportesapp.pojo.TipoEscenario;

import java.util.ArrayList;


public class RegistrarCanchaActivity extends AppCompatActivity implements ListarTipoSubtipo.GetTipoSubtipoListener  {
    private Spinner spnTipo;
    private Spinner spnSubtipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cancha);
        prepareUI();

        new ListarTipoSubtipo(this, this).execute();
    }

    private void prepareUI() {
        spnTipo = (Spinner) findViewById(R.id.spnTipo);
        spnSubtipo = (Spinner) findViewById(R.id.spnSubtipo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_registrar_cancha, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTiposListed(ArrayList<TipoDeporte> listTiposDeporte) {
        ArrayAdapter<CharSequence> adapterTipos = ArrayAdapter.createFromResource(this,
                R.array.arrTipos, android.R.layout.simple_spinner_item);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipo.setAdapter(adapterTipos);
    }

    @Override
    public void onSubtiposListed(ArrayList<TipoEscenario> listTiposEscenario) {
        ArrayAdapter<CharSequence> adapterSubtipos = ArrayAdapter.createFromResource(this,
                R.array.arrSubtipos, android.R.layout.simple_spinner_item);
        adapterSubtipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSubtipo.setAdapter(adapterSubtipos);
    }
}

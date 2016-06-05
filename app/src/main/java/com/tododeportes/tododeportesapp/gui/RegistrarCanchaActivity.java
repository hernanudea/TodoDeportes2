package com.tododeportes.tododeportesapp.gui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.tododeportes.tododeportesapp.R;


public class RegistrarCanchaActivity extends AppCompatActivity {
    private Spinner spnTipo;
    private Spinner spnSubtipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cancha);
        prepareUI();

        // TIPO
        ArrayAdapter<CharSequence> adapterTipos = ArrayAdapter.createFromResource(this,
                R.array.arrTipos, android.R.layout.simple_spinner_item);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipo.setAdapter(adapterTipos);

        // SUBTIPO
        ArrayAdapter<CharSequence> adapterSubtipos = ArrayAdapter.createFromResource(this,
                R.array.arrSubtipos, android.R.layout.simple_spinner_item);
        adapterSubtipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSubtipo.setAdapter(adapterSubtipos);
    }

    private void prepareUI() {
        spnTipo = (Spinner) findViewById(R.id.spnTipo);
        spnSubtipo = (Spinner) findViewById(R.id.spnSubtipo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registrar_cancha, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

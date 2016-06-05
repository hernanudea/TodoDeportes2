package com.tododeportes.tododeportesapp.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tododeportes.tododeportesapp.R;
import com.tododeportes.tododeportesapp.comm.ListarTipoSubtipo;
import com.tododeportes.tododeportesapp.comm.RegistrarCancha;
import com.tododeportes.tododeportesapp.pojo.Cancha;
import com.tododeportes.tododeportesapp.pojo.TipoDeporte;
import com.tododeportes.tododeportesapp.pojo.TipoEscenario;

import java.util.ArrayList;


public class RegistrarCanchaActivity extends AppCompatActivity implements
        ListarTipoSubtipo.GetTipoSubtipoListener, RegistrarCancha.RegistrarCanchaListener {
    private EditText edtFieldName;
    private Spinner spnTipo;
    private Spinner spnSubtipo;
    private Cancha cancha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cancha);
        prepareUI();
        if (cancha == null) cancha = new Cancha(0, edtFieldName.getText().toString());
        new ListarTipoSubtipo(this, this).execute();
    }

    private void prepareUI() {
        edtFieldName = (EditText) findViewById(R.id.edt_field_name);
        spnTipo = (Spinner) findViewById(R.id.spnTipo);
        spnSubtipo = (Spinner) findViewById(R.id.spnSubtipo);
    }

    private void sendCancha() {
        cancha.setDescripcion(edtFieldName.getText().toString());
        new RegistrarCancha(this, this, cancha).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_registrar_cancha, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save_cancha) {
            sendCancha();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTiposListed(final ArrayList<TipoDeporte> listTiposDeporte) {
        ArrayAdapter<TipoDeporte> adapter = new ArrayAdapter<>(
                this, R.layout.widget_spinner_textview,
                listTiposDeporte
        );
        spnTipo.setAdapter(adapter);
        spnTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                  @Override
                  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        cancha.setTipoDeporte(listTiposDeporte.get(position));

                  }

                  @Override
                  public void onNothingSelected(AdapterView<?> parent) {}
              }
        );
    }

    @Override
    public void onSubtiposListed(final ArrayList<TipoEscenario> listTiposEscenario) {
        ArrayAdapter<TipoEscenario> adapter = new ArrayAdapter<>(
                this, R.layout.widget_spinner_textview,
                listTiposEscenario
        );
        spnSubtipo.setAdapter(adapter);
        spnSubtipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     cancha.setTipoEscenario(listTiposEscenario.get(position));
                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> parent) {

                 }
             }
        );
    }

    @Override
    public void onCanchaRegistrada() {
        Toast.makeText(this, "Cancha guardada", Toast.LENGTH_LONG).show();
        onNavigateUp();
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, "Error al guardar: " + message, Toast.LENGTH_LONG).show();
    }
}

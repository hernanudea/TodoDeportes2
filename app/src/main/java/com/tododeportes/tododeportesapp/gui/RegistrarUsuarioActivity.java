package com.tododeportes.tododeportesapp.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.tododeportes.tododeportesapp.R;

public class RegistrarUsuarioActivity extends AppCompatActivity {
    Spinner tipoDocumento;
    Button tbnBuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        tipoDocumento = (Spinner) findViewById(R.id.spnTipoDocumento);
        tbnBuardar = (Button) findViewById(R.id.boton_guardar);

        ArrayAdapter<CharSequence> adapterTipos = ArrayAdapter.createFromResource(this,
                R.array.tipoDocumento, android.R.layout.simple_spinner_item);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoDocumento.setAdapter(adapterTipos);
    }

    public void guardar(View v) {
        //Toast.makeText(getApplicationContext(), "ToDo", Toast.LENGTH_LONG).show();
    }
}

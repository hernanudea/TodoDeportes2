package com.tododeportes.tododeportesapp.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tododeportes.tododeportesapp.R;
import com.tododeportes.tododeportesapp.pojo.Usuario;

public class IniciarSesionActivity extends AppCompatActivity {

    public static final Usuario USUARIOACTIVO = new Usuario();

    Button botonIniciarSeccion;
    TextView testIdUsuario;
    TextView testContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        botonIniciarSeccion = (Button) findViewById(R.id.boton_iniciar_sesion);
        testIdUsuario = (TextView) findViewById(R.id.input_usuario);
        testContrasena = (TextView) findViewById(R.id.input_contrasena);
    }

    public void iniarSesion(View v) {
        switch (v.getId()) {
            case R.id.boton_iniciar_sesion:
                USUARIOACTIVO.setContraseña(testContrasena.getText().toString());
                USUARIOACTIVO.setIdUsuario(testIdUsuario.getText().toString());
                break;
        }
    }

    public static Usuario getUSUARIOACTIVO() {
        return USUARIOACTIVO;
    }
}

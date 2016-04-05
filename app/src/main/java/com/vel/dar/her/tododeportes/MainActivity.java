package com.vel.dar.her.tododeportes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    LinearLayout fila05;
    LinearLayout fila06;
    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        fila05 = (LinearLayout) findViewById(R.id.fila05);
        fila06 = (LinearLayout) findViewById(R.id.fila06);
        toggleButton = (ToggleButton) findViewById(R.id.tbCambioperfil);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewAdministrador();
                } else {
                    viewUsuario();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void viewAdministrador() {
        fila05.setVisibility(View.VISIBLE);
        fila05.getLayoutParams().width = fila06.getWidth();
        fila05.getLayoutParams().height = fila06.getHeight();
        Toast.makeText(getApplicationContext(), R.string.vistaAdministrador, Toast.LENGTH_SHORT).show();
    }

    public void viewUsuario() {
        fila05.setVisibility(View.INVISIBLE);
        fila05.getLayoutParams().width = 0;
        fila05.getLayoutParams().height = 0;
        Toast.makeText(getApplicationContext(), R.string.vistaUsuario, Toast.LENGTH_SHORT).show();
    }

    public void seleccionSeccion(View v) {
        startActivity(new Intent(this, RegistrarCanchaActivity.class));
        switch (v.getId()) {
            case R.id.seccionCanchas:
                Toast.makeText(getApplicationContext(), "Canchas", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}

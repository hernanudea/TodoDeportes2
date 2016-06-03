package com.tododeportes.tododeportesapp.comm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tododeportes.tododeportesapp.pojo.Persona;
import com.tododeportes.tododeportesapp.pojo.TipoDocumento;
import com.tododeportes.tododeportesapp.util.UriManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hernan on 2/06/16.
 */
public class ListarPersonas extends AsyncTask<Void, String, String[]> {
    private final String TAG = "ListarPersonas";
    private Context mContext;
    private ListarPersonasListener mListener;
    ArrayList<Persona> listPersonas;

    public ListarPersonas(Context context, ListarPersonasListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    protected String[] doInBackground(Void... params) {
        String result = "";

        String strURI = UriManager.uriWebService + UriManager.uriListarPersonas;
        Log.d(TAG, strURI);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(strURI)
                .build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.d(TAG, result);

            JSONArray jsonPersonas = new JSONArray(result);
            int cantidad = jsonPersonas.length();

            listPersonas = new ArrayList<>();

            for (int i = 0; i < cantidad; i++) {
                JSONObject jsonPersona = jsonPersonas.getJSONObject(i);
                Persona persona = new Persona(
                        jsonPersona.getString("nombres"),
                        jsonPersona.getString("apellidos"),
                        jsonPersona.getString("numeroDocumento"),
                        jsonPersona.getString("direccion"),
                        jsonPersona.getString("telefonoCelular"));

                JSONObject jsonTipoDocumento = jsonPersona.getJSONObject("tbTiposDocumento");
                persona.setTipoDocumento(new TipoDocumento(
                        jsonTipoDocumento.getInt("idtipoDocumento"),
                        jsonTipoDocumento.getString("descripcion"),
                        jsonTipoDocumento.getString("abreviado")));

                listPersonas.add(persona);
            }

            return new String[]{"1", "Descarga de personas completa"};
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
            return new String[]{"0", "Error en consumo de servicio GetUpdates: " + e.getLocalizedMessage()};
        }
    }

    @Override
    protected void onPostExecute(String[] result) {
        if (result[0].equals("1")) {
            mListener.onListarPersonasFinish(listPersonas);
        } else {
            Log.e(TAG, "Error: " + result[1]);
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {

    }

    public interface ListarPersonasListener {
        void onListarPersonasFinish(ArrayList<Persona> listPersonas);
    }
}

package com.tododeportes.tododeportesapp.comm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tododeportes.tododeportesapp.pojo.Cancha;
import com.tododeportes.tododeportesapp.pojo.TipoDeporte;
import com.tododeportes.tododeportesapp.pojo.TipoEscenario;
import com.tododeportes.tododeportesapp.util.UriManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gio on 30/05/16.
 */
public class ListarCanchas extends AsyncTask<Void, String, String[]> {
    private final String TAG = "ListarCanchas";
    private Context mContext;
    private ListarCanchasListener mListener;
    ArrayList<Cancha> listCanchas;

    public ListarCanchas(Context context, ListarCanchasListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    protected String[] doInBackground(Void... params) {
        String result = "";

        String strURI = UriManager.uriWebService + UriManager.uriListarCanchas;
        Log.d(TAG, strURI);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(strURI)
                .build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.d(TAG, result);

            JSONArray jsonCanchas = new JSONArray(result);
            int cantidad = jsonCanchas.length();

            listCanchas = new ArrayList<>();

            for (int i = 0; i < cantidad; i++) {
                JSONObject jsonCancha = jsonCanchas.getJSONObject(i);
                Cancha cancha = new Cancha(jsonCancha.getInt("idcancha"), jsonCancha.getString("nombre"));

                JSONObject jsonTipoDeporte = jsonCancha.getJSONObject("tbTiposDeporte");
                cancha.setTipoDeporte(new TipoDeporte(
                        jsonTipoDeporte.getInt("idtipoDeporte"), jsonTipoDeporte.getString("nombre")));

                JSONObject jsonTipoEscenario = jsonCancha.getJSONObject("tbTipoEscenario");
                cancha.setTipoEscenario(new TipoEscenario(
                        jsonTipoEscenario.getInt("idtipoEscenario"), jsonTipoEscenario.getString("descripcion")
                ));

                listCanchas.add(cancha);
            }

            return new String[]{"1", "Descarga de canchas completa"};
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
            return new String[]{"0", "Error en consumo de servicio GetUpdates: " + e.getLocalizedMessage()};
        }
    }

    @Override
    protected void onPostExecute(String[] result) {
        if (result[0].equals("1")) {
            mListener.onListarCanchasFinish(listCanchas);
        } else {
            Log.e(TAG, "Error: " + result[1]);
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {

    }

    public interface ListarCanchasListener {
        void onListarCanchasFinish(ArrayList<Cancha> listCanchas);
    }
}

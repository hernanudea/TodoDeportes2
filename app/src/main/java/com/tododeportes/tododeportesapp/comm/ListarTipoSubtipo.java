package com.tododeportes.tododeportesapp.comm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
 * Created by gio on 04/06/16.
 */
public class ListarTipoSubtipo extends AsyncTask<Void, String, String[]> {
    private final String TAG = "ListarTipoSubtipo";
    private Context mContext;
    private GetTipoSubtipoListener mListener;
    private ArrayList<TipoDeporte> listTiposDeporte;
    private ArrayList<TipoEscenario> listTiposEscenario;

    public ListarTipoSubtipo(Context context, GetTipoSubtipoListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    protected String[] doInBackground(Void... params) {
        String result;

        OkHttpClient client = new OkHttpClient();

        String strURI = UriManager.uriWebService + UriManager.uriGetTiposDeporte;
        Log.d(TAG, strURI);


        Request request = new Request.Builder()
                .url(strURI)
                .build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.d(TAG, result);

            JSONArray jsonTiposDeporte = new JSONArray(result);
            int cantidad = jsonTiposDeporte.length();

            listTiposDeporte = new ArrayList<>();

            for (int i = 0; i < cantidad; i++) {
                JSONObject jsonTipoDeporte = jsonTiposDeporte.getJSONObject(i);
                TipoDeporte tipoDeporte = new TipoDeporte(
                        jsonTipoDeporte.optInt("idtipoDeporte"),
                        jsonTipoDeporte.optString("nombre"));

                listTiposDeporte.add(tipoDeporte);
            }
            publishProgress("1");

            // Tipo de escenario
            strURI = UriManager.uriWebService + UriManager.uriGetTiposEscenario;
            Log.d(TAG, strURI);

            request = new Request.Builder()
                    .url(strURI)
                    .build();

            response = client.newCall(request).execute();
            result = response.body().string();
            Log.d(TAG, result);

            JSONArray jsonTiposEscenario = new JSONArray(result);
            cantidad = jsonTiposEscenario.length();

            listTiposEscenario = new ArrayList<>();

            for (int i = 0; i < cantidad; i++) {
                JSONObject jsonTipoEscenario = jsonTiposEscenario.getJSONObject(i);
                TipoEscenario tipoEscenario = new TipoEscenario(
                        jsonTipoEscenario.optInt("idtipoEscenario"),
                        jsonTipoEscenario.optString("descripcion"));

                listTiposEscenario.add(tipoEscenario);
            }
            publishProgress("2");

            return new String[]{"1", "Descarga de canchas completa"};
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
            return new String[]{"0", "Error en consumo de servicio: " + e.getLocalizedMessage()};
        }
    }

    @Override
    protected void onPostExecute(String[] result) {

    }

    @Override
    protected void onProgressUpdate(String... values) {
        if (values[0].equals("1")) {
            mListener.onTiposListed(listTiposDeporte);
        } else if (values[0].equals("2")) {
            mListener.onSubtiposListed(listTiposEscenario);
        }
    }

    public interface GetTipoSubtipoListener {
        void onTiposListed(ArrayList<TipoDeporte> listTiposDeporte);
        void onSubtiposListed(ArrayList<TipoEscenario> listTiposEscenario);
    }
}

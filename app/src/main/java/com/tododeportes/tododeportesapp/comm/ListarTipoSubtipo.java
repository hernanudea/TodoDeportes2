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

        String strURIDeporte = UriManager.uriWebService + UriManager.uriGetTiposDeporte;
        Log.d(TAG, strURIDeporte);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(strURIDeporte)
                .build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.d(TAG, result);

            JSONArray jsonTiposDeporte = new JSONArray(result);
            int cantidad = jsonTiposDeporte.length();

            listTiposDeporte = new ArrayList<>();

            for (int i = 0; i < cantidad; i++) {
                JSONObject jsonCancha = jsonTiposDeporte.getJSONObject(i);


                //listTiposDeporte.add(cancha);
            }

            return new String[]{"1", "Descarga de canchas completa"};
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
            return new String[]{"0", "Error en consumo de servicio GetUpdates: " + e.getLocalizedMessage()};
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

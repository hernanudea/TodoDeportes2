package com.tododeportes.tododeportesapp.comm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tododeportes.tododeportesapp.util.UriManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gio on 19/08/15.
 */
public class ListarCanchas extends AsyncTask<Void, String, String[]> {
    private final String TAG = "ListarCanchas";
    private Context context;

    public ListarCanchas(Context context) {
        this.context = context;
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

            return new String[]{"1", "Error descargando canchas"};
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

    }

    public interface ListarCanchasListener {
        void onListarCanchasFinish();
    }
}

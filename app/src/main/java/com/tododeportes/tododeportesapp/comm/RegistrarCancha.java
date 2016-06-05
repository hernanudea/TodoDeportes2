package com.tododeportes.tododeportesapp.comm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tododeportes.tododeportesapp.pojo.Cancha;
import com.tododeportes.tododeportesapp.util.UriManager;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by gio on 04/06/16.
 */
public class RegistrarCancha extends AsyncTask<Void, String, String[]> {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private final String TAG = "RegistrarCancha";
    private Context mContext;
    private RegistrarCanchaListener mListener;
    private Cancha cancha;

    public RegistrarCancha(Context context, RegistrarCanchaListener listener, Cancha cancha) {
        this.mContext = context;
        this.mListener = listener;
        this.cancha = cancha;
    }

    @Override
    protected String[] doInBackground(Void... params) {
        String strURI = UriManager.uriWebService + UriManager.uriGuardarCancha;
        Log.d(TAG, strURI);

        OkHttpClient client = new OkHttpClient();

        JSONObject jsonCancha = new JSONObject();

        try {
            jsonCancha.put("idCancha", 0);
            jsonCancha.put("nombre", cancha.getDescripcion());
            jsonCancha.put("tipoDeporte", cancha.getTipoDeporte().getId());
            jsonCancha.put("tipoEscenario", cancha.getTipoEscenario().getId());

            RequestBody requestBody = RequestBody.create(JSON, jsonCancha.toString());
            Request request = new Request.Builder()
                    .url(strURI)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();

            if (response.code() == 200)
                return new String[]{"1", "Registro de cancha correcto"};
            else
                return new String[]{"0", response.message()};
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
            return new String[]{"0", "Error en consumo de servicio: " + e.getLocalizedMessage()};
        }
    }

    @Override
    protected void onPostExecute(String[] result) {
        if (result[0].equals("1")) {
            mListener.onCanchaRegistrada();
        } else if (result[0].equals("0")){
            mListener.onError(result[1]);
            Log.e(TAG, "Error: " + result[1]);
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {

    }

    public interface RegistrarCanchaListener {
        void onCanchaRegistrada();
        void onError(String message);
    }
}

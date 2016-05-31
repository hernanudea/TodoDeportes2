package com.tododeportes.tododeportesapp.comm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.saludfamiliar.sigrimovil.controller.ConfiguracionController;
import com.saludfamiliar.sigrimovil.controller.EncuestaController;
import com.saludfamiliar.sigrimovil.controller.IPSController;
import com.saludfamiliar.sigrimovil.controller.ListasRefController;
import com.saludfamiliar.sigrimovil.controller.ModuloController;
import com.saludfamiliar.sigrimovil.controller.PersonasController;
import com.saludfamiliar.sigrimovil.controller.ProgramacionController;
import com.saludfamiliar.sigrimovil.controller.RiesgoController;
import com.saludfamiliar.sigrimovil.controller.SenalesController;
import com.saludfamiliar.sigrimovil.controller.TipsController;
import com.saludfamiliar.sigrimovil.controller.UbicacionController;
import com.saludfamiliar.sigrimovil.gui.dialog.ProgressDialogFragment;
import com.saludfamiliar.sigrimovil.model.Transaction;
import com.saludfamiliar.sigrimovil.util.SigriPreferences;
import com.saludfamiliar.sigrimovil.util.SituacionPersona;
import com.saludfamiliar.sigrimovil.util.UtilidadesApp;
import com.saludfamiliar.sigrimovil.util.UtilidadesDB;
import com.tododeportes.tododeportesapp.util.UriManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;

import static com.saludfamiliar.sigrimovil.model.Transaction.INSERT;
import static com.saludfamiliar.sigrimovil.model.Transaction.UPDATE;

/**
 * Created by gio on 19/08/15.
 */
public class ListarCanchas extends AsyncTask<Void, String, String[]> {

    private final String TAG = "GetUpdates";
    private Context context;

    private ProgressDialogFragment fragment;
    
    public ListarCanchas(ProgressDialogFragment fragment) {
        this.context = fragment.getActivity();
        this.fragment = fragment;
    }

    public ListarCanchas(Context context) {
        this.context = context;
    }

    @Override
    protected String[] doInBackground(Void... params) {
        InputStream is;
        String result = "";

        String strURI = UriManager

        try {

            HttpClient hc = new DefaultHttpClient();
            Log.d(TAG, strURI);
            Log.d(TAG, jsonPush.toString());

            HttpPost httpPost = new HttpPost(strURI);
            StringEntity se = new StringEntity(jsonPush.toString());
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(se);

            publishProgress("1", "Conectando con el servidor");
            HttpResponse hr = hc.execute(httpPost);
            is = hr.getEntity().getContent();
            if(is != null)
                result = UtilidadesApp.convertInputStreamToString(is);
            else
                result = "Did not work!";
            Log.d(TAG, result);
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
            return new String[]{"0", "Error en consumo de servicio GetUpdates: " + e.getLocalizedMessage()};
        }

        // Insertar base de datos
        try {
            publishProgress("1", "Guardando actualizaciones");
            Log.d(TAG, "Response: " + result);
            JSONObject rsp = new JSONObject(result);
            // Guarda departamentos
            JSONArray vector = rsp.optJSONArray("DEPARTAMENTOS");
            int cantidad = vector.length();
            if (cantidad > 0) publishProgress("1", "Actualizando " + cantidad + " departamentos");

            UbicacionController ubicacionController = new UbicacionController(context);
            for (int i = 0; i < cantidad; i++) {
                JSONObject dpto = vector.getJSONObject(i);
                String strId = dpto.optString("ID");
                String strNombre = dpto.optString("NOMBRE");
                String strPais = dpto.optString("ID_PAIS");
                String strCodigo = dpto.optString("CODIGO");
                String strActivo = dpto.optString("ESTADO");

                Transaction transaction;
                if (ubicacionController.existDepartamento(strCodigo))
                    transaction = UPDATE;
                else
                    transaction = INSERT;

                ubicacionController.putDepartamento(transaction,
                        strId, strNombre, strPais, strCodigo, strActivo);
            }

            // Guarda barrios
            vector = rsp.optJSONArray("AREAS");
            cantidad = vector.length();
            if (cantidad > 0) publishProgress("1", "Actualizando " + cantidad + " barrios");

            for (int i = 0; i < cantidad; i++) {
                JSONObject jsonBarrio = vector.getJSONObject(i);
                String strId = jsonBarrio.optString("ID_AREA");
                String strDescripcion = jsonBarrio.optString("DESCRIPCION");
                String strCodArea = jsonBarrio.optString("CODAREA");
                String strDpto = jsonBarrio.optString("DPTO");
                String strMunicipio = jsonBarrio.optString("MUNICIPIO");
                String strZona = jsonBarrio.optString("ZONA");
                String strNivel4 = jsonBarrio.optString("NIVEL4");
                String strCodigo = jsonBarrio.optString("CODIGO");
                String strEstado = jsonBarrio.optString("ESTADO");
                String strOrden = jsonBarrio.optString("ORDEN");

                Transaction transaction;
                if (ubicacionController.existBarrio(strId))
                    transaction = UPDATE;
                else
                    transaction = INSERT;

                ubicacionController.putBarrio(transaction, strId, strDescripcion, strCodArea, strDpto,
                        strMunicipio, strZona, strNivel4, strCodigo, strEstado, strOrden);
            }

            // Guarda módulos
            vector = rsp.optJSONArray("MODULOS");
            cantidad = vector.length();
            if (cantidad > 0) publishProgress("1", "Guardando " + cantidad + " modulos");

            ModuloController moduloController = new ModuloController(context);
            for (int i = 0; i < cantidad; i++) {
                JSONObject modulo = vector.getJSONObject(i);
                String strIdModulo = modulo.optString("ID_MODULO");
                String strDesc = modulo.optString("DESCRIPCION");
                String strCodigo = modulo.optString("CODIGO");
                String strEntidad = modulo.optString("ENTIDAD");
                String strEstado = modulo.optString("ESTADO");
                String strOrden = modulo.optString("ORDEN");
                String strTipo = modulo.optString("TIPO");
                String strValidar = modulo.optString("VALIDAR");
                String strEdadIni = modulo.optString("EDADINI");
                String strEdadFin = modulo.optString("EDADFIN");
                String strGenero = modulo.optString("GENERO");
                String strModuloP = modulo.optString("MODULO_P");
                String strRegistros = modulo.optString("REGISTROS");

                Transaction transaction;
                if (moduloController.existModulo(strIdModulo))
                    transaction = UPDATE;
                else
                    transaction = INSERT;

                moduloController.putModulo(transaction, strIdModulo, strDesc, strCodigo, strEntidad,
                        strEstado, strOrden, strTipo, strValidar, strEdadIni, strEdadFin, strGenero,
                        strModuloP, strRegistros);
            }

            // Guarda preguntas
            vector = rsp.optJSONArray("PREGUNTAS");
            cantidad = vector.length();
            if (cantidad > 0) publishProgress("1", "Guardando " + cantidad + " preguntas");

            EncuestaController encuestaController = new EncuestaController(context);
            for (int i = 0; i < cantidad; i++) {
                JSONObject pregunta = vector.getJSONObject(i);
                String strId = pregunta.optString("ID_PREGUNTA");
                String strDesc = pregunta.optString("DESCRIPCION");
                String strTipoCampo = pregunta.optString("TIPOCAMPO");
                String strLonCampo = pregunta.optString("LONCAMPO");
                String strDepende = pregunta.optString("DEPENDE");
                String strObl = pregunta.optString("OBLIGATORIO");
                String strIdModulo = pregunta.optString("ID_MODULO");
                String strIdLista = pregunta.optString("ID_LISTA");
                String strNomLista = pregunta.optString("NOMLISTA");
                String strValor = pregunta.optString("VALORLISTA");
                String strTipo = pregunta.optString("TIPO");
                String strValidar = pregunta.optString("VALIDAR");
                String strEdadIni = pregunta.optString("EDADINI");
                String strEdaFin = pregunta.optString("EDADFIN");
                String strGenero = pregunta.optString("GENERO");
                String strEstado = pregunta.optString("ESTADO");
                String strOrden = pregunta.optString("ORDEN");
                String strVisibilidad = pregunta.optString("VISIBILIDAD");
                String strCodigo = pregunta.optString("CODIGO");

                Transaction transaction;
                if (encuestaController.existPregunta(strId))
                    transaction = UPDATE;
                else
                    transaction = INSERT;

                encuestaController.putPregunta(transaction, strId, strDesc, strTipoCampo, strLonCampo,
                        strDepende, strObl, strIdModulo, strIdLista, strNomLista, strValor, strTipo,
                        strValidar, strEdadIni, strEdaFin, strGenero, strEstado, strOrden,
                        strVisibilidad, strCodigo);
            }

            // Guarda Listas
            vector = rsp.optJSONArray("LISTAS_REFERENCIA");
            cantidad = vector.length();
            if (cantidad > 0) publishProgress("1", "Guardando " + cantidad + " opciones de referencia");

            ListasRefController listasController = new ListasRefController(context);
            for (int i = 0; i < cantidad; i++) {
                JSONObject jsonLista = vector.getJSONObject(i);
                String strId = jsonLista.optString("ID_LISTA");
                String strPadre = jsonLista.optString("PADRE");
                String strDesc = jsonLista.optString("DESCRIPCION");
                String strCod = jsonLista.optString("CODLISTA");
                String strValor = jsonLista.optString("VALOR");
                String strEstado = jsonLista.optString("ESTADO");
                String strOrden = jsonLista.optString("ORDEN");

                Transaction transaction;
                if (listasController.existItemLista(strId)) {
                    publishProgress("1", "Actualizando opción de referencia " + i + " de " + cantidad);
                    transaction = UPDATE;
                } else {
                    publishProgress("1", "Agregando opción de referencia " + i + " de " + cantidad);
                    transaction = INSERT;
                }

                listasController.putItemLista(transaction, strId, strPadre, strDesc, strCod,
                        strValor, strEstado, strOrden);
            }

            // Guarda IPS
            vector = rsp.optJSONArray("IPS");
            cantidad = vector.length();
            if (cantidad > 0) publishProgress("1", "Guardando " + cantidad + " IPS's");

            IPSController ipsController = new IPSController(context);
            for (int i = 0; i < cantidad; i++) {
                JSONObject jsonIPS = vector.getJSONObject(i);
                String strId = jsonIPS.optString("ID");
                String strCodIns = jsonIPS.optString("COD_INS");
                String strNit = jsonIPS.optString("NIT");
                String strNombre = jsonIPS.optString("NOMBRE");
                String strDir = jsonIPS.optString("DIRECCION");
                String strPais = jsonIPS.optString("PAIS");
                String strDpto = jsonIPS.optString("DPTO");
                String strCiudad = jsonIPS.optString("CIUDAD");
                String strTel = jsonIPS.optString("TELEFONO");
                String strMovil = jsonIPS.optString("MOVIL");
                String strCorreo = jsonIPS.optString("EMAIL");
                String strRep = jsonIPS.optString("REPRESENTANTE");
                String strActivo = jsonIPS.optString("ACTIVO");

                Transaction transaction;
                if (ipsController.existIPS(strId)) {
                    publishProgress("1", "Actualizando IPS " + i + " de " + cantidad);
                    transaction = UPDATE;
                } else {
                    publishProgress("1", "Agregando IPS " + i + " de " + cantidad);
                    transaction = INSERT;
                }

                ipsController.putIPS(transaction, strId, strCodIns, strNit, strNombre, strDir,
                        strPais, strDpto, strCiudad, strTel, strMovil, strCorreo, strRep,
                        strActivo);

            }

            // Guarda Señales
            vector = rsp.optJSONArray("SENALES");
            cantidad = vector.length();
            if (cantidad > 0) publishProgress("1", "Guardando " + cantidad + " señales");

            SenalesController senalesController = new SenalesController(context);
            for (int i = 0; i < cantidad; i++) {
                JSONObject jsonSenal = vector.getJSONObject(i);
                String strId = jsonSenal.optString("ID_SENAL");
                String strDesc = jsonSenal.optString("DESCRIPCION");
                String strEstado = jsonSenal.optString("ESTADO");
                String strIdModulo = jsonSenal.optString("ID_MODULO");

                Transaction transaction;
                if (senalesController.existSenal(strId)) {
                    publishProgress("1", "Actualizando señal " + i + " de " + cantidad);
                    transaction = UPDATE;
                } else {
                    publishProgress("1", "Agregando señal " + i + " de " + cantidad);
                    transaction = INSERT;
                }

                senalesController.putSenal(transaction, strId, strDesc, strEstado, strIdModulo);
            }

            // Guarda Validaciones
            vector = rsp.optJSONArray("VALIDACIONES");
            cantidad = vector.length();
            if (cantidad > 0) publishProgress("1", "Guardando " + cantidad + " validaciones");

            RiesgoController riesgoController = new RiesgoController(context);
            for (int i = 0; i < cantidad; i++) {
                JSONObject jsonValidacion = vector.getJSONObject(i);
                String strId = jsonValidacion.optString("ID_SEMAFORO");
                String strDiabetes = jsonValidacion.optString("DIABETES");
                String strEdadMin = jsonValidacion.optString("EDADINI");
                String strEdadMax = jsonValidacion.optString("EDADFIN");
                String strSexo = jsonValidacion.optString("SEXO");
                String strTabaco = jsonValidacion.optString("TABACO");
                String strPASMin = jsonValidacion.optString("PASINI");
                String strPASMax = jsonValidacion.optString("PASFIN");
                String strCodigo = jsonValidacion.optString("CODIGO");
                String strEstado = jsonValidacion.optString("ESTADO");

                Transaction transaction;
                if (riesgoController.existValidacionCardio(strId)) {
                    publishProgress("1", "Actualizando validación " + i + " de " + cantidad);
                    transaction = UPDATE;
                } else {
                    publishProgress("1", "Agregando validación " + i + " de " + cantidad);
                    transaction = INSERT;
                }

                riesgoController.putValidacionCardio(transaction, strId, strDiabetes, strEdadMin,
                        strEdadMax, strSexo, strTabaco, strPASMin, strPASMax, strCodigo, strEstado);
            }

            // Guarda Tips
            vector = rsp.optJSONArray("TIPS");
            cantidad = vector.length();
            publishProgress("Guardando " + cantidad + " tips");
            TipsController tipsController = new TipsController(context);
            for (int i = 0; i < cantidad; i++) {
                JSONObject pregunta = vector.getJSONObject(i);
                String strId = pregunta.optString("ID_TIPS");
                String strDescripcion = pregunta.optString("DESCRIPCION");
                String strFechaIni = pregunta.optString("FECHAINI");
                String strFechaFin = pregunta.optString("FECHAFIN");
                String strEstado = pregunta.optString("ESTADO");

                Transaction transaction;
                if (tipsController.existTip(strId)) {
                    publishProgress("1", "Actualizando tip " + i + " de " + cantidad);
                    transaction = UPDATE;
                } else {
                    publishProgress("1", "Agregando tip " + i + " de " + cantidad);
                    transaction = INSERT;
                }

                tipsController.putTip(transaction, strId, strDescripcion, strFechaIni, strFechaFin,
                        strEstado);
            }

            // Guarda personas
            vector = rsp.optJSONArray("PERSONAS");
            cantidad = vector.length();
            if (cantidad > 0) publishProgress("1", "Guardando " + cantidad + " personas");

            PersonasController personasController = new PersonasController(context);
            for (int i = 0; i < cantidad; i++) {
                JSONObject jsonPersona = vector.getJSONObject(i);
                String strId = jsonPersona.optString("ID_USUARIO");
                String strApellido1  = jsonPersona.optString("APELLIDO1");
                String strApellido2 = jsonPersona.optString("APELLIDO2");
                String strNombre1 = jsonPersona.optString("NOMBRE1");
                String strNombre2 = jsonPersona.optString("NOMBRE2");
                String strTipoDoc = jsonPersona.optString("TIPODOC");
                String strDoc = jsonPersona.optString("DOCUMENTO");
                String strCarnet = jsonPersona.optString("CARNET");
                String strFechaNac = jsonPersona.optString("FECHANAC");
                String strSexo = jsonPersona.optString("SEXO");
                String strCelular = jsonPersona.optString("CELULAR");
                String strEmail = jsonPersona.optString("EMAIL");
                String strPesoNacer = jsonPersona.optString("PESONACER");
                String strTallaNacer = jsonPersona.optString("TALLANACER");
                String strDocMama = jsonPersona.optString("DOCMAMA");
                String strDocPapa = jsonPersona.optString("DOCPAPA");
                String strIdUltVisita = jsonPersona.optString("IDULTVISITA");
                String strFecUltVisita = jsonPersona.optString("FECULTVISITA");
                String strDireccion = jsonPersona.optString("DIRECCION");
                String strCodInst = jsonPersona.optString("CODINST");
                String strEstado = jsonPersona.optString("ESTADO");

                Transaction transaction;
                if (personasController.existPersona(strId)) {
                    publishProgress("1", "Actualizando persona " + i + " de " + cantidad);
                    transaction = UPDATE;
                } else {
                    publishProgress("1", "Agregando persona " + i + " de " + cantidad);
                    transaction = INSERT;
                }

                personasController.putPersona(transaction, strId, strApellido1, strApellido2,
                        strNombre1, strNombre2, strTipoDoc, strDoc, strCarnet, strFechaNac, strSexo,
                        strCelular, strEmail, strPesoNacer, strTallaNacer, strDocMama, strDocPapa,
                        strIdUltVisita, strFecUltVisita, strDireccion, strCodInst,
                        SituacionPersona.CONFIRMADA, strEstado);
            }

            // Guarda programaciones
            vector = rsp.optJSONArray("PROGRAMACION");
            cantidad = vector.length();
            if (cantidad > 0) publishProgress("1", "Guardando " + cantidad + " programaciones");

            ProgramacionController programacionController = new ProgramacionController(context);

            for (int i = 0; i < cantidad; i++) {
                JSONObject jsonProgramacion = vector.getJSONObject(i);
                String strId = jsonProgramacion.optString("ID_PROGRAMACION");
                String strEstado = jsonProgramacion.optString("ESTADO");

                Transaction transaction;
                if (programacionController.existProgramacion(strId)) {
                    if (strEstado.equals("I")) {
                        publishProgress("1", "Eliminando programación " + i + " de " + cantidad);
                        programacionController.deleteProgramacion(strId);
                        continue;
                    } else {
                        transaction = UPDATE;
                        publishProgress("1", "Actualizando programación " + i + " de " + cantidad);
                    }
                } else {
                    transaction = INSERT;
                    publishProgress("1", "Agregando programación " + i + " de " + cantidad);
                }

                String strCEB = jsonProgramacion.optString("CEB");
                String strDireccion1 = jsonProgramacion.optString("DIRECCION");
                String strDireccion2 = jsonProgramacion.optString("OTRADIR");
                String strTel1 = jsonProgramacion.optString("TELEFONO1");
                String strTel2 = jsonProgramacion.optString("TELEFONO2");
                String strLatitud = jsonProgramacion.optString("LATITUD");
                String strLongitud = jsonProgramacion.optString("LONGITUD");
                String strIdBarrio = jsonProgramacion.optString("ID_BARRIO");
                String strBarrio = jsonProgramacion.optString("BARRIO");

                programacionController.putProgramacion(transaction, strId, strCEB,
                        strDireccion1, strDireccion2, strTel1,
                        strTel2, strLatitud, strLongitud, strIdBarrio, strBarrio);

                JSONArray jsonPersonasProgramadas = jsonProgramacion.optJSONArray("PERSONAS");
                for (int j = 0; j < jsonPersonasProgramadas.length(); j++) {
                    JSONObject jsonPersonaProg = jsonPersonasProgramadas.optJSONObject(j);
                    String strIdUsuario = jsonPersonaProg.optString("ID_USUARIO");
                    String strMotivo = jsonPersonaProg.optString("MOTVISITA");
                    String strTipoVisita = jsonPersonaProg.optString("TIPOVISITA");
                    String strParentesco = jsonPersonaProg.optString("PARENTESCO");

                    if (programacionController.existDetalleProgramacion(strId, strIdUsuario)) {
                        transaction = UPDATE;
                        publishProgress("1", "Actualizando persona ID: " + strIdUsuario);
                    } else {
                        transaction = INSERT;
                        publishProgress("1", "Agregando persona ID: " + strIdUsuario);
                    }

                    programacionController.putDetalleProgramacion(transaction, strId, strIdUsuario,
                            strMotivo, strTipoVisita, strParentesco);
                }
            }

            publishProgress("1", "Descargando personas del municipio");
            try {

                String resURI = UtilidadesApp.getUrlServidor(context) + UtilidadesApp.getPersonasUpdates()
                        + controller.getToken() + "/" + timeLastUpdate + "/" +
                        new PersonasController(context).getMaxId();

                Log.d(TAG, resURI);
                HttpClient hc = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(resURI);

                HttpResponse hr = hc.execute(httpGet);
                is = hr.getEntity().getContent();

                int code = hr.getStatusLine().getStatusCode();
                if (code == 200) {
                    //UtilidadesApp.convertInputStreamToFile(is, getContext());

                    //InputStream inputStream = getContext().openFileInput("personas.json");
                    JsonReader jsonReader = new JsonReader(new InputStreamReader(is, "UTF-8"));
                    //JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
                    createFromJSONPersona(jsonReader);
                }
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
                return new String[]{"0", "Error actualizando personas: " + e.getLocalizedMessage()};
            }

            UtilidadesDB.BaseSincronizada(context, rsp.optString("FECHA_SYNC"));
            publishProgress("2", "Base de datos actualizada");
            return new String[]{"1", String.valueOf(rsp.getBoolean("NUEVA_VERSION"))};
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
            return new String[]{"0", "Error actualizando información: " + e.getLocalizedMessage()};
        }
    }

    public void createFromJSONPersona(JsonReader jsonReader) throws Exception {
        PersonasController personasController = new PersonasController(context);
        jsonReader.beginObject();
        while( jsonReader.hasNext() ) {
            final String innerName = jsonReader.nextName();
            final boolean isInnerNull = jsonReader.peek() == JsonToken.NULL;
            if( innerName.equals( "PERSONAS" ) && !isInnerNull ) {
                jsonReader.beginArray();
                while( jsonReader.hasNext() ) {
                    jsonReader.beginObject();
                    String strId = "";
                    String strApellido1  = "";
                    String strApellido2 = "";
                    String strNombre1 = "";
                    String strNombre2 = "";
                    String strTipoDoc = "";
                    String strDoc = "";
                    String strCarnet = "";
                    String strFechaNac = "";
                    String strSexo = "";
                    String strCelular = "";
                    String strEmail = "";
                    String strPesoNacer = "";
                    String strTallaNacer = "";
                    String strDocMama = "";
                    String strDocPapa = "";
                    String strIdUltVisita = "";
                    String strFecUltVisita = "";
                    String strCodInst = "";
                    String strEstado = "";

                    while( jsonReader.hasNext() ) {
                        final String innerInnerName = jsonReader.nextName();
                        final boolean isInnerInnerNull = jsonReader.peek() == JsonToken.NULL;
                        if( innerInnerName.equals( "ID_USUARIO" ) && !isInnerInnerNull ) {
                            strId = jsonReader.nextString();
                        } else if( innerInnerName.equals( "APELLIDO1" ) && !isInnerInnerNull ) {
                            strApellido1 = jsonReader.nextString();
                        } else if( innerInnerName.equals( "APELLIDO2" ) && !isInnerInnerNull ) {
                            strApellido2 = jsonReader.nextString();
                        } else if( innerInnerName.equals( "NOMBRE1" ) && !isInnerInnerNull ) {
                            strNombre1 = jsonReader.nextString();
                        } else if( innerInnerName.equals( "NOMBRE2" ) && !isInnerInnerNull ) {
                            strNombre2 = jsonReader.nextString();
                        } else if( innerInnerName.equals( "TIPODOC" ) && !isInnerInnerNull ) {
                            strTipoDoc = jsonReader.nextString();
                        } else if( innerInnerName.equals( "DOCUMENTO" ) && !isInnerInnerNull ) {
                            strDoc = jsonReader.nextString();
                        } else if( innerInnerName.equals( "CARNET" ) && !isInnerInnerNull ) {
                            strCarnet = jsonReader.nextString();
                        } else if( innerInnerName.equals( "FECHANAC" ) && !isInnerInnerNull ) {
                            strFechaNac = jsonReader.nextString();
                        } else if( innerInnerName.equals( "SEXO" ) && !isInnerInnerNull ) {
                            strSexo = jsonReader.nextString();
                        } else if( innerInnerName.equals( "CELULAR" ) && !isInnerInnerNull ) {
                            strCelular = jsonReader.nextString();
                        } else if( innerInnerName.equals( "EMAIL" ) && !isInnerInnerNull ) {
                            strEmail = jsonReader.nextString();
                        } else if( innerInnerName.equals( "PESONACER" ) && !isInnerInnerNull ) {
                            strPesoNacer = jsonReader.nextString();
                        } else if( innerInnerName.equals( "TALLANACER" ) && !isInnerInnerNull ) {
                            strTallaNacer = jsonReader.nextString();
                        } else if( innerInnerName.equals( "DOCMAMA" ) && !isInnerInnerNull ) {
                            strDocMama = jsonReader.nextString();
                        } else if( innerInnerName.equals( "DOCPAPA" ) && !isInnerInnerNull ) {
                            strDocPapa = jsonReader.nextString();
                        } else if( innerInnerName.equals( "IDULTVISITA" ) && !isInnerInnerNull ) {
                            strIdUltVisita = jsonReader.nextString();
                        } else if( innerInnerName.equals( "FECULTVISITA" ) && !isInnerInnerNull ) {
                            strFecUltVisita = jsonReader.nextString();
                        } else if( innerInnerName.equals( "CODINST" ) && !isInnerInnerNull ) {
                            strCodInst = jsonReader.nextString();
                        } else if( innerInnerName.equals( "ESTADO" ) && !isInnerInnerNull ) {
                            strEstado = jsonReader.nextString();
                        } else {
                            jsonReader.skipValue();
                        }
                    }

                    Transaction transaction;
                    if (personasController.existPersona(strId)) {
                        Log.d(TAG, "Actualizando persona ID: " + strId);
                        publishProgress("1", "Actualizando persona ID: " + strId);
                        transaction = UPDATE;
                    } else {
                        Log.d(TAG, "Guardando persona ID: " + strId);
                        publishProgress("1", "Guardando persona ID: " + strId);
                        transaction = INSERT;
                    }

                    personasController.putPersona(transaction, strId, strApellido1,
                            strApellido2, strNombre1, strNombre2, strTipoDoc, strDoc, strCarnet,
                            strFechaNac, strSexo, strCelular, strEmail, strPesoNacer, strTallaNacer,
                            strDocMama, strDocPapa, strIdUltVisita, strFecUltVisita, "", strCodInst,
                            SituacionPersona.CONFIRMADA, strEstado);
                    jsonReader.endObject();
                }
                jsonReader.endArray();
            } else if( innerName.equals( "ERROR" ) && !isInnerNull ) {
                throw new Exception("ERROR: " + jsonReader.nextString());
            } else jsonReader.skipValue();
        }
        jsonReader.endObject();
    }

    @Override
    protected void onPostExecute(String[] result) {
        if (result[0].equals("0")) {
            UtilidadesApp.showDialogAndFinish(context, "Error conexión web service",
                    result[1], false);
            if (fragment != null) fragment.dismiss();
        } else if (result[0].equals("1")) {

            try {
                if (result[1].equals("true")) {
                    Log.d(TAG, result[1]);
                    onProgressUpdate("1", "Hay una nueva versión de la aplicación. Por favor acepte" +
                            " la instalación en el cuadro que aparecerá en unos segundos");
                    ConfiguracionController controller = new ConfiguracionController(context);
                    new DownloadAPK(context).execute(UtilidadesApp.getUrlServidor(context) +
                            UtilidadesApp.getNewVersion() + controller.getToken());
                }
                ((OnNotificationListener) context).onGetUpdatesFinish();
            } catch (ClassCastException e) {

            }
        } else if (result[0].equals("2")) {

        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if (values[0].equals("0")) {
            if (fragment != null)
                fragment.showError(values[1]);
        } else if (values[0].equals("1")) {
            if (fragment != null)
                fragment.showMessage(values[1]);
        } else if (values[0].equals("2")) {
            if (fragment != null)
                fragment.showFinalDialog(values[1]);
        }
    }

    public interface OnNotificationListener {
        void notifyProgress(String notification);
        void onGetUpdatesFinish();
    }
}

package com.example.camaba1.camaba1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.camaba1.camaba1.adapter.Notificaciones_adapter;
import com.example.camaba1.camaba1.entidades.Notificaciones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

public class NotificacionService extends Service{
    RequestQueue rq;

    int EstadoMensaje;
    Button btn_prueba;

    MainActivity mainActivity = new MainActivity();

    @Override
    public void onCreate(){
        rq = Volley.newRequestQueue(this);
    }

    @Override
    public int onStartCommand(Intent intent,int flag, int idProcess){

        //  Toast.makeText(NotificacionService.this,"Servicio iniciado ", Toast.LENGTH_SHORT).show();
        //  ConsultaNotificaciones();
        time time = new time();
        time.execute();

        return START_STICKY;
    }

    @Override
    public  void onDestroy(){

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    //Consulta de notificaciones para camio de color de icono de campana

    private void ConsultaNotificaciones(){
        Notificaciones notificaciones = null;

        String url ="http://bigencode.com/ubot/notificaciones/listar_notificaciones.php?Mens_id_usu_recibe=17";

        JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String concepto;
                Notificaciones notificaciones = null;

                JSONArray json=response.optJSONArray("datos");

                try {

                    for (int i=0;i<json.length();i++){
                        notificaciones=new Notificaciones();
                        JSONObject jsonObject=null;
                        jsonObject=json.getJSONObject(i);

                        notificaciones.setMens_bandera_nueva_notificacion( jsonObject.optString( "Mens_bandera_nueva_notificacion" ) );

                    }

                    if(notificaciones.getMens_bandera_nueva_notificacion().equals( "0" )){
                        Drawable myIcon = getResources().getDrawable( R.drawable.ic_action_campananegra ); ColorFilter filter = new LightingColorFilter( Color.RED, Color.RED); myIcon.setColorFilter(filter);
                        Toast.makeText(getApplicationContext(), "Rojo ",Toast.LENGTH_SHORT).show();



                        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_action_campananegra);
                        int color = Color.parseColor("#d61313");
                        drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));



                    }
                    else {
                        Drawable myIcon = getResources().getDrawable( R.drawable.ic_action_campananegra ); ColorFilter filter = new LightingColorFilter( Color.GRAY, Color.GRAY); myIcon.setColorFilter(filter);
                        Toast.makeText(getApplicationContext(), "Gris ",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "No se ha podido establecer conexión con el servidor", Toast.LENGTH_SHORT).show();


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   Toast.makeText(NotificacionService.this,"No se pudo hacer la consulta", Toast.LENGTH_SHORT).show();


            }
        });
        rq.add(request);

    }


    public void hilo(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public class time extends AsyncTask<Void,Integer,Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

                hilo();


            return true;
        }


        public void ejecutarTiempo() {
            time time = new time();
            time.execute();

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            ejecutarTiempo();

            // line de codigo de instancia de la librería Volley


            ConsultaNotificaciones();
            //Toast.makeText(getApplicationContext(), "cada 5 segundos", Toast.LENGTH_SHORT).show();

        }
    }


}

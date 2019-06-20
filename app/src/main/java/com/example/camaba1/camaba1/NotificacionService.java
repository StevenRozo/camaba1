package com.example.camaba1.camaba1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificacionService extends Service{
    RequestQueue rq;

    int EstadoMensaje;

    MainActivity mainActivity = new MainActivity();

    @Override
    public void onCreate(){
        rq = Volley.newRequestQueue(this);



    }



    @Override
    public int onStartCommand(Intent intent,int flag, int idProcess){



      //  Toast.makeText(NotificacionService.this,"Servicio iniciado ", Toast.LENGTH_SHORT).show();

        ConsultaNotificaciones();
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



        String url ="http://bigencode.com/ubot/notificaciones/listar_notificaciones.php?Mens_id_usu_envia=8";

        JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray= response.getJSONArray("datos");

                    // Toast.makeText(MainActivity.this,"r: "+response, Toast.LENGTH_SHORT).show();

                    for(int i=0; i<jsonArray.length();i++){


                        EstadoMensaje = jsonArray.getJSONObject( i ).getInt( "Mens_estado_mensaje"  );

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(EstadoMensaje==1){
                    Toast.makeText(NotificacionService.this,"C: "+EstadoMensaje, Toast.LENGTH_SHORT).show();

                }
                else{
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
}

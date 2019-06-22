package com.example.camaba1.camaba1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;


public class fracment_responder_notificacion extends Fragment {

   // public static final String Token="Token";

    RequestQueue rq;

    private String TokenUsuariEnvia, IdUsuarioEnvia;

    TextView id_responder;
    Button cambaSi,cambaNo;

    public fracment_responder_notificacion() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rq = Volley.newRequestQueue(getContext());
       View view=  inflater.inflate( R.layout.fragment_fracment_responder_notificacion, container, false );

        id_responder =(TextView) view.findViewById( R.id.id_responder );
        cambaSi = (Button) view.findViewById( R.id.cambaSi );
        cambaNo = (Button) view.findViewById( R.id.cambaNo );

        //id_responder.setText( Token );

        if(getArguments() !=null){

            TokenUsuariEnvia = getArguments().getString("Token" );
            IdUsuarioEnvia = getArguments().getString("IdUsuariEnvia" );



        }

        cambaSi.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarTokenToServer("AceptocambalacheGlobal",TokenUsuariEnvia,IdUsuarioEnvia);

            }
        } );

        cambaNo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarTokenToServer("NograciasGlobal!",TokenUsuariEnvia,IdUsuarioEnvia);
            }
        } );


        return view;

    }

    private void enviarTokenToServer(final String Cuerpo, String TokenUsuariEnvia,String IdUsuarioEnviaD) {

        GuardarNotificacion("17",IdUsuarioEnviaD,Cuerpo,"1");

        StringRequest stringRequest = new StringRequest( Request.Method.POST, "http://bigencode.com/ubot/notificaciones/enviarNotificacion.php?token="+TokenUsuariEnvia, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText( getContext(), "Se envió el mensaje correctamente", Toast.LENGTH_LONG ).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( getContext(), "Error en la conexión", Toast.LENGTH_LONG ).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new Hashtable<String, String>(  );
                parametros.put( "Cuerpo",Cuerpo );

                return  parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
        requestQueue.add(stringRequest);
    }


    private void GuardarNotificacion(String mens_id_usu_envia,String mens_id_usu_recibe,String mens_mensaje_envio,String mens_estado_mensaje){

        StringRequest stringRequest = new StringRequest( Request.Method.POST, "http://bigencode.com/ubot/notificaciones/guardar_mensaje.php?mens_id_usu_envia="+mens_id_usu_envia+
                "&mens_id_usu_recibe="+mens_id_usu_recibe+"&mens_mensaje_envio="+mens_mensaje_envio+"&mens_estado_mensaje="+mens_estado_mensaje, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText( getContext(), "Se guradó el mensaje correctamente", Toast.LENGTH_LONG ).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( getContext(), "Error en la conexión", Toast.LENGTH_LONG ).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
        requestQueue.add(stringRequest);

    }





}

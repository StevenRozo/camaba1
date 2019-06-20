package com.example.camaba1.camaba1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
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
import com.example.camaba1.camaba1.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Response.ErrorListener, Response.Listener<JSONObject> {



    ArrayList<Notificaciones> listarNotificaciones;


    TextView txt_prue;

    RecyclerView RecyclerNotificaciones;

    TableRow esp_busqueda;

    RequestQueue rq;
  //  ArrayList<String> lista = new ArrayList<String>(  );

    ListView lista_resultado;
    String nombreProducto;

    // RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    int contador=1;

    public String banderColor="0";

    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );




        rq = Volley.newRequestQueue(this);


        //Drawable myIcon = getResources().getDrawable( R.drawable.ic_action_campananegra ); ColorFilter filter = new LightingColorFilter( Color.RED, Color.RED); myIcon.setColorFilter(filter);


        //instanciamiento del servicio NotificacionService

        Intent intent = new Intent(getApplicationContext(), NotificacionService.class);
        //Se inicia el servicio
        startService(intent);


        listarNotificaciones = new ArrayList<>(  );
        RecyclerNotificaciones =(RecyclerView) findViewById( R.id.RecyclerNotificaciones );

        esp_busqueda = (TableRow) findViewById( R.id.esp_busqueda );


        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );

       //txt_prue = (TextView) findViewById( R.id.txt_prue );
       // lista_resultado = (ListView) findViewById( R.id.lista_resultado );




    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_campana) {

            listarNotificaciones.clear();
            //txt_prue.setText( id);


           // ConsultaNotificaciones();
            ConsultaNotificaciones();
            contador=contador+1;

            if(contador%2!=0){
                RecyclerNotificaciones.setVisibility(View.INVISIBLE );
                esp_busqueda.setVisibility( View.VISIBLE );

            }
            else{
                RecyclerNotificaciones.setVisibility(View.VISIBLE );
                esp_busqueda.setVisibility( View.INVISIBLE );
            }

         //   Drawable myIcon = getResources().getDrawable( R.drawable.ic_action_campananegra ); ColorFilter filter = new LightingColorFilter( Color.GRAY, Color.GRAY); myIcon.setColorFilter(filter);

            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }






    //Consulta de notificaciones

   /*

    private void ConsultaNotificaciones(){

        String url ="http://bigencode.com/ubot/notificaciones/listar_notificaciones.php?Mens_id_usu_envia=8";

        JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray= response.getJSONArray("datos");

                   // Toast.makeText(MainActivity.this,"r: "+response, Toast.LENGTH_SHORT).show();

                    for(int i=0; i<jsonArray.length();i++){




                        // cont_prod.setProd_id(jsonObject.optString("prod_id"));
                        //

                        //  String nombre = jsonObject.getString( "prod_nombre" );
                        //  Toast.makeText(this, "P: "+nombre, Toast.LENGTH_SHORT).show();

                        //cont_prod.setProd_nombre(jsonObject.getString("prod_nombre"));

                        nombreProducto = jsonArray.getJSONObject( i ).getString( "Mens_mensaje_envio"  );


                        lista.add(nombreProducto);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cargarListView();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"No tienes notificaciones", Toast.LENGTH_SHORT).show();


            }
        });

        rq.add(request);

    }


    private void cargarListView(){

       // Toast.makeText(MainActivity.this,"t: "+lista, Toast.LENGTH_SHORT).show();
        //lista_resultado.setAdapter(null);


        ArrayAdapter<CharSequence> a= new ArrayAdapter(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,lista);
        lista_resultado.setAdapter(a);
    }
*/

    private void ConsultaNotificaciones(){

        dialog=new ProgressDialog(MainActivity.this);
        dialog.setMessage("Consultando Historial");
        dialog.show();


        String url="http://bigencode.com/ubot/notificaciones/listar_notificaciones.php?Mens_id_usu_envia=8";
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null, this,this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(MainActivity.this).addToRequestQueue(jsonObjectRequest);

    }

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

                notificaciones.setIdMensajeNotif( jsonObject.optString( "Mens_mensaje_envio" ) );

                listarNotificaciones.add(notificaciones);
            }
            dialog.hide();
            Notificaciones_adapter adapter=new Notificaciones_adapter(listarNotificaciones, getApplicationContext());
            adapter.notifyDataSetChanged();

            RecyclerNotificaciones =(RecyclerView) findViewById( R.id.RecyclerNotificaciones );
            RecyclerNotificaciones.setHasFixedSize( true );
            RecyclerNotificaciones.setLayoutManager( new LinearLayoutManager( this ) );
            RecyclerNotificaciones.setAdapter(adapter);

           // Toast.makeText(getApplicationContext(), "Mens: ", Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No se ha podido establecer conexión con el servidor", Toast.LENGTH_SHORT).show();

            dialog.hide();
        }

    }


    @Override
    public void onErrorResponse(VolleyError error) {


        Toast.makeText(MainActivity.this, "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        dialog.hide();
        Log.d("ERROR: ", error.toString());





    }


}